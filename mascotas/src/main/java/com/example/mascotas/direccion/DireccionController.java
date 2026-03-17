package com.example.mascotas.direccion;


import com.example.mascotas.clientes.Cliente;
import com.example.mascotas.clientes.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/direccion")

public class DireccionController {

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // Obtener todas las direcciones
    @GetMapping
    public ResponseEntity<Iterable<Direccion>> findAll() {
        return ResponseEntity.ok(direccionRepository.findAll());
    }

    // Crear direccion
    @PostMapping
    public ResponseEntity<Direccion> create(
            @RequestBody Direccion direccion,
            UriComponentsBuilder uriBuilder) {

        Optional<Cliente> clienteOptional =
                clienteRepository.findById(direccion.getCliente().getIdCliente());

        if (!clienteOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        direccion.setCliente(clienteOptional.get());

        Direccion direccionGuardada = direccionRepository.save(direccion);

        var uri = uriBuilder
                .path("/direccion/{id}")
                .buildAndExpand(direccionGuardada.getIdDireccion())
                .toUri();

        return ResponseEntity.created(uri).body(direccionGuardada);
    }

    // Buscar direccion por id
    @GetMapping("/{idDireccion}")
    public ResponseEntity<Direccion> findById(@PathVariable Long idDireccion) {

        Optional<Direccion> direccionOptional = direccionRepository.findById(idDireccion);

        if (direccionOptional.isPresent()) {
            return ResponseEntity.ok(direccionOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Actualizar direccion
        @PutMapping("/{idDireccion}")
        public ResponseEntity<Direccion> update(
                @PathVariable Long idDireccion,
                @RequestBody Direccion direccionActualizada) {

            Optional<Direccion> direccionOptional = direccionRepository.findById(idDireccion);

            if (!direccionOptional.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Direccion direccionExistente = direccionOptional.get();

            direccionExistente.setCalle(direccionActualizada.getCalle());
            direccionExistente.setNumero(direccionActualizada.getNumero());

            Direccion direccionUpdate = direccionRepository.save(direccionExistente);

            return ResponseEntity.ok(direccionUpdate);
        }

    @DeleteMapping("/{idDireccion}")
    public ResponseEntity<Void> delete(@PathVariable Long idDireccion) {

        System.out.println("Eliminando direccion con ID: " + idDireccion);

        if (direccionRepository.existsById(idDireccion)) {
            direccionRepository.deleteById(idDireccion);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    }

