package com.example.peliculas.dto;

public record UserResponse(
		Integer id,
		Integer provinciaId,
		String name,
		String apellido1,
		String apellido2,
		String email,
		String tlf1,
		String tlf2,
		String role	
	) {} 
