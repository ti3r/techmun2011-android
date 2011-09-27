package org.blanco.techmun.android.fragments;

import java.util.List;

import org.blanco.techmun.android.R;
import org.blanco.techmun.android.cproviders.TechMunContentProvider;
import org.blanco.techmun.entities.Comentario;
import org.blanco.techmun.entities.Evento;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class ComentariosListFragment extends ListFragment {

	private Evento evento = null;
	private ProgressBar progress = null;
	ComentariosLoader loader = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, 
			Bundle bundle){
		View v = inflater.inflate(R.layout.comentarios_list_layout, null);
		progress = (ProgressBar) v.findViewById(R.id.comentarios_list_layout_title_bar_progress_bar);
		return v;
	}
	
	@Override
	public void onStart() {
		loader = new ComentariosLoader();
		loader.execute(this.evento);
		super.onStart();
	}



	public void setEvento(Evento evento){
		this.evento = evento;
	}
	
	
	
	/**
	 * The class in charge of loading the Eventos object from the content
	 * provider and populate the list of the fragment.
	 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
	 *
	 */
	private class ComentariosLoader extends AsyncTask<Evento, Void, List<Comentario>>{

		@Override
		protected void onPreExecute() {
			ComentariosListFragment.this.progress.setVisibility(View.VISIBLE);
			super.onPreExecute();
		}

		@Override
		protected List<Comentario> doInBackground(Evento... arg0) {
			getActivity().managedQuery(Uri.parse(
					TechMunContentProvider.CONTENT_BASE_URI+"/comentarios/"+evento.getId()), 
					null,null, null, null);
			return null;
		}

		@Override
		protected void onPostExecute(List<Comentario> result) {
			// TODO Auto-generated method stub
			ComentariosListFragment.this.progress.setVisibility(View.GONE);
			super.onPostExecute(result);
		}
		
		
	}
	
}
