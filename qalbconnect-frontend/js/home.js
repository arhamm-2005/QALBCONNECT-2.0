// qalbconnect-frontend/js/home.js

document.addEventListener('DOMContentLoaded', () => {
    
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
        },
        {
            quote: "And put your trust in Allah if you are believers indeed.",
            source: "Quran 5:23"
        },
        {
            quote: "Patience is a pillar of faith.",
            source: "Prophet Muhammad (PBUH)"
        }
    ];

    const randomQuoteElement = document.getElementById('randomQuote');
    let currentQuoteIndex = 0;

    function displayNextQuote() {
        if (randomQuoteElement) {
            const quoteData = islamicQuotes[currentQuoteIndex];
            // CORRECTED LINE BELOW: Using proper template literal syntax
            randomQuoteElement.textContent = `${quoteData.quote} - (${quoteData.source})`;
            currentQuoteIndex = (currentQuoteIndex + 1) % islamicQuotes.length; // Cycle through quotes
        }
    }

    // Display a quote immediately on load
    displayNextQuote();

    // Change quote every 10 seconds
    setInterval(displayNextQuote, 10000); // 10000 ms = 10 seconds

    // Placeholder for future logout functionality (e.g., clearing JWT token)
    // For now, the logout button directly redirects via onclick in home.html
    // When JWTs are implemented, this JS could handle the API call to invalidate the token.
});