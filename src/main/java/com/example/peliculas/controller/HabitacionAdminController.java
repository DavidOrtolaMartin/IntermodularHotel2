package com.example.peliculas.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.peliculas.dto.HabitacionDTO;
import com.example.peliculas.entity.Habitacion;
import com.example.peliculas.exception.DataAccessException;
import com.example.peliculas.repository.HabitacionRepository;


@RestController
@RequestMapping("/api/admin/habitaciones")
public class HabitacionAdminController {
	private final DataSource ds;
	
	public HabitacionAdminController(DataSource ds) {
		this.ds = ds;
	}
	
	@GetMapping
    public List<HabitacionDTO> index() throws SQLException {
    	try (Connection con = ds.getConnection()) {
    	    HabitacionRepository repo = new HabitacionRepository(con);
    	    return repo.findAllHabitaciones();
    	 } catch (SQLException e) {
    	        throw new DataAccessException(e);
    	 }
    }
	
	@GetMapping("/{id}")
    public Habitacion show(@PathVariable int id) {
        try (Connection con = ds.getConnection()) {
        	HabitacionRepository repo = new HabitacionRepository(con);
            return repo.find(id);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
	
	
	@PostMapping
    public Habitacion store(@RequestBody Habitacion habitacion) {
        try (Connection con = ds.getConnection()) {
        	HabitacionRepository repo = new HabitacionRepository(con);
            repo.insert(habitacion);
            return habitacion;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
	

	@PutMapping("/{id}")
    public Habitacion update(@PathVariable int id, @RequestBody Habitacion habitacion) {
    	System.out.println(habitacion);
        try (Connection con = ds.getConnection()) {
        	HabitacionRepository repo = new HabitacionRepository(con);
            habitacion.setId(id);
            repo.update(habitacion);
            return habitacion;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
	
	
	@DeleteMapping("/{id}")
    public void destroy(@PathVariable int id) {
        try (Connection con = ds.getConnection()) {
        	HabitacionRepository repo = new HabitacionRepository(con);
            repo.delete(id);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
	
	
	

}
