package com.servitek.vistas;

import com.example.servitek.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Accion extends Activity implements OnClickListener {

	ImageButton vehiculo, detalles, compras, config, salir, registro;
	public static final String activity = "com.example.servitek.ACCION";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accion);

		vehiculo = (ImageButton) findViewById(R.id.vehiculo);
		vehiculo.setOnClickListener(this);
		compras = (ImageButton) findViewById(R.id.compras);
		compras.setOnClickListener(this);
		detalles = (ImageButton) findViewById(R.id.det);
		detalles.setOnClickListener(this);
		config = (ImageButton) findViewById(R.id.config);
		config.setOnClickListener(this);
		salir = (ImageButton) findViewById(R.id.salir);
		salir.setOnClickListener(this);
		registro = (ImageButton) findViewById(R.id.user);
		registro.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int key = v.getId();
		switch (key) {
		case R.id.vehiculo:
			Intent intent = new Intent(Accion.this, Vehiculo.class);
			intent.putExtra("activity", activity);
			startActivity(intent);
			finish();
			break;
		case R.id.compras:
			Intent ord = new Intent(Accion.this, Orden.class);
			ord.putExtra("activity", activity);
			startActivity(ord);
			finish();
			break;
		case R.id.det:
			Intent com = new Intent(Accion.this, Compra.class);
			com.putExtra("activity", activity);
			startActivity(com);
			finish();
			break;
		case R.id.salir:
			Intent log = new Intent(Accion.this, Login.class);
			startActivity(log);
			finish();
			break;
		case R.id.config:
			Intent con = new Intent(Accion.this, Config.class);
			startActivity(con);
			finish();
			break;

		case R.id.user:
			Intent us = new Intent(Accion.this, RegistroUser.class);
			startActivity(us);
			finish();
			break;
		}

	}
}