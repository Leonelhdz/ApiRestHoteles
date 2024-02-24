package com.example.api_rest_spring;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
/**
 * Interfaz que define métodos para acceder a la base de datos de hoteles.
 */
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    // Método para obtener todos los hoteles
    List<Hotel> findAll();
    // Método para guardar un nuevo hotel
    Hotel save(Hotel hotel);

    // Método para eliminar un hotel existente
    void delete(Hotel hotel);

    // Método para buscar hoteles por ciudad
    List<Hotel> findByCiudad(String ciudad);

    // Método para buscar hoteles por provincia
    List<Hotel> findByProvincia(String provincia);

    // Método para buscar hoteles por categoría
    List<Hotel> findByCategoria(int categoria);

    /**
     * Busca el hotel más cercano a una ubicación geográfica dada.
     * @param latitud Latitud de la ubicación.
     * @param longitud Longitud de la ubicación.
     * @return El hotel más cercano a la ubicación especificada.
     */
    @Query(value = "SELECT * FROM hoteles ORDER BY SQRT(POWER(:latitud - latitud, 2) + POWER(:longitud - longitud, 2)) LIMIT 1", nativeQuery = true)
    Hotel findNearestHotel(@Param("latitud") double latitud, @Param("longitud") double longitud);

}
