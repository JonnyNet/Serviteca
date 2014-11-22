package com.bd.modelos;

public class Servicio {
	
	private String codigo;
	private String nombre;
	private int valor ;
	private double iva;
	private double comision;
	
	
	public Servicio(String codigo, String nombre, int valor, double iva,
			double comision) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.valor = valor;
		this.iva = iva;
		this.comision = comision;
	}


	public Servicio() {

	}


	public String getCodigo() {
		return codigo;
	}


	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public int getValor() {
		return valor;
	}


	public void setValor(int valor) {
		this.valor = valor;
	}


	public double getIva() {
		return iva;
	}


	public void setIva(double iva) {
		this.iva = iva;
	}


	public double getComision() {
		return comision;
	}


	public void setComision(double comision) {
		this.comision = comision;
	}
	
	
	
	

}
