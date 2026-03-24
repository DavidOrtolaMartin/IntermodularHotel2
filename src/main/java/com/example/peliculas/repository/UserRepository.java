package com.example.peliculas.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.example.peliculas.entity.User;
import com.example.peliculas.exception.DataAccessException;
import com.example.peliculas.mapper.RowMapper;
import com.example.peliculas.mapper.UserMapper;
import com.example.peliculas.mapper.UserResponseMapper;

import com.example.peliculas.dto.UserResponse;
import com.example.peliculas.db.DB;

public class UserRepository extends BaseRepository<User> {

	public UserRepository(Connection con) {
		super(con, new UserMapper());
	}

	public UserRepository(Connection con, RowMapper<User> mapper) {
		super(con, mapper);
	}

	@Override
	public String getTable() {
		return "users";
	}

	@Override
	public String[] getColumnNames() {
		return new String[] { "id", "id_provincia", "name", "apellido1", "apellido2", "email", "tlf1", "tlf2", "role", "password" };
	}
	
	@Override
	public Integer getPrimaryKey(User u) {
		return u.getId();
	}
	
	@Override
	public void setPrimaryKey(User u, int id) {
		u.setId(id);
	}

	@Override
	public Object[] getInsertValues(User u) {
		return new Object[] {u.getProvinciaId(), u.getName(), u.getApellido1(), u.getApellido2(),u.getEmail(), u.getTlf1(), u.getTlf2(), u.getRole(), u.getPassword() };
	}

	@Override
	public Object[] getUpdateValues(User u) {
		return new Object[] {u.getProvinciaId(), u.getName(), u.getApellido1(), u.getApellido2(),u.getEmail(), u.getTlf1(), u.getTlf2(), u.getRole(), u.getPassword(), u.getId() };
	}
	
	public UserResponse findResponseById(int id) {
		
		try {
			String sql = "select id,  id_provincia, name, apellido1, apellido2, email, tlf1, tlf2, role	from users where id = ?";
			return DB.queryOne(con, sql, new UserResponseMapper(), id);
		} catch (SQLException e) {
			throw new DataAccessException("Error al buscar el usuario con id " + id, e);
		}
	}
	
	public List<UserResponse> findAllResponses() {
		
		try {
			String sql = "select id,  id_provincia, name, apellido1, apellido2, email, tlf1, tlf2, role	from users";
			return DB.queryMany(con, sql, new UserResponseMapper());
		} catch (SQLException e) {
			throw new DataAccessException("Error obteniendo los usuarios", e);
		}
	}
	
	public User findByEmail(String email) {
		
		try {
			String sql = "SELECT * FROM users WHERE email = ?";  //hay que cambiar la tabla de xamp que pone user o todo lo de aqui a user
			return DB.queryOne(con, sql, mapper, email);
		} catch (SQLException e) {
			throw new DataAccessException("Error al buscar el usuario con email " + email);
		}
	}
}
