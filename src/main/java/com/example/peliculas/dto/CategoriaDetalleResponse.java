package com.example.peliculas.dto;

import java.util.List;

public record CategoriaDetalleResponse(
		int id, 
		String nombre, 
		String descripcion,
		int precio,
		List<ImagenResponse> imagenes
	) {}