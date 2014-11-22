package com.bd.modelos;

public class Tecnico {
	
	private String codigo;
	private String nombre;
	private int id;
	
	
	
	public Tecnico() {
	}



	public Tecnico(String codigo, String nombre, int id) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.id = id;
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



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}
	
	
	
	

}
