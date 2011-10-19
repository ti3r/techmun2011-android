package org.blanco.techmun.android.fragments;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;

import org.blanco.techmun.android.R;
import org.blanco.techmun.android.cproviders.TechMunContentProvider;
import org.blanco.techmun.android.misc.EventosListAdapter;
import org.blanco.techmun.entities.Evento;
import org.blanco.techmun.entities.Eventos;
import org.blanco.techmun.entities.Mesa;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

public class EventosListFragment extends Fragment 
	implements OnItemClickListener{

	EventosLoader loader = null;
	Mesa mesa = null;
	ProgressBar progress = null;
	ListView eventosList = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.eventos_list_layout, null);
		progress = (ProgressBar) v
				.findViewById(R.id.eventos_list_layout_title_bar_progress_bar);
		eventosList = (ListView) v
				.findViewById(R.id.eventos_list_layout_eventos_list);
		eventosList.setEmptyView(inflater.inflate(R.layout.eventos_list_empty_layout, null));
		eventosList.setOnItemClickListener(this);
		return v;
	}
	
	@Override
	public void onStart() {
		loadMesaEvents();
		super.onStart();
	}

	private void loadMesaEvents(){
		if (mesa != null ){
			loader = new EventosLoader();
			loader.execute(mesa);
		}
	}

	public void setMesa(Mesa mesa){
		this.mesa = mesa;
		loadMesaEvents();
	}

	//Created two methods for clarity reasons.
	/**
	 * Show the progress bar in the top of the fragment
	 * that means that something is loading and we should
	 * wait
	 */
	public void showProgressBar(){
		if (this.progress != null){
			progress.setVisibility(View.VISIBLE);
		}
	}
	/**
	 * Hide the progress bar in the top of the fragment
	 * that means that something that the loading is done
	 */
	public void hideProgressBar(){
		if (this.progress != null){
			progress.setVisibility(View.GONE);
		}
	}
	
	/**
	 * The class in charge of loading the Eventos object from the content
	 * provider and populate the list of the fragment.
	 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
	 *
	 */
	private class EventosLoader extends AsyncTask<Mesa, Void, Eventos>{

		@Override
		protected void onPreExecute() {
			showProgressBar();
		}

		@Override
		protected Eventos doInBackground(Mesa... mesas) {
			if (mesas.length != 1){
				throw new RuntimeException("Can only load eventos for one mesa." +
						" Params Length: "+mesas.length);
			}
			Mesa mesa = mesas[0];
			Eventos result = new Eventos();
			Cursor c = getActivity().getContentResolver().query(
					Uri.parse(TechMunContentProvider.CONTENT_BASE_URI+"/"+mesa.getId()+"/eventos"), 
					null, null, null, null);
			if (c != null){
				int x = 0;
				while (c.moveToNext()){
					try {
						ByteArrayInputStream input = new ByteArrayInputStream(c.getBlob(x++));
						ObjectInputStream stream = new ObjectInputStream(input);
						Evento e  = (Evento) stream.readObject();
						result.getEventos().add(e);
					} catch (StreamCorruptedException e) {
						Log.e("techmun", "Error parsing eventos",e);
					} catch (IOException e) {
						Log.e("techmun", "Error parsing eventos",e);
					} catch (ClassNotFoundException e) {
						Log.e("techmun", "Error parsing eventos",e);
					}
				}
				c.close();
			}
			return result;
		}

		@Override
		protected void onPostExecute(Eventos result) {
			EventosListAdapter adapter = new EventosListAdapter(result);
			eventosList.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			hideProgressBar();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int pres, long time) {
		int position = adapterView.getPositionForView(view);
		Evento e = (Evento) eventosList.getAdapter().getItem(position);
		Intent intent = new Intent("org.blanco.techmun2011.COMENTARIOS");
		intent.putExtra("evento", e);
		startActivity(intent);
	}
}
