package org.blanco.techmun.android.misc;

import org.blanco.techmun.android.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MesasCursorAdapter extends CursorAdapter {

	public MesasCursorAdapter(Context context, Cursor c) {
		super(context,c);
	}

	@Override
	public void bindView(View arg0, Context arg1, Cursor arg2) {
		setViewValues(arg0, arg2);
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		LayoutInflater inflater = LayoutInflater.from(arg0);
		View v = inflater.inflate(R.layout.mesas_list_item_layout, null);
		setViewValues(v, arg1);
		return v;
	}

	private void setViewValues(View view, Cursor cursor){
		TextView id = (TextView) view
				.findViewById(R.id.mesas_list_item_layout_id);
		TextView nombre = (TextView) view
				.findViewById(R.id.mesas_list_item_layout_nombre);
		TextView repres = (TextView) view
				.findViewById(R.id.mesas_list_item_layout_representante);
		int vid = cursor.getInt(cursor.getColumnIndex("_id"));
		String vnombre = cursor.getString(cursor.getColumnIndex("nombre"));
		String vrep = cursor.getString(cursor.getColumnIndex("representante"));
		id.setText(String.valueOf(vid));
		nombre.setText(vnombre);
		repres.setText(vrep);
	}
	
}
