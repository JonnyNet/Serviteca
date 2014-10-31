package com.servitek.vistas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import com.clases.controladores.Admin_BD;
import com.clases.controladores.Util;
import com.example.servitek.R;
import com.servitek.camara.ActivityCam;
import com.servitek.camara.BitmapHelper;

public class Tecnico extends ActivityCam implements OnClickListener {

	AutoCompleteTextView nombre;
	EditText cedula, direccion, tel, email;
	Button eliminar, editar, guardar, atras;
	ImageButton foto;
	AutoTecnico adapter;
	Admin_BD db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newtecnico);
		db = new Admin_BD(this);
		nombre = (AutoCompleteTextView) findViewById(R.id.nombre);
		cedula = (EditText) findViewById(R.id.cedula);
		direccion = (EditText) findViewById(R.id.dir);
		tel = (EditText) findViewById(R.id.tel);
		email = (EditText) findViewById(R.id.email);
		eliminar = (Button) findViewById(R.id.eliminar);
		editar = (Button) findViewById(R.id.editar);
		guardar = (Button) findViewById(R.id.guardar);
		atras = (Button) findViewById(R.id.menu);
		foto = (ImageButton) findViewById(R.id.foto);

		eliminar.setOnClickListener(this);
		editar.setOnClickListener(this);
		guardar.setOnClickListener(this);
		atras.setOnClickListener(this);
		foto.setOnClickListener(this);
		
		eliminar.setEnabled(false);
		editar.setEnabled(false);

		nombre.setThreshold(1);
		Cursor cursor = db.TecnicoAutoComplete("");
		adapter = new AutoTecnico(getApplicationContext(), cursor);
		nombre.setAdapter(adapter);
		nombre.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Cursor c = db.Cursor2("Tecnicos", "nomtec", nombre.getText()
						.toString());
				if (c.moveToFirst())
					LlenarCampos(c);
				eliminar.setEnabled(true);
				OculTeclado(nombre);
			}
		});

	}

	protected void LlenarCampos(Cursor c) {
		cedula.setText(c.getString(c.getColumnIndexOrThrow("codtec")));
		direccion.setText(c.getString(c.getColumnIndexOrThrow("dirtec")));
		tel.setText(c.getString(c.getColumnIndexOrThrow("teltec")));
		email.setText(c.getString(c.getColumnIndexOrThrow("email")));
		eliminar.setEnabled(true);
		editar.setEnabled(true);
		guardar.setEnabled(false);
		byte[] img = c.getBlob(c.getColumnIndexOrThrow("foto"));
		if (img != null) {
			foto.setImageBitmap(Util.GetImage(img));
		}

		Activar(false, false);
	}

	private void Activar(boolean b, boolean a) {
		nombre.setFocusableInTouchMode(a);
		cedula.setFocusableInTouchMode(a);
		direccion.setFocusableInTouchMode(b);
		tel.setFocusableInTouchMode(b);
		email.setFocusableInTouchMode(b);
		foto.setFocusableInTouchMode(b);
		guardar.setEnabled(a);
	}

	@Override
	public void onClick(View v) {
		if (v == guardar) {
			Guardar();
		}

		if (v == editar) {
			Activar(true, false);
		}

		if (v == eliminar) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this,android.R.style.Theme_Holo_Dialog_MinWidth);
			builder.setMessage("¿Desea eliminar este Tecnico?")
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
									db.EliminarTecnico(cedula.getText()
											.toString());
										Util.MensajeCorto(Tecnico.this,
												"Tecnico Eliminado");
										Reset();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
		}

		if (v == foto) {
			startCameraIntent();
		}

		if (v == atras) {
			Intent intent = new Intent(Tecnico.this, Config.class);
			startActivity(intent);
			overridePendingTransition(R.anim.right_in, R.anim.right_out);
			finish();
		}

	}

	private void Guardar() {
		if (!nombre.getText().toString().equals("")
				&& !cedula.getText().toString().equals("")
				&& !direccion.getText().toString().equals("")
				&& !tel.getText().toString().equals("")
				&& !email.getText().toString().equals("")) {

			db.Tecnicos(nombre.getText().toString(), cedula.getText()
					.toString(), direccion.getText().toString(), tel.getText()
					.toString(), email.getText().toString(),
					Util.GetBytes(((BitmapDrawable) foto.getDrawable())
							.getBitmap()));
			Util.MensajeCorto(Tecnico.this, "Tecnico Guardado");
			Reset();
		} else
			Util.MensajeCorto(this, "Llene Todos Los Campos");

	}
	
	
	@Override
	protected void onPhotoUriFound() {
		Bitmap photo = BitmapHelper.readBitmap(this, photoUri);
		if (photo != null) {
			photo = BitmapHelper.shrinkBitmap(photo, 230, 90);
			
		}
		if (preDefinedCameraUri != null
				&& !preDefinedCameraUri.equals(photoUri)) {
			BitmapHelper.deleteImageWithUriIfExists(preDefinedCameraUri, this);
		}
		if (photoUriIn3rdLocation != null) {
			BitmapHelper
					.deleteImageWithUriIfExists(photoUriIn3rdLocation, this);
		}
	}

	public class AutoTecnico extends CursorAdapter implements Filterable {

		public AutoTecnico(Context context, Cursor c) {
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

	protected void Reset() {
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}
	
	protected void OculTeclado(View v) {
		InputMethodManager tecladoVirtual = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		tecladoVirtual.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
