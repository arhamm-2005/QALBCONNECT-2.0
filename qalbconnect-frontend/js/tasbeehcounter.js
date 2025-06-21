// qalbconnect-frontend/js/tasbeehcounter.js
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
    const counterDisplay = document.getElementById('counterDisplay');
    const countButton = document.getElementById('countButton');
    const undoButton = document.getElementById('undoButton');
    const resetButton = document.getElementById('resetButton');

    const BACKEND_URL = 'http://localhost:8080/api/tasbeeh';
    const LOCAL_STORAGE_KEY = 'tasbeehCount';

    // Function to update the display
    function updateDisplay(value) {
        counterDisplay.textContent = value;
    }

    // Function to save count to Local Storage
    function saveCountToLocalStorage(count) {
        localStorage.setItem(LOCAL_STORAGE_KEY, count);
        console.log('Count saved to Local Storage:', count);
    }

    // Function to load count from Local Storage
    function loadCountFromLocalStorage() {
        const storedCount = localStorage.getItem(LOCAL_STORAGE_KEY);
        if (storedCount !== null) {
            return parseInt(storedCount, 10);
        }
        return 0; // Default if no value found
    }

    // Function to fetch current count from backend
    async function fetchCurrentCount() {
        try {
            const response = await fetch(`${BACKEND_URL}/current`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const currentBackendCount = await response.json();
            updateDisplay(currentBackendCount);
            saveCountToLocalStorage(currentBackendCount);
            console.log('Current count fetched from backend:', currentBackendCount);
        } catch (error) {
            console.error('Error fetching current count from backend:', error);
            // If backend is unavailable, load from local storage
            const localCount = loadCountFromLocalStorage();
            updateDisplay(localCount);
            console.log('Loaded count from local storage due to backend error:', localCount);
        }
    }

    // Event listeners for buttons
    countButton.addEventListener('click', async () => {
        try {
            const response = await fetch(`${BACKEND_URL}/count`, {
                method: 'POST'
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const newCount = await response.json();
            updateDisplay(newCount);
            saveCountToLocalStorage(newCount);
        } catch (error) {
            console.error('Error incrementing count:', error);
            alert('Failed to increment count. Please ensure the backend is running.');
            // Fallback: Increment local storage value
            let currentLocalCount = loadCountFromLocalStorage();
            currentLocalCount++;
            updateDisplay(currentLocalCount);
            saveCountToLocalStorage(currentLocalCount);
        }
    });

    undoButton.addEventListener('click', async () => {
        try {
            const response = await fetch(`${BACKEND_URL}/undo`, {
                method: 'POST'
            });
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const newCount = await response.json();
            updateDisplay(newCount);
            saveCountToLocalStorage(newCount);
        } catch (error) {
            console.error('Error decrementing count:', error);
            alert('Failed to decrement count. Please ensure the backend is running.');
            // Fallback: Decrement local storage value
            let currentLocalCount = loadCountFromLocalStorage();
            if (currentLocalCount > 0) {
                currentLocalCount--;
            }
            updateDisplay(currentLocalCount);
            saveCountToLocalStorage(currentLocalCount);
        }
    });

    resetButton.addEventListener('click', async () => {
        if (confirm('Are you sure you want to reset the counter?')) {
            try {
                const response = await fetch(`${BACKEND_URL}/reset`, {
                    method: 'POST'
                });
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const newCount = await response.json();
                updateDisplay(newCount);
                saveCountToLocalStorage(newCount);
            } catch (error) {
                console.error('Error resetting count:', error);
                alert('Failed to reset count. Please ensure the backend is running.');
                // Fallback: Reset local storage value
                updateDisplay(0);
                saveCountToLocalStorage(0);
            }
        }
    });

    // On page load, try to fetch current count from backend
    // If backend is down, it will load from local storage
    fetchCurrentCount();
});