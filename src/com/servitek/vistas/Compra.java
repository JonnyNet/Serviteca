package com.servitek.vistas;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.clases.controladores.Admin_BD;
import com.clases.controladores.Dialogo;
import com.clases.controladores.Util;
import com.example.servitek.R;
import com.servitek.adapter.CursorDiaAdapter;
import com.servitek.adapter.FechaAdapter;
import com.servitek.adapter.TecnicoAdacter;
import com.bd.modelos.Item;

public class Compra extends Activity implements OnClickListener,
		OnItemClickListener {

	private Admin_BD bd;
	private Button menu;
	private TabHost tabs;
	private ListView ltab1, ltab2, ltab3;
	private Spinner tecnico;
	private EditText desde, hasta, desde2, hasta2;
	private ImageButton buscar, buscar2;
	private CursorDiaAdapter adapter;
	private FechaAdapter fdate;
	private TecnicoAdacter tec;
	private boolean sw = true;
	private ProgressDialog pd;
	private Button d, d2, a, a2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.archivos);
		bd = new Admin_BD(this);
		ltab1 = (ListView) findViewById(R.id.ltab1);
		ltab2 = (ListView) findViewById(R.id.ltab2);
		ltab3 = (ListView) findViewById(R.id.ltab3);
		tecnico = (Spinner) findViewById(R.id.tecnicos);
		tabs = (TabHost) findViewById(android.R.id.tabhost);
		menu = (Button) findViewById(R.id.menu);
		ltab1 = (ListView) findViewById(R.id.ltab1);
		ltab2 = (ListView) findViewById(R.id.ltab2);
		ltab3 = (ListView) findViewById(R.id.ltab3);
		desde = (EditText) findViewById(R.id.desde);
		hasta = (EditText) findViewById(R.id.hasta);
		desde2 = (EditText) findViewById(R.id.desde2);
		hasta2 = (EditText) findViewById(R.id.hasta2);
		d = (Button) findViewById(R.id.d);
		d2 = (Button) findViewById(R.id.d2);
		a = (Button) findViewById(R.id.a);
		a2 = (Button) findViewById(R.id.a2);

		buscar = (ImageButton) findViewById(R.id.buscar);
		buscar2 = (ImageButton) findViewById(R.id.buscar2);
		init();
	}

	private void init() {
		menu.setOnClickListener(this);
		buscar.setOnClickListener(this);
		buscar2.setOnClickListener(this);
		d.setOnClickListener(this);
		d2.setOnClickListener(this);
		a.setOnClickListener(this);
		a2.setOnClickListener(this);
		ltab1.setOnItemClickListener(this);
		ltab3.setOnItemClickListener(this);
		hasta.setFocusable(false);
		hasta2.setFocusable(false);
		desde.setFocusable(false);
		desde2.setFocusable(false);
		Resources res = getResources();
		tabs.setup();

		TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("", res.getDrawable(android.R.drawable.ic_menu_view));
		tabs.addTab(spec);

		spec = tabs.newTabSpec("mitab2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("", res.getDrawable(android.R.drawable.ic_dialog_map));
		tabs.addTab(spec);

		spec = tabs.newTabSpec("mitab3");
		spec.setContent(R.id.tab3);
		spec.setIndicator("", res.getDrawable(android.R.drawable.ic_dialog_map));
		tabs.addTab(spec);
		tabs.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				if (tabId.equals("mitab1")) {

				}

			}
		});

		CargarCursorSpinner();
		operacion();
	}

	private void CargarCursorSpinner() {
		Cursor tipos = bd.Cursor("_id", "nomtec", "Tecnicos");
		SimpleCursorAdapter adactador1 = new SimpleCursorAdapter(this,
				android.R.layout.simple_spinner_dropdown_item, tipos,
				new String[] { "nomtec" }, new int[] { android.R.id.text1 }, 0);
		tecnico.setAdapter(adactador1);
	}

	private void ListaTabs1(Cursor c) {
		if (c.moveToFirst()) {
			if (adapter == null) {
				adapter = new CursorDiaAdapter(Compra.this, R.layout.listdia,
						c, new String[] { "placa", "norde", "fecha" },
						new int[] { R.id.placa, R.id.norden, R.id.fecha }, bd);
				ltab1.setAdapter(adapter);
			}
		} else {
			Util.MensajeCorto(this, "No Hay Registros En Este Dia");
		}

	}

	private void ListaTabs2(Cursor c) {
		if (c.moveToFirst()) {
			if (tec == null) {
				ArrayList<Item> aux = List(c);
				tec = new TecnicoAdacter(this, R.layout.listtecnico, aux);
				ltab2.setAdapter(tec);
			} else {
				ArrayList<Item> aux = List(c);
				tec.clear();
				tec.addAll(aux);
			}
		} else {
			Util.MensajeCorto(this, "No Hay Registros");
		}

	}

	private void ListaTabs3(Cursor c) {
		if (c.moveToFirst()) {
			if (fdate == null) {
				fdate = new FechaAdapter(this, c, bd);
				ltab3.setAdapter(fdate);
			} else {
				fdate.changeCursor(c);
			}
		} else {
			Util.MensajeCorto(this, "No Hay Registros");
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		bd.Cerrar();
	}

	@Override
	public void onClick(View v) {
		if (v == menu) {
			Intent intent = new Intent(Compra.this, Accion.class);
			startActivity(intent);
			finish();
		}
		if (v == d | v == d2) {
			sw = true;
			showDatePickerDialog();
		}
		if (v == a | v == a2) {
			sw = false;
			showDatePickerDialog();
		}

		if (v == buscar) {
			if (!desde.getText().toString().equals("")
					& !hasta.getText().toString().equals("")
					& tecnico.getSelectedItemPosition() != 0) {
				operacion();
			} else
				Util.MensajeCorto(this, "Llene las Fechas");

		}
		if (v == buscar2) {
			if (!desde2.getText().toString().equals("")
					& !hasta2.getText().toString().equals("")) {
				operacion();
			} else
				Util.MensajeCorto(this, "Llene las Fechas");
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		int key = parent.getId();
		if (key == R.id.ltab1 | key == R.id.ltab3) {
			new Dialogo(Compra.this, id, bd).show();
		}

	}

	private void Fecha(int anio, int mes, int dia) {
		String v = tabs.getCurrentTabTag();
		if (v.equals("mitab2")) {
			SetFecha(desde, hasta, anio, mes, dia);
		} else {
			SetFecha(desde2, hasta2, anio, mes, dia);
		}

	}

	private void SetFecha(TextView desde, TextView hasta, int anio, int mes,
			int dia) {
		StringBuilder fecha;
		String m = mes + "";
		String d = dia + "";

		if (mes < 10) {
			m = "0" + mes;
		}
		if (dia < 10) {
			d = "0" + mes;
		}

		fecha = new StringBuilder().append(anio).append("-").append(m)
				.append("-").append(d).append(" ");

		if (sw)
			desde.setText(fecha);
		else
			hasta.setText(fecha);
	}

	public void showDatePickerDialog() {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}

	public class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			Fecha(year, month + 1, day);
		}
	}

	private void operacion() {
		new AsyncTask<Void, Void, Cursor>() {

			@Override
			protected void onPreExecute() {
				if (pd == null) {
					pd = ProgressDialog.show(Compra.this, "Buscando",
							"Espere unos segundos...", true, false);
				} else {
					pd.onStart();
				}
			}

			@Override
			protected Cursor doInBackground(Void... params) {
				if (tabs.getCurrentTabTag().equals("mitab1")) {
					Cursor c = bd.BuscarOrdenDia(Util.facha());
					return c;
				} else if (tabs.getCurrentTabTag().equals("mitab2")) {
					Cursor c = bd.BuscarPorTecnico(tecnico
							.getSelectedItemPosition() + 1, desde.getText()
							.toString(), hasta.getText().toString());
					return c;
				} else {
					Cursor c = bd.BuscarPorFecha(desde2.getText().toString(),
							hasta2.getText().toString());
					return c;
				}
			}

			@Override
			protected void onPostExecute(Cursor result) {
				if (tabs.getCurrentTabTag().equals("mitab1")) {
					ListaTabs1(result);
				} else if (tabs.getCurrentTabTag().equals("mitab2")) {
					ListaTabs2(result);
				} else {
					ListaTabs3(result);
				}
				pd.dismiss();
			}
		}.execute();
	}

	private ArrayList<Item> List(Cursor c) {
		Log.e("gkgkh", c.getCount() + "");
		ArrayList<Item> list = new ArrayList<Item>();
		for (int i = 0; i < c.getCount(); i++) {

			String placa = c.getString(c.getColumnIndexOrThrow("placa"));
			String cod = c.getString(c.getColumnIndexOrThrow("codser"));
			Cursor movil = bd.BuscarPlaca(placa);
			String cc = movil.getString(movil.getColumnIndexOrThrow("Codter"));
			Cursor usuario = bd.BuscarCliente(cc);
			
			String mar = movil.getString(movil.getColumnIndexOrThrow("Codmarca"));
			Log.e("gkgkh", cod);
			
			Cursor marcas = bd.Cursor2("Mov_Marcas", "codmarca", mar);
			Cursor servi = bd.Cursor2("Servicios", "codser", cod);
			
			String marca = marcas.getString(marcas.getColumnIndexOrThrow("nombre"));

			String cliente = usuario.getString(usuario.getColumnIndexOrThrow("Nomter"));
			String orden = c.getString(c.getColumnIndexOrThrow("norde"));
			String servicio = servi.getString(servi.getColumnIndexOrThrow("nomser"));
			String valor = c.getInt(c.getColumnIndexOrThrow("total")) + "";
			String cant = c.getInt(c.getColumnIndexOrThrow("cantd")) + "";
			String fecha = c.getString(c.getColumnIndexOrThrow("fecha"));
			Item item = new Item(servicio, placa, orden, valor, marca, cant,
					cliente, fecha);
			list.add(item);

		}
		return list;
	}
}