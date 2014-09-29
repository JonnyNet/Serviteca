package com.servitek.vistas;

import com.clases.controladores.Admin_BD;
import com.clases.controladores.Util;
import com.example.servitek.R;
import com.servitek.adapter.BuscarItem;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class Vehiculo extends ActionBarActivity implements OnClickListener {
	private EditText cedula, nombre, direccion, celular, modelo, mail;
	private AutoCompleteTextView placa;
	private Spinner tipo, marca;
	private Button color, guardar, menu;
	private ImageButton borrar, imagen, imagen2, imagen3;
	private Intent camara;
	private static TextView carcolor;
	private final static int cons = 0;
	private static int cc = 0;
	private Admin_BD bd;
	private boolean sw = true;
	private BuscarItem buscar;
	private int in = 0;
	private String activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		activity = getIntent().getStringExtra("activity");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vehiculo);
		bd = new Admin_BD(this);
		BusquedaAuto();
		init();
	}

	private void BusquedaAuto() {
		placa = (AutoCompleteTextView) findViewById(R.id.Autocom);
		placa.setThreshold(1);
		Cursor cursor = bd.AutoComplete("");
		buscar = new BuscarItem(getApplicationContext(), cursor, bd);
		placa.setAdapter(buscar);
		placa.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String aux = s.toString();
				if (aux.length() == 3) {
					placa.setText(aux + " - ");
					placa.setInputType(InputType.TYPE_CLASS_NUMBER);

				} else if (aux.length() > 3 && aux.length() < 6) {
					placa.setInputType(InputType.TYPE_CLASS_TEXT);
					placa.setSelection(1);
					placa.setText("");

				}

				if (!aux.equals(s.toString().toUpperCase())) {
					aux = s.toString().toUpperCase();
					placa.setText(aux);
				}
				placa.setSelection(placa.getText().length());

				if (aux.length() == 9) {
					cedula.requestFocus();
					Buscar(aux);
				}
			}
		});
	}

	private void init() {
		cedula = (EditText) findViewById(R.id.cedula);
		nombre = (EditText) findViewById(R.id.nombre);
		direccion = (EditText) findViewById(R.id.dir);
		celular = (EditText) findViewById(R.id.tel);
		modelo = (EditText) findViewById(R.id.model);
		mail = (EditText) findViewById(R.id.mail);
		tipo = (Spinner) findViewById(R.id.tipos);
		marca = (Spinner) findViewById(R.id.marcas);
		imagen = (ImageButton) findViewById(R.id.foto);
		imagen2 = (ImageButton) findViewById(R.id.foto2);
		imagen3 = (ImageButton) findViewById(R.id.foto3);
		borrar = (ImageButton) findViewById(R.id.borrar);
		menu = (Button) findViewById(R.id.menu);
		color = (Button) findViewById(R.id.btcolor);
		carcolor = (TextView) findViewById(R.id.carcolor);
		guardar = (Button) findViewById(R.id.guardar);

		imagen.setOnClickListener(this);
		imagen2.setOnClickListener(this);
		imagen3.setOnClickListener(this);
		menu.setOnClickListener(this);
		color.setOnClickListener(this);
		borrar.setOnClickListener(this);
		guardar.setOnClickListener(this);
		CargarCursor();
	}

	protected void OculTeclado(View v) {
		InputMethodManager tecladoVirtual = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		tecladoVirtual.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}

	private void CargarCursor() {
		bd.Leer();
		Cursor tipos = bd.Cursor("_id", "Nomclase", "Mov_Clases");
		SimpleCursorAdapter adactador1 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_dropdown_item, tipos,
				new String[] { "Nomclase" }, new int[] { android.R.id.text1 },
				0);
		tipo.setAdapter(adactador1);

		Cursor marcas = bd.Cursor("_id", "nombre", "Mov_Marcas");
		SimpleCursorAdapter adactador2 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_dropdown_item, marcas,
				new String[] { "nombre" }, new int[] { android.R.id.text1 }, 0);
		marca.setAdapter(adactador2);
	}

	@Override
	public void onClick(View v) {
		int key = v.getId();
		switch (key) {
		case R.id.foto:
			in = R.id.foto;
			TomarFoto();
			break;
		case R.id.foto2:
			in = R.id.foto2;
			TomarFoto();
			break;
		case R.id.foto3:
			in = R.id.foto3;
			TomarFoto();
			break;
		case R.id.menu:
			Intent intent = new Intent(activity);
			startActivity(intent);
			finish();
			break;
		case R.id.btcolor:
			color_carro();
			OculTeclado(v);
			break;
		case R.id.borrar:
			Reset();
			placa.setInputType(InputType.TYPE_CLASS_TEXT);
			DesAct(true, true);
			break;
		case R.id.editar:
			DesAct(false, true);
			break;
		case R.id.guardar:
			if (sw) {
				GuardarRegistro();
			} else {
				Editar();
			}

			break;
		}

	}

	private void TomarFoto() {
		bd.Cerrar();
		camara = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(camara, cons);
	}

	private void Editar() {
		if (!cedula.getText().toString().equals("")
				&& !nombre.getText().toString().equals("")) {
			if (bd.EditarCliente(cedula.getText().toString(), nombre.getText()
					.toString(), direccion.getText().toString(), celular
					.getText().toString(), "hola", mail.getText().toString(),
					placa.getText().toString()) != -1) {
				Util.MensajeCorto(this, "Edicion Exitosa");
				Reset();
			}
		}

	}

	private void GuardarRegistro() {
		if (!cedula.getText().toString().equals("")
				&& !nombre.getText().toString().equals("")
				&& !placa.getText().toString().equals("")
				&& placa.getText().toString().trim().length() == 9
				&& !modelo.getText().toString().equals("")
				&& marca.getSelectedItemPosition() != 0
				&& tipo.getSelectedItemPosition() != 0 && cc != 0) {

			if (bd.RegistrarVehiculo(cedula.getText().toString(), nombre
					.getText().toString(), direccion.getText().toString(),
					celular.getText().toString(), "hola", mail.getText()
							.toString(), placa.getText().toString(), marca
							.getSelectedItemPosition(), cc + "", modelo
							.getText().toString(), tipo
							.getSelectedItemPosition(), Util
							.GetBytes(((BitmapDrawable) imagen.getDrawable())
									.getBitmap()), Util
							.GetBytes(((BitmapDrawable) imagen2.getDrawable())
									.getBitmap()), Util
							.GetBytes(((BitmapDrawable) imagen3.getDrawable())
									.getBitmap()), sw)) {
				Util.MensajeCorto(this, "Registro Exitoso");
				Reset();
			} else {
				Util.MensajeCorto(this, "Error al registrar");
			}

		} else {

			Util.MensajeCorto(this, "Llene todos los campos");
		}
	}

	protected void LlenarCampos(Cursor[] cur) {
		Cursor c = cur[0];
		Cursor imgs = cur[1];
		Cursor b = cur[2];
		
		
		cedula.setText(c.getString(c.getColumnIndexOrThrow("Codter")));
		String m = c.getString(3);
		carcolor.setBackgroundColor(c.getInt(4));
		modelo.setText(c.getString(5));
		String t = c.getString(6);
		nombre.setText(b.getString(b.getColumnIndexOrThrow("Nomter")));
		direccion.setText(b.getString(b.getColumnIndexOrThrow("Dirter")));
		celular.setText(b.getString(b.getColumnIndexOrThrow("Telter")));
		mail.setText(b.getString(b.getColumnIndexOrThrow("Email")));
		tipo.setSelection((int) bd.CodigoId("Mov_Clases", "codclase", t));
		marca.setSelection((int) bd.CodigoId("Mov_Marcas", "codmarca", m));
		imagen.setImageBitmap(Util.GetImage(imgs.getBlob(imgs
				.getColumnIndexOrThrow("bitmap1"))));
		imagen2.setImageBitmap(Util.GetImage(imgs.getBlob(imgs
				.getColumnIndexOrThrow("bitmap2"))));
		imagen3.setImageBitmap(Util.GetImage(imgs.getBlob(imgs
				.getColumnIndexOrThrow("bitmap3"))));
		b.close();
		c.close();
		imgs.close();
	}

	private void Cliente(final Cursor c) {
		final ProgressDialog pro = new ProgressDialog(Vehiculo.this,
				android.R.style.Theme_Holo_Dialog_MinWidth);
		new AsyncTask<Cursor, Void, Cursor[]>() {

			@Override
			protected void onPreExecute() {
				pro.setTitle("Buscando Vehiculo...");
				pro.setMessage("Espere Porfavor");
				pro.show();
			}

			@Override
			protected Cursor[] doInBackground(Cursor... params) {
				Cursor c = params[0];
				Cursor[] cursor = new Cursor[3];
				cursor[0] = params[0];
				cursor[1] = bd.BuscarImagen(c.getString(c.getColumnIndexOrThrow("placa")));
				cursor[2] = bd.BuscarCliente(c.getString(c.getColumnIndexOrThrow("Codter")));
				return cursor;
			}

			@Override
			protected void onPostExecute(Cursor[] result) {
				pro.dismiss();
				LlenarCampos(result);
			}
		}.execute(c);
	}

	private void DesAct(boolean b1, boolean b2) {
		placa.setEnabled(b1);
		cedula.setEnabled(b2);
		nombre.setEnabled(b2);
		direccion.setEnabled(b2);
		celular.setEnabled(b2);
		mail.setEnabled(b2);
		modelo.setEnabled(b1);
		tipo.setEnabled(b1);
		marca.setEnabled(b1);
		color.setEnabled(b1);
		imagen.setEnabled(b2);
		guardar.setEnabled(b2);
		sw = b1;
	}

	private void color_carro() {
		Bitmap color = ((BitmapDrawable) imagen.getDrawable()).getBitmap();
		Util.GetColor(color);
	}

	@Override
	protected void onActivityResult(int RequesCode, int ResultCode, Intent data) {
		super.onActivityResult(RequesCode, ResultCode, data);
		bd.Escribir();
		if (ResultCode == Activity.RESULT_OK) {
			Bundle ext = data.getExtras();
			Bitmap bmt = (Bitmap) ext.get("data");
			if (in == R.id.foto) {
				imagen.setImageBitmap(bmt);
			} else if (in == R.id.foto2) {
				imagen2.setImageBitmap(bmt);
			} else {
				imagen3.setImageBitmap(bmt);
			}

		}
	}

	private void Buscar(String placa) {
		final ProgressDialog pro = new ProgressDialog(Vehiculo.this,
				android.R.style.Theme_Holo_Dialog_MinWidth);
		new AsyncTask<String, Void, Cursor>() {

			@Override
			protected void onPreExecute() {
				pro.setTitle("Buscando Vehiculo...");
				pro.setMessage("Espere Porfavor");
				pro.show();
			}

			@Override
			protected Cursor doInBackground(String... params) {
				Cursor c = bd.BuscarPlaca(params[0]);
				return c;
			}

			@Override
			protected void onPostExecute(Cursor result) {
				pro.dismiss();
				Listener(result);
			}

		}.execute(placa);
	}

	protected void Listener(final Cursor c) {
		if (c.moveToFirst()) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					Vehiculo.this, android.R.style.Theme_Holo_Dialog_MinWidth);
			builder.setMessage("¿Que desea hacer?")
					.setTitle("Vehiculo Registrado")
					.setCancelable(false)
					.setNegativeButton("Editar Cliente",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									Cliente(c);
									DesAct(false, true);
									OculTeclado(placa);
									dialog.cancel();
								}
							})
					.setPositiveButton("Cargar Servicios",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									Intent log = new Intent(Vehiculo.this,
											Orden.class);
									log.putExtra("placa", placa.getText()
											.toString());
									log.putExtra("activity", activity);
									dialog.cancel();
									startActivity(log);
									finish();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
		}
	}

	@Override
	protected void onDestroy() {
		bd.Cerrar();
		super.onDestroy();
	}

	public static Handler puente = new Handler() {
		public void handleMessage(Message msg) {
			cc = (Integer) msg.obj;
			carcolor.setBackgroundColor(cc);
		}
	};

	private void Reset() {
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}
}
