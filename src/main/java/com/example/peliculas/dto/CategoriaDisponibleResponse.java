package com.example.peliculas.dto;

public record CategoriaDisponibleResponse(
    int idCategoria,
    String nombre,
    int precioPorDia,
    int precioTotal,
    int habitacionId
) {}