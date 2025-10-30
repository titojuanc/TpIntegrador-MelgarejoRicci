package com.mercadotecnico.mr.mercadotecnico.controller;


import com.mercadotecnico.mr.mercadotecnico.dto.CompraDTO;
import com.mercadotecnico.mr.mercadotecnico.dto.PublicacionDTO;
import com.mercadotecnico.mr.mercadotecnico.model.*;
import com.mercadotecnico.mr.mercadotecnico.repository.*;
import com.mercadotecnico.mr.mercadotecnico.service.CompraService;
import com.mercadotecnico.mr.mercadotecnico.service.PublicacionService;
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
    CalendarioRepository bdd_calendario;
    PublicacionService servicio_publicaciones;
    CompraService servicio_compras;

    public EjController(UserRepository bdd_usuarios, PublicacionRepository bdd_publicaciones, DiaReposiroty bdd_dias, ServicioRepository bdd_servicios, DiasServicioRepository bdd_diasDeServicio, ProductoRepository bdd_productos, CalendarioRepository bdd_calendario, PublicacionService servicio_publicaciones, CompraService servicio_compras) {
        this.bdd_usuarios = bdd_usuarios;
        this.bdd_publicaciones = bdd_publicaciones;
        this.bdd_dias = bdd_dias;
        this.bdd_servicios = bdd_servicios;
        this.bdd_diasDeServicio = bdd_diasDeServicio;
        this.bdd_productos = bdd_productos;
        this.bdd_calendario = bdd_calendario;
        this.servicio_publicaciones = servicio_publicaciones;
        this.servicio_compras = servicio_compras;
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


    // punto 3c
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
        Publicacion publicacion = new Publicacion(dto.getNombre(), dto.getDescripcion(), dto.getFechaPublicacion(), dto.getPrecio(), usuario, dto.getEstado());
        System.out.println(publicacion.getEstado());
        bdd_publicaciones.save(publicacion);
        if (dto.getTipo().equals("Producto")){
            Producto producto = new Producto(publicacion, dto.getGarantia(), dto.getStock());
            System.out.println(producto.getGarantia());
            bdd_productos.save(producto);
        }
        else {
            Servicio servicio = new Servicio(publicacion);
            bdd_servicios.save(servicio);
            for (Long d : dto.getDias()){
                Dia diaAux = bdd_dias.findById(d).get();
                Servicio_has_dia diasdeservicio = new Servicio_has_dia(servicio, diaAux);
                System.out.println(diasdeservicio.getServicio().getPublicacion().getNombre());
                System.out.println(bdd_dias.findById(d).get().getDia());
                bdd_diasDeServicio.save(diasdeservicio);
            }
        }
    }


    //3d - debería poder accederse sólo desde la cuenta de un admin
    @DeleteMapping("DELETE/api/admin/publicaciones/{id}")
    public ResponseEntity<?> eliminarPublicacion(@PathVariable Long id) throws Exception {
        if (bdd_publicaciones.findById(id).isPresent()){
            Publicacion publicacion = bdd_publicaciones.findById(id).get();
            if (bdd_servicios.findById(id).isPresent()){
                if (!bdd_calendario.findByServicio(bdd_servicios.findById(id).get()).isEmpty()){
                    servicio_publicaciones.actualizarEstado(id, "En pausa");
                    return ResponseEntity.ok("La publicación se pausó porque hay usuarios que tienen contratado el servicio. Una vez finalizado, puede borrarse");
                } else {
                    bdd_servicios.delete(bdd_servicios.findById(id).get());
                    bdd_publicaciones.delete(publicacion);
                    return ResponseEntity.ok("Se borró con éxito");
                }
            } else { // Si no es servicio, es producto si o si
                bdd_productos.delete(bdd_productos.findById(id).get());
                bdd_publicaciones.delete(publicacion);
                return ResponseEntity.ok("Se borró con éxito");
            }
        } else {
            return ResponseEntity.badRequest().body("No existe publicacion con ese ID");
        }
    }

    //3e
    @GetMapping("GET/api/usuarios/{id}/compras/")
    public List<Compra> mostrarCompras(@PathVariable Long id){
        Usuario usuario = bdd_usuarios.findById(id).get();
        return servicio_compras.comprasPorUsuario(usuario);
    }

    //3f
    @PostMapping("POST/api/usuarios/{idUsuario}/compras/{idPublicacion}")
    public ResponseEntity<?> comprar(@PathVariable Long idUsuario, @PathVariable Long idPublicacion, @RequestBody CompraDTO dto){
        System.out.println(dto.getId_publicacion());
        return servicio_compras.crear(dto);
    }

    
}
