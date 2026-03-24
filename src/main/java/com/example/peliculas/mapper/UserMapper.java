package com.example.peliculas.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.peliculas.entity.User;

public class UserMapper implements RowMapper<User> {

	@Override
	public User map(ResultSet rs) throws SQLException {
		 return new User(
	                rs.getInt("id"),                
	                rs.getString("name"),
	                rs.getString("apellido1"),
	                rs.getString("apellido2"),                
	                rs.getString("tlf1"),
	                rs.getString("tlf2"),
	                rs.getString("role"),
	                rs.getString("email"),
	                rs.getString("password"),
	                rs.getInt("id_provincia")
	        );
	    }
}
