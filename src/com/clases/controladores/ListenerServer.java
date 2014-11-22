package com.clases.controladores;

import com.bd.modelos.Cliente;
import com.bd.modelos.Servicio;
import com.bd.modelos.Tecnico;
import com.bd.modelos.Vehiculo;

public interface ListenerServer {
	public void  sinkClientes(Cliente[] c);
	public void sinkVehiculos(Vehiculo[] v);
	public void sinkTecnicos(Tecnico[] t);
	public void sinkServicios(Servicio[] s);
}
