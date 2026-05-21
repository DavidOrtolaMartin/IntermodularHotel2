package com.example.peliculas.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.peliculas.dto.CategoriaDetalleResponse;
import com.example.peliculas.dto.CategoriaResumenResponse;
import com.example.peliculas.dto.ImagenResponse;
import com.example.peliculas.entity.Categoria;
import com.example.peliculas.exception.DataAccessException;
import com.example.peliculas.repository.CategoriaImagenRepository;
import com.example.peliculas.repository.CategoriaRepository;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final DataSource ds;

    public CategoriaController(DataSource ds) {
        this.ds = ds;
    }

    @GetMapping
    public List<CategoriaResumenResponse> index() {
        try (Connection con = ds.getConnection()) {
            return new CategoriaRepository(con).findAllResumenResponses();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @GetMapping("/{id}")
    public CategoriaDetalleResponse show(@PathVariable int id) {
        try (Connection con = ds.getConnection()) {
            CategoriaRepository repo = new CategoriaRepository(con);
            CategoriaImagenRepository imgRepo = new CategoriaImagenRepository(con);

            Categoria c = repo.findOrThrow(id);
            List<ImagenResponse> imagenes = imgRepo.findByCategoriaId(id);

            return new CategoriaDetalleResponse(
                c.getId(),
                c.getNombre(),
                c.getDescripcion(),
                c.getPrecio(),
                imagenes
            );
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}