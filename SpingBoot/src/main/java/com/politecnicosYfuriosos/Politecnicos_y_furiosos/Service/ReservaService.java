
package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Service;
// ReservaService.java


import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Garage.EspacioReservaDTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Garage.ReservaRequestDTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Garage.ReservaResponseDTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Modelo.*;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Repository.ClienteRepository;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Repository.garaje.LugarRepository;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Repository.garaje.ReservaGarajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaGarajeRepository reservaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private LugarRepository lugarRepository;

    public ReservaResponseDTO crearReserva(ReservaRequestDTO request, Integer clienteId) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        if (clienteOpt.isEmpty()) {
            return new ReservaResponseDTO(false, "Cliente no encontrado", null, 0.0, null, new ArrayList<>());
        }

        Cliente cliente = clienteOpt.get();
        List<EspacioReservaDTO> espaciosReserva = new ArrayList<>();
        double total = 0.0;

        // Verificar y procesar cada espacio
        for (Integer espacioId : request.getEspaciosIds()) {
            Optional<Lugar> lugarOpt = lugarRepository.findById(espacioId);
            if (lugarOpt.isEmpty()) {
                return new ReservaResponseDTO(false, "Espacio no encontrado: " + espacioId, null, 0.0, null, new ArrayList<>());
            }

            Lugar lugar = lugarOpt.get();

            // Verificar disponibilidad
            if (!verificarDisponibilidad(lugar)) {
                return new ReservaResponseDTO(false, "El espacio " + lugar.getNumeroLugar() + " no está disponible", null, 0.0, null, new ArrayList<>());
            }

            // Calcular precio según duración
            double precio = calcularPrecio(lugar, request.getDuracion());
            total += precio;

            // Crear DTO para respuesta
            EspacioReservaDTO espacioDTO = new EspacioReservaDTO();
            espacioDTO.setId(lugar.getId());
            espacioDTO.setNumero(lugar.getNumeroLugar());
            espacioDTO.setTipo(lugar.getTipo().name());
            espacioDTO.setPiso(lugar.getPiso());
            espacioDTO.setPrecio(precio);
            espaciosReserva.add(espacioDTO);

            // Crear reserva individual para cada espacio
            ReservaGaraje reserva = new ReservaGaraje();
            reserva.setCliente(cliente);
            reserva.setLugar(lugar);
            reserva.setFechaReserva(LocalDateTime.now());

            // Configurar fechas según duración
            configurarFechasReserva(reserva, request);
            reserva.setEstado(ReservaGaraje.EstadoReserva.pendiente);

            reservaRepository.save(reserva);

            // Marcar espacio como reservado
            lugar.setEstado(Lugar.EstadoLugar.reservado);
            lugarRepository.save(lugar);
        }

        return new ReservaResponseDTO(true, "Reserva creada exitosamente",
                null, total, LocalDateTime.now(), espaciosReserva);
    }

    private boolean verificarDisponibilidad(Lugar lugar) {
        return lugar.getEstado() == Lugar.EstadoLugar.disponible;
    }

    private double calcularPrecio(Lugar lugar, String duracion) {
        double precioBase = lugar.getPrecio() != null ? lugar.getPrecio().doubleValue() : getPrecioDefault(lugar.getTipo());

        if (duracion.endsWith("h")) {
            switch (duracion) {
                case "1h": return precioBase * 0.008;
                case "6h": return precioBase * 0.04;
                case "12h": return precioBase * 0.08;
                default: return precioBase * 0.008;
            }
        } else {
            int meses = Integer.parseInt(duracion);
            return precioBase * meses;
        }
    }

    private double getPrecioDefault(Lugar.TipoLugar tipo) {
        switch (tipo) {
            case auto: return 1200.0;
            case premium: return 2500.0;
            case moto: return 800.0;
            default: return 1000.0;
        }
    }

    private void configurarFechasReserva(ReservaGaraje reserva, ReservaRequestDTO request) {
        LocalDateTime fechaInicio = request.getFechaInicio().atStartOfDay();

        if (request.getHoraInicio() != null) {
            fechaInicio = LocalDateTime.of(request.getFechaInicio(), request.getHoraInicio());
        }

        reserva.setFechaInicio(fechaInicio);

        if (request.getDuracion().endsWith("h")) {
            int horas = Integer.parseInt(request.getDuracion().replace("h", ""));
            reserva.setFechaFin(fechaInicio.plusHours(horas));
            reserva.setDuracion(horas);
        } else {
            int meses = Integer.parseInt(request.getDuracion());
            reserva.setFechaFin(fechaInicio.plusMonths(meses));
            reserva.setDuracion(meses * 30 * 24);
        }
    }
}
