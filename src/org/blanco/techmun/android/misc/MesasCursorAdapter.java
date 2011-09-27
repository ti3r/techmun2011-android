package org.blanco.techmun.android.misc;

import java.util.List;

import org.blanco.techmun.android.R;
import org.blanco.techmun.entities.Mesa;
import org.blanco.techmun.entities.Mesas;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MesasCursorAdapter extends ArrayAdapter<Mesa>
		implements OnItemClickListener{

	public MesasCursorAdapter(Context context, int resource, int textViewResourceId,
			List<Mesa> objects,MesaListItemClickListener listener) {
		super(context, textViewResourceId, objects);
		mesas = objects;
		clickListener = listener;
	}

	List<Mesa> mesas = null;
	MesaListItemClickListener clickListener = null;

	private void setViewValues(View view, Mesa mesa){
		TextView id = (TextView) view
				.findViewById(R.id.mesas_list_item_layout_id);
		TextView nombre = (TextView) view
				.findViewById(R.id.mesas_list_item_layout_nombre);
		TextView repres = (TextView) view
				.findViewById(R.id.mesas_list_item_layout_representante);
		ImageView img = (ImageView) view
				.findViewById(R.id.mesas_list_item_layout_logo);
		long vid = mesa.getId();
		String vnombre = mesa.getNombre();
		String vrep = mesa.getRepresentante().getNombre();
		String color = mesa.getColor();
		id.setText(String.valueOf(vid));
		nombre.setText(vnombre);
		repres.setText(vrep);
		img.setBackgroundColor(Color.parseColor(color));
		//set the OnClickListener of the item.
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null){
			convertView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.mesas_list_item_layout, null);
		}
		setViewValues(convertView, getItem(position));
		final int pos = position;
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				MesasCursorAdapter.this.clickListener.MesaListItemClicked(MesasCursorAdapter.this.getItem(pos));
			}
		});
		return convertView;
	}

	@Override
	public int getCount() {
		return this.mesas.size();
	}

	@Override
	public Mesa getItem(int arg0) {
		if (arg0 > (this.mesas.size()-1))
			throw new IndexOutOfBoundsException("index grater than mesas size");
		return this.mesas.get(arg0);
	}
	
	@Override
	public long getItemId(int arg0) {
		return ((Mesa)getItem(arg0)).getId();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Toast.makeText(arg1.getContext(), "Fua", 500).show();
	}

	
}
