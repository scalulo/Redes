package com.politecnicosYfuriosos.Politecnicos_y_furiosos.Repository;

import com.politecnicosYfuriosos.Politecnicos_y_furiosos.Modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
    public interface MembresiaRepository extends JpaRepository<Membresia, Integer> {
    
}