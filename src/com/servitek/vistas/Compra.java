package com.servitek.vistas;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TabHost.OnTabChangeListener;

import com.clases.controladores.ListenerFragment;
import com.example.servitek.R;

public class Compra extends FragmentActivity implements OnClickListener,
		ListenerFragment {

	private Button menu;
	private FragmentTabHost tabs;
	private String activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getIntent().getStringExtra("activity");
		setContentView(R.layout.archivos);
		Resources res = getResources();

		menu = (Button) findViewById(R.id.menu);
		menu.setOnClickListener(this);
		tabs = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabs.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		tabs.addTab(
				tabs.newTabSpec("mitab1").setIndicator("",
						res.getDrawable(R.drawable.icomsol)), Tab1.class, null);

		tabs.addTab(
				tabs.newTabSpec("mitab2").setIndicator("",
						res.getDrawable(R.drawable.icomtec)), Tab2.class, null);

		tabs.addTab(
				tabs.newTabSpec("mitab3").setIndicator("",
						res.getDrawable(R.drawable.icomcal)), Tab3.class, null);
		
		

		tabs.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				if (tabId.equals("mitab1")) {

				}
			}
		});

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

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
		
		String tab;
		
		public DatePickerFragment(String t) {
			tab = t;
		}

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
			
		}
	}

	@Override
	public void GetFecha(String tab) {
		DialogFragment newFragment = new DatePickerFragment(tab);
		newFragment.show(getFragmentManager(), "datePicker");
	}

}