// qalbconnect-frontend/js/register.js
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
    const registerForm = document.getElementById('registerForm');
    const messageDiv = document.getElementById('message'); // Ensure this element exists in register.html

    if (registerForm) {
        registerForm.addEventListener('submit', async (event) => {
            event.preventDefault(); // Prevent default form submission

            const username = registerForm.username.value;
            const email = registerForm.email.value;
            const password = registerForm.password.value;

            console.log('Attempting registration for:', username);
            displayMessage('Registering...', 'info'); // Show a loading message

            try {
                const response = await fetch('http://localhost:8080/api/auth/register', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({ username, email, password })
                });

                const data = await response.json(); // Parse the JSON response from the backend

                if (response.ok) { // Check if the HTTP status code is 2xx (success)
                    displayMessage(data.message, 'success');
                    console.log('Registration successful:', data);
                    // Optional: Redirect to login page after successful registration
                    setTimeout(() => {
                        window.location.href = 'login.html';
                    }, 2000); // Redirect after 2 seconds
                } else { // Handle errors (e.g., username/email already exists, bad request)
                    displayMessage(data.message || 'Registration failed. Please try again.', 'error');
                    console.error('Registration failed:', data);
                }
            } catch (error) {
                console.error('Error during registration:', error);
                displayMessage('Network error or server unavailable. Please try again later.', 'error');
            }
        });
    }

    // Helper function to display messages
    function displayMessage(msg, type) {
        if (messageDiv) {
            messageDiv.textContent = msg;
            // Remove existing type classes and add the new one
            messageDiv.className = `message ${type}`;
            messageDiv.style.display = 'block'; // Make it visible
        }
    }
});