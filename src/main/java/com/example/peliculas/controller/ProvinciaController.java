package com.example.peliculas.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.peliculas.entity.Provincia;
import com.example.peliculas.exception.DataAccessException;
import com.example.peliculas.repository.ProvinciaRepository;

@RestController
@RequestMapping("/api/provincias")
public class ProvinciaController {

    private final DataSource ds;

    public ProvinciaController(DataSource ds) {
        this.ds = ds;
    }

    @GetMapping
    public List<Provincia> index() {

        try (Connection con = ds.getConnection()) {

            ProvinciaRepository repo = new ProvinciaRepository(con);

            return repo.findAll();

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}