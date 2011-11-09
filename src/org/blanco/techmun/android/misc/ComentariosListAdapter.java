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

import org.blanco.techmun.android.R;
import org.blanco.techmun.entities.Comentario;
import org.blanco.techmun.entities.Comentarios;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ComentariosListAdapter extends BaseAdapter {

	private Comentarios comentarios = null;
	
	public ComentariosListAdapter(Comentarios comentarios){
		this.comentarios = comentarios;
	}
	
	public int getCount() {
		return (comentarios != null)? comentarios.getComentarios().size(): 0;
	}

	public Object getItem(int position) {
		return (comentarios != null && position < comentarios.getComentarios().size())
				? comentarios.getComentarios().get(position): null;
	}

	public long getItemId(int position) {
		return (comentarios != null && position < comentarios.getComentarios().size())
		? 1 : -1;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null){
			convertView = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.comentarios_list_item_layout, null);
		}
		Comentario comentario = (Comentario) getItem(position);
		((TextView)convertView.findViewById(R.id.comentarios_list_item_comentario))
			.setText(comentario.getComentario());
		if(comentario.getAutor() != null && !comentario.getAutor().equals("")){
			((TextView)convertView.findViewById(R.id.comentarios_list_item_autor))
			.setText(comentario.getAutor());
		}
		return convertView;
	}

	public void addComentarios(Comentarios comentarios){
		this.comentarios.addComentarios(comentarios);
	}
}
