package com.example.peliculas.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.example.peliculas.db.DB;
import com.example.peliculas.dto.ImagenResponse;
import com.example.peliculas.entity.CategoriaImagen;
import com.example.peliculas.exception.DataAccessException;
import com.example.peliculas.mapper.CategoriaImagenMapper;
import com.example.peliculas.mapper.RowMapper;

public class CategoriaImagenRepository extends BaseRepository<CategoriaImagen>{

	public CategoriaImagenRepository(Connection con) {
		super(con, new CategoriaImagenMapper());
	}

	public CategoriaImagenRepository(Connection con, RowMapper<CategoriaImagen> mapper) {
		super(con, mapper);
	}

	@Override
	public String getTable() {
		return "categoria_imagenes";//cambiar bbdd
	}

	@Override
	public String[] getColumnNames() {
		return new String[] { "id", "categoria_id", "url" };
	}
	
	@Override
	public Integer getPrimaryKey(CategoriaImagen ci) {
		return ci.getId();
	}
	
	@Override
	public void setPrimaryKey(CategoriaImagen ci, int id) {
		ci.setId(id);
	}

	@Override
	public Object[] getInsertValues(CategoriaImagen ci) {
		return new Object[] { ci.getCategoriaId(), ci.getUrl() };
	}

	@Override
	public Object[] getUpdateValues(CategoriaImagen ci) {
		return new Object[] { ci.getCategoriaId(), ci.getUrl(), ci.getId() };
	}
	
	public List<ImagenResponse> findByCategoriaId(int habitacionId) {

		String sql = """
					SELECT id, url
					FROM categoria_imagenes
					WHERE categoria_id = ?
					ORDER BY id ASC
				""";

		try {
			return DB.queryMany(con, sql, rs -> new ImagenResponse(
				rs.getInt("id"), 
				rs.getString("url")
			), habitacionId);
			
		} catch (SQLException e) {
			throw new DataAccessException("Error Obteniendo las imágenes de la categoría");
		}
	}
	
}
