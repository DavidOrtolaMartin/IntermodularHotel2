package com.example.peliculas.dto;


public record UserEditAdmin(
    String name,
    String apellido1,
    String apellido2,
    String email,
    String tlf1,
    String tlf2,
    String role,
    Integer provinciaId
) {}