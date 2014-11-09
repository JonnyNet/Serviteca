package com.servitek.vistas;

import com.example.servitek.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Standar extends Activity implements OnClickListener{
	
	Button vehiculo, detalles, compras, salir;
	private static final String activity = "com.example.servitek.Standar";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accionesstandar);
		
		TextView url = (TextView) findViewById(R.id.url);
		url.setText(Html.fromHtml("<a href="+GetUrl()+">www.mobilsoftsas.com</a>"));
		
		vehiculo = (Button) findViewById(R.id.vehiculo);
		vehiculo.setOnClickListener(this);
		compras = (Button) findViewById(R.id.compras);
		compras.setOnClickListener(this);
		detalles = (Button) findViewById(R.id.det);
		detalles.setOnClickListener(this);
		salir = (Button) findViewById(R.id.salir);
		salir.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		int key = v.getId();
		switch (key) {
		case R.id.vehiculo:
			Intent intent = new Intent(Standar.this, Vehiculo.class);
			intent.putExtra("activity", activity);
			startActivity(intent);
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			finish();
			break;
		case R.id.compras:
			Intent ord = new Intent(Standar.this, Orden.class);
			ord.putExtra("activity", activity);
			startActivity(ord);
			overridePendingTransition(R.anim.left_in, R.anim.left_out);
			finish();
			break;
		case R.id.det:
			Intent com = new Intent(Standar.this, Compra.class);
			com.putExtra("activity", activity);
			startActivity(com);
			overridePendingTransition(R.anim.right_in, R.anim.right_out);
			finish();
			break;
		case R.id.salir:
			Intent log = new Intent(Standar.this, Login.class);
			log.putExtra("activity", activity);
			startActivity(log);
			finish();
			break;
		}

	}
	
	public  String GetUrl(){
		return "http://www.mobilsoftsas.com/";
	}
	
	public void Click_Url(View v){
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(GetUrl()));
		startActivity(browserIntent);
	}
}
