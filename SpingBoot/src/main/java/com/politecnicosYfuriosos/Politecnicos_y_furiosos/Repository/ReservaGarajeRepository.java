package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Repository;



import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Modelo.ReservaGaraje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaGarajeRepository extends JpaRepository<ReservaGaraje, Integer> {
    List<ReservaGaraje> findByClienteId(Integer clienteId);
}