package com.servitek.vistas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;
import android.widget.TextView;

import com.clases.controladores.Admin_BD;
import com.clases.controladores.Util;
import com.example.servitek.R;

public class Servicios extends Activity implements OnClickListener {

	AutoCompleteTextView servicio;
	EditText codigo, valor, iva, comision;
	Button atras, editar, guardar, eliminar;
	Admin_BD db;
	AutoServicio adapter;

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

		eliminar.setEnabled(false);
		editar.setEnabled(false);

		servicio.setThreshold(1);
		Cursor cursor = db.ServicioAutoComplete("");
		adapter = new AutoServicio(getApplicationContext(), cursor);
		servicio.setAdapter(adapter);
		servicio.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Cursor c = db.Cursor2("Servicios", "nomser", servicio.getText()
						.toString());
				if (c.moveToFirst())
					LlenarCampos(c);

			}
		});

	}

	protected void LlenarCampos(Cursor c) {
		codigo.setText(c.getString(c.getColumnIndexOrThrow("codser")));
		valor.setText(c.getInt(c.getColumnIndexOrThrow("valser")) + "");
		iva.setText(c.getInt(c.getColumnIndexOrThrow("ivaser")) + "");
		comision.setText(c.getInt(c.getColumnIndexOrThrow("tasacomis")) + "");
		Activar(false, false);
		guardar.setEnabled(false);
		eliminar.setEnabled(true);
		editar.setEnabled(true);

	}

	@Override
	public void onClick(View v) {
		if (v == atras) {
			Intent intent = new Intent(Servicios.this, Config.class);
			startActivity(intent);
			overridePendingTransition(R.anim.left_in, R.anim.left_out);
			finish();
		}

		if (v == guardar)
			Guardar();

		if (v == editar) {
			Activar(true, false);
			eliminar.setEnabled(false);
			guardar.setEnabled(true);
			editar.setEnabled(false);
		}

		if (v == eliminar) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this,android.R.style.Theme_Holo_Dialog_MinWidth);
			builder.setMessage("¿Desea eliminar este Servicio?")
					.setTitle("Advertencia")
					.setCancelable(false)
					.setNegativeButton("Cancelar",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							})
					.setPositiveButton("Continuar",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									db.EliminarServicio(codigo.getText()
											.toString());
									Util.MensajeCorto(Servicios.this, "Servicio Eliminado");
									Reset();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
		}
	}

	private void Activar(boolean b, boolean c) {
		servicio.setFocusableInTouchMode(c);
		codigo.setFocusableInTouchMode(c);
		valor.setFocusableInTouchMode(b);
		iva.setFocusableInTouchMode(b);
		comision.setFocusableInTouchMode(b);

	}

	private void Guardar() {
		if (!servicio.getText().toString().equals("")
				&& !codigo.getText().toString().equals("")
				&& !valor.getText().toString().equals("")
				&& !iva.getText().toString().equals("")
				&& !comision.getText().toString().equals("")) {

			db.Servicio(servicio.getText().toString(), codigo.getText()
					.toString(), Integer.parseInt(valor.getText().toString()),
					Integer.parseInt(iva.getText().toString()), Integer
							.parseInt(comision.getText().toString()));
			Util.MensajeCorto(this, "Servicio Guardado");
			Reset();
		} else
			Util.MensajeCorto(this, "Llene Todos Los Campos");
	}

	protected void Reset() {
		Intent intent = getIntent();
		finish();
		startActivity(intent);
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
			Cursor c = db.ServicioAutoComplete(args);
			return c;
		}

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
