
package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Repository.garaje;


import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Modelo.ReservaGaraje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaGarajeRepository extends JpaRepository<ReservaGaraje, Integer> {

    // ✅ CORREGIDO: Usar el nombre correcto del campo
    @Query("SELECT r FROM ReservaGaraje r WHERE r.cliente.id = :clienteId")
    List<ReservaGaraje> findByClienteId(@Param("clienteId") Integer clienteId);
}