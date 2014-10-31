package com.servitek.vistas;

import java.util.ArrayList;

import com.bd.modelos.Item;
import com.clases.controladores.ListenerFragment;
import com.clases.controladores.Util;
import com.example.servitek.R;
import com.servitek.adapter.TecnicoAdacter;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class Tab2 extends Fragment implements OnClickListener {

	private Spinner tecnico;
	private Button buscar;
	private Button desde, hasta;
	private TecnicoAdacter tec;
	private ListView list;
	private boolean sw;
	private ListenerFragment call;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab2, container, false);
		if (view != null) {
			tecnico = (Spinner) view.findViewById(R.id.tecnicos);
			desde = (Button) view.findViewById(R.id.d);
			hasta = (Button) view.findViewById(R.id.h);
			buscar = (Button) view.findViewById(R.id.buscar);
			list = (ListView) view.findViewById(R.id.ltab2);
			CargarCursorSpinner();
		}
		
		desde.setOnClickListener(this);
		hasta.setOnClickListener(this);
		buscar.setOnClickListener(this);
		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		call = (ListenerFragment) activity;
	}

	private void CargarCursorSpinner() {
		Cursor tipos = call.DataBese().Cursor("_id", "nomtec", "Tecnicos");
		SimpleCursorAdapter adactador1 = new SimpleCursorAdapter(getActivity(),
				android.R.layout.simple_spinner_dropdown_item, tipos,
				new String[] { "nomtec" }, new int[] { android.R.id.text1 }, 0);
		tecnico.setAdapter(adactador1);
	}


	public void ListaTabs2(Cursor c) {
		if (c.moveToFirst()) {
			if (tec == null) {
				ArrayList<Item> aux = List(c);
				tec = new TecnicoAdacter(getActivity(), R.layout.listtecnico,
						aux);
				list.setAdapter(tec);
			} else {
				ArrayList<Item> aux = List(c);
				tec.clear();
				tec.addAll(aux);
			}
		} else {
			Util.MensajeCorto(getActivity(), "No Hay Registros");
		}

	}

	private ArrayList<Item> List(Cursor c) {
		Log.e("gkgkh", c.getCount() + "");
		ArrayList<Item> list = new ArrayList<Item>();
		for (int i = 0; i < c.getCount(); i++) {
			String placa = c.getString(c.getColumnIndexOrThrow("placa"));
			String cod = c.getString(c.getColumnIndexOrThrow("codser"));
			Cursor movil = call.DataBese().BuscarPlaca(placa);
			String cc = movil.getString(movil.getColumnIndexOrThrow("Codter"));
			Cursor usuario = call.DataBese().BuscarCliente(cc);
			Cursor servi = call.DataBese().Cursor2("Servicios", "codser", cod);
			String cliente = usuario.getString(usuario
					.getColumnIndexOrThrow("Nomter"));
			String servicio = servi.getString(servi
					.getColumnIndexOrThrow("nomser"));
			String valor = c.getInt(c.getColumnIndexOrThrow("total")) + "";
			String cant = c.getInt(c.getColumnIndexOrThrow("cantd")) + "";
			String fecha = c.getString(c.getColumnIndexOrThrow("fecha"));
			Item item = new Item(servicio, placa, valor, cant, cliente, fecha);
			list.add(item);

		}
		return list;
	}

	

	public void Buscar() {
		String f1 = desde.getText().toString();
		String f2 = hasta.getText().toString();
		if (!f1.equals("Desde") && !f2.equals("Hasta") && tecnico.getSelectedItemPosition() != 0) {
			call.Operacion(f1, f2, tecnico.getSelectedItemPosition());
		} else {
			Util.MensajeCorto(getActivity(), "Faltan campos requeridos");
		}
	}

	public void Fecha(int year, int month, int day) {
		String m = null;
		String d = null;
		if (month < 10)
			m = "0" + month;
		else
			m = "" + month;

		if (day < 10)
			d = "0" + day;
		else
			d = "" + day;

		if (sw) {

			String f1 = year + "-" + m + "-" + d;
			 desde.setText(f1);
		} else {
			String f2 = year + "-" + m + "-" + d;
			hasta.setText(f2);
		}
	}

	@Override
	public void onClick(View v) {
		if (v == desde) {
			call.GetFecha();
			sw = true;
		}
		if (v == hasta) {
			call.GetFecha();
			sw = false;
		}


		if (v == buscar)
			Buscar();
	}

}
