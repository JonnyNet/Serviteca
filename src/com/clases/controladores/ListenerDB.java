package com.clases.controladores;

public interface ListenerDB {
	public void NuevoCliente(String cc, String nombre, String dir, String tel,String mail, String placa, int marca, String color, int modelo,int tipo);

	public void ItemOrden(String placa, long norden, int codser,int cantd, int iva, int subtal, int total, String codtec);
	
	public void UpdateCliente(String cc, String nombre, String dir, String tel, String email, String placa);
}
