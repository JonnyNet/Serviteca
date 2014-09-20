package com.servitek.vistas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.servitek.R;

public class Config extends Activity implements OnClickListener {
	
	EditText ip, puerto, user, pass;
	Button menu,guardar,editar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config);
		
		ip = (EditText) findViewById(R.id.ip);
		puerto = (EditText) findViewById(R.id.puerto);
		user = (EditText) findViewById(R.id.user);
		pass = (EditText) findViewById(R.id.password);
		
		menu = (Button) findViewById(R.id.menu);
		menu.setOnClickListener(this);
		guardar = (Button) findViewById(R.id.guardar);
		guardar.setOnClickListener(this);
		editar = (Button) findViewById(R.id.editar);
		editar.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		if (v == menu) {
			Intent intent = new Intent(Config.this, Accion.class);
        	startActivity(intent);
        	finish();
		}
		
	}
}
