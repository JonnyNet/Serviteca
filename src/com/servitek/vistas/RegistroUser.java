package com.servitek.vistas;

import com.example.servitek.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class RegistroUser extends Activity implements OnClickListener{

	EditText user, nombre, cedula, direccion, celular, email, pass1, pass2;
	Button menu,eliminar,crear,editar;
	ImageButton foto;
	Spinner opt;
	String[] tipos = {"Tipo de Cuenta","Admin","Estandar"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.crear);
		
		user = (EditText) findViewById(R.id.nuser);
		nombre = (EditText) findViewById(R.id.nombre);
		cedula = (EditText) findViewById(R.id.cedula);
		direccion = (EditText) findViewById(R.id.dir);
		celular = (EditText) findViewById(R.id.tel);
		email = (EditText) findViewById(R.id.mail);
		pass1 = (EditText) findViewById(R.id.pass);
		pass2 = (EditText) findViewById(R.id.pass2);
		
		menu = (Button) findViewById(R.id.menu);
		menu.setOnClickListener(this);
		eliminar = (Button) findViewById(R.id.eliminar);
		eliminar.setOnClickListener(this);
		crear = (Button) findViewById(R.id.crear);
		crear.setOnClickListener(this);
		editar = (Button) findViewById(R.id.editar);
		editar.setOnClickListener(this);
		
		foto = (ImageButton) findViewById(R.id.foto);
		opt = (Spinner) findViewById(R.id.tipo);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, tipos);
		opt.setAdapter(adapter);

	}

	@Override
	public void onClick(View v) {
		if (v == menu) {
			Intent intent = new Intent(RegistroUser.this, Accion.class);
        	startActivity(intent);
        	finish();
		}
		
	}

}
