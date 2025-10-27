package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Service;
// GarageService.java


import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Garage.GarageDTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Garage.PisoDTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Garage.EspacioDTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Modelo.Garaje;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Modelo.Lugar;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Repository.garaje.GarajeRepository;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Repository.garaje.LugarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class GarageService {

    @Autowired
    private GarajeRepository garajeRepository;

    @Autowired
    private LugarRepository lugarRepository;

    public GarageDTO obtenerGarageCompleto(Integer garageId) {
        Optional<Garaje> garajeOpt = garajeRepository.findById(garageId);
        if (garajeOpt.isEmpty()) {
            return null;
        }

        Garaje garaje = garajeOpt.get();
        GarageDTO garageDTO = new GarageDTO();
        garageDTO.setId(garaje.getId());
        garageDTO.setNombre(garaje.getNombre());
        garageDTO.setUbicacion(garaje.getUbicacion());
        garageDTO.setPisos(garaje.getPisos());

        // Obtener espacios del garage y ordenar por número
        List<Lugar> lugares = lugarRepository.findByGarajeId(garageId);
        lugares.sort(Comparator.comparing(Lugar::getNumeroLugar));

        // Organizar por pisos
        List<PisoDTO> pisosDetalle = organizarEspaciosPorPiso(lugares, garaje.getPisos());
        garageDTO.setPisosDetalle(pisosDetalle);

        return garageDTO;
    }

    private List<PisoDTO> organizarEspaciosPorPiso(List<Lugar> lugares, Integer totalPisos) {
        List<PisoDTO> pisos = new ArrayList<>();

        for (int pisoNum = 0; pisoNum < totalPisos; pisoNum++) {
            PisoDTO pisoDTO = new PisoDTO();
            pisoDTO.setNumeroPiso(pisoNum);
            pisoDTO.setNombre(generarNombrePiso(pisoNum));

            List<EspacioDTO> espaciosPiso = new ArrayList<>();
            for (Lugar lugar : lugares) {
                if (lugar.getPiso().equals(pisoNum)) {
                    EspacioDTO espacioDTO = new EspacioDTO();
                    espacioDTO.setId(lugar.getId());
                    espacioDTO.setNumero(lugar.getNumeroLugar());
                    espacioDTO.setTipo(lugar.getTipo().name());
                    espacioDTO.setPiso(lugar.getPiso());
                    espacioDTO.setEstado(lugar.getEstado().name());
                    espacioDTO.setPrecio(lugar.getPrecio() != null ? lugar.getPrecio().doubleValue() : 0.0);
                    espacioDTO.setOcupado(!lugar.getEstado().name().equals("disponible"));

                    espaciosPiso.add(espacioDTO);
                }
            }
            pisoDTO.setEspacios(espaciosPiso);
            pisos.add(pisoDTO);
        }

        return pisos;
    }

    private String generarNombrePiso(Integer numeroPiso) {
        switch (numeroPiso) {
            case 0: return "Planta Baja – Acceso Principal";
            case 1: return "Primer Nivel – Zona Premium";
            case 2: return "Segundo Nivel – Zona Standard";
            default: return "Piso " + numeroPiso;
        }
    }
}