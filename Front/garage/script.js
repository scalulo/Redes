// Precios de los espacios (como fallback)
const prices = {
    normal: 1200,
    premium: 2500,
    moto: 800
};

// Variables de estado
let selectedSpaces = [];
let currentFloor = 0;
let selectedDate = null;
let garageData = []; // Ahora vendrá de la base de datos

// Función para cargar datos del garage desde Spring Boot
async function loadGarageData() {
    try {
        console.log('Cargando datos del garage desde Spring Boot...');
        const response = await fetch('http://localhost:8081/api/garage/1'); // ID 1 de tu garage
        
        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }
        
        const garage = await response.json();
        console.log('Datos del garage cargados:', garage);
        
        // Convertir la estructura de la API a la que espera tu frontend
        garageData = convertGarageStructure(garage);
        console.log('Datos convertidos para frontend:', garageData);
        
        // Actualizar el selector de pisos
        updateFloorSelector();
        
        // Renderizar el garage con datos reales
        renderGarage();
        
    } catch (error) {
        console.error('Error cargando datos del garage:', error);
        // Usar datos de prueba como fallback
        garageData = getFallbackData();
        updateFloorSelector();
        renderGarage();
        alert('⚠️ Usando datos de prueba. El garage real no está disponible.');
    }
}

// Función para convertir la estructura de la API
function convertGarageStructure(garageApi) {
    return garageApi.pisosDetalle.map(piso => ({
        floor: piso.nombre,
        rows: organizeSpacesIntoRows(piso.espacios)
    }));
}

// Función para organizar espacios en filas (como en tu diseño original)
function organizeSpacesIntoRows(espacios) {
    const rows = [];
    let currentRow = [];
    
    espacios.forEach((espacio, index) => {
        currentRow.push({
            num: espacio.numero,
            type: mapTipoToFrontend(espacio.tipo),
            occupied: espacio.ocupado,
            id: espacio.id, // Guardar el ID real de la base de datos
            precio: espacio.precio
        });
        
        // Lógica para crear filas según tu diseño
        // Para planta baja (piso 0): filas de 5, 5, 4 espacios
        if (espacio.piso === 0) {
            if (index === 4 || index === 9 || index === 13) {
                rows.push([...currentRow]);
                currentRow = [];
            }
        } 
        // Para primer piso (piso 1): filas de 5, 5 espacios  
        else if (espacio.piso === 1) {
            if (index === 4 || index === 9) {
                rows.push([...currentRow]);
                currentRow = [];
            }
        }
        // Para otros pisos: filas de 5 espacios
        else if ((index + 1) % 5 === 0) {
            rows.push([...currentRow]);
            currentRow = [];
        }
    });
    
    // Agregar la última fila si queda algo
    if (currentRow.length > 0) {
        rows.push(currentRow);
    }
    
    return rows;
}

// Mapear tipos de la base de datos al frontend
function mapTipoToFrontend(tipoBD) {
    const mapping = {
        'auto': 'normal',
        'premium': 'premium', 
        'moto': 'moto'
    };
    return mapping[tipoBD] || 'normal';
}

// Actualizar el selector de pisos dinámicamente
function updateFloorSelector() {
    const floorSelector = document.getElementById('floorSelector');
    floorSelector.innerHTML = '';
    
    garageData.forEach((piso, index) => {
        const option = document.createElement('option');
        option.value = index;
        option.textContent = piso.floor;
        floorSelector.appendChild(option);
    });
}

