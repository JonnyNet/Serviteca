package com.servitek.vistas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.clases.controladores.Admin_BD;
import com.clases.controladores.Util;
import com.example.servitek.R;

public class Config extends Activity implements OnClickListener {

	EditText url, namespace,marca;
	Button menu, guardar, editar;
	Button servicio, tecnico;
	private SharedPreferences bdsgl;
	private SharedPreferences.Editor sqleditor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);
		
		url = (EditText) findViewById(R.id.namespace);
		namespace = (EditText) findViewById(R.id.puerto);

		servicio = (Button) findViewById(R.id.servicio);
		servicio.setOnClickListener(this);
		tecnico = (Button) findViewById(R.id.tecnico);
		tecnico.setOnClickListener(this);
		
		marca = (EditText) findViewById(R.id.marcas);

		menu = (Button) findViewById(R.id.menu);
		menu.setOnClickListener(this);
		guardar = (Button) findViewById(R.id.guardar);
		guardar.setOnClickListener(this);
		editar = (Button) findViewById(R.id.editar);
		editar.setOnClickListener(this);

		bdsgl = getSharedPreferences("loginPrefs", MODE_PRIVATE);
		sqleditor = bdsgl.edit();
		
		boolean savesql = bdsgl.getBoolean("save", false);
		if (!savesql) {
			sqleditor.putString("url", "http://suarpe.com/");
			sqleditor.putString("namespace", "http://192.168.1.51:80/ServicioClientes.asmx");
			sqleditor.putBoolean("save", true);
			sqleditor.commit();
		}
		
		savesql = bdsgl.getBoolean("save", false);
		Activar(!savesql);
		if (savesql) {
			url.setText(bdsgl.getString("url", ""));
			namespace.setText(bdsgl.getString("namespace", ""));
			guardar.setEnabled(false);
		}
	}

	@Override
	public void onClick(View v) {
		if (v == menu) {
			Intent intent = new Intent(Config.this, Accion.class);
			startActivity(intent);
			overridePendingTransition(R.anim.zoom_forward_in,
					R.anim.zoom_forward_out);
			finish();
		}

		if (v == guardar)
			Guardar();

		if (v == editar)
			Activar(true);

		if (v == servicio) {
			Intent intent = new Intent(Config.this, Servicios.class);
			startActivity(intent);
			overridePendingTransition(R.anim.right_in, R.anim.right_out);
			finish();
		}

		if (v == tecnico) {
			Intent intent = new Intent(Config.this, Tecnico.class);
			startActivity(intent);
			overridePendingTransition(R.anim.left_in, R.anim.left_out);
			finish();
		}

	}

	private void Guardar() {
		if (!url.getText().toString().equals("")
				&& !namespace.getText().toString().equals("")) {

			sqleditor.putString("url", url.getText().toString());
			sqleditor.putString("namespace", namespace.getText().toString());
			sqleditor.putBoolean("save", true);
			Activar(false);
			sqleditor.commit();
		} else
			Util.MensajeCorto(this, "Llene Todos Los Campos");
	}

	private void Activar(boolean b) {
		url.setFocusableInTouchMode(b);
		namespace.setFocusableInTouchMode(b);      
		guardar.setEnabled(b);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public void AgregarMarca(View v){
		if (!marca.getText().toString().trim().equals("")) {
			Admin_BD db = new Admin_BD(this);
			db.AddMarca(marca.getText().toString().trim());
			marca.setText("");
			OculTeclado(v);
			Util.MensajeCorto(this, "Marca Guardada con Exito");
		}else{
			Util.MensajeCorto(this, "Campo Nueva Marca Vacio");
		}
	}
	
	protected void OculTeclado(View v) {
		InputMethodManager tecladoVirtual = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		tecladoVirtual.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

}

