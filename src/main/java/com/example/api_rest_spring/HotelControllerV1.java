package com.example.api_rest_spring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * Controlador para manejar las solicitudes relacionadas con hoteles.
 */
@RestController
@RequestMapping("/v1/hoteles")
public class HotelControllerV1 {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private SecurityService securityService;
    /**
     * Obtiene todos los hoteles.
     * @return Lista de todos los hoteles.
     */
    @GetMapping("/hoteles")
    public List<Hotel> getAllHoteles() {
        return hotelRepository.findAll();
    }
    /**
     * Obtiene un hotel por su ID.
     * @param id_hotel ID del hotel a buscar.
     * @return ResponseEntity con el hotel si se encuentra, o NOT FOUND si no se encuentra.
     */
    @GetMapping("/hoteles/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable(value = "id") Long id_hotel) {
        Optional<Hotel> hotel = hotelRepository.findById(id_hotel);
        if (hotel.isPresent()) {
            return ResponseEntity.ok().body(hotel.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea un nuevo hotel.
     * @param hotel Detalles del hotel a crear.
     * @param token Token de autenticación.
     * @return ResponseEntity con el hotel creado si la autenticación es válida, o UNAUTHORIZED si no lo es.
     */
    @PostMapping("/hoteles")
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel, @RequestParam String token) {
        if (securityService.validateToken(token)) {
            return new ResponseEntity<>(hotelRepository.save(hotel), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    /**
     * Actualiza los detalles de un hotel existente.
     * @param hotelId ID del hotel a actualizar.
     * @param hotelDetails Detalles actualizados del hotel.
     * @param token Token de autenticación.
     * @return ResponseEntity con el hotel actualizado si la autenticación es válida y el hotel existe, o UNAUTHORIZED si no lo es, o NOT FOUND si el hotel no existe.
     */
    @PutMapping("/hoteles/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable(value = "id") Long hotelId, @RequestBody Hotel hotelDetails, @RequestParam String token) {
        if (!securityService.validateToken(token)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);
        if (optionalHotel.isPresent()) {
            Hotel hotel = optionalHotel.get();
            hotel.setNombre(hotelDetails.getNombre());
            // Actualiza los demás campos según sea necesario
            final Hotel updatedHotel = hotelRepository.save(hotel);
            return ResponseEntity.ok(updatedHotel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * Elimina un hotel existente.
     * @param hotelId ID del hotel a eliminar.
     * @param token Token de autenticación.
     * @return ResponseEntity vacío si la autenticación es válida y el hotel existe, o UNAUTHORIZED si no lo es, o NOT FOUND si el hotel no existe.
     */
    @DeleteMapping("/hoteles/{id}")
    public ResponseEntity<?> deleteHotel(@PathVariable(value = "id") Long hotelId, @RequestParam(value = "token") String token) {
        if (!securityService.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Hotel> hotel = hotelRepository.findById(hotelId);
        if (hotel.isPresent()) {
            hotelRepository.delete(hotel.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * Obtiene una lista de hoteles por ciudad.
     * @param ciudad Ciudad para la que se buscan los hoteles.
     * @return Lista de hoteles en la ciudad especificada.
     */
    @GetMapping("/ciudad/{ciudad}")
    public List<Hotel> getHotelesByCiudad(@PathVariable(value = "ciudad") String ciudad) {
        return hotelRepository.findByCiudad(ciudad);
    }
    /**
     * Obtiene una lista de hoteles por provincia.
     * @param provincia Provincia para la que se buscan los hoteles.
     * @return Lista de hoteles en la provincia especificada.
     */
    @GetMapping("/provincia/{provincia}")
    public List<Hotel> getHotelesByProvincia(@PathVariable(value = "provincia") String provincia) {
        return hotelRepository.findByProvincia(provincia);
    }
    /**
     * Obtiene una lista de hoteles por categoría.
     * @param categoria Categoría de hoteles a buscar.
     * @return Lista de hoteles en la categoría especificada.
     */
    @GetMapping("/categoria/{categoria}")
    public List<Hotel> getHotelesByCategoria(@PathVariable(value = "categoria") int categoria) {
        return hotelRepository.findByCategoria(categoria);
    }
    /**
     * Obtiene el hotel más cercano a una ubicación geográfica dada.
     * @param latitud Latitud de la ubicación.
     * @param longitud Longitud de la ubicación.
     * @return ResponseEntity con el hotel más cercano si se encuentra, o NOT FOUND si no se encuentra ningún hotel.
     */
    @GetMapping("/hotel-cercano")
    public ResponseEntity<Hotel> getHotelMasCercano(@RequestParam(value = "latitud") double latitud,
                                                    @RequestParam(value = "longitud") double longitud) {
        List<Hotel> hoteles = hotelRepository.findAll(); // Obtener todos los hoteles de la base de datos
        Hotel hotelMasCercano = null;
        double distanciaMinima = Double.MAX_VALUE;

        for (Hotel hotel : hoteles) {
            double distancia = calcularDistancia(latitud, longitud, hotel.getLatitud(), hotel.getLongitud());
            if (distancia < distanciaMinima) {
                distanciaMinima = distancia;
                hotelMasCercano = hotel;
            }
        }

        if (hotelMasCercano != null) {
            return ResponseEntity.ok().body(hotelMasCercano);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //GET /v1/hoteles/hotel-cercano?latitud=37.123&longitud=-4.567

    private static final int RADIO_TIERRA = 6371; // Radio medio de la Tierra en kilómetros
    /**
     * Calcula la distancia entre dos puntos geográficos utilizando la fórmula de Haversine.
     * @param latitud1 Latitud del primer punto en grados decimales.
     * @param longitud1 Longitud del primer punto en grados decimales.
     * @param latitud2 Latitud del segundo punto en grados decimales.
     * @param longitud2 Longitud del segundo punto en grados decimales.
     * @return La distancia entre los dos puntos en kilómetros.
     */
    private double calcularDistancia(double latitud1, double longitud1, double latitud2, double longitud2) {
        // Convertir las latitudes y longitudes de grados a radianes
        double latitud1Rad = Math.toRadians(latitud1);
        double longitud1Rad = Math.toRadians(longitud1);
        double latitud2Rad = Math.toRadians(latitud2);
        double longitud2Rad = Math.toRadians(longitud2);

        // Calcular la diferencia de latitud y longitud
        double latitudDelta = latitud2Rad - latitud1Rad;
        double longitudDelta = longitud2Rad - longitud1Rad;

        // Calcular la distancia utilizando la fórmula de Haversine
        double a = Math.pow(Math.sin(latitudDelta / 2), 2) + Math.cos(latitud1Rad) * Math.cos(latitud2Rad) * Math.pow(Math.sin(longitudDelta / 2), 2);
        double distancia = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Retorna la distancia en kilómetros
        return RADIO_TIERRA * distancia;
    }

}
