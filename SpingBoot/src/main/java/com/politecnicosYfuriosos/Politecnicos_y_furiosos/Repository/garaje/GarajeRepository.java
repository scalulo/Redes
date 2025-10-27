package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Repository.garaje;
// GarajeRepository.java

import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Modelo.Garaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarajeRepository extends JpaRepository<Garaje, Integer> {
}