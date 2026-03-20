package com.example.mascotas.mascotasServicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/mascota-servicio")
public class MascotaServicioController {

    @Autowired
    private MascotaServicioRepository mascotaServicioRepository;

    //Obetener todos los servicos de las mascotas
    @GetMapping
    public List<MascotaServicio> obtenerTodos(){
        return mascotaServicioRepository.findAll();
    }


    @PostMapping
    public MascotaServicio guardar(@RequestBody MascotaServicio mascotaServicio){
        return mascotaServicioRepository.save(mascotaServicio);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        mascotaServicioRepository.deleteById(id);
    }
}