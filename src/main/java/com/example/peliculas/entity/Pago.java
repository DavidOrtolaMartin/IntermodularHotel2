package com.example.peliculas.entity;

import java.time.LocalDate;

public class Pago {

	private Integer id;
	private Integer reservaId;
	private boolean pagado;
	private LocalDate  fecha_pago;
	
	
	public Pago(Integer id, Integer reservaId, boolean pagado, LocalDate fecha_pago) {
		super();
		this.id = id;
		this.reservaId = reservaId;
		this.pagado = pagado;
		this.fecha_pago = fecha_pago;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getReservaId() {
		return reservaId;
	}


	public void setReservaId(Integer reservaId) {
		this.reservaId = reservaId;
	}


	public boolean isPagado() {
		return pagado;
	}


	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}


	public LocalDate getFecha_pago() {
		return fecha_pago;
	}


	public void setFecha_pago(LocalDate fecha_pago) {
		this.fecha_pago = fecha_pago;
	}


	@Override
	public String toString() {
		return "Pago [id=" + id + ", reservaId=" + reservaId + ", pagado=" + pagado + ", fecha_pago=" + fecha_pago
				+ "]";
	}
	
	
	
}

