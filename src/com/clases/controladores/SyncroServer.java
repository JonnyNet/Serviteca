package com.clases.controladores;

import java.sql.Connection;
import java.sql.SQLException;

import com.bd.modelos.BDsqlServer;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.IBinder;

public class SyncroServer extends Service {
	private BDsqlServer bd;
	private Admin_BD manager;
	private Connection socket;

	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {
		manager = new Admin_BD(this);
		Progress();
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private SharedPreferences Configuracion() {
		SharedPreferences ConfigPreferent = getSharedPreferences(
				"loginPrefs", MODE_PRIVATE);
		return ConfigPreferent;
	}
	
	private void Progress() {
		SharedPreferences datos = Configuracion();
		if (datos.getBoolean("bdconfig", false)) {
			String url = datos.getString("url", "");
			String user = datos.getString("userql", "");
			String pass = datos.getString("passsql", "");
			bd = new BDsqlServer(url, user, pass); 
			if (Util.Internet(this)) {
				Conexion();
			}
		}
	}

	private void Conexion() {
		new AsyncTask<String, Void, Void>() {
			@Override
			protected Void doInBackground(String... params) {
				socket = bd.Conectar();
				try {
					if (socket.isValid(0)) {
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}.execute();
	}
	
	private void EnviaRegistro() throws SQLException {
		while (true) {
			
			if (socket.isValid(5)) {
				
			}else{
				Conexion();
			}
			
		}
		
	}
	
	private void TablaCliente(){
		Cursor cursor = manager.SyncroTabla("Vehiculo");
		if (cursor.moveToFirst()) {
			while (cursor.moveToNext()) {
				
			}
		}
		
	}
	
	private String CursorCliente(Cursor cursor){
		String query = "INSERT INTO Vehiculo (Codter, Nomter,Dirter,Telter,Coddane,Email,) VALUES (123, 'A description of part 123.')";
		return query;
	}
	
	
}
