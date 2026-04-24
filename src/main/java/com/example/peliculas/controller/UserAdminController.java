package com.example.peliculas.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.peliculas.db.DB;
import com.example.peliculas.dto.UserAdmin;
import com.example.peliculas.dto.UserStoreRequest;
import com.example.peliculas.dto.UserUpdateRequest;
import com.example.peliculas.entity.User;
import com.example.peliculas.exception.DataAccessException;
import com.example.peliculas.repository.UserRepository;

@RestController
@RequestMapping("/api/admin/users")
public class UserAdminController {
	private final DataSource ds;
	private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

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
    public UserUpdateRequest showAdmin(@PathVariable int id) {
    	System.out.println("JLD2:Dentro UserEditAdmin");
        try (Connection con = ds.getConnection()) {
        	System.out.println("JLD3:Dentro UserEditAdmin try");
            UserRepository repo = new UserRepository(con);
            System.out.println("JLD3:Dentro UserEditAdmin aantes Find");
            return repo.findUserEditAdmin(id);
            

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
  

  
    
    @PostMapping
    public User store(@RequestBody UserStoreRequest u) {
        try (Connection con = ds.getConnection()) {
            UserRepository repo = new UserRepository(con);

            User user = map(u);
            repo.insert(user);

            return user;

        } catch (SQLException e) {
            throw new DataAccessException("Error creando usuario", e);
        }
    }

   
    
    @PutMapping("/{id}")
    public User updateAdmin(@PathVariable int id, @RequestBody UserUpdateRequest u) {
    	System.out.println("---------------------------------------------------------------------------");
    	System.out.println("JLD3:Dentro Userupdte antedtry Find");
        try (Connection con = ds.getConnection()) {
        	System.out.println("JLD3:Dentro UserEditAdmin despuestry Find");
            UserRepository repo = new UserRepository(con);
            System.out.println("JLD3:Dentro UserEditAdmin antesupdatedadmin u:" + u);
            repo.updateAdmin(id, u); // Actualiza usando provinciaId de UserEditAdmin
            System.out.println("JLD3:Dentro UserEditAdmin despuesupdateadmin Find");
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
    
    // TEMPORAL OJO ESTO NO DEBERIA IR AQUI OJOOOOOOOOOOOOOOO
    @GetMapping("/provincias")
    public List<Map<String, Object>> provincias() {
        try (Connection con = ds.getConnection()) {

            String sql = "SELECT id_provincia AS id, nombre FROM provincia";

            return DB.queryMany(con, sql, rs -> {
                Map<String, Object> p = new HashMap<>();
                p.put("id", rs.getInt("id"));
                p.put("nombre", rs.getString("nombre"));
                return p;
            });

        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
    
    private User map(UserStoreRequest u) {
        return new User(
            null,              // id
            u.name(),          // name
            u.apellido1(),
            u.apellido2(),
            u.tlf1(),
            u.tlf2(),
            u.role(),
            u.email(),
            encoder.encode(u.password()),
            u.provinciaId()
        );
    }
    
    
}


