package com.example.peliculas.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.peliculas.entity.Habitacion;

public class HabitacionMapper implements RowMapper<Habitacion>{

	@Override
	public Habitacion map(ResultSet rs) throws SQLException {
		 return new Habitacion(
	                rs.getInt("id"),                
	                rs.getInt("numHabitacion"),
	                rs.getInt("categoriaId")
	        );
	    }
}
