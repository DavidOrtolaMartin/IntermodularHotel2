package com.example.peliculas.repository;

import java.sql.Connection;

import com.example.peliculas.entity.Provincia;
import com.example.peliculas.mapper.ProvinciaMapper;
import com.example.peliculas.mapper.RowMapper;


public class ProvinciaRepository extends BaseRepository<Provincia>{

	public ProvinciaRepository(Connection con) {
		super(con, new ProvinciaMapper());
	}
	
	public ProvinciaRepository(Connection con, RowMapper<Provincia> mapper) {
		super(con, mapper);
	}

	@Override
	public String getTable() {
		return "provincia";
	}

	@Override
	public String[] getColumnNames() {
		return new String[] { "id", "nombre"};
	}
	
	@Override
	public void setPrimaryKey(Provincia p, int id) {
		p.setId(id);
	}

	@Override
	public Integer getPrimaryKey(Provincia p) {
		return p.getId();
	}
	
	@Override
	public Object[] getInsertValues(Provincia p) {
		return new Object[] {p.getId(), p.getNombre()};
	}

	@Override
	public Object[] getUpdateValues(Provincia p) {
		return new Object[] {p.getId(), p.getNombre() };
	}
	
	
}
