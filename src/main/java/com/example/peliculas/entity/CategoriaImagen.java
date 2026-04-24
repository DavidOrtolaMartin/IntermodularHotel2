package com.example.peliculas.entity;

public class CategoriaImagen {

	private Integer id;
	private Integer habitacionId;
	private String url;
	
	public CategoriaImagen(Integer id, Integer habitacionId, String url) {
		super();
		this.id = id;
		this.habitacionId = habitacionId;
		this.url = url;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getHabitacionId() {
		return habitacionId;
	}

	public void setHabitacionId(Integer habitacionId) {
		this.habitacionId = habitacionId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "CategoriaImagen [id=" + id + ", habitacionId=" + habitacionId + ", url=" + url + "]";
	}
	
	
	
}
