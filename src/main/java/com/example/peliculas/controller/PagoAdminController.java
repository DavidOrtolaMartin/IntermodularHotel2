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

import com.example.peliculas.entity.Pago;
import com.example.peliculas.exception.DataAccessException;
import com.example.peliculas.repository.PagoRepository;


@RestController
@RequestMapping("/api/admin/provincias")
public class PagoAdminController {

private final DataSource ds;
	
	public PagoAdminController(DataSource ds) {
		this.ds = ds;
	}
	
	@GetMapping
    public List<Pago> index() throws SQLException {
    	try (Connection con = ds.getConnection()) {
    	    PagoRepository repo = new PagoRepository(con);
    	    return repo.findAll();
    	 } catch (SQLException e) {
    	        throw new DataAccessException(e);
    	 }
    }
	
	@GetMapping("/{id}")
    public Pago show(@PathVariable int id) {
        try (Connection con = ds.getConnection()) {
        	PagoRepository repo = new PagoRepository(con);
            return repo.find(id);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
	
	
	@PostMapping
    public Pago store(@RequestBody Pago pago) {
        try (Connection con = ds.getConnection()) {
        	PagoRepository repo = new PagoRepository(con);
            repo.insert(pago);
            return pago;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
	

	@PutMapping("/{id}")
    public Pago update(@PathVariable int id, @RequestBody Pago pago) {
    	System.out.println(pago);
        try (Connection con = ds.getConnection()) {
        	PagoRepository repo = new PagoRepository(con);
            pago.setId(id);
            repo.update(pago);
            return pago;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
	
	
	@DeleteMapping("/{id}")
    public void destroy(@PathVariable int id) {
        try (Connection con = ds.getConnection()) {
        	PagoRepository repo = new PagoRepository(con);
            repo.delete(id);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
	
	
	
}
