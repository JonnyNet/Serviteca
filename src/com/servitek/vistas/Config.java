package com.servitek.vistas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.clases.controladores.Util;
import com.example.servitek.R;

public class Config extends Activity implements OnClickListener {

	EditText ip, puerto, user, pass, nombre;
	Button menu, guardar, editar;
	ImageButton servicio, tecnico;
	private SharedPreferences bdsgl;
	private SharedPreferences.Editor sqleditor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);

		ip = (EditText) findViewById(R.id.ip);
		puerto = (EditText) findViewById(R.id.puerto);
		nombre = (EditText) findViewById(R.id.nombre);
		user = (EditText) findViewById(R.id.user);
		pass = (EditText) findViewById(R.id.password);

		servicio = (ImageButton) findViewById(R.id.servicio);
		servicio.setOnClickListener(this);
		tecnico = (ImageButton) findViewById(R.id.tecnico);
		tecnico.setOnClickListener(this);

		menu = (Button) findViewById(R.id.menu);
		menu.setOnClickListener(this);
		guardar = (Button) findViewById(R.id.guardar);
		guardar.setOnClickListener(this);
		editar = (Button) findViewById(R.id.editar);
		editar.setOnClickListener(this);

		bdsgl = getSharedPreferences("loginPrefs", MODE_PRIVATE);
		sqleditor = bdsgl.edit();

		boolean savesql = bdsgl.getBoolean("save", false);
		Activar(!savesql);
		if (savesql) {
			ip.setText(bdsgl.getString("ip", ""));
			puerto.setText(bdsgl.getString("puerto", ""));
			nombre.setText(bdsgl.getString("nombre", ""));
			user.setText(bdsgl.getString("user", ""));
			pass.setText(bdsgl.getString("pass", ""));
		}

	}

	@Override
	public void onClick(View v) {
		if (v == menu) {
			Intent intent = new Intent(Config.this, Accion.class);
			startActivity(intent);
			finish();
		}

		if (v == guardar)
			Guardar();

		if (v == editar)
			Activar(true);

		if (v == servicio) {
			Intent intent = new Intent(Config.this, Servicios.class);
			startActivity(intent);
			finish();
		}

		if (v == tecnico) {
			Intent intent = new Intent(Config.this, Tecnico.class);
			startActivity(intent);
			finish();
		}

	}

	private void Guardar() {
		if (!ip.getText().toString().equals("")
				&& !puerto.getText().toString().equals("")
				&& !nombre.getText().toString().equals("")
				&& !user.getText().toString().equals("")
				&& !pass.getText().toString().equals("")) {

			sqleditor.putString("ip", ip.getText().toString());
			sqleditor.putString("puerto", puerto.getText().toString());
			sqleditor.putString("nombre", nombre.getText().toString());
			sqleditor.putString("user", user.getText().toString());
			sqleditor.putString("pass", pass.getText().toString());
			sqleditor.putBoolean("save", true);
			Activar(false);
			sqleditor.commit();
		} else
			Util.MensajeCorto(this, "Llene Todos Los Campos");
	}

	private void Activar(boolean b) {
		ip.setFocusableInTouchMode(b);
		puerto.setFocusableInTouchMode(b);
		user.setFocusableInTouchMode(b);
		pass.setFocusableInTouchMode(b);
	}

}
