package com.servitek.vistas;

import java.util.ArrayList;

import com.bd.modelos.Item;
import com.clases.controladores.Admin_BD;
import com.clases.controladores.Util;
import com.example.servitek.R;
import com.servitek.adapter.BuscarItem;
import com.servitek.adapter.CampoItem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class Orden extends Activity implements OnClickListener {

	private EditText cedula, nombre, cantidad;
	private AutoCompleteTextView placa;
	private TextView precio, numorden;
	private Spinner servicio, tecnico;
	private Button menu;
	private ImageButton agregar, borrar;
	private ImageView imagen, imagen2, imagen3;
	private ListView lista;
	private Admin_BD bd;
	private BuscarItem buscar;
	private ProgressDialog pd;
	private Cursor s;
	private ArrayList<Item> item;
	private CampoItem adapter;
	private String activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getIntent().getStringExtra("activity");
		String p = getIntent().getStringExtra("placa");
		setContentView(R.layout.orden);
		bd = new Admin_BD(this);
		BusquedaAuto();
		init();
		if (p != null) {
			placa.setText(p);
		}
	}

	private void BusquedaAuto() {
		placa = (AutoCompleteTextView) findViewById(R.id.Autocom);		
		placa.setThreshold(1);
		Cursor cursor = bd.AutoComplete("");
		cursor.close();
		buscar = new BuscarItem(getApplicationContext(), cursor,bd);
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
					BuscarRegistro(aux);
				}
			}
		});
	}

	private void init() {
		cedula = (EditText) findViewById(R.id.cedula);
		nombre = (EditText) findViewById(R.id.nombre);
		menu = (Button) findViewById(R.id.menu);
		imagen = (ImageView) findViewById(R.id.foto);
		imagen2 = (ImageView) findViewById(R.id.foto2);
		imagen3 = (ImageView) findViewById(R.id.foto3);
		servicio = (Spinner) findViewById(R.id.servi);
		cantidad = (EditText) findViewById(R.id.Cantidad);
		agregar = (ImageButton) findViewById(R.id.agregar);
		borrar = (ImageButton) findViewById(R.id.limpiar);
		precio = (TextView) findViewById(R.id.Precio);
		numorden = (TextView) findViewById(R.id.numorden);
		tecnico = (Spinner) findViewById(R.id.tecnicos);
		lista =  (ListView) findViewById(R.id.lista);

		menu.setOnClickListener(this);
		agregar.setOnClickListener(this);
		borrar.setOnClickListener(this);

		CargarSpinner();

		item = new ArrayList<Item>();
		adapter = new CampoItem(this, item);
		lista.setAdapter(adapter);

		servicio.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				if (position > 0) {
					cantidad.setText(1 + "");
					s = bd.BuscarServicio("_id", (position + 1) + "");
					precio.setText(s.getInt(4) + "");
				} else {
					s = null;
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

	}

	protected void BuscarRegistro(String aux) {
		Cursor c = bd.BuscarPlaca(aux);
		if (c.moveToFirst()) {
			LlenarCampos(c);
			placa.setFocusable(false);
			OcultaTeclado(placa);
			ComponentesActivar(true);
			jj.execute(placa.getText().toString());
		} else {
			Util.MensajeCorto(Orden.this, "Esta placa no ha sido registrada");
		}

	}

	protected void ComponentesActivar(boolean b) {
		agregar.setEnabled(b);
		tecnico.setEnabled(b);
		servicio.setEnabled(b);
		cantidad.setEnabled(b);

	}

	protected void LlenarCampos(Cursor c) {
		Cursor imgs = bd.BuscarImagen(c.getString(c.getColumnIndexOrThrow("placa")));
		cedula.setText(c.getString(c.getColumnIndexOrThrow("Codter")));
		Cursor b = bd.BuscarCliente(c.getString(c.getColumnIndexOrThrow("Codter")));
		nombre.setText(b.getString(b.getColumnIndexOrThrow("Nomter")));
		imagen.setImageBitmap(Util.GetImage(imgs.getBlob(imgs.getColumnIndexOrThrow("bitmap1"))));
		imagen2.setImageBitmap(Util.GetImage(imgs.getBlob(imgs.getColumnIndexOrThrow("bitmap2"))));
		imagen3.setImageBitmap(Util.GetImage(imgs.getBlob(imgs.getColumnIndexOrThrow("bitmap3"))));
	}

	private void CargarSpinner() {
		Cursor tipos = bd.Cursor("_id", "nomtec", "Tecnicos");
		SimpleCursorAdapter adactador1 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_dropdown_item, tipos,
				new String[] { "nomtec" }, new int[] { android.R.id.text1 }, 0);
		tecnico.setAdapter(adactador1);
		tecnico.setEnabled(false);

		Cursor marcas = bd.Cursor("rowid", "nomser", "Servicios");
		SimpleCursorAdapter adactador2 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_dropdown_item, marcas,
				new String[] { "nomser" }, new int[] { android.R.id.text1 }, 0);
		servicio.setAdapter(adactador2);
		servicio.setEnabled(false);
		agregar.setEnabled(false);
		cantidad.setEnabled(false);
	}

	@Override
	public void onClick(View v) {
		int key = v.getId();
		switch (key) {
		case R.id.agregar:
			OcultaTeclado(v);
			AgregarOrden();
			break;
		case R.id.menu:
			Intent intent = new Intent(activity);
			startActivity(intent);
			finish();
			break;
		case R.id.limpiar:
			Reset();
			break;
		}

	}

	private void AgregarOrden() {
		String[] str = new String[8];
		if (!cantidad.getText().toString().equals("")
				&& servicio.getSelectedItemPosition() != 0
				&& tecnico.getSelectedItemPosition() != 0) {
			str[0] = s.getString(1);
			str[1] = s.getString(2);
			str[2] = cantidad.getText().toString();
			str[3] = s.getInt(4) + "";
			str[4] = s.getInt(4)
					* Integer.parseInt(cantidad.getText().toString()) + "";
			str[5] = s.getInt(5)
					* Integer.parseInt(cantidad.getText().toString()) + "";
			str[6] = (s.getInt(4) + s.getInt(5)) + "";
			Log.e("tecnico", tecnico.getSelectedItemPosition()+"");
			str[7] = bd.NombreTecnico(tecnico.getSelectedItemPosition());
			crearfila(str[0], str[1], str[2], str[3], str[4], str[5], str[6]);
			if (numorden.getText().toString().equals("")) {
				int id = bd.OrdeneCompra(placa.getText().toString(), 0, str);
				numorden.setText(id + "");
			} else {
				bd.OrdeneCompra(placa.getText().toString(),
						Integer.parseInt(numorden.getText().toString()), str);
			}

			cantidad.setText("");
			servicio.setSelection(0);
			precio.setText("0");
			s = null;
		} else {
			Util.MensajeCorto(this, "Llene todos los campos");
		}
	}

	private void crearfila(String codigo, String servicio, String cantidad,
			String unidad, String precio, String iva, String total) {
		Item object = new Item(codigo, servicio, cantidad, unidad, precio, iva,
				total);
		item.add(object);
		adapter.notifyDataSetChanged();
	}

	private void OcultaTeclado(View v) {
		InputMethodManager tecladoVirtual = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		tecladoVirtual.hideSoftInputFromWindow(v.getWindowToken(), 0);

	}

	@Override
	protected void onDestroy() {
		try {

			bd.Cerrar();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		try {
			bd.Cerrar();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onStop();
	}

	private AsyncTask<String, Void, Long> jj = new AsyncTask<String, Void, Long>() {

		@Override
		protected void onPreExecute() {
			pd = ProgressDialog.show(Orden.this, "Buscando",
					"Espere unos segundos...", true, false);
		}

		@Override
		protected Long doInBackground(String... params) {
			Long i = Long.valueOf(bd.BuscarOrden(placa.getText().toString(),
					Util.facha()));
			return i;
		}

		@Override
		protected void onPostExecute(Long result) {
			pd.dismiss();
			if (result > 0) {
				numorden.setText("" + result);
				Detalles(bd.GetDetalles((long) result));
			}
		}

		@Override
		protected void onCancelled() {
			Util.MensajeCorto(Orden.this, "Tarea cancelada");
		}

	};

	protected void Detalles(Cursor c) {
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
			crearfila(str[0], str[1], str[2], str[3], str[4], str[5], str[6]);
		}
	}

	private void Reset() {
		Intent intent = getIntent();
		finish();
		startActivity(intent);
	}
}
