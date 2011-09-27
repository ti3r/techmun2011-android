package org.blanco.techmun.android.misc;

import org.blanco.techmun.android.R;
import org.blanco.techmun.entities.Evento;
import org.blanco.techmun.entities.Eventos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * The List adapter that populates a list view based on the 
 * passed Eventos.
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class EventosListAdapter extends BaseAdapter{

	Eventos eventos = null;
	
	public EventosListAdapter(Eventos eventos){
		this.eventos = eventos;
	}
	/**
	 * Establishes the values of the view with the properties of 
	 * the passed Evento. The View must be an inflate of the 
	 * R.layout.eventos_list_item_layout layout.
	 * 
	 * @param view The View object where to set the values of the Evento
	 * @param evento The Evento object where to get the properties from
	 */
	private void setViewValues(View view, Evento evento){
		((TextView)view.findViewById(R.id.eventos_list_item_layout_desc))
			.setText(evento.getTitulo());
		((TextView)view.findViewById(R.id.eventos_list_item_layout_fecha))
			.setText(evento.getFecha().toString());
	}

	@Override
	public int getCount() {
		return (this.eventos != null) ? this.eventos.getEventos().size() : 0;
	}
	
	@Override
	public Object getItem(int position) {
		
		return (this.eventos != null && this.eventos.getEventos().size() >= position ) ?
				this.eventos.getEventos().get(position) : null ;
	}
	
	@Override
	public long getItemId(int position) {
		Evento evento = (Evento) getItem(position);
		return (evento != null)? evento.getId() : 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if ( convertView == null ) { //we need to inflate a new view
			convertView = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.eventos_list_item_layout, null);
		}
		setViewValues(convertView, (Evento)getItem(position));
		return convertView;
	}
	@Override
	public boolean isEmpty() {
		return this.eventos.getEventos().isEmpty();
	}	
		
}
