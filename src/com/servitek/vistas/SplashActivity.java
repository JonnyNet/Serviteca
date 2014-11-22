package com.servitek.vistas;

import com.bd.modelos.Cliente;
import com.bd.modelos.Servicio;
import com.bd.modelos.Tecnico;
import com.bd.modelos.Vehiculo;
import com.clases.controladores.Admin_BD;
import com.clases.controladores.ListenerServer;
import com.clases.controladores.Syncro;
import com.example.servitek.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity implements ListenerServer {

	private final int DURACION_SPLASH = 3000;
	private Admin_BD manager;
	private Syncro server;
	private SharedPreferences loginPreferences;
	private SharedPreferences.Editor loginPrefsEditor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splash);

		manager = new Admin_BD(this);
		server = new Syncro(this, this);
		
		loginPreferences = getSharedPreferences("server", MODE_PRIVATE);
		loginPrefsEditor = loginPreferences.edit();
		
		boolean s = loginPreferences.getBoolean("sync", false);
		if (!s) {
			server.getAllClientes();
		}else{
			new Handler().postDelayed(new Runnable() {
				public void run() {
					Intent intent = new Intent(SplashActivity.this, Login.class);
					startActivity(intent);
					finish();
				};
			}, DURACION_SPLASH);
			
		}
	}

	@Override
	public void sinkClientes(Cliente[] c) {
		new AsyncTask<Cliente[], Void, Void>() {

			@Override
			protected Void doInBackground(Cliente[]... params) {
				for (Cliente c : params[0]) {
					manager.InsertarCliente(c.getCodigo(), c.getNombre(), c.getDireccion(), c.getTelefono(), c.getEmail());
				}
				
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				server.getAllVehiculo();
			}
		}.execute(c);

	}

	@Override
	public void sinkVehiculos(Vehiculo[] v) {
		new AsyncTask<Vehiculo[], Void, Void>() {
			@Override
			protected Void doInBackground(Vehiculo[]... params) {
				for (Vehiculo v : params[0]) {
					manager.InsertarMovil(v.getPlaca(), v.getCodter(), v.getCodmarca(), v.getColor(), 0, 0);
				}
				
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				server.getAllTecnico();
			}
		}.execute(v);
	}

	@Override
	public void sinkTecnicos(Tecnico[] t) {
		new AsyncTask<Tecnico[], Void, Void>() {
			@Override
			protected Void doInBackground(Tecnico[]... params) {
				for (Tecnico s : params[0]) {
					manager.Tecnicos(s.getNombre(), s.getCodigo(), null, null, null, null);
				}
				
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				server.getAllServicios();
			}
		}.execute(t);


	}

	@Override
	public void sinkServicios(Servicio[] s) {
		new AsyncTask<Servicio[], Void, Void>() {
			@Override
			protected Void doInBackground(Servicio[]... params) {
				for (Servicio s : params[0]) {
					manager.Servicio(s.getNombre(), s.getCodigo(), s.getValor(), 0, 0);
				}
				
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				loginPrefsEditor.putBoolean("sync", true);
				Intent intent = new Intent(SplashActivity.this, Login.class);
				startActivity(intent);
				finish();
			}
		}.execute(s);

	}
	
	

}
