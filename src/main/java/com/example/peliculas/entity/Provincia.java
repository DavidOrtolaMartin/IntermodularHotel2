package com.example.peliculas.entity;

public class Provincia {

	private Integer id_provincia;
	private String nombre;
	
	
	public Provincia(Integer id, String nombre) {
		super();
		this.id_provincia = id;
		this.nombre = nombre;
		
	}

	public Integer getId() {
		return id_provincia;
	}

	public void setId(Integer id) {
		this.id_provincia = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}



	@Override
	public String toString() {
		return "Provincia [id=" + id_provincia + ", nombre=" + nombre + "]";
	}
	
	
	
}
