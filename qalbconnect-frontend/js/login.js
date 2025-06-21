function createParticles() {
            const particlesContainer = document.getElementById('particles');
            const particleCount = 50;

            for (let i = 0; i < particleCount; i++) {
                const particle = document.createElement('div');
                particle.className = 'particle';
                particle.style.left = Math.random() * 100 + '%';
                particle.style.top = Math.random() * 100 + '%';
                particle.style.animationDelay = Math.random() * 15 + 's';
                particle.style.animationDuration = (Math.random() * 10 + 10) + 's';
                particlesContainer.appendChild(particle);
            }
        }

document.addEventListener('DOMContentLoaded', () => {
    createParticles(); // Initialize particles on page load
    const loginForm = document.getElementById('loginForm');
    const messageDiv = document.getElementById('message'); // Ensure you have a div with id="message" in your login.html

    // Helper function to display messages
    function displayMessage(msg, type) {
        if (messageDiv) {
            messageDiv.textContent = msg;
            messageDiv.className = `message ${type}`; // 'info', 'success', 'error'
            messageDiv.style.display = 'block';
            // Optional: Hide message after a few seconds
            setTimeout(() => {
                messageDiv.style.display = 'none';
            }, 5000);
        }
    }

    if (loginForm) {
        // Attach the submit event listener to the login form
        loginForm.addEventListener('submit', async (event) => {
            event.preventDefault(); // Prevent default form submission (page reload)

            const username = loginForm.username.value; // Access input by name (assuming name="username")
            const password = loginForm.password.value; // Access input by name (assuming name="password")

            console.log('Attempting login for:', username);
            displayMessage('Logging in...', 'info');

            try {
                const response = await fetch('http://localhost:8080/api/auth/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ username, password })
                });

                const data = await response.json(); // Parse the JSON response from the backend

                if (response.ok) {
                    // Login was successful!
                    if (data.token) {
                        //  const data = await response.json();
                        // Store the JWT token and other user data in localStorage
                        localStorage.setItem('jwtToken', data.token);
                        localStorage.setItem('username', data.username);
                        localStorage.setItem('userId', data.userId);

                        displayMessage(data.message || 'Login successful!', 'success');
                        console.log('Login successful! Token and user data stored.');

                        // Redirect the user to the home page after a slight delay for message to be seen
                        setTimeout(() => {
                            window.location.href = 'home.html'; // <<< THIS IS YOUR HOME PAGE
                        }, 1000); // Redirect after 1 second
                    } else {
                        // This case should ideally not happen if backend always sends a token on success
                        console.error('Login successful, but no token received in the response!');
                        displayMessage('Login successful, but an issue occurred. Please try again.', 'error');
                    }
                } else {
                    // Login failed (e.g., invalid credentials, 401 Unauthorized, 400 Bad Request)
                    console.error('Login failed:', data.message);
                    displayMessage(data.message || 'Login failed. Invalid username or password.', 'error');
                }
            } catch (error) {
                // Handle network errors or other exceptions (e.g., server not running)
                console.error('Error during login:', error);
                displayMessage('Network error or server unavailable. Please try again later.', 'error');
            }
        });
    } else {
        console.error('Login form with ID "loginForm" not found in the DOM.');
    }
});