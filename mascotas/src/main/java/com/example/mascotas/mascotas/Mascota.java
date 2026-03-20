package com.example.mascotas.mascotas;

import com.example.mascotas.clientes.Cliente;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "mascota")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMascota; // Cambiado a Long con mayúscula para evitar errores de nulos

    @Column(nullable = false, length = 100)
    private String nombre;

    private char sexo;

    // --- AQUÍ ESTABA EL ERROR ---
    // Usamos @JsonProperty para que acepte "especie" desde React
    // pero lo guarde en la columna "tipo" de la base de datos.
    @Column(nullable = true, length = 100)
    @JsonProperty("especie")
    private String tipo;

    private byte edad;
    private boolean enPeligro;

    @ManyToOne(fetch = FetchType.EAGER) // EAGER ayuda a que no dé error de LazyInitialization
    @JoinColumn(name = "idCliente", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private Cliente cliente;
}