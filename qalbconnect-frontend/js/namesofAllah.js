// qalbconnect-frontend/js/namesofAllah.js

document.addEventListener('DOMContentLoaded', () => {
    const namesContainer = document.getElementById('names-container');
    const backButtonLink = document.getElementById('backButtonLink'); // Get the back button link element

    // Function to fetch and display the names
    async function fetchNamesOfAllah() {
        try {
            // Make a GET request to your Spring Boot backend API
            // Assumes backend is running on localhost:8080 as discussed
            const response = await fetch('http://localhost:8080/api/namesofallah');

            // Check if the request was successful (status code 2xx)
            if (!response.ok) {
                // If not successful, throw an error with the status
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            // Parse the JSON response body into a JavaScript array of objects
            const names = await response.json();

            // Clear any placeholder content or previous names inside the container
            namesContainer.innerHTML = '';

            // Loop through each name received from the API
            names.forEach(name => {
                // Create a div element for each name card
                const nameCard = document.createElement('div');
                // Add the 'name-card' class for styling
                nameCard.classList.add('name-card');

                // Create a div for the Arabic name
                const arabicName = document.createElement('div');
                arabicName.classList.add('arabic'); // Add 'arabic' class for styling
                // Set the text content to the 'arabic' field from the backend object
                arabicName.textContent = name.arabic;

                // Create a div for the English meaning
                const englishMeaning = document.createElement('div');
                englishMeaning.classList.add('english'); // Add 'english' class for styling
                // Set the text content to the 'englishMeaning' field from the backend object
                englishMeaning.textContent = name.englishMeaning;

                // Append the Arabic and English divs to the name card
                nameCard.appendChild(arabicName);
                nameCard.appendChild(englishMeaning);

                // Append the completed name card to the main names container
                namesContainer.appendChild(nameCard);
            });

        } catch (error) {
            // Catch any errors during the fetch operation (e.g., network issues, server errors)
            console.error('Error fetching the 99 Names of Allah:', error);
            // Display an error message to the user
            namesContainer.innerHTML = '<p style="color: #ff6b6b; text-align: center;">Failed to load names. Please ensure the backend server is running and accessible. Error: ' + error.message + '</p>';
        }
    }

    // Call the function to fetch and display names when the page loads
    fetchNamesOfAllah();

    // You already have a simple HTML anchor for the back button,
    // so no complex JS event listener is strictly needed here unless you want
    // to add dynamic behavior or prevent default.
    // The current HTML: <a href="/home"><button class="back-button">&lt;&lt;&lt; Back to Home</button></a>
    // will navigate client-side directly.
    // If '/home' is a Spring MVC endpoint, it will correctly redirect.
    // If it's a static HTML file, ensure the path is correct (e.g., '../home.html' if in a subdirectory).
});