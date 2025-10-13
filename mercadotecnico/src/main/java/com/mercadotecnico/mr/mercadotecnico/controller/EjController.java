package com.mercadotecnico.mr.mercadotecnico.controller;


import com.mercadotecnico.mr.mercadotecnico.model.Usuario;
import com.mercadotecnico.mr.mercadotecnico.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/")
    public String main() {
        return "hola";
    }

    @GetMapping("/user")
    public Optional<Usuario> getUser() {
        return bdd.findByNombre("Juan");
    }
}
