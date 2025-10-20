// Precios de los espacios (precio mensual)
const prices = {
    normal: 1200,
    premium: 2500,
    moto: 800
};

// Configuración del garage
const garageData = [
    {
        floor: "Planta Baja – Acceso Principal",
        rows: [
            [
                { num: 1, type: 'premium', occupied: false },
                { num: 2, type: 'premium', occupied: false },
                { num: 3, type: 'premium', occupied: true },
                { num: 4, type: 'premium', occupied: false },
                { num: 5, type: 'premium', occupied: false }
            ],
            [
                { num: 6, type: 'normal', occupied: false },
                { num: 7, type: 'normal', occupied: false },
                { num: 8, type: 'normal', occupied: true },
                { num: 9, type: 'normal', occupied: false },
                { num: 10, type: 'normal', occupied: false }
            ],
            [
                { num: 11, type: 'moto', occupied: false },
                { num: 12, type: 'moto', occupied: false },
                { num: 13, type: 'moto', occupied: false },
                { num: 14, type: 'moto', occupied: true }
            ]
        ]
    },
    {
        floor: "Primer Nivel – Zona Premium",
        rows: [
            [
                { num: 15, type: 'normal', occupied: false },
                { num: 16, type: 'normal', occupied: true },
                { num: 17, type: 'normal', occupied: false },
                { num: 18, type: 'normal', occupied: false },
                { num: 19, type: 'normal', occupied: false }
            ],
            [
                { num: 20, type: 'normal', occupied: false },
                { num: 21, type: 'normal', occupied: false },
                { num: 22, type: 'normal', occupied: true },
                { num: 23, type: 'normal', occupied: false },
                { num: 24, type: 'normal', occupied: false }
            ]
        ]
    }
];

// Variables de estado
let selectedSpaces = [];
let currentFloor = 0;
let selectedDate = null;
let duration = 1; // meses

// Función para cambiar de piso
function changeFloor() {
    currentFloor = parseInt(document.getElementById('floorSelector').value);
    renderGarage();
}

// Función para cambiar fecha
function changeDate() {
    selectedDate = document.getElementById('dateSelector').value;
    // Aquí puedes hacer una llamada al backend para obtener disponibilidad
    // fetchAvailability(selectedDate, currentFloor);
    console.log('Fecha seleccionada:', selectedDate);
}

// Función ejemplo para llamar al backend (descomentala y adaptala cuando tengas tu API)
/*
async function fetchAvailability(date, floor) {
    try {
        const response = await fetch(`/api/availability?date=${date}&floor=${floor}`);
        const data = await response.json();
        
        // Actualizar el estado de ocupación según la respuesta del backend
        garageData[floor].rows.forEach((row, rowIndex) => {
            row.forEach((space, spaceIndex) => {
                // Asumiendo que el backend devuelve un array de números ocupados
                space.occupied = data.occupiedSpaces.includes(space.num);
            });
        });
        
        renderGarage();
    } catch (error) {
        console.error('Error al obtener disponibilidad:', error);
        alert('Error al cargar la disponibilidad. Por favor, intenta nuevamente.');
    }
}
*/

// Función para renderizar el garage (solo el piso actual)
function renderGarage() {
    const layout = document.getElementById('garageLayout');
    layout.innerHTML = '';

    const floor = garageData[currentFloor];
    const floorDiv = document.createElement('div');
    floorDiv.className = 'floor';

    // Agregar entrada solo en la planta baja
    if (currentFloor === 0) {
        const entrance = document.createElement('div');
        entrance.className = 'entrance';
        entrance.innerHTML = '⬇ ENTRADA PRINCIPAL ⬇';
        floorDiv.appendChild(entrance);
    }

    // Título del piso
    const title = document.createElement('div');
    title.className = 'floor-title';
    title.textContent = floor.floor;
    floorDiv.appendChild(title);

    // Renderizar filas de espacios
    floor.rows.forEach(row => {
        const rowDiv = document.createElement('div');
        rowDiv.className = 'spaces-row';

        row.forEach(space => {
            const spaceDiv = document.createElement('div');
            spaceDiv.className = `space ${space.type}`;
            
            // Marcar espacios ocupados
            if (space.occupied) {
                spaceDiv.classList.add('occupied');
            }

            // Marcar espacios seleccionados
            if (selectedSpaces.find(s => s.num === space.num)) {
                spaceDiv.classList.add('selected');
            }

            // Etiquetas de tipo
            const typeLabel = {
                normal: 'Standard',
                premium: 'Premium',
                moto: 'Moto'
            };

            spaceDiv.innerHTML = `
                <span class="space-number">${space.num}</span>
                <span class="space-type">${typeLabel[space.type]}</span>
            `;

            // Agregar evento click solo a espacios disponibles
            if (!space.occupied) {
                spaceDiv.onclick = () => toggleSpace(space.num, space.type);
            }

            rowDiv.appendChild(spaceDiv);
        });

        floorDiv.appendChild(rowDiv);
    });

    layout.appendChild(floorDiv);
}

