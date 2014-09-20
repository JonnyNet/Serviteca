package com.bd.modelos;

public class Item {
	private String codigo;
	private String servicio;
	private String cantidad;
	private String unidad;
	private String precio;
	private String iva;
	private String total;
	private String tecnico;
	private String placa;
	private String orden;
	private String valor;
	private String marca;
	private String cliente;
	private String fecha;

	public Item(String codigo, String servicio, String cantidad, String unidad,
			String precio, String iva, String total) {
		this.codigo = codigo;
		this.servicio = servicio;
		this.cantidad = cantidad;
		this.unidad = unidad;
		this.precio = precio;
		this.iva = iva;
		this.total = total;
	}

	public Item(String servicio, String placa, String orden,
			String valor, String marca, String cantidad, String cliente,
			String fecha) {
		this.servicio = servicio;
		this.placa = placa;
		this.orden = orden;
		this.valor = valor;
		this.marca = marca;
		this.cantidad = cantidad;
		this.cliente = cliente;
		this.fecha = fecha;
	}

	public String getTecnico() {
		return tecnico;
	}

	public String getPlaca() {
		return placa;
	}

	public String getOrden() {
		return orden;
	}

	public String getValor() {
		return valor;
	}

	public String getMarca() {
		return marca;
	}

	public String getCliente() {
		return cliente;
	}

	public String getFecha() {
		return fecha;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getServicio() {
		return servicio;
	}

	public String getCantidad() {
		return cantidad;
	}

	public String getUnidad() {
		return unidad;
	}

	public String getPrecio() {
		return precio;
	}

	public String getIva() {
		return iva;
	}

	public String getTotal() {
		return total;
	}
	
	

}
