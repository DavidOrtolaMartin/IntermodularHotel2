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

import com.example.peliculas.dto.UserAdmin;
import com.example.peliculas.dto.UserEditAdmin;
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
    public List<UserAdmin> index() throws SQLException {
    	try (Connection con = ds.getConnection()) {
    	    UserRepository repo = new UserRepository(con);
    	    return repo.findAllUsersAdmins(); 
    	 } catch (SQLException e) {
    	        throw new DataAccessException(e);
    	 }
    }
    
    @GetMapping("/{id}")
    public UserEditAdmin showAdmin(@PathVariable int id) {
        try (Connection con = ds.getConnection()) {
            UserRepository repo = new UserRepository(con);
            return repo.findUserEditAdmin(id);
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
    public User updateAdmin(@PathVariable int id, @RequestBody UserEditAdmin u) {
        try (Connection con = ds.getConnection()) {
            UserRepository repo = new UserRepository(con);
            repo.updateAdmin(id, u); // Actualiza usando provinciaId de UserEditAdmin
            return repo.find(id);     // Devuelve usuario actualizado
        } catch (SQLException e) {
            throw new DataAccessException("Error actualizando usuario", e);
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


