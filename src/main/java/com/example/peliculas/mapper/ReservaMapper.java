package com.example.peliculas.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.example.peliculas.entity.Reserva;

public class ReservaMapper implements RowMapper<Reserva>{

	public Object[] insertValues(Reserva reserva) {
		return new Object[] {reserva.getId(), reserva.getUsuarioId(), Date.valueOf(reserva.getFecha_desde()), Date.valueOf(reserva.getFecha_hasta())};
	}
	
	@Override
	public Reserva map(ResultSet rs) throws SQLException {
		return new Reserva(
				rs.getInt("id"),
				rs.getInt("id_usuario"),
				rs.getDate("fecha_desde").toLocalDate(),
				rs.getDate("fecha_hasta").toLocalDate()
				);
	}
}
