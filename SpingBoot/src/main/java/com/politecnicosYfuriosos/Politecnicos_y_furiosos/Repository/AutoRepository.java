package com.rda.concesionaria.repository;

import com.rda.concesionaria.entity.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoRepository extends JpaRepository<Auto, Integer> {
    
    // Filtrar por tipo de auto
    List<Auto> findByTipo(Auto.TipoAuto tipo);
    
    // Filtrar por disponibilidad
    List<Auto> findByDisponible(Boolean disponible);
    
    // Filtrar por tipo y disponibilidad
    List<Auto> findByTipoAndDisponible(Auto.TipoAuto tipo, Boolean disponible);
    
    // Buscar por marca o modelo (para la barra de búsqueda)
    @Query("SELECT a FROM Auto a WHERE LOWER(a.marca) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(a.modelo) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Auto> searchByMarcaOrModelo(@Param("search") String search);
    
    // Buscar por marca o modelo y tipo
    @Query("SELECT a FROM Auto a WHERE " +
           "(LOWER(a.marca) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(a.modelo) LIKE LOWER(CONCAT('%', :search, '%'))) " +
           "AND a.tipo = :tipo")
    List<Auto> searchByMarcaOrModeloAndTipo(@Param("search") String search, @Param("tipo") Auto.TipoAuto tipo);
    
    // Obtener autos destacados (por ejemplo, los más nuevos)
    @Query("SELECT a FROM Auto a ORDER BY a.anio DESC, a.id DESC")
    List<Auto> findDestacados();
    
    // Obtener último lanzamiento
    @Query("SELECT a FROM Auto a ORDER BY a.anio DESC, a.id DESC LIMIT 1")
    Auto findUltimoLanzamiento();
}