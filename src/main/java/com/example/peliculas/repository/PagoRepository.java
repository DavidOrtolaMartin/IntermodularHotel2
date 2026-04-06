package com.example.peliculas.repository;

import java.sql.Connection;

import com.example.peliculas.entity.Pago;
import com.example.peliculas.entity.Reserva;
import com.example.peliculas.mapper.PagoMapper;
import com.example.peliculas.mapper.RowMapper;

public class PagoRepository extends BaseRepository<Pago>{

	public PagoRepository(Connection con) {
		super(con, new PagoMapper());
	}

	public PagoRepository(Connection con, RowMapper<Pago> mapper) {
		super(con, mapper);
	}

	@Override
	public String getTable() {
		return "pago";
	}

	@Override
	public String[] getColumnNames() {
		return new String[] { "id", "id_reserva", "pagado", "fecha_pago"};
	}
	
	@Override
	public void setPrimaryKey(Pago p, int id) {
		p.setId(id);
	}

	@Override
	public Integer getPrimaryKey(Pago p) {
		return p.getId();
	}
	
	@Override
	public Object[] getInsertValues(Pago p) {
		return new Object[] {p.getId(), p.getReservaId(), p.isPagado(), p.getFecha_pago()};
	}

	@Override
	public Object[] getUpdateValues(Pago p) {
		return new Object[] {p.getId(), p.getReservaId(), p.isPagado(), p.getFecha_pago() };
	}

	
}
