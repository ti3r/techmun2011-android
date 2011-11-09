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
package org.blanco.techmun.android.fragments;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.blanco.techmun.android.ComentariosActivity;
import org.blanco.techmun.android.R;
import org.blanco.techmun.android.cproviders.FetchComentariosResult;
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
	private Button btnMas = null;
	private Integer nextPageToFetch = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup group, 
			Bundle bundle){
		setRetainInstance(true);
		View v = inflater.inflate(R.layout.comentarios_list_layout, null);
		progress = (ProgressBar) v.findViewById(R.id.comentarios_list_layout_title_bar_progress_bar);
		btnRefresh = (Button) v.findViewById(R.id.comentarios_list_layout_refresh_button);
		btnRefresh.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				refreshComentarios(false);
			}
		});
		btnMas = (Button) v.findViewById(R.id.comentarios_list_layout_more_button);
		btnMas.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				refreshComentarios(true);
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


	public void refreshComentarios(boolean append){
		if (this.evento != null){
			if (loader != null && loader.getStatus() == AsyncTask.Status.RUNNING){
				loader.cancel(true);
			}
			loader = new ComentariosLoader(append,nextPageToFetch);
			loader.execute(this.evento);
		}
	}

	public void setEvento(Evento evento){
		this.evento = evento;
		refreshComentarios(false);
	}
	
	
	
	/**
	 * The class in charge of loading the Eventos object from the content
	 * provider and populate the list of the fragment.
	 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
	 *
	 */
	private class ComentariosLoader extends AsyncTask<Evento, Void, List<Comentario>>{

		private boolean fetchMore = false;
		private Integer currentPage = 0;
		private boolean append = false;
		
		public ComentariosLoader(boolean append, Integer page) {
			this.append = append;
			//if not append the page it means complete refresh so current page should be 0
			this.currentPage = (append == false)? 0 : page;
		}
		
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
					null,"pagina="+currentPage, 
					null, null);
			if (c != null){
				//Only one result is returned in this call of type ComentariosFetcher.FetchResult
				if (c.moveToNext()){
					
					try {
						ByteArrayInputStream input = new ByteArrayInputStream(c.getBlob(0));
						ObjectInputStream stream = new ObjectInputStream(input);
						Object object = stream.readObject();
						if (object instanceof FetchComentariosResult){
							FetchComentariosResult fetchResult = 
									(FetchComentariosResult) object;
							if (fetchResult.comentarios != null){
								for(Comentario comentario : fetchResult.comentarios){
									result.add(comentario);
								}
							}
							//Set the result state of this fetcher
							fetchMore = fetchResult.mas;
							currentPage = (fetchResult.pagina != null) ? fetchResult.pagina : 0;
						}else{
							Log.e("techmun", "Unable to retrieve FetchComentariosResult class from the " +
									"Objects Cursor. result is not instance of this class");
						}
						stream.close();
					} catch (Exception e) {
						Log.e("techmun", "Error parsing eventos",e);
					}
					
				}				
			}
			return result;
		}

		@Override
		protected void onPostExecute(List<Comentario> result) {
			Comentarios comentarios = new Comentarios();
			comentarios.getComentarios().addAll(result);
			if (append && ComentariosListFragment.this.getListAdapter() != null){
				//retrieve the list adapter
				ComentariosListAdapter adapter = (ComentariosListAdapter) ComentariosListFragment.this.getListAdapter();
				adapter.addComentarios(comentarios);
				adapter.notifyDataSetChanged();
			}else{
				//Build a new adapter and add the retrieved results
				ComentariosListAdapter adapter = new ComentariosListAdapter(comentarios);
				ComentariosListFragment.this.setListAdapter(adapter);
				
			}
			ComentariosListFragment.this.progress.setVisibility(View.GONE);
			ComentariosListFragment.this.nextPageToFetch = this.currentPage;
			if (!this.fetchMore){
				ComentariosListFragment.this.btnMas.setVisibility(View.GONE);
			}else{
				ComentariosListFragment.this.btnMas.setVisibility(View.VISIBLE);
			}
			super.onPostExecute(result);
		}
		
		
	}
	
}
