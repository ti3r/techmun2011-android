/**
 * Tec ch mun 2011 for Android, is the android application used to 
 *  
 * review all the information that is generated during the event
 * Tec Ch Mun 2011 of the ITESM campus chihuahua.
 * You can use this application as an example of all the technologies
 * used in this app.
 * Copyright (C) 2011  Alexandro Blanco <ti3r.bubblenet@gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Visit http://tec-ch-mun-2011.herokuapps.com
 */
package org.blanco.techmun.android.misc;

import java.util.List;

import org.blanco.techmun.android.R;
import org.blanco.techmun.entities.Mensaje;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * The List adapter that populates a list view based on the 
 * passed Mensajes.
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class MensajesListAdapter extends BaseAdapter {

	List<Mensaje> mensajes = null;
	
	public MensajesListAdapter(List<Mensaje> mensajes){
		this.mensajes = mensajes;
	}
	/**
	 * Establishes the values of the view with the properties of 
	 * the passed Mensaje. The View must be an inflate of the 
	 * R.layout.mensajes_list_item_layout layout.
	 * 
	 * @param view The View object where to set the values of the Evento
	 * @param evento The Evento object where to get the properties from
	 */
	private void setViewValues(View view, Mensaje mensaje){
		TextView txtMensaje =  (TextView) view.findViewById(R.id.mensajes_list_item_mensaje);
		txtMensaje.setText(mensaje.getMensaje());
		ImageView foto =  (ImageView) view.findViewById(R.id.mensajes_list_item_foto);
		if (mensaje.getFoto() == null){
			foto.setImageDrawable(view.getContext().getResources().getDrawable(
				android.R.drawable.progress_indeterminate_horizontal)
				);
		}else{
			foto.setImageBitmap(mensaje.getFoto());
		}
		TextView footer = (TextView) view.findViewById(R.id.mensajes_list_item_footer);
		StringBuilder sFooter = new StringBuilder();
		if (mensaje.getAutor() != null){
			sFooter.append(mensaje.getAutor().getNombre()).append(" - ");
		}
		sFooter.append(DateFormat.getDateFormat(view.getContext()).format(mensaje.getFecha()));
		footer.setText(sFooter);
	}

	public List<Mensaje> getMensajesList(){
		return this.mensajes;
	}
	
	public int getCount() {
		return (this.mensajes != null) ? this.mensajes.size() : 0;
	}
	
	public Object getItem(int position) {
		
		return (this.mensajes != null && this.mensajes.size() >= position ) ?
				this.mensajes.get(position) : null ;
	}
	
	public long getItemId(int position) {
		Mensaje mensaje = (Mensaje) getItem(position);
		return (mensaje != null)? mensaje.getId() : 0;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		if ( convertView == null ) { //we need to inflate a new view
			convertView = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.mensajes_list_item, null);
		}
		setViewValues(convertView, (Mensaje)getItem(position));
		return convertView;
	}
	@Override
	public boolean isEmpty() {
		return this.mensajes.isEmpty();
	}	
	
}
