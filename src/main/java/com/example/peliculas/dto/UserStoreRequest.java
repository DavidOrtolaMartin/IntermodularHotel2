package com.example.peliculas.dto;

public record UserStoreRequest(
	    String name,
	    String apellido1,
	    String apellido2,
	    String email,
	    String tlf1,
	    String tlf2,
	    String role,
	    String password,
	    Integer provinciaId
	) {}