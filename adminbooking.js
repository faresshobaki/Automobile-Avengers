let appointments = []; // Data fetched from API
    let currentDate = new Date(); // Today's date
    let selectedAppointment = null;
  
    const calendarGrid = document.getElementById("calendarGrid");
    const detailsPanel = document.getElementById("detailsPanel");
    const detailsContent = document.getElementById("detailsContent");
    const cancelButton = document.getElementById("cancelButton");
  
    // Fetch appointments from API
    async function fetchAppointments() {
      try {
        console.log("Fetching appointments...");
        const response = await fetch("http://localhost:8080/api/appointments/admin/appointments");
        if (!response.ok) throw new Error("Failed to fetch appointments.");
  
        appointments = await response.json();
        console.log("Fetched appointments:", appointments);
        renderCalendar(); // Render calendar after fetching appointments
      } catch (error) {
        console.error("Error fetching appointments:", error);
        alert("Failed to load appointments.");
      }
    }
  
    // Render the calendar grid
    function renderCalendar() {
      console.log("Rendering calendar...");
      calendarGrid.innerHTML = ""; // Clear the calendar grid
  
      const startOfWeek = new Date(currentDate);
      startOfWeek.setDate(currentDate.getDate() - currentDate.getDay());
  
      const endOfWeek = new Date(startOfWeek);
      endOfWeek.setDate(startOfWeek.getDate() + 6);
  
      calendarGrid.innerHTML += `<div class="time-slot"></div>`;
      ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"].forEach(day => {
        calendarGrid.innerHTML += `<div class="day-header">${day}</div>`;
      });
  
      for (let hour = 7; hour <= 19; hour++) {
        const timeLabel = `${hour.toString().padStart(2, "0")}:00`;
        calendarGrid.innerHTML += `<div class="time-slot">${timeLabel}</div>`;
  
        for (let day = 0; day < 7; day++) {
          const dayDate = new Date(startOfWeek);
          dayDate.setDate(startOfWeek.getDate() + day);
  
          const cellDiv = document.createElement("div");
          cellDiv.className = "day-column";
  
          appointments.forEach(appointment => {
            if (
              new Date(appointment.date).toDateString() === dayDate.toDateString() &&
              appointment.time === timeLabel
            ) {
              const bookingDiv = document.createElement("div");
              bookingDiv.className = "booking-item";
              bookingDiv.textContent = `${appointment.service}`;
              bookingDiv.addEventListener("click", () => {
                showAppointmentDetails(appointment);
              });
              cellDiv.appendChild(bookingDiv);
            }
          });
  
          calendarGrid.appendChild(cellDiv);
        }
      }
    }
  
    // Show appointment details in the details panel
    function showAppointmentDetails(appointment) {
      if (!appointment) {
        detailsContent.innerHTML = "No appointment data available.";
        cancelButton.style.display = "none";
        return;
      }
  
      selectedAppointment = appointment;
      detailsContent.innerHTML = `
        <strong>ID:</strong> ${appointment.id}<br>
        <strong>Service:</strong> ${appointment.service}<br>
        <strong>Date:</strong> ${appointment.date}<br>
        <strong>Time:</strong> ${appointment.time}<br>
        <strong>Make:</strong> ${appointment.make || "N/A"}<br>
        <strong>Model:</strong> ${appointment.model || "N/A"}<br>
        <strong>Mechanic:</strong> ${appointment.mechanicName || "N/A"} (${appointment.mechanicId || "N/A"})<br>
        <strong>Customer Email:</strong> ${appointment.customerEmail}
      `;
      cancelButton.style.display = "block"; // Show cancel button
    }
  
    // Cancel an appointment
    cancelButton.addEventListener("click", async () => {
      if (!selectedAppointment) {
        alert("No appointment selected.");
        return;
      }
  
      const reason = prompt("Please enter the reason for cancellation:");
      if (!reason) {
        alert("Cancellation reason is required.");
        return;
      }
  
      try {
        console.log("Cancelling appointment:", selectedAppointment.id);
        const response = await fetch(`http://localhost:8080/api/admin/appointments/${selectedAppointment.id}/cancel`, {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ reason })
        });
  
        if (!response.ok) throw new Error("Failed to cancel appointment.");
        alert("Appointment cancelled successfully.");
        selectedAppointment = null;
        cancelButton.style.display = "none"; // Hide cancel button
        detailsContent.innerHTML = "Click on an appointment to see details.";
        fetchAppointments(); // Refresh appointments
      } catch (error) {
        console.error("Error cancelling appointment:", error);
        alert("Failed to cancel the appointment.");
      }
    });
  
    // Initial load
    fetchAppointments();