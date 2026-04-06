package com.example.peliculas.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.peliculas.entity.Pago;
public class PagoMapper implements RowMapper<Pago>{

	public Object[] insertValues(Pago pago) {
		return new Object[] {pago.getId(), pago.getReservaId(), pago.isPagado(), Date.valueOf(pago.getFecha_pago())};
	}
	
	@Override
	public Pago map(ResultSet rs) throws SQLException {
		return new Pago(
				rs.getInt("id"),
				rs.getInt("id_reserva"),
				rs.getBoolean("pagado"),
				rs.getDate("fecha_pago").toLocalDate()
				);
	}
}
