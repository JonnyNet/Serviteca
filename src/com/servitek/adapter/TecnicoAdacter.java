package com.servitek.adapter;

import java.util.ArrayList;

import com.bd.modelos.Item;
import com.example.servitek.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TecnicoAdacter  extends ArrayAdapter<Item>{
	
	private Context context;
	private ArrayList<Item> datos;
	private int total;
	
	public TecnicoAdacter(Context context, int resource, ArrayList<Item> datos) {
		super(context, resource, datos);
		this.context = context;
		this.datos = datos;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View item = inflater.inflate(R.layout.listtecnico, parent, false); 

		TextView placa = (TextView) item.findViewById(R.id.placa);
		placa.setText(datos.get(position).getPlaca());

		TextView servicio = (TextView) item.findViewById(R.id.servicio);
		servicio.setText(datos.get(position).getServicio());

		TextView total = (TextView) item.findViewById(R.id.vtal);
		total.setText(datos.get(position).getValor());

		TextView cantidad = (TextView) item.findViewById(R.id.cantidad);
		cantidad.setText(datos.get(position).getCantidad());
		
		TextView fecha = (TextView) item.findViewById(R.id.fecha);
		fecha.setText(datos.get(position).getFecha());
		
		if (position % 2 == 0)
			item.setBackgroundResource(R.drawable.odd_row);
		else
			item.setBackgroundResource(R.drawable.even_row);

		return item;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
