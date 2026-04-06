package com.example.peliculas.entity;

public class Provincia {

	private Integer id;
	private String nombre;
	private Integer paisId;
	
	public Provincia(Integer id, String nombre, Integer paisId) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.paisId = paisId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getPaisId() {
		return paisId;
	}

	public void setPaisId(Integer paisId) {
		this.paisId = paisId;
	}

	@Override
	public String toString() {
		return "Provincia [id=" + id + ", nombre=" + nombre + ", paisId=" + paisId + "]";
	}
	
	
	
}
