package com.example.mascotas.mascotas;
import com.example.mascotas.mascotas.Mascota;
import com.example.mascotas.mascotas.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/mascota")
public class MascotaController {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private com.example.mascotas.clientes.ClienteRepository clienteRepository; // Inyectamos el repo de clientes

    @GetMapping
    public ResponseEntity<Iterable<Mascota>> findAll() {
        return ResponseEntity.ok(mascotaRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Mascota mascota) {
        // 1. Verificamos si la mascota trae un cliente asociado
        if (mascota.getCliente() != null && mascota.getCliente().getIdCliente() != null) {
            Long idCliente = mascota.getCliente().getIdCliente();

            // 2. Buscamos al cliente real en la DB
            return clienteRepository.findById(idCliente)
                    .map(cliente -> {
                        mascota.setCliente(cliente); // 3. Vinculamos el cliente persistente
                        Mascota guardada = mascotaRepository.save(mascota);
                        return ResponseEntity.ok(guardada);
                    })
                    .orElse(ResponseEntity.badRequest().build()); // Error si el ID no existe
        }

        return ResponseEntity.badRequest().body("La mascota debe tener un dueño válido.");
    }

    @DeleteMapping("/{idMascota}")
    public ResponseEntity<Void> delete(@PathVariable Long idMascota) {
        if (mascotaRepository.existsById(idMascota)) {
            mascotaRepository.deleteById(idMascota);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}