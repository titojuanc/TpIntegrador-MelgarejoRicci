package com.mercadotecnico.mr.mercadotecnico.controller;

import com.mercadotecnico.mr.mercadotecnico.dto.CompraDTO;
import com.mercadotecnico.mr.mercadotecnico.model.Compra;
import com.mercadotecnico.mr.mercadotecnico.model.Usuario;
import com.mercadotecnico.mr.mercadotecnico.repository.UserRepository;
import com.mercadotecnico.mr.mercadotecnico.service.CompraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class ComprasController {

    CompraService servicio_compras;
    UserRepository bdd_usuarios;

    public ComprasController(CompraService servicio_compras, UserRepository bdd_usuarios) {
        this.servicio_compras = servicio_compras;
        this.bdd_usuarios = bdd_usuarios;
    }

    @PostMapping("POST/api/usuarios/{idUsuario}/compras/{idPublicacion}")
    public ResponseEntity<?> comprar(@PathVariable Long idUsuario, @PathVariable Long idPublicacion, @RequestBody CompraDTO dto){
        System.out.println(dto.getId_publicacion());
        return servicio_compras.crear(dto);
    }

    @GetMapping("GET/api/usuarios/{id}/compras/")
    public List<Compra> mostrarCompras(@PathVariable Long id){
        Usuario usuario = bdd_usuarios.findById(id).get();
        return servicio_compras.comprasPorUsuario(usuario);
    }


}
