package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Repository.garaje;


import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Modelo.Lugar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LugarRepository extends JpaRepository<Lugar, Integer> {
    List<Lugar> findByGarajeId(Integer garajeId);
    List<Lugar> findByEstado(Lugar.EstadoLugar estado);
    List<Lugar> findByTipoAndEstado(Lugar.TipoLugar tipo, Lugar.EstadoLugar estado);
}