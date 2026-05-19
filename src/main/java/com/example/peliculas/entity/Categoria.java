package com.example.peliculas.entity;

public class Categoria {

	private Integer id_categoria;
	private String nombre;
	private String descripcion;
	private int precio;
	
	public Categoria(Integer id, String nombre, String descripcion, int precio) {
		super();
		this.id_categoria = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
	}

	public Integer getId() {
		return id_categoria;
	}

	public void setId(Integer id) {
		this.id_categoria = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Categoria [id=" + id_categoria + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio
				+ "]";
	}
	
	 
	
	
	
	
	
}
