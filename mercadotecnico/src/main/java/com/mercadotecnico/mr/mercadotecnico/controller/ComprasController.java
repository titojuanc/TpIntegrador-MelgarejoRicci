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


    public ComprasController(CompraService servicio_compras) {
        this.servicio_compras = servicio_compras;
    }

    @PostMapping("POST/api/usuarios/{idUsuario}/compras/{idPublicacion}")
    public ResponseEntity<?> comprar(@PathVariable Long idUsuario, @PathVariable Long idPublicacion, @RequestBody CompraDTO dto){
        return servicio_compras.crear(dto, idUsuario, idPublicacion);
    }

    @GetMapping("GET/api/usuarios/{id}/compras/")
    public List<Compra> mostrarCompras(@PathVariable Long id){
        return servicio_compras.comprasPorUsuario(id);
    }


}