// Función para seleccionar/deseleccionar espacios
function toggleSpace(num, type) {
    const index = selectedSpaces.findIndex(s => s.num === num);
    
    if (index > -1) {
        // Si ya está seleccionado, lo removemos
        selectedSpaces.splice(index, 1);
    } else {
        // Si no está seleccionado, lo agregamos
        selectedSpaces.push({ num, type });
    }

    updateSummary();
    renderGarage();
}

// Función para actualizar el resumen
function updateSummary() {
    duration = parseInt(document.getElementById('durationSelector').value);
    const count = selectedSpaces.length;
    const monthlyTotal = selectedSpaces.reduce((sum, space) => sum + prices[space.type], 0);
    const total = monthlyTotal * duration;

    document.getElementById('selectedCount').textContent = count;
    document.getElementById('durationDisplay').textContent = duration === 1 ? '1 Mes' : `${duration} Meses`;
    document.getElementById('totalPrice').textContent = `$${total.toLocaleString()}`;
    document.getElementById('reserveBtn').disabled = count === 0;
}

// Función para confirmar la reserva
function confirmReservation() {
    if (selectedSpaces.length === 0) return;

    const spaceNumbers = selectedSpaces.map(s => s.num).join(', ');
    const monthlyTotal = selectedSpaces.reduce((sum, space) => sum + prices[space.type], 0);
    const total = monthlyTotal * duration;
    const durationText = duration === 1 ? '1 Mes' : `${duration} Meses`;

    // Aquí puedes hacer la llamada al backend para confirmar la reserva
    /*
    const reservationData = {
        spaces: selectedSpaces.map(s => s.num),
        date: selectedDate,
        duration: duration,
        total: total,
        floor: currentFloor
    };
    
    fetch('/api/reservations', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(reservationData)
    })
    .then(response => response.json())
    .then(data => {
        alert(`RESERVA CONFIRMADA\n\nID: ${data.id}\nEspacios: ${spaceNumbers}\nDuración: ${durationText}\nFecha inicio: ${selectedDate}\nTotal: $${total.toLocaleString()}\n\nGracias por confiar en RGA Garage Premium`);
        
        // Limpiar selección
        selectedSpaces = [];
        updateSummary();
        renderGarage();
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error al procesar la reserva. Por favor, intenta nuevamente.');
    });
    */

    // Por ahora mostramos un alert simple
    alert(`RESERVA CONFIRMADA\n\nEspacios: ${spaceNumbers}\nDuración: ${durationText}\nFecha inicio: ${selectedDate || 'No seleccionada'}\nTotal: $${total.toLocaleString()}\n\nGracias por confiar en RGA Garage Premium`);

    // Limpiar selección
    selectedSpaces = [];
    updateSummary();
    renderGarage();
}

// Inicializar el garage al cargar la página
function initializePage() {
    // Establecer fecha mínima como hoy
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('dateSelector').value = today;
    document.getElementById('dateSelector').min = today;
    selectedDate = today;
    
    renderGarage();
}

initializePage();

// =======================
// NUEVAS FUNCIONES (no afectan el mapa)
// =======================

function toggleTimeSelector() {
    const durationSelector = document.getElementById("durationSelector");
    const timeGroup = document.getElementById("timeGroup");

    const selectedValue = durationSelector.value;
    if (selectedValue.endsWith("h")) {
        timeGroup.style.display = "flex";
    } else {
        timeGroup.style.display = "none";
    }
}

function updateSummary() {
    const durationSelector = document.getElementById("durationSelector");
    const durationDisplay = document.getElementById("durationDisplay");
    const totalPrice = document.getElementById("totalPrice");
    const selectedValue = durationSelector.value;

    let displayText = "";
    let price = 0;

    if (selectedValue.endsWith("h")) {
        switch (selectedValue) {
            case "1h": displayText = "1 Hora"; price = 10; break;
            case "6h": displayText = "6 Horas"; price = 40; break;
            case "12h": displayText = "12 Horas"; price = 70; break;
        }
    } else {
        switch (selectedValue) {
            case "1": displayText = "1 Mes"; price = 300; break;
            case "3": displayText = "3 Meses"; price = 850; break;
            case "6": displayText = "6 Meses"; price = 1600; break;
            case "12": displayText = "12 Meses"; price = 3000; break;
        }
    }

    durationDisplay.textContent = displayText;
    totalPrice.textContent = `$${price}`;
}

function confirmReservation() {
    const date = document.getElementById("dateSelector")?.value;
    const duration = document.getElementById("durationSelector")?.value;
    const time = document.getElementById("timeSelector")?.value;

    if (!date) {
        alert("Por favor selecciona una fecha para la reserva.");
        return;
    }

    if (duration.endsWith("h") && !time) {
        alert("Selecciona una hora de inicio para la reserva por horas.");
        return;
    }

    let mensaje = `Reserva confirmada para el ${date}`;
    if (duration.endsWith("h")) mensaje += ` a las ${time}`;
    alert(mensaje);
}

