package com.example.peliculas.dto;

public record ReservaRequest(
		Integer habId,
	    String fechaDesde,
	    String fechaHasta
	) {}