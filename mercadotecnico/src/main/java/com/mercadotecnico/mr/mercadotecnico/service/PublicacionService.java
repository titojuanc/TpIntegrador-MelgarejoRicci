package com.mercadotecnico.mr.mercadotecnico.service;

import com.mercadotecnico.mr.mercadotecnico.dto.PublicacionDTO;
import com.mercadotecnico.mr.mercadotecnico.model.*;
import com.mercadotecnico.mr.mercadotecnico.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PublicacionService {
    private PublicacionRepository bdd_publicaciones;
    private UserRepository bdd_usuarios;
    private ProductoRepository bdd_productos;
    private ServicioRepository bdd_servicios;
    private DiasServicioRepository bdd_diasDeServicio;
    private DiaReposiroty bdd_dias;
    private CategoriaRepository bdd_categorias;

    public PublicacionService(PublicacionRepository bdd_publicaciones, UserRepository bdd_usuarios, ProductoRepository bdd_productos, ServicioRepository bdd_servicios, DiasServicioRepository bdd_diasDeServicio, DiaReposiroty bdd_dias, CategoriaRepository bdd_categorias) {
        this.bdd_publicaciones = bdd_publicaciones;
        this.bdd_usuarios = bdd_usuarios;
        this.bdd_productos = bdd_productos;
        this.bdd_servicios = bdd_servicios;
        this.bdd_diasDeServicio = bdd_diasDeServicio;
        this.bdd_dias = bdd_dias;
        this.bdd_categorias = bdd_categorias;
    }

    public Publicacion actualizarEstado(Long id, String estado) throws Exception {
        Publicacion actualizar = bdd_publicaciones.findById(id).get();

        if (actualizar == null) {
            throw new Exception("No se encontró la especialidad con ID " + id);
        }

        actualizar.setEstado(estado);
        return bdd_publicaciones.save(actualizar);
    }

    public void crearPublicacion(Long idUsuario,
                                 PublicacionDTO dto
                                 ){
        System.out.println(dto );
        Usuario usuario =  bdd_usuarios.findById(dto.getId_usuario()).get();
        System.out.println(usuario.getNombre());
        Publicacion publicacion = new Publicacion(dto.getNombre(), dto.getDescripcion(), dto.getFechaPublicacion(), dto.getPrecio(), usuario, dto.getEstado());
        System.out.println(publicacion.getEstado());
        bdd_publicaciones.save(publicacion);
        if (dto.getTipo().equals("Producto")){
            Producto producto = new Producto(publicacion, dto.getGarantia(), dto.getStock(), bdd_categorias.findById((long) dto.getCategoria()).get(), dto.isUsado() );
            System.out.println(producto.getGarantia());
            bdd_productos.save(producto);
        }
        else {
            Servicio servicio = new Servicio(publicacion, dto.getFrecuencia());
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
}
