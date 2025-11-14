package com.mercadotecnico.mr.mercadotecnico.service;

import com.mercadotecnico.mr.mercadotecnico.dto.CompraDTO;
import com.mercadotecnico.mr.mercadotecnico.model.*;
import com.mercadotecnico.mr.mercadotecnico.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompraService {
    private CompraRepository bdd_compras;
    private PublicacionRepository bdd_publicaciones;
    private ProductoRepository bdd_producto;
    private UserRepository bdd_usuario;
    private CalendarioRepository bdd_calendario;
    private ServicioRepository bdd_servicio;
    private DiaReposiroty bdd_dia;

    public CompraService(CompraRepository bdd_compras, PublicacionRepository bdd_publicaciones, ProductoRepository bdd_producto, UserRepository bdd_usuario, CalendarioRepository bdd_calendario, ServicioRepository bdd_servicio, DiaReposiroty bdd_dia) {
        this.bdd_compras = bdd_compras;
        this.bdd_publicaciones = bdd_publicaciones;
        this.bdd_producto = bdd_producto;
        this.bdd_usuario = bdd_usuario;
        this.bdd_calendario = bdd_calendario;
        this.bdd_servicio = bdd_servicio;
        this.bdd_dia = bdd_dia;
    }

    public ResponseEntity<?> crear(CompraDTO dto, Long id_usuario, Long id_publicacion){
        if (bdd_publicaciones.findById(id_publicacion).isEmpty()){
            return ResponseEntity.ok("que");
        }
        Publicacion publicacion = bdd_publicaciones.findById(id_publicacion).get();
        Usuario usuario = bdd_usuario.findById(id_usuario).get();
        if (dto.getDias_elegidos().isEmpty()){
            System.out.println(dto.getCantidad());
            System.out.println(bdd_producto.findById(id_publicacion).get().getStock());
            if (bdd_producto.findById(id_publicacion).get().getStock()>=dto.getCantidad() ){
                Compra compra = new Compra(LocalDateTime.now(), dto.getTotal(), usuario, publicacion, dto.getCantidad());
                bdd_compras.save(compra);
                Producto producto = bdd_producto.findById(id_publicacion).get();
                producto.setStock(producto.getStock()-dto.getCantidad());
                bdd_producto.save(producto);
                return ResponseEntity.ok("Compra realizada");
            } else {
                return ResponseEntity.ok("Error, No hay suficiente stock");
            }
        } else {
            System.out.println("entré acá");
            Servicio servicio = bdd_servicio.findById(id_publicacion).get();
            List<Calendario> calendarios = bdd_calendario.findByServicio(servicio);
            if (!calendarios.isEmpty()){
                for (Calendario calendario : calendarios){
                    if (dto.getDias_elegidos().contains(calendario.getDia().getId())){
                        return ResponseEntity.ok("Error, ese día no está disponible");
                    }
                }
            }
            Compra compra = new Compra(LocalDateTime.now(), dto.getTotal(), usuario, publicacion, dto.getCantidad());
            bdd_compras.save(compra);
            for (Long dia : dto.getDias_elegidos()){
                int i = 0;
                Calendario calendario = new Calendario(dto.getFechas_elegidas().get(i), servicio, usuario, bdd_dia.findById(dia).get());
                bdd_calendario.save(calendario);
                return ResponseEntity.ok("Compra realizada");
            }
        }
        return ResponseEntity.ok("Cómo pingo llegué acá???");
    }

    public List<Compra> comprasPorUsuario(Long id){
        Usuario usuario = bdd_usuario.findById(id).get();
        return bdd_compras.findByUsuario(usuario);
    }
}
