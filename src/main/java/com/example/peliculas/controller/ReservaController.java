package com.example.peliculas.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.peliculas.entity.Reserva;
import com.example.peliculas.exception.DataAccessException;
import com.example.peliculas.repository.ReservaRepository;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {
	private final DataSource ds;
    public ReservaController(DataSource ds) {
        this.ds = ds;
    }
    
    @GetMapping("/usuario/{userId}")
    public List<Reserva> index(@PathVariable int id) {
        try (Connection con = ds.getConnection()) {
            ReservaRepository repo = new ReservaRepository(con);
            return repo.findByUserId(id);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
	
	
}
