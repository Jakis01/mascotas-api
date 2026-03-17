package com.example.mascotas.direccion;

import com.example.mascotas.clientes.Cliente;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.hibernate.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "direccion")
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDireccion;

    @Column(nullable = false, length = 100)
    private String calle;

    @Column(nullable = false, length = 20)
    private String numero;

    @Column(nullable = false, length= 100)
    private String ciudad;

    @OneToOne
    @JoinColumn (name = "idCliente")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cliente cliente;
}
