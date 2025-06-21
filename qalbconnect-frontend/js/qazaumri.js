// src/main/resources/static/js/qazaumri.js
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
    const backendBaseUrl = 'http://localhost:8080/api/qaza'; // Your Qaza API base URL

    // Get HTML elements
    const logoutButton = document.getElementById('logoutButton');
    const qazaCalculationForm = document.getElementById('qazaCalculationForm');
    const genderSelect = document.getElementById('gender');
    const femaleOnlyFields = document.getElementById('femaleOnlyFields');
    const calculationMessage = document.getElementById('calculationMessage');

    const qazaDisplaySection = document.querySelector('.qaza-display-section');
    const totalDaysCalculatedSpan = document.getElementById('totalDaysCalculated');
    const excludedPeriodDaysRow = document.getElementById('excludedPeriodDaysRow');
    const excludedPeriodDaysSpan = document.getElementById('excludedPeriodDays');

    const fajrCountSpan = document.getElementById('fajrCount');
    const zuhrCountSpan = document.getElementById('zuhrCount');
    const asrCountSpan = document.getElementById('asrCount');
    const maghribCountSpan = document.getElementById('maghribCount');
    const ishaCountSpan = document.getElementById('ishaCount');
    const witrCountSpan = document.getElementById('witrCount');
    const adjustmentMessage = document.getElementById('adjustmentMessage');

    const prayerList = document.getElementById('prayerList'); // Parent for adjustment buttons

    let authToken = localStorage.getItem('jwtToken'); // Retrieve token from localStorage

    // --- Authentication Check ---
    function checkAuthAndRedirect() {
        if (!authToken) {
            alert('You are not logged in. Please log in to access this feature.');
            window.location.href = 'login.html'; // Redirect to login page
        }
    }
    checkAuthAndRedirect(); // Run on page load

    // --- Helper for displaying messages ---
    function displayMessage(element, msg, type) {
        if (element) {
            element.textContent = msg;
            element.className = `message ${type}`; // 'info', 'success', 'error'
            element.style.display = 'block';
            setTimeout(() => {
                element.style.display = 'none';
            }, 5000); // Hide message after 5 seconds
        }
    }

    // --- Logout Functionality (if not in common.js) ---
    if (logoutButton) {
        logoutButton.addEventListener('click', () => {
            localStorage.removeItem('token'); // Clear token
            localStorage.removeItem('userId'); // Clear userId
            window.location.href = 'login.html'; // Redirect to login
        });
    }

    // --- Toggle Female Specific Fields ---
    genderSelect.addEventListener('change', () => {
        if (genderSelect.value === 'female') {
            femaleOnlyFields.style.display = 'block';
            // Make these fields required when visible
            femaleOnlyFields.querySelectorAll('input').forEach(input => input.setAttribute('required', 'required'));
        } else {
            femaleOnlyFields.style.display = 'none';
            // Remove required attribute when hidden
            femaleOnlyFields.querySelectorAll('input').forEach(input => input.removeAttribute('required'));
        }
    });

    // --- Fetch Initial Qaza Prayer Data on Page Load ---
    async function fetchUserQazaData() {
        if (!authToken) return; // Should be caught by checkAuthAndRedirect, but good defensive coding

        try {
            const response = await fetch(`${backendBaseUrl}/my-prayers`, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'Content-Type': 'application/json'
                }
            });

            if (response.ok) {
                const data = await response.json();
                updateQazaDisplay(data);
                qazaDisplaySection.style.display = 'block'; // Show display section if data is found
            } else if (response.status === 404) {
                // No record found, keep display section hidden, form visible
                displayMessage(calculationMessage, 'No previous Qaza record found. Please calculate.', 'info');
                qazaDisplaySection.style.display = 'none';
            } else if (response.status === 401) {
                displayMessage(calculationMessage, 'Unauthorized. Please log in again.', 'error');
                localStorage.removeItem('token');
                setTimeout(() => window.location.href = 'login.html', 1500);
            } else {
                const errorData = await response.json();
                displayMessage(calculationMessage, errorData.message || 'Failed to fetch Qaza data.', 'error');
            }
        } catch (error) {
            console.error('Error fetching Qaza data:', error);
            displayMessage(calculationMessage, 'Network error. Could not connect to backend.', 'error');
        }
    }

    fetchUserQazaData(); // Call on DOMContentLoaded

    // --- Handle Qaza Calculation Form Submission ---
    qazaCalculationForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        displayMessage(calculationMessage, 'Calculating...', 'info');

        const formData = new FormData(qazaCalculationForm);
        const data = {
            gender: formData.get('gender'),
            balighDate: formData.get('balighDate'),
            endDate: formData.get('endDate')
        };

        if (data.gender === 'female') {
            data.periodDays = parseInt(formData.get('periodDays'));
            data.monthlyCyclesMissed = parseInt(formData.get('monthlyCyclesMissed'));
        } else {
            // Ensure female-specific fields are not sent for male users or set to default
            data.periodDays = 0;
            data.monthlyCyclesMissed = 0;
        }

        // Convert date format from YYYY-MM-DD (HTML date input) to DD-MM-YYYY (Backend expectation)
        const convertDateFormat = (dateString) => {
            if (!dateString) return '';
            const [year, month, day] = dateString.split('-');
            return `${day}-${month}-${year}`;
        };

        data.balighDate = convertDateFormat(data.balighDate);
        data.endDate = convertDateFormat(data.endDate);

        try {
            const response = await fetch(`${backendBaseUrl}/calculate`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${authToken}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });

            if (response.ok) {
                const result = await response.json();
                updateQazaDisplay(result);
                qazaDisplaySection.style.display = 'block'; // Show display section
                displayMessage(calculationMessage, 'Qaza calculation successful!', 'success');
            } else if (response.status === 401) {
                displayMessage(calculationMessage, 'Unauthorized. Please log in again.', 'error');
                localStorage.removeItem('token');
                setTimeout(() => window.location.href = 'login.html', 1500);
            } else {
                const errorData = await response.json();
                displayMessage(calculationMessage, errorData.message || 'Calculation failed.', 'error');
            }
        } catch (error) {
            console.error('Error during Qaza calculation:', error);
            displayMessage(calculationMessage, 'Network error. Could not connect to backend.', 'error');
        }
    });

    // --- Handle Prayer Adjustment Buttons ---
    prayerList.addEventListener('click', async (event) => {
        const target = event.target;
        if (target.classList.contains('adjust-btn')) {
            const prayerName = target.dataset.prayer;
            const adjustmentType = target.textContent; // "+" or "-"
            const adjustmentValue = (adjustmentType === '+') ? 1 : -1;

            displayMessage(adjustmentMessage, `Adjusting ${prayerName}...`, 'info');

            try {
                const response = await fetch(`${backendBaseUrl}/adjust`, {
                    method: 'POST',
                    headers: {
                        'Authorization': `Bearer ${authToken}`,
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        prayerName: prayerName,
                        adjustmentValue: adjustmentValue
                    })
                });

                if (response.ok) {
                    const updatedQaza = await response.json();
                    updateQazaDisplay(updatedQaza); // Update displayed counts
                    displayMessage(adjustmentMessage, `${prayerName} adjusted successfully!`, 'success');
                } else if (response.status === 401) {
                    displayMessage(adjustmentMessage, 'Unauthorized. Please log in again.', 'error');
                    localStorage.removeItem('token');
                    setTimeout(() => window.location.href = 'login.html', 1500);
                } else {
                    const errorData = await response.json();
                    displayMessage(adjustmentMessage, errorData.message || 'Adjustment failed.', 'error');
                }
            } catch (error) {
                console.error('Error during Qaza adjustment:', error);
                displayMessage(adjustmentMessage, 'Network error. Could not connect to backend.', 'error');
            }
        }
    });

    // --- Function to update the HTML display with Qaza data ---
    function updateQazaDisplay(qazaData) {
        totalDaysCalculatedSpan.textContent = qazaData.totalDaysCalculated;

        if (qazaData.gender && qazaData.gender.toLowerCase() === 'female') {
            excludedPeriodDaysRow.style.display = 'block';
            excludedPeriodDaysSpan.textContent = qazaData.excludedPeriodDays;
        } else {
            excludedPeriodDaysRow.style.display = 'none';
        }

        fajrCountSpan.textContent = qazaData.fajr;
        zuhrCountSpan.textContent = qazaData.zuhr;
        asrCountSpan.textContent = qazaData.asr;
        maghribCountSpan.textContent = qazaData.maghrib;
        ishaCountSpan.textContent = qazaData.isha;
        witrCountSpan.textContent = qazaData.witr;
    }

    // --- Common JavaScript (e.g., for logout, which you mentioned exists) ---
    // You can move shared functions like displayMessage or logout handler to a common.js
    // if they are used across multiple pages. For now, they are self-contained here.
});