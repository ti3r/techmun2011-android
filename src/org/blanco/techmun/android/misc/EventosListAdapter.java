package org.blanco.techmun.android.misc;

import org.blanco.techmun.android.R;
import org.blanco.techmun.entities.Evento;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventosListAdapter extends ArrayAdapter<Evento> {

	
public EventosListAdapter(Context context, int textViewResourceId,
			Evento[] objects) {
		super(context, textViewResourceId, objects);
	}

//	@Override
//	public void bindView(View replaceView, Context context, Cursor cursor) {
//		setViewValues(replaceView, cursor);
//	}
//
//	@Override
//	public View newView(Context context, Cursor cursor, ViewGroup parent) {
//		View v = LayoutInflater.from(context).inflate(R.layout.eventos_list_item_layout, null);
//		setViewValues(v, cursor);
//		return v;
//	}
	


	private void setViewValues(View view, Cursor cursor){
		String desc = cursor.getString(cursor.getColumnIndex("evento"));
		
		((TextView)view.findViewById(R.id.eventos_list_item_layout_desc))
			.setText(desc);
		String fecha = cursor.getString(cursor.getColumnIndex("fecha"));
		((TextView)view.findViewById(R.id.eventos_list_item_layout_fecha))
			.setText(fecha);
		
	}
	
}
