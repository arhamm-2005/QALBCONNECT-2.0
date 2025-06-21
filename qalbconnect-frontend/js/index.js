// qalbconnect-frontend/js/index.js
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
    createParticles();
    const islamicQuotes = [
        {
            quote: "Indeed, Allah does not change the condition of a people until they change what is in themselves.",
            source: "Quran 13:11"
        },
        {
            quote: "The best among you are those who have the best character.",
            source: "Prophet Muhammad (PBUH)"
        },
        {
            quote: "Do not lose hope, nor be sad.",
            source: "Quran 3:139"
        },
        {
            quote: "Verily, with hardship comes ease.",
            source: "Quran 94:5"
        },
        {
            quote: "He who knows himself, knows his Lord.",
            source: "Imam Ali (AS)"
        },
        {
            quote: "The most beloved of deeds to Allah are those that are continuous, even if they are few.",
            source: "Prophet Muhammad (PBUH)"
        }
    ];

    const islamicQuoteElement = document.getElementById('islamicQuote');
    const quoteSourceElement = document.getElementById('quoteSource');
    let currentQuoteIndex = 0;

    function displayRandomQuote() {
        // Display quotes in order for now, can be randomized later
        const quoteData = islamicQuotes[currentQuoteIndex];
        islamicQuoteElement.textContent = quoteData.quote;
        quoteSourceElement.textContent = `- ${quoteData.source}`;
        currentQuoteIndex = (currentQuoteIndex + 1) % islamicQuotes.length; // Cycle through quotes
    }

    // Display a quote immediately on load
    displayRandomQuote();

    // Change quote every 10 seconds
    setInterval(displayRandomQuote, 10000); // 10000 ms = 10 seconds
});