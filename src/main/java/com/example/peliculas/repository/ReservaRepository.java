package com.example.peliculas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.peliculas.entity.Reserva;
import com.example.peliculas.exception.DataAccessException;
import com.example.peliculas.mapper.ReservaMapper;
import com.example.peliculas.mapper.RowMapper;

public class ReservaRepository extends BaseRepository<Reserva>{

	public ReservaRepository(Connection con) {
		super(con, new ReservaMapper());
	}

	public ReservaRepository(Connection con, RowMapper<Reserva> mapper) {
		super(con, mapper);
	}

	@Override
	public String getTable() {
		return "reserva";
	}

	@Override
	public String[] getColumnNames() {
		return new String[] { "id", "id_usuario", "fecha_desde", "fecha_hasta"};
	}
	
	@Override
	public void setPrimaryKey(Reserva r, int id) {
		r.setId(id);
	}
	
	@Override
	public Integer getPrimaryKey(Reserva reserva) {
		return reserva.getId();
	}

	@Override
	public Object[] getInsertValues(Reserva r) {
		return new Object[] {r.getUsuarioId(), r.getFecha_desde(), r.getFecha_hasta()};
	}

	@Override
	public Object[] getUpdateValues(Reserva r) {
		return new Object[] {r.getUsuarioId(), r.getFecha_desde(), r.getFecha_hasta() };
	}
	
	
	public List<Reserva> findByUserId(int id){
		String sql = "SELECT * FROM reserva WHERE id_usuario = ?";
		List<Reserva> reservas = new ArrayList<>();
		try(PreparedStatement stmt = this.con.prepareStatement(sql)){
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
	        ReservaMapper mapper = new ReservaMapper();
			while(rs.next()) {
				reservas.add(mapper.map(rs));
			}
			return reservas;
		}catch(SQLException e) {
			throw new DataAccessException("Error encontrando las reservas", e);
		}
	}

	
	
	
}
