// qalbconnect-frontend/js/qazaCalculator.js

document.addEventListener('DOMContentLoaded', () => {
    const API_BASE_URL = 'http://localhost:8080/api/qaza'; // Adjust if your backend is on a different host/port

    // DOM Elements
    const statusMessageDiv = document.getElementById('statusMessage');
    const qazaCalculatorForm = document.getElementById('qazaCalculatorForm');
    const genderMale = document.getElementById('genderMale');
    const genderFemale = document.getElementById('genderFemale');
    const femaleDetailsDiv = document.getElementById('femaleDetails');
    const averagePeriodDaysInput = document.getElementById('averagePeriodDays');
    const totalMonthlyCyclesInput = document.getElementById('totalMonthlyCycles');
    const excludedPeriodDaysDisplay = document.getElementById('excludedPeriodDaysDisplay');

    const resultsSection = document.getElementById('results');
    const calcTimestampSpan = document.getElementById('calcTimestamp');
    const calcPeriodSpan = document.getElementById('calcPeriod');
    const calcGenderSpan = document.getElementById('calcGender');
    const totalCalendarDaysSpan = document.getElementById('totalCalendarDays');
    const femaleCalcDetailsDiv = document.getElementById('femaleCalcDetails');
    const avgPeriodDaysSpan = document.getElementById('avgPeriodDays');
    const totalMonthlyCyclesDisplaySpan = document.getElementById('totalMonthlyCyclesDisplay');
    const excludedPeriodDaysResultSpan = document.getElementById('excludedPeriodDaysResult');
    const finalMissedDaysSpan = document.getElementById('finalMissedDays');
    const fajrCountSpan = document.getElementById('fajrCount');
    const zuhrCountSpan = document.getElementById('zuhrCount');
    const asrCountSpan = document.getElementById('asrCount');
    const maghribCountSpan = document.getElementById('maghribCount');
    const ishaCountSpan = document.getElementById('ishaCount');
    const witrCountSpan = document.getElementById('witrCount');
    const totalRemainingPrayersSpan = document.getElementById('totalRemainingPrayers');

    const updateSection = document.querySelector('.update-section');
    const updateQazaForm = document.getElementById('updateQazaForm');
    const addMissedSection = document.querySelector('.add-missed-section');
    const addMissedForm = document.getElementById('addMissedForm');
    const historySection = document.querySelector('.history-section');
    const historyTableBody = document.getElementById('historyTableBody');
    const emptyHistoryMessage = document.getElementById('emptyHistoryMessage');

    // --- Helper Functions ---

    /**
     * Retrieves the JWT token from localStorage.
     * @returns {string | null} The JWT token or null if not found.
     */
    function getJwtToken() {
        return localStorage.getItem('jwtToken');
    }

    /**
     * Displays a status message to the user.
     * @param {string} message The message to display.
     * @param {boolean} isError True if it's an error message, false otherwise.
     */
    function showStatusMessage(message, isError = false) {
        statusMessageDiv.textContent = message;
        statusMessageDiv.className = isError ? 'status-message error' : 'status-message success';
        statusMessageDiv.style.display = 'block';
        statusMessageDiv.classList.remove('fade-out');

        // Automatically hide after 5 seconds
        setTimeout(() => {
            statusMessageDiv.classList.add('fade-out');
            setTimeout(() => {
                statusMessageDiv.style.display = 'none';
            }, 500); // Wait for fade-out transition to complete
        }, 5000);
    }

    /**
     * Toggles the visibility and required attributes of female-specific input fields.
     */
    function toggleFemaleDetails() {
        const isFemale = genderFemale.checked;
        femaleDetailsDiv.style.display = isFemale ? 'block' : 'none';

        femaleDetailsDiv.querySelectorAll('input').forEach(input => {
            if (isFemale) {
                input.setAttribute('required', 'required');
            } else {
                input.removeAttribute('required');
                input.value = ''; // Clear values when hidden
            }
        });
        updateExcludedPeriodDaysDisplay(); // Update display immediately
    }

    /**
     * Updates the calculated excluded period days display in the form.
     */
    function updateExcludedPeriodDaysDisplay() {
        if (genderFemale.checked) {
            const avgDays = parseInt(averagePeriodDaysInput.value) || 0;
            const totalCycles = parseInt(totalMonthlyCyclesInput.value) || 0;
            const excluded = avgDays * totalCycles;
            excludedPeriodDaysDisplay.textContent = excluded;
        } else {
            excludedPeriodDaysDisplay.textContent = 'N/A';
        }
    }

    // --- Data Fetching and UI Population ---

    /**
     * Fetches the latest Qaza prayer counts and updates the #results section.
     */
    async function fetchLatestQazaCounts() {
        const token = getJwtToken();
        if (!token) {
            showStatusMessage('You are not logged in. Please log in to view your Qaza counts.', true);
            hideAllDynamicSections();
            return;
        }

        try {
            const response = await fetch(`${API_BASE_URL}/latest`, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            if (!response.ok) {
                if (response.status === 404) {
                    showStatusMessage('No existing Qaza prayer entries found for you. Please calculate first.', false);
                    resultsSection.style.display = 'none';
                    updateSection.style.display = 'none';
                    addMissedSection.style.display = 'none';
                    return; // Exit as there's no data to display
                } else if (response.status === 403 || response.status === 401) {
                    showStatusMessage('Access denied. Please log in again.', true);
                    // Optionally redirect to login page or clear token
                    // localStorage.removeItem('jwtToken');
                    hideAllDynamicSections();
                    return;
                }
                const errorData = await response.json();
                throw new Error(errorData.statusMessage || `HTTP error! status: ${response.status}`);
            }
            const data = await response.json();
            populateResultsSection(data);
            showSectionsAfterData(); // Show related sections if data is present
        } catch (error) {
            console.error('Error fetching latest Qaza counts:', error);
            showStatusMessage(`Failed to load latest Qaza counts: ${error.message}`, true);
        }
    }

    /**
     * Fetches the Qaza prayer history and populates the history table.
     */
    async function fetchQazaHistory() {
        const token = getJwtToken();
        if (!token) {
            // Message handled by fetchLatestQazaCounts or login page.
            // history will simply not load if no token.
            historySection.style.display = 'none';
            emptyHistoryMessage.style.display = 'block';
            return;
        }

        try {
            const response = await fetch(`${API_BASE_URL}/history`, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            if (!response.ok) {
                if (response.status === 404) {
                    historySection.style.display = 'none';
                    emptyHistoryMessage.style.display = 'block';
                    return; // No history yet, so hide table and show message
                } else if (response.status === 403 || response.status === 401) {
                    // Handled by fetchLatestQazaCounts if it runs first
                    historySection.style.display = 'none';
                    emptyHistoryMessage.style.display = 'block';
                    return;
                }
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const historyData = await response.json();
            populateHistoryTable(historyData);
        } catch (error) {
            console.error('Error fetching Qaza history:', error);
            showStatusMessage(`Failed to load Qaza history: ${error.message}`, true);
        }
    }

    /**
     * Populates the main results display section with data.
     * @param {object} data The QazaCalculatorResponseDto object.
     */
    function populateResultsSection(data) {
        if (!data || data.totalRemainingPrayers === undefined) {
             resultsSection.style.display = 'none';
             updateSection.style.display = 'none';
             addMissedSection.style.display = 'none';
             return;
        }
        calcTimestampSpan.textContent = data.calculationTimestampString || 'N/A';
        calcPeriodSpan.textContent = `${data.startDateString || 'N/A'} to ${data.endDateString || 'N/A'}`;
        calcGenderSpan.textContent = data.gender || 'N/A';
        totalCalendarDaysSpan.textContent = data.totalCalendarDays !== undefined ? data.totalCalendarDays : 'N/A';

        if (data.gender && data.gender.toLowerCase() === 'female') {
            femaleCalcDetailsDiv.style.display = 'block';
            avgPeriodDaysSpan.textContent = data.averagePeriodDays !== undefined ? data.averagePeriodDays : 'N/A';
            totalMonthlyCyclesDisplaySpan.textContent = data.totalMonthlyCycles !== undefined ? data.totalMonthlyCycles : 'N/A';
            excludedPeriodDaysResultSpan.textContent = data.excludedPeriodDays !== undefined ? data.excludedPeriodDays : 'N/A';
        } else {
            femaleCalcDetailsDiv.style.display = 'none';
        }
        finalMissedDaysSpan.textContent = data.finalMissedDaysForCalculation !== undefined ? data.finalMissedDaysForCalculation : 'N/A';

        fajrCountSpan.textContent = data.fajrCount !== undefined ? data.fajrCount : 0;
        zuhrCountSpan.textContent = data.zuhrCount !== undefined ? data.zuhrCount : 0;
        asrCountSpan.textContent = data.asrCount !== undefined ? data.asrCount : 0;
        maghribCountSpan.textContent = data.maghribCount !== undefined ? data.maghribCount : 0;
        ishaCountSpan.textContent = data.ishaCount !== undefined ? data.ishaCount : 0;
        witrCountSpan.textContent = data.witrCount !== undefined ? data.witrCount : 0;
        totalRemainingPrayersSpan.textContent = data.totalRemainingPrayers !== undefined ? data.totalRemainingPrayers : 0;

        resultsSection.style.display = 'block';
    }

    /**
     * Populates the history table with an array of QazaCalculatorResponseDto objects.
     * @param {Array<object>} historyData An array of history entries.
     */
    function populateHistoryTable(historyData) {
        historyTableBody.innerHTML = ''; // Clear previous entries
        if (historyData && historyData.length > 0) {
            historyData.forEach(entry => {
                const row = historyTableBody.insertRow();
                row.insertCell().textContent = entry.calculationTimestampString || 'N/A';
                row.insertCell().textContent = `${entry.startDateString || 'N/A'} to ${entry.endDateString || 'N/A'}`;
                row.insertCell().textContent = entry.totalCalendarDays !== undefined ? entry.totalCalendarDays : 'N/A';
                row.insertCell().textContent = entry.gender || 'N/A';
                row.insertCell().textContent = entry.averagePeriodDays !== undefined ? entry.averagePeriodDays : 'N/A';
                row.insertCell().textContent = entry.totalMonthlyCycles !== undefined ? entry.totalMonthlyCycles : 'N/A';
                row.insertCell().textContent = entry.excludedPeriodDays !== undefined ? entry.excludedPeriodDays : 'N/A';
                row.insertCell().textContent = entry.finalMissedDaysForCalculation !== undefined ? entry.finalMissedDaysForCalculation : 'N/A';

                const prayerCountsCell = row.insertCell();
                prayerCountsCell.innerHTML = `
                    <div class="prayer-counts-grid">
                        <span>Fajr: <strong>${entry.fajrCount !== undefined ? entry.fajrCount : 0}</strong></span>
                        <span>Zuhr: <strong>${entry.zuhrCount !== undefined ? entry.zuhrCount : 0}</strong></span>
                        <span>Asr: <strong>${entry.asrCount !== undefined ? entry.asrCount : 0}</strong></span>
                        <span>Maghrib: <strong>${entry.maghribCount !== undefined ? entry.maghribCount : 0}</strong></span>
                        <span>Isha: <strong>${entry.ishaCount !== undefined ? entry.ishaCount : 0}</strong></span>
                        <span>Witr: <strong>${entry.witrCount !== undefined ? entry.witrCount : 0}</strong></span>
                    </div>
                `;
                row.insertCell().textContent = entry.totalRemainingPrayers !== undefined ? entry.totalRemainingPrayers : 0;
            });
            historySection.style.display = 'block';
            emptyHistoryMessage.style.display = 'none';
        } else {
            historySection.style.display = 'none';
            emptyHistoryMessage.style.display = 'block';
        }
    }

    /**
     * Shows the update, add, and history sections if there is data.
     */
    function showSectionsAfterData() {
        updateSection.style.display = 'block';
        addMissedSection.style.display = 'block';
        // history section display is handled by populateHistoryTable
    }

    /**
     * Hides sections if no data is present, primarily on initial load or clear.
     */
    function hideAllDynamicSections() {
        resultsSection.style.display = 'none';
        updateSection.style.display = 'none';
        addMissedSection.style.display = 'none';
        historySection.style.display = 'none';
        emptyHistoryMessage.style.display = 'block';
    }


    // --- Form Submission Handlers ---

    // Handle calculate form submission
    qazaCalculatorForm.addEventListener('submit', async (event) => {
        event.preventDefault(); // Prevent default form submission

        // Basic client-side validation (can be enhanced)
        const startDate = document.getElementById('dateFrom').value;
        const endDate = document.getElementById('dateTo').value;
        const gender = document.querySelector('input[name="gender"]:checked');

        if (!startDate || !endDate || !gender) {
            showStatusMessage('Please fill in all required fields.', true);
            return;
        }
        if (new Date(endDate) < new Date(startDate)) {
            showStatusMessage('End date cannot be before start date.', true);
            return;
        }

        const requestBody = {
            startDate: startDate,
            endDate: endDate,
            gender: gender.value,
            averagePeriodDays: gender.value === 'female' ? parseInt(averagePeriodDaysInput.value) : null,
            totalMonthlyCycles: gender.value === 'female' ? parseInt(totalMonthlyCyclesInput.value) : null
        };

        const token = getJwtToken();
        if (!token) {
            showStatusMessage('You must be logged in to perform a calculation.', true);
            return;
        }

        try {
            const response = await fetch(`${API_BASE_URL}/calculate`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}` // Add Authorization header here
                },
                body: JSON.stringify(requestBody)
            });

            const data = await response.json();

            if (response.ok) {
                showStatusMessage(data.statusMessage || 'Calculation successful!', false);
                // After successful calculation, refresh the latest counts and history
                await fetchLatestQazaCounts();
                await fetchQazaHistory();
            } else {
                showStatusMessage(data.statusMessage || 'Calculation failed!', true);
            }
        } catch (error) {
            console.error('Error during Qaza calculation:', error);
            showStatusMessage(`An error occurred: ${error.message}`, true);
        }
    });

    // Handle update (prayed) form submission
    updateQazaForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        const requestBody = {
            fajrPrayed: parseInt(document.getElementById('fajrPrayed').value) || 0,
            zuhrPrayed: parseInt(document.getElementById('zuhrPrayed').value) || 0,
            asrPrayed: parseInt(document.getElementById('asrPrayed').value) || 0,
            maghribPrayed: parseInt(document.getElementById('maghribPrayed').value) || 0,
            ishaPrayed: parseInt(document.getElementById('ishaPrayed').value) || 0,
            witrPrayed: parseInt(document.getElementById('witrPrayed').value) || 0
        };

        const token = getJwtToken();
        if (!token) {
            showStatusMessage('You must be logged in to update prayer counts.', true);
            return;
        }

        try {
            const response = await fetch(`${API_BASE_URL}/update`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}` // Add Authorization header here
                },
                body: JSON.stringify(requestBody)
            });

            const data = await response.json();

            if (response.ok) {
                showStatusMessage(data.statusMessage || 'Prayers updated successfully!', false);
                updateQazaForm.reset(); // Reset form fields
                await fetchLatestQazaCounts();
                await fetchQazaHistory();
            } else {
                showStatusMessage(data.statusMessage || 'Failed to update prayers!', true);
            }
        } catch (error) {
            console.error('Error during prayer update:', error);
            showStatusMessage(`An error occurred: ${error.message}`, true);
        }
    });

    // Handle add missed prayers form submission
    addMissedForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        const requestBody = {
            fajrMissed: parseInt(document.getElementById('fajrMissed').value) || 0,
            zuhrMissed: parseInt(document.getElementById('zuhrMissed').value) || 0,
            asrMissed: parseInt(document.getElementById('asrMissed').value) || 0,
            maghribMissed: parseInt(document.getElementById('maghribMissed').value) || 0,
            ishaMissed: parseInt(document.getElementById('ishaMissed').value) || 0,
            witrMissed: parseInt(document.getElementById('witrMissed').value) || 0
        };

        const token = getJwtToken();
        if (!token) {
            showStatusMessage('You must be logged in to add missed prayers.', true);
            return;
        }

        try {
            const response = await fetch(`${API_BASE_URL}/add`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}` // Add Authorization header here
                },
                body: JSON.stringify(requestBody)
            });

            const data = await response.json();

            if (response.ok) {
                showStatusMessage(data.statusMessage || 'Missed prayers added successfully!', false);
                addMissedForm.reset(); // Reset form fields
                await fetchLatestQazaCounts();
                await fetchQazaHistory();
            } else {
                showStatusMessage(data.statusMessage || 'Failed to add missed prayers!', true);
            }
        } catch (error) {
            console.error('Error during adding missed prayers:', error);
            showStatusMessage(`An error occurred: ${error.message}`, true);
        }
    });


    // --- Event Listeners and Initial Setup ---

    // Gender selection change listener
    genderMale.addEventListener('change', toggleFemaleDetails);
    genderFemale.addEventListener('change', toggleFemaleDetails);

    // Period days and cycles input change listener for live calculation display
    averagePeriodDaysInput.addEventListener('input', updateExcludedPeriodDaysDisplay);
    totalMonthlyCyclesInput.addEventListener('input', updateExcludedPeriodDaysDisplay);

    // Global clear function (exposed to window for onclick in HTML)
    window.clearFormAndResults = function() {
        qazaCalculatorForm.reset();
        updateQazaForm.reset();
        addMissedForm.reset();
        hideAllDynamicSections(); // Hide all sections
        showStatusMessage('Form cleared and results hidden.', false);
        toggleFemaleDetails(); // Reset gender-specific fields visibility
    };

    // Global reset update form function
    window.resetUpdateForm = function() {
        updateQazaForm.reset();
        showStatusMessage('Update form reset.', false);
    };

    // Global reset add form function
    window.resetAddForm = function() {
        addMissedForm.reset();
        showStatusMessage('Add missed form reset.', false);
    };

    // Initial load: Fetch and display latest counts and history
    hideAllDynamicSections(); // Initially hide all dynamic sections
    fetchLatestQazaCounts(); // Attempt to load latest counts first
    fetchQazaHistory();       // Then load history
    toggleFemaleDetails();    // Initialize female details display
});