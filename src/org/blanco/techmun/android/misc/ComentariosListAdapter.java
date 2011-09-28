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
	
	@Override
	public int getCount() {
		return (comentarios != null)? comentarios.getComentarios().size(): 0;
	}

	@Override
	public Object getItem(int position) {
		return (comentarios != null && position < comentarios.getComentarios().size())
				? comentarios.getComentarios().get(position): null;
	}

	@Override
	public long getItemId(int position) {
		return (comentarios != null && position < comentarios.getComentarios().size())
		? 1 : -1;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null){
			convertView = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.comentarios_list_item_layout, null);
		}
		Comentario comentario = (Comentario) getItem(position);
		((TextView)convertView.findViewById(R.id.comentarios_list_item_comentario))
			.setText(comentario.getComentario());
		((TextView)convertView.findViewById(R.id.comentarios_list_item_autor))
		.setText("Anonimo");
		return convertView;
	}

	public void addComentarios(Comentarios comentarios){
		this.comentarios.addComentarios(comentarios);
	}
}
