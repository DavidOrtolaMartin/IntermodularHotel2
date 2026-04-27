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
		return new String[] { "id", "nombre", "descripcion", "precio"};
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
	                c.id,
	                c.nombre,
	                c.descripcion,
	                c.precio,
	                (SELECT url FROM categoria_imagenes ci
	                WHERE ci.categoriaId = c.id
	                ORDER BY ci.id ASC
	                LIMIT 1) AS imagen
	            FROM categoria c
	            ORDER BY c.nombre
	        """;

	        return DB.queryMany(con, sql, rs -> new CategoriaResumenResponse(
	            rs.getInt("id"),
	            rs.getString("nombre"),
	            rs.getString("descripcion"),
	            rs.getInt("precio"),
	            rs.getString("imagen") // puede ser null
	        ));

	    } catch (SQLException e) {
	        throw new DataAccessException("Error obteniendo categorías con imagen", e);
	    }
	}
	//}
	//findAllForIndex
	
	
}
