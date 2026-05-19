package com.example.peliculas.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.example.peliculas.entity.Reserva;

public class ReservaMapper implements RowMapper<Reserva>{

	public Object[] insertValues(Reserva reserva) {
		return new Object[] {
	            reserva.getIdReserva(),
	            reserva.getUserId(),
	            reserva.getHabId(),
	            Date.valueOf(reserva.getFechaDesde()),
	            Date.valueOf(reserva.getFechaHasta()),
	            reserva.getPagado(),
	            reserva.getFechaPagado() != null
	                ? Date.valueOf(reserva.getFechaPagado())
	                : null
	        };
	    }
	
	 @Override
	    public Reserva map(ResultSet rs) throws SQLException {
	        return new Reserva(
	            rs.getInt("id_reserva"), // si tú mantienes "id", ok
	            rs.getInt("user_id"),
	            rs.getInt("hab_id"),
	            rs.getDate("fecha_desde").toLocalDate(),
	            rs.getDate("fecha_hasta").toLocalDate(),
	            rs.getBoolean("pagado"),
	            rs.getDate("fecha_pagado") != null
	                ? rs.getDate("fecha_pagado").toLocalDate()
	                : null
	        );
	    }
}
