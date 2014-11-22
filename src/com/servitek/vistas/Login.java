package com.servitek.vistas;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.clases.controladores.Admin_BD;
import com.clases.controladores.Util;
import com.example.servitek.R;
import com.servitek.adapter.AutoCompleteUser;

public class Login extends Activity implements OnClickListener { 

	private Button boton;
	private EditText password;
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
		
		
		TextView url = (TextView) findViewById(R.id.url);
		url.setText(Html.fromHtml("<a href="+GetUrl()+">www.mobilsoftsas.com</a>"));
		

		
		user = (AutoCompleteTextView) findViewById(R.id.user);
		password = (EditText) findViewById(R.id.pass);
		boton = (Button) findViewById(R.id.bvehi);
		boton.setOnClickListener(this);

		loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
		loginPrefsEditor = loginPreferences.edit();

		saveLogin = loginPreferences.getBoolean("saveLogin", false);
		if (saveLogin) {
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
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (saveLogin == true) {
					if (s.toString().equals(
							loginPreferences.getString("username", ""))) {password.setText(loginPreferences.getString("password",""));
							me.setChecked(true);
					} else {
						password.setText("");
						me.setChecked(false);

					}
				}

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
			if (!user.getText().toString().equals("")) {
				Log();
			} else {
				Util.MensajeCorto(Login.this, "Se necesita Usuario Y Password");
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

	private boolean Log() {
		Cursor c = db.Login(user.getText().toString(), password.getText()
				.toString());
		if (c.moveToFirst()) {
			String p = c.getString(c.getColumnIndexOrThrow("pass"));
			if (p.equals(password.getText().toString())) {
				int aux = c.getInt(c.getColumnIndexOrThrow("tipo"));
				if (aux == 1) {
					Intent intent = new Intent(Login.this, Accion.class);
					startActivity(intent);
					finish();
				} else {
					Intent intent = new Intent(Login.this, Standar.class);
					startActivity(intent);
					finish();
				}
			} else
				Util.MensajeCorto(this, "Password Incorrecto");
		} else
			Util.MensajeCorto(this, "Usuario NO Registrado");
		return saveLogin;

	}
	
	public void Click_Salir(View v){
		finish();
	}
	

	public  String GetUrl(){
		return "http://www.mobilsoftsas.com/";
	}
	
	public void Click_Url(View v){
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(GetUrl()));
		startActivity(browserIntent);
	}
}
