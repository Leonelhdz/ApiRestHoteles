package com.example.api_rest_spring;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
/**
 * Clase que representa un hotel.
 */
@Data
@Entity
@Table(name="hoteles")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_hotel;
    /**
     * El nombre del hotel.
     */
    private String nombre;
    /**
     * La dirección del hotel.
     */
    private String direccion;
    /**
     * La ciudad donde se encuentra el hotel.
     */
    private String ciudad;
    /**
     * La provincia donde se encuentra el hotel.
     */
    private String provincia;
    /**
     * El país donde se encuentra el hotel.
     */
    private String pais;
    /**
     * El número de teléfono del hotel.
     */
    private String telefono;
    /**
     * La dirección de correo electrónico del hotel.
     */
    private String correo_electronico;
    /**
     * La categoría del hotel.
     */
    private int categoria;
    /**
     * La descripción del hotel.
     */
    private String descripcion;
    /**
     * La latitud geográfica del hotel.
     */
    private double latitud;
    /**
     * La longitud geográfica del hotel.
     */
    private double longitud;
}
