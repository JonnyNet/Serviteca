package com.servitek.vistas;

import com.clases.controladores.ListenerFragment;
import com.clases.controladores.Util;
import com.example.servitek.R;
import com.servitek.adapter.FechaAdapter;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class Tab3 extends Fragment implements OnClickListener {

	private FechaAdapter fdate;
	private ListView list;
	private boolean sw;
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
		View view = inflater.inflate(R.layout.tab3, container, false);
		if (view != null) {
			desde = (Button) view.findViewById(R.id.d);
			hasta = (Button) view.findViewById(R.id.h);
			buscar = (Button) view.findViewById(R.id.buscar);
			list = (ListView) view.findViewById(R.id.ltab3);
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

	public void ListaTabs3(Cursor c) {
		if (c.moveToFirst()) {
			Log.i("szghdf", "sjrjryir");
			if (fdate == null) {
				fdate = new FechaAdapter(getActivity(), c, call.DataBese());
				list.setAdapter(fdate);
			} else {
				fdate.changeCursor(c);
			}
		} else {
			Util.MensajeCorto(getActivity(), "No Hay Registros");
		}

	}

	public void Buscar() {
		String f1 = desde.getText().toString();
		String f2 = hasta.getText().toString();
		if (!f1.equals("Desde") && !f2.equals("Hasta")) {
			call.Operacion(f1, f2, 0);
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
