package com.mercadotecnico.mr.mercadotecnico.controller;


import com.mercadotecnico.mr.mercadotecnico.dto.PublicacionDTO;
import com.mercadotecnico.mr.mercadotecnico.model.*;
import com.mercadotecnico.mr.mercadotecnico.repository.*;
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
    DiaReposiroty bdd_dias;
    ServicioRepository bdd_servicios;
    DiasServicioRepository bdd_diasDeServicio;
    ProductoRepository bdd_productos;

    public EjController(UserRepository bdd_usuarios, PublicacionRepository bdd_publicaciones, DiaReposiroty bdd_dias, ServicioRepository bdd_servicios, DiasServicioRepository bdd_diasDeServicio, ProductoRepository bdd_productos){
        this.bdd_usuarios=bdd_usuarios;
        this.bdd_publicaciones=bdd_publicaciones;
        this.bdd_dias=bdd_dias;
        this.bdd_diasDeServicio= bdd_diasDeServicio;
        this.bdd_servicios = bdd_servicios;
        this.bdd_productos=bdd_productos;
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
    public void crearPublicacion(@PathVariable Long idUsuario, @RequestBody PublicacionDTO dto){
        System.out.println(dto );
        Usuario usuario =  bdd_usuarios.findById(dto.getId_usuario()).get();
        System.out.println(usuario.getNombre());
        Publicacion publicacion = new Publicacion(dto.getNombre(), dto.getDescripcion(), dto.getFechaPublicacion(), dto.getPrecio(), dto.getStock(), usuario, dto.getEstado());
        System.out.println(publicacion.getEstado());
        bdd_publicaciones.save(publicacion);
        if (dto.getTipo().equals("Producto")){
            Producto producto = new Producto(publicacion, dto.getGarantia());
            System.out.println(producto.getGarantia());
            bdd_productos.save(producto);
        }
    }

}
