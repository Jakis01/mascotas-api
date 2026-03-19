package com.example.mascotas.mascotas;

import com.example.mascotas.clientes.Cliente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.persistence.Entity;

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
    private long idMascota;

    @Column(nullable = false, length = 100)
    private String nombre;
    private char sexo;

    @Column(nullable = true, length = 100)
    private String tipo;
    private byte edad;
    private boolean enPeligro;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "idCliente", nullable = true)
    @JsonIgnore
    private Cliente cliente;
}
