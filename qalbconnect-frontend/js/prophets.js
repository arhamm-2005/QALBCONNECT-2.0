// [frontend-root]/js/prophets.js
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
    createParticles(); // Call the particle creation function
    // Corrected to match the HTML ID for the grid container
    const prophetButtonsContainer = document.getElementById('prophet-buttons');
    // Corrected to match the HTML ID for the story display area
    const storyDisplay = document.getElementById('story-display');
    let prophetStoriesData = []; // To store fetched stories

    // Function to fetch prophet stories from the backend
    async function fetchProphetStories() {
        try {
            // Ensure this URL is correct for your backend API endpoint
            const response = await fetch('http://localhost:8080/api/prophetstories');
            if (!response.ok) {
                // If response is not OK, try to read the error message from the backend
                const errorData = await response.json().catch(() => ({ message: 'Unknown error' }));
                throw new Error(`HTTP error! Status: ${response.status}. Message: ${errorData.message || response.statusText}`);
            }
            prophetStoriesData = await response.json();

            // Check if data is an array and not empty
            if (Array.isArray(prophetStoriesData) && prophetStoriesData.length > 0) {
                renderProphetButtons(prophetStoriesData);
            } else {
                prophetButtonsContainer.innerHTML = '<p class="error-message">No prophet stories found.</p>';
                storyDisplay.innerHTML = '<p class="error-message">No prophet stories available to display.</p>';
            }

        } catch (error) {
            console.error('Error fetching prophet stories:', error);
            // Display a user-friendly error message on the page
            prophetButtonsContainer.innerHTML = '<p class="error-message">Failed to load prophet list. Please try again later.</p>';
            storyDisplay.innerHTML = `<p class="error-message">Error: ${error.message || 'Failed to load prophet stories. Check your network and server.'}</p>`;
        }
    }

    // Function to render buttons for each prophet
    function renderProphetButtons(stories) {
        prophetButtonsContainer.innerHTML = ''; // Clear existing "Loading prophets..." message

        // Sort stories alphabetically by prophet name before rendering buttons
        stories.sort((a, b) => a.name.localeCompare(b.name));

        stories.forEach(story => {
            const button = document.createElement('button');
            button.classList.add('prophet-button'); // This class is styled in prophets.css
            button.textContent = story.name;
            button.dataset.prophetName = story.name; // Store prophet name for easy lookup
            button.addEventListener('click', () => displayProphetStory(story.name));
            prophetButtonsContainer.appendChild(button);
        });
    }

    // Function to display the story of the selected prophet
    function displayProphetStory(prophetName) {
        const selectedStory = prophetStoriesData.find(story => story.name === prophetName);

        if (selectedStory) {
            storyDisplay.innerHTML = `<h3>${selectedStory.name}</h3>`; // Use h3 for story title
            // Split story by newline characters to create paragraphs
            selectedStory.segments.forEach(segment => {
                if (segment.trim() !== '') { // Only create paragraph for non-empty segments
                    const p = document.createElement('p');
                    p.textContent = segment;
                    storyDisplay.appendChild(p);
                }
            });
        } else {
            storyDisplay.innerHTML = `<p class="error-message">Story for ${prophetName} not found.</p>`;
        }
    }

    // Initial fetch of stories when the page loads
    fetchProphetStories();
});