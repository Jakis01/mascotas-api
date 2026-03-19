package com.example.mascotas.mascotas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*") // Permite que Netlify se conecte
@RequestMapping("/mascota") // Ruta en PLURAL para coincidir con React
public class MascotaController {

    @Autowired
    private MascotaRepository mascotaRepository;

    // 1. Ver todas las mascotas
    @GetMapping
    public ResponseEntity<Iterable<Mascota>> findAll() {
        return ResponseEntity.ok(mascotaRepository.findAll());
    }

    // 2. Crear mascota (SIMPLIFICADO PARA TU TAREA)
    @PostMapping
    public ResponseEntity<Mascota> create(@RequestBody Mascota mascota) {
        // Guardamos la mascota directamente sin validar cliente por ahora
        Mascota mascotaGuardada = mascotaRepository.save(mascota);
        return ResponseEntity.ok(mascotaGuardada);
    }

    // 3. Eliminar mascota (Útil para limpiar tu lista)
    @DeleteMapping("/{idMascota}")
    public ResponseEntity<Void> delete(@PathVariable Long idMascota) {
        if (mascotaRepository.existsById(idMascota)) {
            mascotaRepository.deleteById(idMascota);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}