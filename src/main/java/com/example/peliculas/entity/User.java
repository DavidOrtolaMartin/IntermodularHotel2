package com.example.peliculas.entity;

public class User {
	private Integer id;
	private String name;
	private String apellido1;
	private String apellido2;
	private String tlf1;
	private String tlf2;
	private String role;
	private String email;
	private String password;
	private Integer provinciaId;
	
	
	public User(Integer id, String name, String apellido1, String apellido2, String tlf1, String tlf2, String role,
			String email, String password, Integer provinciaId) {
		super();
		this.id = id;
		this.name = name;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.tlf1 = tlf1;
		this.tlf2 = tlf2;
		this.role = role;
		this.email = email;
		this.password = password;
		this.provinciaId = provinciaId;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getApellido1() {
		return apellido1;
	}


	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}


	public String getApellido2() {
		return apellido2;
	}


	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}


	public String getTlf1() {
		return tlf1;
	}


	public void setTlf1(String tlf1) {
		this.tlf1 = tlf1;
	}


	public String getTlf2() {
		return tlf2;
	}


	public void setTlf2(String tlf2) {
		this.tlf2 = tlf2;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Integer getProvinciaId() {
		return provinciaId;
	}


	public void setProvinciaId(Integer provinciaId) {
		this.provinciaId = provinciaId;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", tlf1="
				+ tlf1 + ", tlf2=" + tlf2 + ", role=" + role + ", email=" + email + ", password=" + password
				+ ", provinciaId=" + provinciaId + "]";
	}
	
	
}
