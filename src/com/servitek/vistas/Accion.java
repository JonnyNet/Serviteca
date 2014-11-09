package com.servitek.vistas;

import com.example.servitek.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Accion extends Activity implements OnClickListener {

	Button vehiculo, detalles, compras, config, salir, registro;
	public static final String activity = "com.example.servitek.ACCION";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accion);
		
		TextView url = (TextView) findViewById(R.id.url);
		url.setText(Html.fromHtml("<a href="+GetUrl()+">www.mobilsoftsas.com</a>"));

		vehiculo = (Button) findViewById(R.id.vehiculo);
		vehiculo.setOnClickListener(this);
		compras = (Button) findViewById(R.id.compras);
		compras.setOnClickListener(this);
		detalles = (Button) findViewById(R.id.det);
		detalles.setOnClickListener(this);
		config = (Button) findViewById(R.id.config);
		config.setOnClickListener(this);
		salir = (Button) findViewById(R.id.salir);
		salir.setOnClickListener(this);
		registro = (Button) findViewById(R.id.user);
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
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			finish();
			break;
		case R.id.compras:
			Intent ord = new Intent(Accion.this, Orden.class);
			ord.putExtra("activity", activity);
			startActivity(ord);
			overridePendingTransition(R.anim.left_in, R.anim.left_out);
			finish();
			break;
		case R.id.det:
			Intent com = new Intent(Accion.this, Compra.class);
			com.putExtra("activity", activity);
			startActivity(com);
			overridePendingTransition(R.anim.right_in, R.anim.right_out);
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
			overridePendingTransition(R.anim.zoom_back_in, R.anim.zoom_back_out);
			finish();
			break;

		case R.id.user:
			Intent us = new Intent(Accion.this, RegistroUser.class);
			startActivity(us);
			overridePendingTransition(R.anim.zoom_forward_in,
					R.anim.zoom_forward_out);
			finish();
			break;
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			new AlertDialog.Builder(this,android.R.style.Theme_Holo_Dialog_MinWidth)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle("Salir")
					.setMessage("Estás seguro?")
					.setNegativeButton(android.R.string.cancel, null)
					// sin listener
					.setPositiveButton(android.R.string.ok,
							new DialogInterface.OnClickListener() {
						
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// Salir
									Accion.this.finish();
								}
							}).show();

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	public  String GetUrl(){
		return "http://www.mobilsoftsas.com/";
	}
	
	public void Click_Url(View v){
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(GetUrl()));
		startActivity(browserIntent);
	}

}