package org.blanco.techmun.android.misc;

import org.blanco.techmun.android.R;
import org.blanco.techmun.entities.Mesa;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MesasCursorAdapter extends CursorAdapter
		implements OnClickListener{

	MesaListItemClickListener clickListener = null;
	
	public MesasCursorAdapter(Context context, Cursor c, 
			MesaListItemClickListener listener) {
		super(context,c);
		this.clickListener = listener;
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
		
		//set the OnClickListener of the item.
			view.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		TextView id = (TextView) v
				.findViewById(R.id.mesas_list_item_layout_id);
		TextView nombre = (TextView) v
				.findViewById(R.id.mesas_list_item_layout_nombre);
		TextView repres = (TextView) v
				.findViewById(R.id.mesas_list_item_layout_representante);
		//Build the messa object for the selected item.
		Mesa mesa = new Mesa();
		mesa.setId(Long.parseLong(id.getText().toString()));
		mesa.setNombre(nombre.getText().toString());
		mesa.setRepresentante(repres.getText().toString());
		clickListener.MesaListItemClicked(mesa);
	}
	
}
