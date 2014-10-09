package com.clases.controladores;

public interface ListenerLog {
	public void Registro(String user, String tabla, String dato, String fecha);
	public void Actualizacion(String user, String tabla, String dato, String fecha);
}
