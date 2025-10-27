
package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Controller;

// ReservaController.java


import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Garage.ReservaRequestDTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Garage.ReservaResponseDTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crearReserva(
            @RequestBody ReservaRequestDTO request,
            @RequestHeader("X-User-Id") Integer clienteId) {

        ReservaResponseDTO response = reservaService.crearReserva(request, clienteId);

        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
