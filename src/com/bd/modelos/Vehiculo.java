package com.bd.modelos;

public class Vehiculo {
	private String placa;
	private String codter;
	private int codmarca;
	private String marca;
	private int clase;
	private String color;
	
	
	public Vehiculo(String placa, String codter, String marca, int clase) {
		this.placa = placa;
		this.codter = codter;
		this.marca = marca;
		this.clase = clase;
	}


	public Vehiculo() {

	}


	public String getPlaca() {
		return placa;
	}


	public void setPlaca(String placa) {
		this.placa = placa;
	}


	public String getCodter() {
		return codter;
	}


	public void setCodter(String codter) {
		this.codter = codter;
	}


	public String getMarca() {
		return marca;
	}


	public void setMarca(String marca) {
		this.marca = marca;
	}


	public int getClase() {
		return clase;
	}


	public void setClase(int clase) {
		this.clase = clase;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public int getCodmarca() {
		return codmarca;
	}


	public void setCodmarca(int codmarca) {
		this.codmarca = codmarca;
	}
	
	
	
	
	

}
