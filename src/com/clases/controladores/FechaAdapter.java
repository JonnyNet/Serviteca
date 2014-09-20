package com.clases.controladores;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.servitek.R;

public class FechaAdapter extends CursorAdapter{
	
	LayoutInflater inflater;
	private Admin_BD d;
	public FechaAdapter(Context context, Cursor c, Admin_BD bd) {
		super(context, c, 0);
		inflater = LayoutInflater.from(context);
		d = bd;
	}

	/*
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		if (item == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			item = inflater.inflate(R.layout.listfecha, null);
		}
		
		ImageView img = (ImageView) item.findViewById(R.id.img);
		TextView orden = (TextView) item.findViewById(R.id.orden);
		TextView placa = (TextView) item.findViewById(R.id.placa);
		TextView factura = (TextView) item.findViewById(R.id.factura);
		TextView subtal = (TextView) item.findViewById(R.id.subtal);
		TextView iva = (TextView) item.findViewById(R.id.iva);
		TextView vtotal = (TextView) item.findViewById(R.id.vtal);
		TextView fecha = (TextView) item.findViewById(R.id.fecha);
		
		String pla = c.getString(c.getColumnIndexOrThrow("placa"));
		Cursor imgs = d.BuscarImagen(pla);
		
		if (imgs.moveToFirst()) {
			Bitmap foto = Util.GetImage(imgs.getBlob(imgs.getColumnIndexOrThrow("bitmap1")));
			img.setImageBitmap(foto);
		}
		imgs.close();
		
		
		orden.setText(c.getString(c.getColumnIndexOrThrow("norde")));
		placa.setText(c.getString(c.getColumnIndexOrThrow("placa")));
		factura.setText(c.getString(c.getColumnIndexOrThrow("numfact")));
		subtal.setText(c.getString(c.getColumnIndexOrThrow("subtal")));
		iva.setText(c.getString(c.getColumnIndexOrThrow("iva")));
		vtotal.setText(c.getString(c.getColumnIndexOrThrow("valfact")));
		fecha.setText(c.getString(c.getColumnIndexOrThrow("fecha")));
		c.moveToNext();
		return item;
	}

*/

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.listfecha, parent, false);
		return view;
	}



	@Override
	public void bindView(View item, Context context, Cursor c) {
		ImageView img = (ImageView) item.findViewById(R.id.img);
		TextView orden = (TextView) item.findViewById(R.id.orden);
		TextView placa = (TextView) item.findViewById(R.id.placa);
		TextView factura = (TextView) item.findViewById(R.id.factura);
		TextView subtal = (TextView) item.findViewById(R.id.subtal);
		TextView iva = (TextView) item.findViewById(R.id.iva);
		TextView vtotal = (TextView) item.findViewById(R.id.vtal);
		TextView fecha = (TextView) item.findViewById(R.id.fecha);
		
		String pla = c.getString(c.getColumnIndexOrThrow("placa"));
		Cursor imgs = d.BuscarImagen(pla);
		
		if (imgs.moveToFirst()) {
			Bitmap foto = Util.GetImage(imgs.getBlob(imgs.getColumnIndexOrThrow("bitmap1")));
			img.setImageBitmap(foto);
		}
		imgs.close();
		
		
		orden.setText(c.getString(c.getColumnIndexOrThrow("norde")));
		placa.setText(c.getString(c.getColumnIndexOrThrow("placa")));
		factura.setText(c.getString(c.getColumnIndexOrThrow("numfact")));
		subtal.setText(c.getString(c.getColumnIndexOrThrow("subtal")));
		iva.setText(c.getString(c.getColumnIndexOrThrow("iva")));
		vtotal.setText(c.getString(c.getColumnIndexOrThrow("valfact")));
		fecha.setText(c.getString(c.getColumnIndexOrThrow("fecha")));
		
	}
}
