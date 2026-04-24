package com.example.peliculas.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.example.peliculas.entity.User;
import com.example.peliculas.exception.DataAccessException;
import com.example.peliculas.mapper.RowMapper;
import com.example.peliculas.mapper.UserMapper;
import com.example.peliculas.mapper.UserResponseMapper;
import com.example.peliculas.dto.UserAdmin;
import com.example.peliculas.dto.UserResponse;
import com.example.peliculas.dto.UserUpdateRequest;
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
	
	public List<UserAdmin> findAllUsersAdmins() {
	    try {
	        String sql = """
	            SELECT 
	                u.id,
	                p.nombre AS provincia,
	                u.name,
	                u.apellido1,
	                u.apellido2,
	                u.email,
	                u.tlf1,
	                u.tlf2,
	                u.role
	            FROM users u
	            JOIN provincia p ON u.id_provincia = p.id 
	        """;//vale recuerda que tienes q volver a escribir "JOIN provincia p ON u.id_provincia = p.id_provincia" para que no haya conflictos

	        return DB.queryMany(con, sql, rs -> new UserAdmin(   //esto se construye en orden al constructor del dto useradmin
	            rs.getInt("id"),
	            rs.getString("provincia"),
	            rs.getString("name"),
	            rs.getString("apellido1"),
	            rs.getString("apellido2"),
	            rs.getString("email"),
	            rs.getString("tlf1"),
	            rs.getString("tlf2"),
	            rs.getString("role")
	        ));

	    } catch (SQLException e) {
	        throw new DataAccessException("Error obteniendo los usuarios admin", e);
	    }
	}
	

	public UserUpdateRequest findUserEditAdmin(int id) {
	    try {
	        String sql = """
	            SELECT 
	                u.name,
	                u.apellido1,
	                u.apellido2,
	                u.email,
	                u.tlf1,
	                u.tlf2,
	                u.role,
	                u.id_provincia AS provinciaId
	            FROM users u
	            WHERE u.id = ?
	        """;

	        return DB.queryOne(con, sql, rs -> new UserUpdateRequest(
	            rs.getString("name"),
	            rs.getString("apellido1"),
	            rs.getString("apellido2"),
	            rs.getString("email"),
	            rs.getString("tlf1"),
	            rs.getString("tlf2"),
	            rs.getString("role"),
	            rs.getInt("provinciaId") // 👈 gracias al AS
	        ), id);

	    } catch (SQLException e) {
	        throw new DataAccessException("Error obteniendo usuario para editar", e);
	    }
	}
	
	public int updateAdmin(int id, UserUpdateRequest u) throws SQLException {
	    String sql = """
	        UPDATE users SET
	            id_provincia = ?,
	            name = ?,
	            apellido1 = ?,
	            apellido2 = ?,
	            email = ?,
	            tlf1 = ?,
	            tlf2 = ?,
	            role = ?
	        WHERE id = ?
	    """;
	    System.out.println("david antes update," + sql);
	    return DB.update(con, sql,
	        u.provinciaId(), // 👈 ya no null
	        u.name(),
	        u.apellido1(),
	        u.apellido2(),
	        u.email(),
	        u.tlf1(),
	        u.tlf2(),
	        u.role(),
	        id
	    );
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
