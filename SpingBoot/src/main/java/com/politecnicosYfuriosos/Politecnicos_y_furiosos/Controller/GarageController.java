package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Controller;




import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Dto.Garage.GarageDTO;
import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Service.GarageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/garage")
@CrossOrigin(origins = "*")
public class GarageController {

    @Autowired
    private GarageService garageService;

    @GetMapping("/{id}")
    public ResponseEntity<GarageDTO> obtenerGarage(@PathVariable Integer id) {
        GarageDTO garage = garageService.obtenerGarageCompleto(id);

        if (garage != null) {
            return ResponseEntity.ok(garage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}