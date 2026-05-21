package com.example.peliculas.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.peliculas.entity.CategoriaImagen;

public class CategoriaImagenMapper implements RowMapper<CategoriaImagen> {
	@Override
	public CategoriaImagen map(ResultSet rs) throws SQLException {
		return new CategoriaImagen(
			rs.getInt("id"),
			rs.getInt("categoria_id"), 
			rs.getString("url")
		);
	}
}
