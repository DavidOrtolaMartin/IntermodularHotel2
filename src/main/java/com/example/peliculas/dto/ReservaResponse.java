package com.example.peliculas.dto;

import java.time.LocalDate;

public record ReservaResponse(

    Integer id_reserva,
    Integer hab_id,
    Integer num_hab,
    LocalDate fecha_desde,
    LocalDate fecha_hasta,
    Boolean pagado

) {}