package com.clases.controladores;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;
import android.widget.TextView;

public class AutoCompleteUser extends CursorAdapter implements Filterable {
	Admin_BD manager;

	public AutoCompleteUser(Context context, Cursor c, Admin_BD b) {
		super(context, c, 0);
		manager = b;
	}


	@Override
	public String convertToString(Cursor cursor) {
		final int columnIndex = 1;
		final String str = cursor.getString(columnIndex);
		return str;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		final String text = convertToString(cursor);
		TextView re = (TextView) view.findViewById(android.R.id.text1);
		re.setTextSize(20);
		re.setTextColor(Color.rgb(0, 0, 0));
		re.setText(text);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		final LayoutInflater inflater = LayoutInflater.from(context);
		final View view = inflater.inflate(android.R.layout.simple_list_item_1,
				parent, false);

		return view;
	}

	@Override
	public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
		FilterQueryProvider filter = getFilterQueryProvider();
		if (filter != null) {
			return filter.runQuery(constraint);
		}

		String args = "";

		if (constraint != null) {
			args = constraint.toString();
		}
		Cursor c = manager.LoginAutoComplete(args);
		return c;
	}

}
