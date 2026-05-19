package com.example.peliculas.entity;

import java.time.LocalDate;

public class Reserva {


	 private Integer idReserva;
	 private Integer userId;
	 private Integer habId;
	 private LocalDate fechaDesde;
	 private LocalDate fechaHasta;
	 private Boolean pagado;
	 private LocalDate fechaPagado;
	 
	 
	 
	 
	 
	 public Reserva(Integer idReserva, Integer userId, Integer habId, LocalDate fechaDesde, LocalDate fechaHasta,
			Boolean pagado, LocalDate fechaPagado) {
		super();
		this.idReserva = idReserva;
		this.userId = userId;
		this.habId = habId;
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.pagado = pagado;
		this.fechaPagado = fechaPagado;
	}
	 public Integer getIdReserva() {
		 return idReserva;
	 }
	 public void setIdReserva(Integer idReserva) {
		 this.idReserva = idReserva;
	 }
	 public Integer getUserId() {
		 return userId;
	 }
	 public void setUserId(Integer userId) {
		 this.userId = userId;
	 }
	 public Integer getHabId() {
		 return habId;
	 }
	 public void setHabId(Integer habId) {
		 this.habId = habId;
	 }
	 public LocalDate getFechaDesde() {
		 return fechaDesde;
	 }
	 public void setFechaDesde(LocalDate fechaDesde) {
		 this.fechaDesde = fechaDesde;
	 }
	 public LocalDate getFechaHasta() {
		 return fechaHasta;
	 }
	 public void setFechaHasta(LocalDate fechaHasta) {
		 this.fechaHasta = fechaHasta;
	 }
	 public Boolean getPagado() {
		 return pagado;
	 }
	 public void setPagado(Boolean pagado) {
		 this.pagado = pagado;
	 }
	 public LocalDate getFechaPagado() {
		 return fechaPagado;
	 }
	 public void setFechaPagado(LocalDate fechaPagado) {
		 this.fechaPagado = fechaPagado;
	 }
	 @Override
	 public String toString() {
		return "Reserva [idReserva=" + idReserva + ", userId=" + userId + ", habId=" + habId + ", fechaDesde="
				+ fechaDesde + ", fechaHasta=" + fechaHasta + ", pagado=" + pagado + ", fechaPagado=" + fechaPagado
				+ "]";
	 }
	 

	 
	 
	 
}

