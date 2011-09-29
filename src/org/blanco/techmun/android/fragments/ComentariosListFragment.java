package org.blanco.techmun.android.fragments;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import org.blanco.techmun.android.ComentariosActivity;
import org.blanco.techmun.android.R;
import org.blanco.techmun.android.cproviders.TechMunContentProvider;
import org.blanco.techmun.android.misc.ComentariosListAdapter;
import org.blanco.techmun.entities.Comentario;
import org.blanco.techmun.entities.Comentarios;
import org.blanco.techmun.entities.Evento;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

public class ComentariosListFragment extends ListFragment {

	private Evento evento = null;
	private ProgressBar progress = null;
	private ComentariosLoader loader = null;
	private Button btnRefresh = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, 
			Bundle bundle){
		View v = inflater.inflate(R.layout.comentarios_list_layout, null);
		progress = (ProgressBar) v.findViewById(R.id.comentarios_list_layout_title_bar_progress_bar);
		btnRefresh = (Button) v.findViewById(R.id.comentarios_list_layout_refresh_button);
		btnRefresh.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				refreshComentarios();
			}
		});
		return v;
	}
	
	@Override
	public void onStart() {
		Activity act = getActivity();
		if (act instanceof ComentariosActivity){
			setEvento(((ComentariosActivity)act).getEvento());
		}
		super.onStart();
	}


	public void refreshComentarios(){
		if (this.evento != null){
			if (loader != null && loader.getStatus() == AsyncTask.Status.RUNNING){
				loader.cancel(true);
			}
			loader = new ComentariosLoader();
			loader.execute(this.evento);
		}
	}

	public void setEvento(Evento evento){
		this.evento = evento;
		refreshComentarios();
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
			ComentariosListFragment.this.getListView().clearChoices();
			super.onPreExecute();
		}

		@Override
		protected List<Comentario> doInBackground(Evento... arg0) {
			List<Comentario> result = new ArrayList<Comentario>();
			Cursor c =	getActivity().managedQuery(Uri.parse(
					TechMunContentProvider.CONTENT_BASE_URI+"/comentarios/"+evento.getId()), 
					null,null, null, null);
			if (c != null){
				int x = 0;
				while (c.moveToNext()){
					try {
						ByteArrayInputStream input = new ByteArrayInputStream(c.getBlob(x++));
						ObjectInputStream stream = new ObjectInputStream(input);
						Comentario coment = (Comentario) stream.readObject();
						result.add(coment);
					} catch (StreamCorruptedException e) {
						Log.e("techmun", "Error parsing eventos",e);
					} catch (IOException e) {
						Log.e("techmun", "Error parsing eventos",e);
					} catch (ClassNotFoundException e) {
						Log.e("techmun", "Error parsing eventos",e);
					}
				}				
			}
			return result;
		}

		@Override
		protected void onPostExecute(List<Comentario> result) {
			// TODO Auto-generated method stub
			Comentarios comentarios = new Comentarios();
			comentarios.getComentarios().addAll(result);
			ComentariosListAdapter adapter = new ComentariosListAdapter(comentarios);
			ComentariosListFragment.this.setListAdapter(adapter);
			ComentariosListFragment.this.progress.setVisibility(View.GONE);
			super.onPostExecute(result);
		}
		
		
	}
	
}
