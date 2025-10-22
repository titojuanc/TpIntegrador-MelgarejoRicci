package com.mercadotecnico.mr.mercadotecnico.controller;


import com.mercadotecnico.mr.mercadotecnico.dto.PublicacionDTO;
import com.mercadotecnico.mr.mercadotecnico.model.Publicacion;
import com.mercadotecnico.mr.mercadotecnico.model.Usuario;
import com.mercadotecnico.mr.mercadotecnico.repository.PublicacionRepository;
import com.mercadotecnico.mr.mercadotecnico.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class EjController {

    UserRepository bdd_usuarios;
    PublicacionRepository bdd_publicaciones;

    public EjController(UserRepository bdd_usuarios, PublicacionRepository bdd_publicaciones){
        this.bdd_usuarios=bdd_usuarios;
        this.bdd_publicaciones=bdd_publicaciones;
    }

    //  GET - obtener por nombre
    @GetMapping("/GET/api/usuarios/{nombre}")
    public Optional<Usuario> obtenerPorNombre(@PathVariable String nombre) {
        System.out.println(nombre);
        return bdd_usuarios.findByNombre(nombre);
    }

    // Punto 3a
    @PostMapping("/POST/api/usuarios")
    public Usuario crearUsuario(@RequestBody Usuario usuario) {
        System.out.println(usuario);
        return bdd_usuarios.save(usuario);
    }


    @GetMapping("/GET/api/publicacion/{id}")
    public Optional<Publicacion> obtenerPorId(@PathVariable Long id){
        return bdd_publicaciones.findById(id);
    }

    @GetMapping("/GET/api/usuarios/{idUsuario}/publicaciones")
    public Optional<List<Publicacion>> obtenerPublicacionesDeUsuario(@PathVariable Long idUsuario){
        return bdd_publicaciones.findByUsuario_Id(idUsuario);
    }


    // punto 3b
    @PostMapping("POST/api/usuarios/{idUsuario}/publicaciones")
    public void crearPublicacion(@PathVariable Long idUsuario, @RequestBody PublicacionDTO publicacion){
        System.out.println(publicacion);
    }

}
