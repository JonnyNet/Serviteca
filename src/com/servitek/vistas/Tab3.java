package com.servitek.vistas;

import com.clases.controladores.Admin_BD;
import com.clases.controladores.ListenerFragment;
import com.clases.controladores.Util;
import com.example.servitek.R;
import com.servitek.adapter.FechaAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class Tab3 extends Fragment implements OnClickListener {

	private FechaAdapter fdate;
	private ListView list;
	private String f1, f2;
	private boolean sw;
	private Admin_BD bd;
	private Button buscar, desde, hasta;
	private ListenerFragment call;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		bd = new Admin_BD(getActivity());
		View view = inflater.inflate(R.layout.tab3, container, false);
		if (view != null) {
			desde = (Button) view.findViewById(R.id.d);
			hasta = (Button) view.findViewById(R.id.h);
			buscar = (Button) view.findViewById(R.id.buscar);
			list = (ListView) view.findViewById(R.id.ltab2);
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

	private void ListaTabs3(Cursor c) {
		if (c.moveToFirst()) {
			if (fdate == null) {
				fdate = new FechaAdapter(getActivity(), c, bd);
				list.setAdapter(fdate);
			} else {
				fdate.changeCursor(c);
			}
		} else {
			Util.MensajeCorto(getActivity(), "No Hay Registros");
		}

	}

	public void Buscar() {
		if (f1 != null && f2 != null) {
			Operacion();
		} else {
			Util.MensajeCorto(getActivity(), "Faltan campos requeridos");
		}
	}

	private void Operacion() {
		final ProgressDialog pro = new ProgressDialog(getActivity(),
				android.R.style.Theme_Holo_Dialog_MinWidth);
		new AsyncTask<Void, Void, Cursor>() {

			@Override
			protected void onPreExecute() {
				pro.setTitle("Buscando...");
				pro.setMessage("Espere Porfavor");
				pro.show();
			}

			@Override
			protected Cursor doInBackground(Void... params) {
				Cursor c = bd.BuscarPorFecha(f1, f2);
				return c;
			}

			@Override
			protected void onPostExecute(Cursor result) {
				pro.dismiss();
				ListaTabs3(result);
			}
		}.execute();
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
			f1 = year + "-" + m + "-" + d;
			desde.setText(f1);
		} else {
			f2 = year + "-" + m + "-" + d;
			hasta.setText(f2);
		}
	}

	@Override
	public void onClick(View v) {
		if (v == desde) {
			call.GetFecha("");
			sw = true;
		}
		if (v == hasta) {
			call.GetFecha("");
			sw = false;
		}

		if (v == buscar)
			Buscar();
	}

}
