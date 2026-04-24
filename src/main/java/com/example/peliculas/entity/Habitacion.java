package com.example.peliculas.entity;

public class Habitacion {

	private Integer id;
	private int numHabitacion;
	private Integer  categoriaId;
	
	
	
	
	public Habitacion(Integer id, int numHabitacion, Integer categoriaId) {
		super();
		this.id = id;
		this.numHabitacion = numHabitacion;
		this.categoriaId = categoriaId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getNumHabitacion() {
		return numHabitacion;
	}

	public void setNumHabitacion(int numHabitacion) {
		this.numHabitacion = numHabitacion;
	}

	public Integer getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Integer categoriaId) {
		this.categoriaId = categoriaId;
	}

	@Override
	public String toString() {
		return "Habitacion [id=" + id + ", numHabitacion=" + numHabitacion + ", categoriaId=" + categoriaId + "]";
	}
	
	
	
}
