package com.example.peliculas.entity;

public class CategoriaImagen {

	private Integer id;
	private Integer categoriaId;
	private String url;
	
	public CategoriaImagen(Integer id, Integer categoriaId, String url) {
		super();
		this.id = id;
		this.categoriaId = categoriaId;
		this.url = url;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Integer habitacionId) {
		this.categoriaId = habitacionId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "CategoriaImagen [id=" + id + ", categoriaId=" + categoriaId + ", url=" + url + "]";
	}
	
	
	
}
