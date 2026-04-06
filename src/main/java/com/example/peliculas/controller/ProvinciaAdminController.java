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

import com.example.peliculas.entity.Provincia;
import com.example.peliculas.exception.DataAccessException;
import com.example.peliculas.repository.ProvinciaRepository;

@RestController
@RequestMapping("/api/admin/provincias")
public class ProvinciaAdminController {

	
private final DataSource ds;
	
	public ProvinciaAdminController(DataSource ds) {
		this.ds = ds;
	}
	
	@GetMapping
    public List<Provincia> index() throws SQLException {
    	try (Connection con = ds.getConnection()) {
    	    ProvinciaRepository repo = new ProvinciaRepository(con);
    	    return repo.findAll();
    	 } catch (SQLException e) {
    	        throw new DataAccessException(e);
    	 }
    }
	
	@GetMapping("/{id}")
    public Provincia show(@PathVariable int id) {
        try (Connection con = ds.getConnection()) {
        	ProvinciaRepository repo = new ProvinciaRepository(con);
            return repo.find(id);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
	
	
	@PostMapping
    public Provincia store(@RequestBody Provincia provincia) {
        try (Connection con = ds.getConnection()) {
        	ProvinciaRepository repo = new ProvinciaRepository(con);
            repo.insert(provincia);
            return provincia;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
	

	@PutMapping("/{id}")
    public Provincia update(@PathVariable int id, @RequestBody Provincia provincia) {
    	System.out.println(provincia);
        try (Connection con = ds.getConnection()) {
        	ProvinciaRepository repo = new ProvinciaRepository(con);
            provincia.setId(id);
            repo.update(provincia);
            return provincia;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
	
	
	@DeleteMapping("/{id}")
    public void destroy(@PathVariable int id) {
        try (Connection con = ds.getConnection()) {
        	ProvinciaRepository repo = new ProvinciaRepository(con);
            repo.delete(id);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
	
	
}
