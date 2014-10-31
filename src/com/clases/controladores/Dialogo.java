package com.clases.controladores;

import java.util.ArrayList;
import com.bd.modelos.Item;
import com.example.servitek.R;
import com.servitek.adapter.CampoItem;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class Dialogo extends Dialog{

	TextView total;
	long norden;
	ListView lista;
	Admin_BD bd;
	int tal = 0;
	private ArrayList<Item> item;
	private CampoItem adapter;
	
	public Dialogo(Context context, long cod, Admin_BD db) {
		super(context,android.R.style.Theme_Holo_Dialog_MinWidth);
		setTitle("Detalles Orden No: " + cod);
		norden = cod;
		bd = db;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog);
		lista = (ListView) findViewById(R.id.lista);
		total = (TextView) findViewById(R.id.total);
		
		item = new ArrayList<Item>();
		adapter = new CampoItem(getContext(), item);
		lista.setAdapter(adapter);
		fondo();	
	}
	
	
	private void fondo(){
		new AsyncTask<Void, Void, Cursor>() {

			@Override
			protected Cursor doInBackground(Void... params) {
				return bd.GetDetalles(norden);
			}

			@Override
			protected void onPostExecute(Cursor result) {
				Detalles(result);
			}

		}.execute();
	}
	
	private void crearfila(String codigo, String servicio, String cantidad,
			String unidad, String precio, String iva, String total) {
		Item object = new Item(codigo, servicio, cantidad, unidad, precio, iva,
				total);
		item.add(object);
		adapter.notifyDataSetChanged();
	}
	
	protected void Detalles(Cursor c) {
		int valortotal = 0;
		while (c.moveToNext()) {
			String[] str = new String[7];
			str[0] = c.getString(c.getColumnIndexOrThrow("codser"));
			Cursor o = bd.BuscarServicio("codser", str[0]);
			str[1] = o.getString(o.getColumnIndexOrThrow("nomser"));
			str[2] = c.getInt(c.getColumnIndexOrThrow("cantd")) + "";
			str[3] = o.getInt(o.getColumnIndexOrThrow("valser")) + "";
			str[4] = c.getInt(c.getColumnIndexOrThrow("subtal")) + "";
			str[5] = c.getInt(c.getColumnIndexOrThrow("iva")) + "";
			str[6] = c.getInt(c.getColumnIndexOrThrow("total")) + "";
			valortotal = valortotal + c.getInt(c.getColumnIndexOrThrow("total"));
			crearfila(str[0], str[1], str[2], str[3], str[4], str[5], str[6]);
		}
		c.close();
		total.setText(valortotal+"");
	}

}
