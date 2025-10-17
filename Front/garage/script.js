// Precios de los espacios
const prices = {
    normal: 1200,
    premium: 2500,
    moto: 800
};

// Configuración del garage
const garageData = [
    {
        floor: "Planta Baja — Acceso Principal",
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
        floor: "Primer Nivel — Zona Premium",
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

// Array para almacenar espacios seleccionados
let selectedSpaces = [];

// Función para renderizar el garage
function renderGarage() {
    const layout = document.getElementById('garageLayout');
    layout.innerHTML = '';

    garageData.forEach((floor, floorIndex) => {
        const floorDiv = document.createElement('div');
        floorDiv.className = 'floor';

        // Agregar entrada solo en la planta baja
        if (floorIndex === 0) {
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
    });
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
    const count = selectedSpaces.length;
    const total = selectedSpaces.reduce((sum, space) => sum + prices[space.type], 0);

    document.getElementById('selectedCount').textContent = count;
    document.getElementById('totalPrice').textContent = `$${total.toLocaleString()}`;
    document.getElementById('reserveBtn').disabled = count === 0;
}

// Función para confirmar la reserva
function confirmReservation() {
    if (selectedSpaces.length === 0) return;

    const spaceNumbers = selectedSpaces.map(s => s.num).join(', ');
    const total = selectedSpaces.reduce((sum, space) => sum + prices[space.type], 0);

    alert(`RESERVA CONFIRMADA\n\nEspacios: ${spaceNumbers}\nTotal: $${total.toLocaleString()}\n\nGracias por confiar en RGA Garage Premium`);

    // Limpiar selección
    selectedSpaces = [];
    updateSummary();
    renderGarage();
}

// Inicializar el garage al cargar la página
renderGarage();