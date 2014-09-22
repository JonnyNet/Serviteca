package com.servitek.vistas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.clases.controladores.Admin_BD;
import com.clases.controladores.AutoCompleteUser;
import com.clases.controladores.Util;
import com.example.servitek.R;

public class Login extends ActionBarActivity implements OnClickListener {

	private Button boton;
	private EditText  password;
	private AutoCompleteTextView user;
	private Admin_BD db;
	private CheckBox me;
	private String usuario, pass;
	private SharedPreferences loginPreferences;
	private SharedPreferences.Editor loginPrefsEditor;
	private Boolean saveLogin;
	private AutoCompleteUser adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		me = (CheckBox) findViewById(R.id.check);
		me.setOnClickListener(this);
		db = new Admin_BD(this);
		
		user =  (AutoCompleteTextView) findViewById(R.id.user);
		password = (EditText) findViewById(R.id.pass);
		boton = (Button) findViewById(R.id.bvehi);
		boton.setOnClickListener(this);

		loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
		loginPrefsEditor = loginPreferences.edit();

		saveLogin = loginPreferences.getBoolean("saveLogin", false);
		if (saveLogin == true) {
			user.setText(loginPreferences.getString("username", ""));
			password.setText(loginPreferences.getString("password", ""));
			me.setChecked(true);
		}
		
		AutoUser();
	}

	

	private void AutoUser() {
		user.setThreshold(1);
		Cursor cursor = db.LoginAutoComplete("");
		adapter = new AutoCompleteUser(getApplicationContext(), cursor, db);
		user.setAdapter(adapter);
		user.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}



	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.Cerrar();
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.bvehi) {
			if (user.getText().toString().equals("admin")
					&& password.getText().toString().equals("admin")) {
				Intent intent = new Intent("com.example.servitek.ACCION");
				startActivity(intent);
				db.Cerrar();
				finish();
			} else {
				Util.MensajeCorto(Login.this, "Usuario o Password invalidos");
			}

			usuario = user.getText().toString();
			pass = password.getText().toString();

			if (me.isChecked()) {
				loginPrefsEditor.putBoolean("saveLogin", true);
				loginPrefsEditor.putString("username", usuario);
				loginPrefsEditor.putString("password", pass);
				loginPrefsEditor.putBoolean("urlconfig", false);
				loginPrefsEditor.commit();
			} else {
				loginPrefsEditor.clear();
				loginPrefsEditor.commit();
			}
		}
	}
}
