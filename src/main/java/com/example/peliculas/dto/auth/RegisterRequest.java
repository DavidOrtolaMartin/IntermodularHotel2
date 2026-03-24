package com.example.peliculas.dto.auth;

public record RegisterRequest(

		String name,
		String apellido1,
		String apellido2,
		String email,
		String password,
		String tlf1,
		String tlf2,
		Integer provinciaId // camelcase y asi para foreign key  (idProvincia primary key de provincia)
		
		
	) {}
