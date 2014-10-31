package com.servitek.vistas;


import com.clases.controladores.Dialogo;
import com.clases.controladores.ListenerFragment;
import com.clases.controladores.Util;
import com.example.servitek.R;
import com.servitek.adapter.CursorDiaAdapter;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.database.Cursor;
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
	private ListenerFragment call;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab1, container, false);
		if (view != null) {
			list = (ListView) view.findViewById(R.id.l1);
			list.setOnItemClickListener(this);
		}
		return view;
	}
	
	
	
	@Override
	public void onStart() {
		super.onStart();
		call.Operacion(null, null, 0);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		call = (ListenerFragment) activity;
	}



	public  void ListaTabs1(Cursor c) {
		if (c.moveToFirst()) {
			if (adapter == null) {
				adapter = new CursorDiaAdapter(getActivity(), R.layout.listdia,
						c, new String[] { "placa", "norde", "fecha" },
						new int[] { R.id.placa, R.id.norden, R.id.fecha }, call.DataBese());
				list.setAdapter(adapter);
			}
		} else {
			Util.MensajeCorto(getActivity(), "No Hay Registros En Este Dia");
		}
	}



	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		new Dialogo(getActivity(), id, call.DataBese()).show();	
	}

}
