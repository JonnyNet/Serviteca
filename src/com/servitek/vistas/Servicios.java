package com.servitek.vistas;

import com.clases.controladores.Admin_BD;
import com.example.servitek.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Servicios extends Activity implements OnClickListener{
	
	AutoCompleteTextView servicio;
	EditText codigo, valor , iva ,comision;
	Button atras, editar,guardar,eliminar;
	ProgressBar progres;
	Admin_BD db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newservicio);
		
		db = new Admin_BD(this);
		servicio = (AutoCompleteTextView) findViewById(R.id.servicio);
		codigo = (EditText) findViewById(R.id.codigo);
		valor = (EditText) findViewById(R.id.valor);
		iva = (EditText) findViewById(R.id.iva);
		comision = (EditText) findViewById(R.id.comi);
		
		atras = (Button) findViewById(R.id.menu);
		atras.setOnClickListener(this);
		editar = (Button) findViewById(R.id.editar);
		editar.setOnClickListener(this);
		guardar = (Button) findViewById(R.id.guardar);
		guardar.setOnClickListener(this);
		eliminar = (Button) findViewById(R.id.eliminar);
		eliminar.setOnClickListener(this);
		
		progres = (ProgressBar) findViewById(R.id.progressBar1);
		
	}

	@Override
	public void onClick(View v) {
		if (v == atras) {
			Intent intent = new Intent(Servicios.this, Config.class);
			startActivity(intent);
			finish();
		}
		
	}
	
	public class AutoServicio extends CursorAdapter implements Filterable {

		public AutoServicio(Context context, Cursor c) {
			super(context, c, 0);
		}

		@Override
		public String convertToString(Cursor cursor) {
			final int columnIndex = 1;
			final String str = cursor.getString(columnIndex);
			return str;
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			final String text = convertToString(cursor);
			TextView re = (TextView) view.findViewById(android.R.id.text1);
			re.setTextSize(20);
			re.setTextColor(Color.rgb(0, 0, 0));
			re.setText(text);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			final LayoutInflater inflater = LayoutInflater.from(context);
			final View view = inflater.inflate(
					android.R.layout.simple_list_item_1, parent, false);

			return view;
		}

		@Override
		public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
			FilterQueryProvider filter = getFilterQueryProvider();
			if (filter != null) {
				return filter.runQuery(constraint);
			}

			String args = "";

			if (constraint != null) {
				args = constraint.toString();
			}
			Cursor c = db.TecnicoAutoComplete(args);
			return c;
		}

	}

}
