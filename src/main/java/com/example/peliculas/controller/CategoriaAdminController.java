package com.example.peliculas.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.peliculas.dto.CategoriaDetalleResponse;
import com.example.peliculas.dto.CategoriaResumenResponse;
import com.example.peliculas.dto.ImagenResponse;
import com.example.peliculas.entity.Categoria;
import com.example.peliculas.exception.DataAccessException;
import com.example.peliculas.exception.NotFoundException;
import com.example.peliculas.repository.CategoriaImagenRepository;
import com.example.peliculas.repository.CategoriaRepository;
import com.example.peliculas.helper.StorageHelper;


@RestController
@RequestMapping("/api/admin/categorias")
public class CategoriaAdminController {
	
	private final DataSource ds;	
	private final StorageHelper storage;

	public CategoriaAdminController(DataSource ds, StorageHelper storage) {
		this.ds = ds;
		this.storage = storage;
	}
	
	@GetMapping
    public List<CategoriaResumenResponse> index() throws SQLException {
    	try (Connection con = ds.getConnection()) {
    	    CategoriaRepository repo = new CategoriaRepository(con);
    	    return repo.findAllResumenResponses();
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
	
	
	@PostMapping
    public Categoria store(@RequestBody Categoria categoria) {
        try (Connection con = ds.getConnection()) {
        	CategoriaRepository repo = new CategoriaRepository(con);
            repo.insert(categoria);
            return categoria;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
	

	@PutMapping("/{id}")
    public Categoria update(@PathVariable int id, @RequestBody Categoria categoria) {
    	System.out.println(categoria);
        try (Connection con = ds.getConnection()) {
        	CategoriaRepository repo = new CategoriaRepository(con);
            categoria.setId(id);
            repo.update(categoria);
            return categoria;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
	
	
	@DeleteMapping("/{id}")
    public void destroy(@PathVariable int id) {
        try (Connection con = ds.getConnection()) {
        	CategoriaRepository repo = new CategoriaRepository(con);
        	CategoriaImagenRepository imgRepo = new CategoriaImagenRepository(con);
        	
        	var imagenes = imgRepo.findByCategoriaId(id);
        	if (repo.tieneHabitaciones(id)) {//esto es para ver si hay hab asignadas a la categoria antes de borrarla
        	    throw new ResponseStatusException(HttpStatus.CONFLICT, 
        	        "No se puede eliminar: hay habitaciones asignadas a esta categoría");
        	}
        	if(repo.delete(id) == false) {
        		throw new NotFoundException("No se ha encontrado el id a eliminar");
        	}
        	
        	for(var imagen : imagenes) {
        		storage.deleteByUrl(imagen.url());
        	}
        	
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
	
}
