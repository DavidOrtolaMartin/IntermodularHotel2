package com.example.peliculas.controller;

import java.io.IOException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.peliculas.entity.CategoriaImagen;
import com.example.peliculas.exception.DataAccessException;
import com.example.peliculas.helper.StorageHelper;
import com.example.peliculas.repository.CategoriaImagenRepository;
import com.example.peliculas.repository.CategoriaRepository;
import com.example.peliculas.repository.DirectorRepository;
import com.example.peliculas.validation.ImageValidator;

@RestController
@RequestMapping("/api/admin/categorias/{categoriaId}/imagenes")
public class CategoriaImagenController extends BaseController {

	private final StorageHelper storage;

	public CategoriaImagenController(DataSource ds, StorageHelper storage) {
		super(ds);
		this.storage = storage;
	}

	// CREATE
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CategoriaImagen store(@PathVariable int categoriaId, @RequestParam("file") MultipartFile file) {

		try (Connection con = ds.getConnection()) {

			// validar existencia
			new CategoriaRepository(con).findOrThrow(categoriaId);
			
			ImageValidator.validate(file);

			String url = storage.save(file, "categorias");

			CategoriaImagenRepository repo = new CategoriaImagenRepository(con);

			CategoriaImagen img = new CategoriaImagen(null, categoriaId, url);

			return repo.insert(img);

		} catch (SQLException e) {
			throw new DataAccessException(e);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// DELETE
	@DeleteMapping("/{id}")
	public void delete(@PathVariable int id) {

		try (Connection con = ds.getConnection()) {

			CategoriaImagenRepository repo = new CategoriaImagenRepository(con);

			// opcional: obtener url antes de borrar
			CategoriaImagen img = repo.find(id);

			repo.delete(id);

			if (img != null && img.getUrl() != null) {
				storage.deleteByUrl(img.getUrl());
			}

		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
}
