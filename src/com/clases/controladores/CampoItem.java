package com.clases.controladores;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bd.modelos.Item;
import com.example.servitek.R;

public class CampoItem extends ArrayAdapter<Item> {

	private Context context;
	private ArrayList<Item> datos;

	public CampoItem(Context contex, ArrayList<Item> dato) {
		super(contex, R.layout.listview, dato);
		context = contex;
		datos = dato;
	}
	
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View item = inflater.inflate(R.layout.listview, parent, false); 

		TextView codigo = (TextView) item.findViewById(R.id.a1);
		codigo.setText(datos.get(position).getCodigo());

		TextView servicio = (TextView) item.findViewById(R.id.a2);
		servicio.setText(datos.get(position).getServicio());

		TextView cantidad = (TextView) item.findViewById(R.id.a3);
		cantidad.setText(datos.get(position).getCantidad());

		TextView unidad = (TextView) item.findViewById(R.id.a4);
		unidad.setText(datos.get(position).getUnidad());

		TextView precio = (TextView) item.findViewById(R.id.a5);
		precio.setText(datos.get(position).getPrecio());

		TextView iva = (TextView) item.findViewById(R.id.a6);
		iva.setText(datos.get(position).getIva());

		TextView total = (TextView) item.findViewById(R.id.a7);
		total.setText(datos.get(position).getTotal());

		return item;
	}

}
