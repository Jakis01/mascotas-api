package com.example.mascotas.mascotas;

import com.example.mascotas.clientes.Cliente;
import com.example.mascotas.clientes.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mascota")

public class MascotaController {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    /*Obtener todas las mascotas*/
    @GetMapping
    public ResponseEntity<Iterable<Mascota>> findAll() {
        return ResponseEntity.ok(mascotaRepository.findAll());
    }

    //Crear mascota
    @PostMapping
    public ResponseEntity<Mascota> create
    (@RequestBody Mascota mascota,
     UriComponentsBuilder uriComponentsBuilder) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(mascota.getCliente().getIdCliente());
        if (!clienteOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        mascota.setCliente(clienteOptional.get());
        Mascota mascotaGuardada = mascotaRepository.save(mascota);

        var uri = uriComponentsBuilder
                .path("/mascota/{id}")
                .buildAndExpand(mascotaGuardada.getIdMascota())
                .toUri();

        return ResponseEntity.created(uri).body(mascotaGuardada);
    }

    //Buscar mascota por ID
    @GetMapping("/{idMascota}")
    public ResponseEntity<Mascota> findByIdMascota(@PathVariable Long idMascota) {
        Optional<Mascota> mascotaOptional = mascotaRepository.findById(idMascota);

        if (!mascotaOptional.isPresent()) {
            return ResponseEntity.ok(mascotaOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Actualizar mascota
    @PutMapping("/{idMascota}")
    public ResponseEntity<Mascota> update(
            @PathVariable Long idMascota,
        @RequestBody Mascota mascotaActualizada){

            Optional<Mascota> mascotaOptional = mascotaRepository.findById(idMascota);

            if (!mascotaOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Mascota mascotaExistente = mascotaOptional.get();

            mascotaExistente.setNombre(mascotaActualizada.getNombre());
            mascotaExistente.setEdad(mascotaActualizada.getEdad());
            mascotaExistente.setSexo(mascotaActualizada.getSexo());
            mascotaExistente.setTipo(mascotaActualizada.getTipo());
            mascotaExistente.setEnPeligro(mascotaActualizada.isEnPeligro());

            Mascota mascotaUpdate = mascotaRepository.save(mascotaExistente);

            return ResponseEntity.ok(mascotaUpdate);
        }

    //Eliminar mascota
        @DeleteMapping("/{idMascota}")
        public ResponseEntity<Void> delete(@PathVariable Long idMascota) {

            if (mascotaRepository.existsById(idMascota)) {
                mascotaRepository.deleteById(idMascota);
                return ResponseEntity.ok().build();
            }

            return ResponseEntity.notFound().build();
        }
        }
