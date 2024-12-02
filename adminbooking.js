let appointments = [];
let currentDate = new Date(); // Today's date
let selectedAppointment = null;

const calendarGrid = document.getElementById("calendarGrid");
const detailsContent = document.getElementById("detailsContent");
const cancelBtn = document.getElementById("cancelBtn");

// Fetch appointments from API
async function fetchAppointments() {
  try {
    const response = await fetch("http://localhost:8080/api/appointments/admin/appointments");
    if (!response.ok) throw new Error("Failed to fetch appointments.");
    appointments = await response.json();
    console.log("Fetched appointments:", appointments);
    renderCalendar();
  } catch (error) {
    console.error("Error fetching appointments:", error);
    alert("Failed to load appointments.");
  }
}
function formatServiceName(service) {
  const serviceMap = {
    "oil-change": "Oil Change",
    "tire-replacement": "Tire Replacement",
    "brake-check": "Brake Check",
    // Add more mappings as needed
  };

  return serviceMap[service] || service.charAt(0).toUpperCase() + service.slice(1).toLowerCase();
}

// Render calendar view
function renderCalendar() {
  console.log("Rendering calendar...");
  calendarGrid.innerHTML = "";

  const startOfWeek = new Date(currentDate);
  startOfWeek.setDate(currentDate.getDate() - currentDate.getDay());

  const endOfWeek = new Date(startOfWeek);
  endOfWeek.setDate(startOfWeek.getDate() + 6);

  // Add days headers with dates
  calendarGrid.innerHTML += `<div class="time-slot"></div>`;
  for (let day = 0; day < 7; day++) {
    const dayDate = new Date(startOfWeek);
    dayDate.setDate(startOfWeek.getDate() + day);

    const formattedDate = dayDate.toLocaleDateString("en-US", { month: "short", day: "numeric" });
    calendarGrid.innerHTML += `
      <div class="day-header">
        ${["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"][day]}<br>
        <span style="font-size: 12px; font-weight: normal;">${formattedDate}</span>
      </div>`;
  }

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
    const appointmentDiv = document.createElement("div");
    appointmentDiv.className = "appointment";
    appointmentDiv.textContent = formatServiceName(appointment.service); // Use the formatted name
    appointmentDiv.dataset.appointmentId = appointment.id;

    cellDiv.appendChild(appointmentDiv);
  }
});

      calendarGrid.appendChild(cellDiv);
    }
  }
}


// Open appointment details
function openAppointmentDetails(appointment) {
  if (!appointment) {
    console.error("No appointment data provided.");
    detailsContent.innerHTML = "No appointment selected.";
    cancelBtn.style.display = "none";
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
    <strong>Mechanic Name:</strong> ${appointment.mechanicName || "N/A"}<br>
    <strong>Mechanic ID:</strong> ${appointment.mechanicId || "N/A"}<br>
    <strong>Customer Email:</strong> ${appointment.customerEmail || "N/A"}
  `;
  cancelBtn.style.display = "inline-block";
}

// Event delegation for clicks
calendarGrid.addEventListener("click", (event) => {
  const clickedElement = event.target;

  if (clickedElement.classList.contains("appointment")) {
    const appointmentId = clickedElement.dataset.appointmentId;
    console.log("Clicked appointment ID:", appointmentId);

    const appointment = appointments.find(app => app.id == appointmentId);

    if (appointment) {
      openAppointmentDetails(appointment);
    } else {
      console.error("No appointment data found for ID:", appointmentId);
    }
  }
});

// Add event listeners for navigation buttons
document.getElementById("prevWeek").addEventListener("click", () => {
  console.log("Navigating to previous week...");
  currentDate.setDate(currentDate.getDate() - 7);
  renderCalendar();
});

document.getElementById("nextWeek").addEventListener("click", () => {
  console.log("Navigating to next week...");
  currentDate.setDate(currentDate.getDate() + 7);
  renderCalendar();
});

// Initial load
fetchAppointments();