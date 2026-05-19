package com.example.peliculas.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.peliculas.entity.Provincia;

public class ProvinciaMapper implements RowMapper<Provincia>{

	@Override
	public Provincia map(ResultSet rs) throws SQLException {
		return new Provincia(
				rs.getInt("id_provincia"),
				rs.getString("nombre")				
				);
	}
	
}
