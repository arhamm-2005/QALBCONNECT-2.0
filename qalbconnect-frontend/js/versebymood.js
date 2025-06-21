// [frontend-root]/js/versebymood.js
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
    const moodButtonsContainer = document.getElementById('mood-buttons');
    const verseDisplay = document.getElementById('verse-display');

    // Attach event listeners to mood buttons
    // Using event delegation on the container for efficiency
    moodButtonsContainer.addEventListener('click', (event) => {
        if (event.target.classList.contains('mood-button')) {
            const selectedMood = event.target.dataset.mood;
            displayVerseByMood(selectedMood);
        }
    });

    // Function to display verses based on the selected mood
    async function displayVerseByMood(mood) {
        verseDisplay.innerHTML = `<p>Loading verses for ${mood}...</p>`; // Loading message

        try {
            // Make a GET request to your Spring Boot backend API
            // The endpoint should fetch verses based on the 'mood' parameter
            const response = await fetch(`http://localhost:8080/api/versesbymood/${mood}`);

            if (!response.ok) {
                // Try to parse error message from backend if available
                const errorData = await response.json().catch(() => ({ message: 'Unknown error' }));
                throw new Error(`HTTP error! Status: ${response.status}. Message: ${errorData.message || response.statusText}`);
            }

            const versesData = await response.json();

            if (Array.isArray(versesData) && versesData.length > 0) {
                verseDisplay.innerHTML = `<h3>Verses for: ${mood}</h3>`;
                versesData.forEach(verse => {
                    // Check for null or empty strings before appending
                    if (verse.arabicText && verse.arabicText.trim() !== '') {
                        const arabicP = document.createElement('p');
                        arabicP.classList.add('arabic-text');
                        arabicP.textContent = verse.arabicText;
                        verseDisplay.appendChild(arabicP);
                    }
                    if (verse.englishTranslation && verse.englishTranslation.trim() !== '') {
                        const englishP = document.createElement('p');
                        englishP.classList.add('english-translation');
                        englishP.textContent = verse.englishTranslation;
                        verseDisplay.appendChild(englishP);
                    }
                    if (verse.reference && verse.reference.trim() !== '') {
                        const referenceP = document.createElement('p');
                        referenceP.classList.add('verse-reference');
                        referenceP.textContent = `(${verse.reference})`;
                        verseDisplay.appendChild(referenceP);
                    }
                    verseDisplay.appendChild(document.createElement('hr')); // Separator
                });
                // Remove the last HR if it exists
                if (verseDisplay.lastChild && verseDisplay.lastChild.tagName === 'HR') {
                     verseDisplay.removeChild(verseDisplay.lastChild);
                }

            } else {
                verseDisplay.innerHTML = `<p class="error-message">No verses found for mood: ${mood}.</p>`;
            }

        } catch (error) {
            console.error('Error fetching verses:', error);
            verseDisplay.innerHTML = `<p class="error-message">Failed to load verses for ${mood}. Error: ${error.message}. Please try again.</p>`;
        }
    }

    // Optional: Display a default mood's verses on initial load
    // For example, display "Guidance" verses initially
    // displayVerseByMood('Guidance');
});