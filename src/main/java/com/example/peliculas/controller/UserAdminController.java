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

import com.example.peliculas.dto.UserResponse;
import com.example.peliculas.entity.User;
import com.example.peliculas.exception.DataAccessException;
import com.example.peliculas.repository.UserRepository;

@RestController
@RequestMapping("/api/admin/users")
public class UserAdminController {
	private final DataSource ds;

    public UserAdminController(DataSource ds) {
    	this.ds = ds;
    }
    
    @GetMapping
    public List<UserResponse> index() throws SQLException {
    	try (Connection con = ds.getConnection()) {
    	    UserRepository repo = new UserRepository(con);
    	    return repo.findAllResponses(); // findResponses
    	 } catch (SQLException e) {
    	        throw new DataAccessException(e);
    	 }
    }
    
    @GetMapping("/{id}")
    public User show(@PathVariable int id) {
        try (Connection con = ds.getConnection()) {
        	UserRepository repo = new UserRepository(con);
            return repo.find(id);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @PostMapping
    public User store(@RequestBody User usuario) {
        try (Connection con = ds.getConnection()) {
        	UserRepository repo = new UserRepository(con);
            repo.insert(usuario);
            return usuario;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @PutMapping("/{id}")
    public User update(@PathVariable int id, @RequestBody User usuario) {
    	System.out.println(usuario);
        try (Connection con = ds.getConnection()) {
        	UserRepository repo = new UserRepository(con);
            usuario.setId(id);
            repo.update(usuario);
            return usuario;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @DeleteMapping("/{id}")
    public void destroy(@PathVariable int id) {
        try (Connection con = ds.getConnection()) {
        	UserRepository repo = new UserRepository(con);
            repo.delete(id);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}


