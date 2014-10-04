package com.clases.controladores;

public interface ListenerDB {
	public void NuevoCliente(String cc, String nombre, String dir, String tel,
			String mail, String placa, int marca, int color, int modelo,
			int tipo);

	public boolean ItemOrden(String placa, String norden, int codser,
			int cantd, int iva, int subtal, int total, String codtec);
}
