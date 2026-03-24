package com.example.peliculas.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.peliculas.dto.UserResponse;

public class UserResponseMapper implements RowMapper<UserResponse> {

	@Override
	public UserResponse map(ResultSet rs) throws SQLException {
		return new UserResponse(rs.getInt("id"), rs.getInt("id_provincia"), rs.getString("name"), rs.getString("apellido1"),
				rs.getString("apellido2"), rs.getString("email"), rs.getString("tlf1"), rs.getString("tlf2"), rs.getString("role")); 
				
	}
}
