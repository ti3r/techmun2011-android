package org.blanco.techmun.android.fragments;

import org.blanco.techmun.android.R;
import org.blanco.techmun.entities.Evento;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ComentariosListFragment extends ListFragment {

	private Evento evento = null;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, 
			Bundle bundle){
		return inflater.inflate(R.layout.comentarios_list_layout, null);
	}
}
