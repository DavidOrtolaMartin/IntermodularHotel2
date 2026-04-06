package com.example.peliculas.entity;

import java.time.LocalDate;

public class Reserva {

	private Integer id;
	private Integer usuarioId;
	private LocalDate fecha_desde;
	private LocalDate fecha_hasta;
	
	
	//al mapear rs.getDate, y transformarlo a .localdate
	
	//en el map hay que poner lo de mi foto, en el db al reves(ya lo copié) y en los demás apartedos ya trabajo siempore con LocalDate.
	
	public Reserva(Integer id, Integer id_usuario, LocalDate fecha_desde, LocalDate fecha_hasta) {
		super();
		this.id = id;
		this.usuarioId = id_usuario;
		this.fecha_desde = fecha_desde;
		this.fecha_hasta = fecha_hasta;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getUsuarioId() {
		return usuarioId;
	}


	public void setUsuarioId(Integer id_usuario) {
		this.usuarioId = id_usuario;
	}


	public LocalDate getFecha_desde() {
		return fecha_desde;
	}


	public void setFecha_desde(LocalDate fecha_desde) {
		this.fecha_desde = fecha_desde;
	}


	public LocalDate getFecha_hasta() {
		return fecha_hasta;
	}


	public void setFecha_hasta(LocalDate fecha_hasta) {
		this.fecha_hasta = fecha_hasta;
	}


	@Override
	public String toString() {
		return "Reserva [id=" + id + ", id_usuario=" + usuarioId + ", fecha_desde=" + fecha_desde + ", fecha_hasta="
				+ fecha_hasta + "]";
	}
	
	
	
	
	
	
}

