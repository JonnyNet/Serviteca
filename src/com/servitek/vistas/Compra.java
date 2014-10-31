package com.servitek.vistas;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;

import com.clases.controladores.Admin_BD;
import com.clases.controladores.ListenerFragment;
import com.clases.controladores.Util;
import com.example.servitek.R;

public class Compra extends FragmentActivity implements OnClickListener,
		ListenerFragment {

	private Button menu;
	private FragmentTabHost tabs;
	private String activity;
	private Admin_BD bd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getIntent().getStringExtra("activity");
		setContentView(R.layout.archivos);

		bd = new Admin_BD(this);
		menu = (Button) findViewById(R.id.menu);
		menu.setOnClickListener(this);
		tabs = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabs.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		tabs.addTab(AddTabs("mitab1", R.drawable.icomsol), Tab1.class, null);
		tabs.addTab(AddTabs("mitab2", R.drawable.icomtec), Tab2.class, null);
		tabs.addTab(AddTabs("mitab3", R.drawable.icomcal), Tab3.class, null);
	}

	private TabHost.TabSpec AddTabs(String tag, int drawable) {
		Resources res = getResources();
		TabHost.TabSpec spe = tabs.newTabSpec(tag);

		TabWidget tw = (TabWidget) tabs.findViewById(android.R.id.tabs);
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tag, tw,
				false);

		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageDrawable(res.getDrawable(drawable));
		icon.setScaleType(ImageView.ScaleType.FIT_CENTER);

		spe.setIndicator(tabIndicator);
		return spe;

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onAttachFragment(Fragment fragment) {
		// TODO Auto-generated method stub
		super.onAttachFragment(fragment);
	}

	@Override
	public void onClick(View v) {
		if (v == menu) {
			Intent intent = new Intent(activity);
			startActivity(intent);
			overridePendingTransition(R.anim.left_in, R.anim.left_out);
			finish();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			return new DatePickerDialog(getActivity(),
					android.R.style.Theme_Holo_Dialog_MinWidth, this, year,
					month, day);

		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			String v = tabs.getCurrentTabTag();
			if (v.equals("mitab2")) {
				Tab2 tab2 = TabFragment2(v);
				tab2.Fecha(year, month + 1, day);
			}
			if (v.equals("mitab3")) {
				Tab3 tab3 = TabFragment3(v);
				tab3.Fecha(year, month + 1, day);

			}
		}
	}

	private Tab1 TabFragment1(String v) {
		FragmentManager fm = getSupportFragmentManager();
		Tab1 tab1 = (Tab1) fm.findFragmentByTag(v);
		return tab1;
	}

	private Tab2 TabFragment2(String v) {
		FragmentManager fm = getSupportFragmentManager();
		Tab2 tab2 = (Tab2) fm.findFragmentByTag(v);
		return tab2;
	}

	private Tab3 TabFragment3(String v) {
		FragmentManager fm = getSupportFragmentManager();
		Tab3 tab3 = (Tab3) fm.findFragmentByTag(v);
		return tab3;
	}

	@Override
	public void Operacion(final String desde, final String hasta,
			final int tecnico) {
		final ProgressDialog pro = new ProgressDialog(Compra.this,
				android.R.style.Theme_Holo_Dialog_MinWidth);
		new AsyncTask<Void, Void, Cursor>() {

			@Override
			protected void onPreExecute() {
				pro.setTitle("Buscando...");
				pro.setMessage("Espere Porfavor");
				pro.setCancelable(false);
				pro.show();
			}

			@Override
			protected Cursor doInBackground(Void... params) {
				if (tabs.getCurrentTabTag().equals("mitab1")) {
					Cursor c = bd.BuscarOrdenDia(Util.facha());
					return c;
				} else if (tabs.getCurrentTabTag().equals("mitab2")) {
					Cursor c = bd.BuscarPorTecnico(tecnico, desde, hasta);
					return c;
				} else {
					Cursor c = bd.BuscarPorFecha(desde, hasta);
					return c;
				}
			}

			@Override
			protected void onPostExecute(Cursor result) {
				pro.dismiss();
				String v = tabs.getCurrentTabTag();
				if (v.equals("mitab1")) {
					Tab1 tab1 = TabFragment1(v);
					tab1.ListaTabs1(result);

				} else if (tabs.getCurrentTabTag().equals("mitab2")) {
					Tab2 tab2 = TabFragment2(v);
					tab2.ListaTabs2(result);

				} else if (tabs.getCurrentTabTag().equals("mitab3")) {
					Tab3 tab3 = TabFragment3(v);
					tab3.ListaTabs3(result);
				}
			}
		}.execute();
	}

	@Override
	public Admin_BD DataBese() {
		return bd;

	}

	@Override
	public void GetFecha() {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");

	}
}