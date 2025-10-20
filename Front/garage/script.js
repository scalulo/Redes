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

// Función para cambiar de piso
function changeFloor() {
    currentFloor = parseInt(document.getElementById('floorSelector').value);
    renderGarage();
}

// Función para cambiar fecha
function changeDate() {
    selectedDate = document.getElementById('dateSelector').value;
    console.log('Fecha seleccionada:', selectedDate);
}

// Función para mostrar/ocultar selector de hora
function toggleTimeSelector() {
    const durationSelector = document.getElementById("durationSelector");
    const timeGroup = document.getElementById("timeGroup");

    const selectedValue = durationSelector.value;
    if (selectedValue.endsWith("h")) {
        timeGroup.style.display = "flex";
    } else {
        timeGroup.style.display = "none";
    }
    updateSummary();
}

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
        // Si no está seleccionado, lo agregamos con toda la información
        const spaceInfo = {
            num: num,
            type: type,
            floor: currentFloor,
            floorName: garageData[currentFloor].floor,
            price: calculatePrice(type)
        };
        selectedSpaces.push(spaceInfo);
    }

    updateSummary();
    renderGarage();
}

// Función para calcular precio
function calculatePrice(type) {
    const durationSelector = document.getElementById("durationSelector");
    const selectedValue = durationSelector.value;
    
    // Si es por horas
    if (selectedValue.endsWith("h")) {
        switch (selectedValue) {
            case "1h": 
                switch(type) {
                    case 'normal': return 5;
                    case 'premium': return 10;
                    case 'moto': return 3;
                }
            case "6h": 
                switch(type) {
                    case 'normal': return 20;
                    case 'premium': return 40;
                    case 'moto': return 12;
                }
            case "12h": 
                switch(type) {
                    case 'normal': return 35;
                    case 'premium': return 70;
                    case 'moto': return 20;
                }
        }
    }
    
    // Si es por meses
    const monthlyPrices = {
        normal: 1200,
        premium: 2500,
        moto: 800
    };
    
    const months = parseInt(selectedValue);
    return monthlyPrices[type] * months;
}

// Función para actualizar el resumen (CORREGIDA)
function updateSummary() {
    const durationSelector = document.getElementById("durationSelector");
    const durationDisplay = document.getElementById("durationDisplay");
    const totalPrice = document.getElementById("totalPrice");
    const selectedCount = document.getElementById("selectedCount");
    
    const selectedValue = durationSelector.value;
    let displayText = "";
    
    // Actualizar texto de duración
    if (selectedValue.endsWith("h")) {
        switch (selectedValue) {
            case "1h": displayText = "1 Hora"; break;
            case "6h": displayText = "6 Horas"; break;
            case "12h": displayText = "12 Horas"; break;
        }
    } else {
        switch (selectedValue) {
            case "1": displayText = "1 Mes"; break;
            case "3": displayText = "3 Meses"; break;
            case "6": displayText = "6 Meses"; break;
            case "12": displayText = "12 Meses"; break;
        }
    }
    
    durationDisplay.textContent = displayText;
    selectedCount.textContent = selectedSpaces.length;
    
    // Calcular total
    const total = selectedSpaces.reduce((sum, space) => sum + space.price, 0);
    totalPrice.textContent = `$${total}`;
    
    // Habilitar/deshabilitar botón SOLO SI EXISTE
    const reserveBtn = document.getElementById("reserveBtn");
    if (reserveBtn) {
        reserveBtn.disabled = selectedSpaces.length === 0;
    }
}

// Función para obtener texto de duración
function getDurationText(durationValue) {
    const durations = {
        "1": "1 Mes",
        "3": "3 Meses", 
        "6": "6 Meses",
        "12": "12 Meses",
        "1h": "1 Hora",
        "6h": "6 Horas",
        "12h": "12 Horas"
    };
    return durations[durationValue] || durationValue;
}

// Función para confirmar la reserva
function confirmReservation() {
    if (selectedSpaces.length === 0) {
        alert("Por favor selecciona al menos un espacio.");
        return;
    }

    const date = document.getElementById("dateSelector").value;
    const durationSelector = document.getElementById("durationSelector");
    const time = document.getElementById("timeSelector")?.value;
    
    if (!date) {
        alert("Por favor selecciona una fecha para la reserva.");
        return;
    }

    if (durationSelector.value.endsWith("h") && !time) {
        alert("Selecciona una hora de inicio para la reserva por horas.");
        return;
    }

    // Preparar datos de la reserva para enviar a BD
    const reservationData = prepareReservationData(date, durationSelector.value, time);
    
    // Mostrar resumen y preparar para enviar a BD
    showReservationSummary(reservationData);
}

// Función para preparar datos de reserva (PARA TU BASE DE DATOS)
function prepareReservationData(date, durationValue, time) {
    const total = selectedSpaces.reduce((sum, space) => sum + space.price, 0);
    
    return {
        // Información de espacios
        spaces: selectedSpaces.map(space => ({
            number: space.num,
            type: space.type,
            floor: space.floor,
            price: space.price
        })),
        
        // Información de tiempo
        date: date,
        duration: durationValue,
        startTime: time,
        
        // Información financiera
        total: total,
        currency: "USD",
        
        // Metadatos
        reservationDate: new Date().toISOString(),
        status: "pending",
        
        // Información del cliente (aquí puedes agregar más campos)
        clientInfo: {
            name: "",
            email: "",
            phone: ""
        }
    };
}

// Función para mostrar resumen detallado
function showReservationSummary(reservation) {
    const spaceNumbers = reservation.spaces.map(s => s.number).join(', ');
    const spaceTypes = [...new Set(reservation.spaces.map(s => {
        const types = {
            'normal': 'Standard',
            'premium': 'Premium', 
            'moto': 'Moto'
        };
        return types[s.type];
    }))].join(', ');
    
    let message = `🚗 RESUMEN DE RESERVA 🚗\n\n`;
    message += `📍 Espacios: ${spaceNumbers}\n`;
    message += `🏷️ Tipo: ${spaceTypes}\n`;
    message += `📅 Fecha: ${reservation.date}\n`;
    message += `⏰ Duración: ${getDurationText(reservation.duration)}\n`;
    
    if (reservation.startTime) {
        message += `🕐 Hora inicio: ${reservation.startTime}\n`;
    }
    
    message += `💰 Total: $${reservation.total}\n\n`;
    message += `¿Confirmar reserva?`;

    if (confirm(message)) {
        sendToDatabase(reservation);
    }
}

// ⚠️ FUNCIÓN PARA QUE TÚ IMPLEMENTES LA CONEXIÓN A BD
function sendToDatabase(reservationData) {
    // Por ahora simulamos éxito
    setTimeout(() => {
        alert(`✅ RESERVA PREPARADA PARA BD\n\nDatos listos para enviar:\n- ${reservationData.spaces.length} espacios\n- Total: $${reservationData.total}\n\nRevisa la consola para ver el objeto completo.`);
        
        console.log('📦 DATOS PARA ENVIAR A BD:', reservationData);
        
        // Limpiar selección
        clearSelection();
    }, 500);
}

// Función para limpiar selección
function clearSelection() {
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
    
    // Inicializar selector de hora
    toggleTimeSelector();
    
    renderGarage();
}

// Inicializar cuando se carga la página
initializePage();