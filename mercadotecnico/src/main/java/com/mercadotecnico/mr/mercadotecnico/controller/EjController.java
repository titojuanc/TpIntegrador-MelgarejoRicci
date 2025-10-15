package com.mercadotecnico.mr.mercadotecnico.controller;


import com.mercadotecnico.mr.mercadotecnico.model.Usuario;
import com.mercadotecnico.mr.mercadotecnico.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class EjController {

    UserRepository bdd;

    public EjController(UserRepository bdd){
        this.bdd=bdd;
    }

    @GetMapping("/holamundo")
    public String holaMundo() {
        return "hola Mundo!";
    }

    @GetMapping("/user")
    public Optional<Usuario> getUser() {
        return bdd.findByNombre("Juan");
    }

    //  GET - obtener por nombre
    @GetMapping("/user/{nombre}")
    public Optional<Usuario> obtenerPorNombre(@PathVariable String nombre) {
        System.out.println(nombre);
        return bdd.findByNombre(nombre);
    }

    @PostMapping("/register")
    public Usuario crear(@RequestBody Usuario usuario) {
        System.out.println(usuario);
        return bdd.save(usuario);
    }
}
