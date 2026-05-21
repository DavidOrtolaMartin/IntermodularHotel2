package com.example.peliculas.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.example.peliculas.db.DB;
import com.example.peliculas.dto.CategoriaResumenResponse;
import com.example.peliculas.entity.Categoria;
import com.example.peliculas.exception.DataAccessException;
import com.example.peliculas.mapper.CategoriaMapper;
import com.example.peliculas.mapper.RowMapper;

public class CategoriaRepository extends BaseRepository<Categoria>{

	public CategoriaRepository(Connection con) {
		super(con, new CategoriaMapper());
	}

	public CategoriaRepository(Connection con, RowMapper<Categoria> mapper) {
		super(con, mapper);
	}

	@Override
	public String getTable() {
		return "categoria";
	}

	@Override
	public String[] getColumnNames() {
		return new String[] {"nombre", "descripcion", "precio"};
	}
	
	@Override
	public void setPrimaryKey(Categoria c, int id) {
		c.setId(id);
	}
	
	@Override
	public Integer getPrimaryKey(Categoria categoria) {
		return categoria.getId();
	}

	@Override
	public Object[] getInsertValues(Categoria c) {
		return new Object[] { c.getNombre(), c.getDescripcion(), c.getPrecio()};
	}

	@Override
	public Object[] getUpdateValues(Categoria c) {
		return new Object[] {c.getId(), c.getNombre(), c.getDescripcion(), c.getPrecio() };
	}
	
	//List<CategoriaDetalleResponse> findAllResumenResponses() {
	public List<CategoriaResumenResponse> findAllResumenResponses() {
	    try {
	        String sql = """
	            SELECT 
	                c.id_categoria,
	                c.nombre,
	                c.descripcion,
	                c.precio,
	                (SELECT url FROM categoria_imagenes ci
	                WHERE ci.categoria_id = c.id_categoria
	                ORDER BY ci.id ASC
	                LIMIT 1) AS imagen
	            FROM categoria c
	            ORDER BY c.precio
	        """;

	        return DB.queryMany(con, sql, rs -> new CategoriaResumenResponse(
	            rs.getInt("id_categoria"),
	            rs.getString("nombre"),
	            rs.getString("descripcion"),
	            rs.getInt("precio"),
	            rs.getString("imagen") // puede ser null
	        ));

	    } catch (SQLException e) {
	        throw new DataAccessException("Error obteniendo categorías con imagen", e);
	    }
	}
	
	public Categoria findOrThrow(int id) {
	    try {
	        String sql = "SELECT * FROM categoria WHERE id_categoria = ? ";
	        return DB.queryOne(con, sql, mapper, id);
	    } catch (SQLException e) {
	        throw new DataAccessException("Error al buscar categoría con id=" + id, e);
	    }
	}
	
	
	@Override
	public int update(Categoria c) {
	    try {
	        String sql = "UPDATE categoria SET nombre = ?, descripcion = ?, precio = ? WHERE id_categoria = ?";
	        return DB.update(con, sql, c.getNombre(), c.getDescripcion(), c.getPrecio(), c.getId());
	    } catch (SQLException e) {
	        throw new DataAccessException("Error al actualizar categoría con id=" + c.getId(), e);
	    }
	}
	
	

	@Override
	public boolean delete(int id) {
	    try {
	        String sql = "DELETE FROM categoria WHERE id_categoria = ?";
	        DB.update(con, sql, id);
	        return true;
	    } catch (SQLException e) {
	        throw new DataAccessException("Error al eliminar categoría con id=" + id, e);
	    }
	}
	
	
	//comprueba antes de borrar la categoria si hay una hab asingnada
	public boolean tieneHabitaciones(int categoriaId) {
	    try {
	        String sql = "SELECT COUNT(*) FROM habitacion WHERE categoria_id = ?";
	        return DB.queryOne(con, sql, rs -> rs.getInt(1), categoriaId) > 0;
	    } catch (SQLException e) {
	        throw new DataAccessException("Error comprobando habitaciones", e);
	    }
	}
	
	
}
