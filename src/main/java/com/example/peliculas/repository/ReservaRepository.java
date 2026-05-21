package com.example.peliculas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.peliculas.db.DB;
import com.example.peliculas.dto.CategoriaDisponibleResponse;
import com.example.peliculas.dto.ReservaResponse;
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
	public String getPrimaryKeyName() {
	    return "id_reserva";
	}

	@Override
	public String[] getColumnNames() {
		return new String[] { "id_reserva", "user_id",
		        "hab_id",
		        "fecha_desde",
		        "fecha_hasta",
		        "pagado",
		        "fecha_pagado"};
	}
	
	@Override
	public void setPrimaryKey(Reserva r, int id) {
		r.setIdReserva(id);
	}
	
	@Override
	public Integer getPrimaryKey(Reserva reserva) {
		return reserva.getIdReserva();
	}

	@Override
	public Object[] getInsertValues(Reserva r) {
		return new Object[] {r.getUserId(),
		        r.getHabId(),
		        r.getFechaDesde(),
		        r.getFechaHasta(),
		        r.getPagado(),
		        r.getFechaPagado()};
	}

	@Override
	public Object[] getUpdateValues(Reserva r) {
		return new Object[] { r.getUserId(),
		        r.getHabId(),
		        r.getFechaDesde(),
		        r.getFechaHasta(),
		        r.getPagado(),
		        r.getFechaPagado(),
		        r.getIdReserva() };
	}
	
	
	public List<Reserva> findByUserId(int id){
		String sql = "SELECT * FROM reserva WHERE user_id = ?";
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

	public List<CategoriaDisponibleResponse> findDisponibles(String desde, String hasta) throws SQLException {

	    String sql = """
	        SELECT
	            c.id_categoria,
	            c.nombre,
	            c.precio AS precioPorDia,
	            MIN(h.id_habitacion) AS habitacionId
	        FROM categoria c
	        JOIN habitacion h
	            ON h.categoria_id = c.id_categoria
	        WHERE h.id_habitacion NOT IN (
	            SELECT r.hab_id
	            FROM reserva r
	            WHERE NOT (
	                r.fecha_hasta < ?
	                OR r.fecha_desde > ?
	            )
	        )
	        GROUP BY
	            c.id_categoria,
	            c.nombre,
	            c.precio
	    """;

	    return DB.queryMany(con, sql, rs -> {

	        int precioPorDia = rs.getInt("precioPorDia");

	        java.time.LocalDate f1 = java.time.LocalDate.parse(desde);
	        java.time.LocalDate f2 = java.time.LocalDate.parse(hasta);

	        long dias = java.time.temporal.ChronoUnit.DAYS.between(f1, f2);

	        int precioTotal = (int) dias * precioPorDia;

	        return new CategoriaDisponibleResponse(
	            rs.getInt("id_categoria"),
	            rs.getString("nombre"),
	            precioPorDia,
	            precioTotal,
	            rs.getInt("habitacionId")
	        );
	    }, desde, hasta);
	}
	
	public boolean existsSolapamiento(
	        int habId,
	        int reservaId,
	        String desde,
	        String hasta
	) {

	    String sql = """
	        SELECT COUNT(*)
	        FROM reserva
	        WHERE hab_id = ?
	        AND id_reserva != ?
	        AND NOT (
	            fecha_hasta < ?
	            OR fecha_desde > ?
	        )
	    """;

	    try (
	        PreparedStatement stmt =
	            con.prepareStatement(sql)
	    ) {

	        stmt.setInt(1, habId);

	        stmt.setInt(2, reservaId);

	        stmt.setString(3, desde);

	        stmt.setString(4, hasta);

	        ResultSet rs = stmt.executeQuery();

	        rs.next();

	        return rs.getInt(1) > 0;

	    } catch (SQLException e) {

	        throw new DataAccessException(
	            "Error comprobando disponibilidad",
	            e
	        );
	    }
	}
	
	public List<ReservaResponse> findReservasConNumeroHabitacion(int userId) {

	    String sql = """
	        SELECT
	            r.id_reserva,
	            r.hab_id,
	            h.num_hab,
	            r.fecha_desde,
	            r.fecha_hasta,
	            r.pagado
	        FROM reserva r
	        JOIN habitacion h
	            ON r.hab_id = h.id_habitacion
	        WHERE r.user_id = ?
	    """;

	    try (
	        PreparedStatement stmt = con.prepareStatement(sql)
	    ) {

	        stmt.setInt(1, userId);

	        ResultSet rs = stmt.executeQuery();

	        List<ReservaResponse> reservas = new ArrayList<>();

	        while (rs.next()) {

	            reservas.add(new ReservaResponse(

	                rs.getInt("id_reserva"),
	                rs.getInt("hab_id"),
	                rs.getInt("num_hab"),
	                rs.getDate("fecha_desde").toLocalDate(),
	                rs.getDate("fecha_hasta").toLocalDate(),
	                rs.getBoolean("pagado")

	            ));
	        }

	        return reservas;

	    } catch (SQLException e) {

	        throw new DataAccessException(
	            "Error obteniendo reservas",
	            e
	        );
	    }
	}
	
	
}
