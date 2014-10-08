package com.servitek.vistas;

import com.clases.controladores.Admin_BD;
import com.clases.controladores.Dialogo;
import com.clases.controladores.Util;
import com.example.servitek.R;
import com.servitek.adapter.CursorDiaAdapter;

import android.support.v4.app.Fragment;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Tab1 extends Fragment implements OnItemClickListener {

	private CursorDiaAdapter adapter;
	private ListView list;
	private Admin_BD bd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab1, container, false);
		bd = new Admin_BD(getActivity());
		if (view != null) {
			list = (ListView) view.findViewById(R.id.l1);
			list.setOnItemClickListener(this);
		}
		Operacion();
		return view;
	}

	private void ListaTabs1(Cursor c) {
		if (c.moveToFirst()) {
			if (adapter == null) {
				adapter = new CursorDiaAdapter(getActivity(), R.layout.listdia,
						c, new String[] { "placa", "norde", "fecha" },
						new int[] { R.id.placa, R.id.norden, R.id.fecha }, bd);
				list.setAdapter(adapter);
			}
		} else {
			Util.MensajeCorto(getActivity(), "No Hay Registros En Este Dia");
		}
	}
	
	

	private void Operacion() {
		new AsyncTask<Void, Void, Cursor>() {

			@Override
			protected Cursor doInBackground(Void... params) {
				Cursor c = bd.BuscarOrdenDia(Util.facha());
				return c;
			}

			@Override
			protected void onPostExecute(Cursor result) {
				ListaTabs1(result);
			}
		}.execute();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		new Dialogo(getActivity(), id, bd).show();
		
	}

}
