package com.example.peliculas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.peliculas.db.DB;
import com.example.peliculas.dto.HabitacionDTO;
import com.example.peliculas.entity.Habitacion;
import com.example.peliculas.exception.DataAccessException;
import com.example.peliculas.mapper.HabitacionMapper;
import com.example.peliculas.mapper.RowMapper;

public class HabitacionRepository extends BaseRepository<Habitacion>{

	public HabitacionRepository(Connection con) {
		super(con, new HabitacionMapper());
	}

	public HabitacionRepository(Connection con, RowMapper<Habitacion> mapper) {
		super(con, mapper);
	}

	@Override
	public String getTable() {
		return "habitacion";
	}

	@Override
	public String[] getColumnNames() {
		return new String[] { "num_hab", "categoria_id"};
	}
	
	@Override
	public void setPrimaryKey(Habitacion h, int id) {
		h.setId(id);
	}
	
	@Override
	public Integer getPrimaryKey(Habitacion habitacion) {
		return habitacion.getId();
	}

	@Override
	public Object[] getInsertValues(Habitacion h) {
		return new Object[] { h.getNumHabitacion(), h.getCategoriaId()};
	}

	@Override
	public Object[] getUpdateValues(Habitacion h) {
		return new Object[] {h.getNumHabitacion(), h.getCategoriaId(), h.getId() };
	}
	
	
	public List<HabitacionDTO> findAllHabitaciones() {
	    try {
	        String sql = """
	            SELECT 
	                h.id_habitacion as id,
	                h.num_hab AS numero,
	                c.nombre AS categoria,
	                c.precio AS precio
	            FROM habitacion h
	            JOIN categoria c ON h.categoria_id = c.id_categoria
	            ORDER BY c.precio, c.nombre
	        """;

	        return DB.queryMany(con, sql, rs -> new HabitacionDTO(
	            rs.getInt("id"),
	            rs.getInt("numero"),
	            rs.getString("categoria"),
	            rs.getInt("precio")
	        ));

	    } catch (SQLException e) {
	        throw new DataAccessException("Error obteniendo habitaciones", e);
	    }
	}

	
	//estos tres métodos hacen falta porq ahora la bbdd usa id_habitación en vez de id como dice el base repository
	@Override
	public Habitacion find(int id) {
	    try {
	        String sql = "SELECT * FROM habitacion WHERE id_habitacion = ?";
	        return DB.queryOne(con, sql, mapper, id);
	    } catch (SQLException e) {
	        throw new DataAccessException("Error al buscar habitación con id=" + id, e);
	    }
	}

	@Override
	public boolean delete(int id) {
	    try {
	        String sql = "DELETE FROM habitacion WHERE id_habitacion = ?";
	        DB.update(con, sql, id);
	        return true;
	    } catch (SQLException e) {
	        throw new DataAccessException("Error al eliminar habitación con id=" + id, e);
	    }
	}

	@Override
	public int update(Habitacion h) {
	    try {
	        String sql = "UPDATE habitacion SET num_hab = ?, categoria_id = ? WHERE id_habitacion = ?";
	        return DB.update(con, sql, h.getNumHabitacion(), h.getCategoriaId(), h.getId());
	    } catch (SQLException e) {
	        throw new DataAccessException("Error al actualizar habitación con id=" + h.getId(), e);
	    }
	}
	
	
}
