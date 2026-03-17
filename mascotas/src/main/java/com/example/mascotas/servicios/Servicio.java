package com.example.mascotas.servicios;

import com.example.mascotas.mascotas.Mascota;
import com.example.mascotas.mascotasServicios.MascotaServicio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.JoinColumnOrFormula;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "servicio")

public class Servicio {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int idServicio;

@Column(nullable = false, length = 150)
    private String descripcion;

@Column(scale = 2)
    private float precio;

@OneToMany(mappedBy = "servicio")
    private List<MascotaServicio> mascotaServicios;
}
