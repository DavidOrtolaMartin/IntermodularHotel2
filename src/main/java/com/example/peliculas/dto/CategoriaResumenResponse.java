package com.example.peliculas.dto;


public record CategoriaResumenResponse(
		int id, 
		String nombre, 
		String descripcion,
		int precio,
		String imagen
	) {}