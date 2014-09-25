package com.servitek.adapter;

import com.clases.controladores.Admin_BD;
import com.clases.controladores.Util;
import com.example.servitek.R;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CursorDiaAdapter extends SimpleCursorAdapter {

	private Cursor c;
	private Context context;
	private Admin_BD d;

	public CursorDiaAdapter(Context contex, int layout, Cursor cursor,
			String[] from, int[] to, Admin_BD bd) {
		super(contex, layout, cursor, from, to, 0);
		c = cursor;
		context = contex;
		d = bd;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.listdia, null);
		}
		c.moveToPosition(position);

		// /extraer datos del cursor
		String placa = c.getString(c.getColumnIndex("placa"));
		String norden = c.getString(c.getColumnIndex("norde"));
		String fecha = c.getString(c.getColumnIndex("fecha"));
		int syncro = c.getInt(c.getColumnIndex("syncro"));
		// // buscar en la base de datos la foto del vehiculo en mencion
		Cursor imgs = d.BuscarImagen(placa);
		Bitmap foto = Util.GetImage(imgs.getBlob(1));
		imgs.close();

		// ////asignar los valores al listview
		ImageView iv = (ImageView) v.findViewById(R.id.list_image);
		iv.setImageBitmap(foto);
		TextView id = (TextView) v.findViewById(R.id.placa);
		if (syncro == 0) {
			id.setTextColor(Color.RED);
		}
		id.setText(placa);
		TextView orden = (TextView) v.findViewById(R.id.norden);
		orden.setText(norden);
		TextView date = (TextView) v.findViewById(R.id.fecha);
		date.setText(fecha);
		return v;
	}

}

/*
 * this.c.moveToPosition(pos); String firstName =
 * this.c.getString(this.c.getColumnIndex("firstName")); String lastName =
 * this.c.getString(this.c.getColumnIndex("lastName")); String titleStr =
 * this.c.getString(this.c.getColumnIndex("title")); byte[] image =
 * this.c.getBlob(this.c.getColumnIndex("personImage")); ImageView iv =
 * (ImageView) v.findViewById(R.id.pic); if (image != null) { // If there is no
 * image in the database "NA" is stored instead of a blob // test if there more
 * than 3 chars "NA" + a terminating char if more than // there is an image
 * otherwise load the default if(image.length > 3) {
 * iv.setImageBitmap(BitmapFactory.decodeByteArray(image, 0, image.length)); }
 * else { iv.setImageResource(R.drawable.icon); } } TextView fname = (TextView)
 * v.findViewById(R.id.label); fname.setText(firstName);
 * 
 * TextView lname = (TextView) v.findViewById(R.id.label1);
 * lname.setText(lastName);
 * 
 * TextView title = (TextView) v.findViewById(R.id.label2);
 * title.setText(titleStr); return(v);
 */