// Datos de prueba como fallback
function getFallbackData() {
    return [
        {
            floor: "Planta Baja – Acceso Principal",
            rows: [
                [
                    { num: 1, type: 'premium', occupied: false, id: 1 },
                    { num: 2, type: 'premium', occupied: false, id: 2 },
                    { num: 3, type: 'premium', occupied: true, id: 3 },
                    { num: 4, type: 'premium', occupied: false, id: 4 },
                    { num: 5, type: 'premium', occupied: false, id: 5 }
                ],
                [
                    { num: 6, type: 'normal', occupied: false, id: 6 },
                    { num: 7, type: 'normal', occupied: false, id: 7 },
                    { num: 8, type: 'normal', occupied: true, id: 8 },
                    { num: 9, type: 'normal', occupied: false, id: 9 },
                    { num: 10, type: 'normal', occupied: false, id: 10 }
                ],
                [
                    { num: 11, type: 'moto', occupied: false, id: 11 },
                    { num: 12, type: 'moto', occupied: false, id: 12 },
                    { num: 13, type: 'moto', occupied: false, id: 13 },
                    { num: 14, type: 'moto', occupied: true, id: 14 }
                ]
            ]
        },
        {
            floor: "Primer Nivel – Zona Premium", 
            rows: [
                [
                    { num: 15, type: 'normal', occupied: false, id: 15 },
                    { num: 16, type: 'normal', occupied: true, id: 16 },
                    { num: 17, type: 'normal', occupied: false, id: 17 },
                    { num: 18, type: 'normal', occupied: false, id: 18 },
                    { num: 19, type: 'normal', occupied: false, id: 19 }
                ],
                [
                    { num: 20, type: 'normal', occupied: false, id: 20 },
                    { num: 21, type: 'normal', occupied: false, id: 21 },
                    { num: 22, type: 'normal', occupied: true, id: 22 },
                    { num: 23, type: 'normal', occupied: false, id: 23 },
                    { num: 24, type: 'normal', occupied: false, id: 24 }
                ]
            ]
        }
    ];
}

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

    if (!garageData || garageData.length === 0) {
        layout.innerHTML = '<div class="error">No hay datos del garage disponibles</div>';
        return;
    }

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
                spaceDiv.onclick = () => toggleSpace(space.num, space.type, space.id);
            }

            rowDiv.appendChild(spaceDiv);
        });

        floorDiv.appendChild(rowDiv);
    });

    layout.appendChild(floorDiv);
}

// Función para seleccionar/deseleccionar espacios (ACTUALIZADA)
function toggleSpace(num, type, id) {
    const index = selectedSpaces.findIndex(s => s.num === num);
    
    if (index > -1) {
        // Si ya está seleccionado, lo removemos
        selectedSpaces.splice(index, 1);
    } else {
        // Si no está seleccionado, lo agregamos con toda la información
        const spaceInfo = {
            num: num,
            type: type,
            id: id, // Usar el ID real de la base de datos
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

// Función para actualizar el resumen
function updateSummary() {
    const durationSelector = document.getElementById("durationSelector");
    const durationDisplay = document.getElementById("durationDisplay");
    const totalPrice = document.getElementById("totalPrice");
    const selectedCount = document.getElementById("selectedCount");
    const reserveBtn = document.getElementById("reserveBtn");
    
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
            price: space.price,
            id: space.id // ID real de la base de datos
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
        
        // Información del cliente
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

// FUNCIÓN ACTUALIZADA: Enviar reserva a Spring Boot
function sendToDatabase(reservationData) {
    const clienteId = obtenerClienteId();
    
    // Usar los IDs reales de la base de datos
    const espaciosIds = reservationData.spaces.map(space => space.id);
    
    const requestData = {
        espaciosIds: espaciosIds,
        fechaInicio: reservationData.date,
        duracion: reservationData.duration,
        horaInicio: reservationData.startTime || null
    };

    console.log('Enviando reserva con IDs reales:', requestData);

    fetch('http://localhost:8081/api/reservas', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-User-Id': clienteId.toString()
        },
        body: JSON.stringify(requestData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        console.log('Respuesta del backend:', data);
        if (data.success) {
            alert(`✅ RESERVA CONFIRMADA\n\nEspacios: ${reservationData.spaces.map(s => s.number).join(', ')}\nDuración: ${getDurationText(reservationData.duration)}\nTotal: $${data.total}\n\n${data.mensaje}`);
            
            // Recargar los datos del garage para reflejar los cambios
            loadGarageData();
            clearSelection();
        } else {
            alert(`❌ ERROR\n\n${data.mensaje}`);
        }
    })
    .catch(error => {
        console.error('Error completo:', error);
        alert('❌ Error al conectar con el servidor. Verifica que Spring Boot esté ejecutándose en puerto 8081.');
    });
}

// Función para obtener ID del cliente (TEMPORAL)
function obtenerClienteId() {
    // TEMPORAL: Usar ID 1 para pruebas
    // En el futuro, esto vendrá de tu sistema de login
    return 1;
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
    
    // Cargar datos del garage desde Spring Boot
    loadGarageData();
}

// Inicializar cuando se carga la página
initializePage();