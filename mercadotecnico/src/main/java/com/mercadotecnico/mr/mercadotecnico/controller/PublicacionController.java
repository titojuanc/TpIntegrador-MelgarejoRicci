package com.mercadotecnico.mr.mercadotecnico.controller;

import com.mercadotecnico.mr.mercadotecnico.dto.PublicacionDTO;
import com.mercadotecnico.mr.mercadotecnico.model.*;
import com.mercadotecnico.mr.mercadotecnico.repository.*;
import com.mercadotecnico.mr.mercadotecnico.service.PublicacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class PublicacionController {

    UserRepository bdd_usuarios;
    PublicacionRepository bdd_publicaciones;
    DiaReposiroty bdd_dias;
    ServicioRepository bdd_servicios;
    ProductoRepository bdd_productos;
    DiasServicioRepository bdd_diasDeServicio;
    CategoriaRepository bdd_categoria;
    CalendarioRepository bdd_calendario;
    PublicacionService servicio_publicaciones;

    public PublicacionController(UserRepository bdd_usuarios, PublicacionRepository bdd_publicaciones, DiaReposiroty bdd_dias, ServicioRepository bdd_servicios, ProductoRepository bdd_productos, DiasServicioRepository bdd_diasDeServicio, CategoriaRepository bdd_categoria, CalendarioRepository bdd_calendario, PublicacionService servicio_publicaciones) {
        this.bdd_usuarios = bdd_usuarios;
        this.bdd_publicaciones = bdd_publicaciones;
        this.bdd_dias = bdd_dias;
        this.bdd_servicios = bdd_servicios;
        this.bdd_productos = bdd_productos;
        this.bdd_diasDeServicio = bdd_diasDeServicio;
        this.bdd_categoria = bdd_categoria;
        this.bdd_calendario = bdd_calendario;
        this.servicio_publicaciones = servicio_publicaciones;
    }

    @PostMapping("POST/api/usuarios/{idUsuario}/publicaciones")
    public void crearPublicacion(@PathVariable Long idUsuario, @RequestBody PublicacionDTO dto){
        System.out.println(dto );
        Usuario usuario =  bdd_usuarios.findById(dto.getId_usuario()).get();
        System.out.println(usuario.getNombre());
        Publicacion publicacion = new Publicacion(dto.getNombre(), dto.getDescripcion(), dto.getFechaPublicacion(), dto.getPrecio(), usuario, dto.getEstado());
        System.out.println(publicacion.getEstado());
        bdd_publicaciones.save(publicacion);
        if (dto.getTipo().equals("Producto")){
            Producto producto = new Producto(publicacion, dto.getGarantia(), dto.getStock(), bdd_categoria.findById((long) dto.getCategoria()).get(), dto.isUsado() );
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

    @GetMapping("/GET/api/publicacion/{id}")
    public Optional<Publicacion> obtenerPorId(@PathVariable Long id){
        return bdd_publicaciones.findById(id);
    }

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
}
