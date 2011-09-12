package org.blanco.techmun.android;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.blanco.techmun.android.cproviders.TechMunContentProvider;
import org.blanco.techmun.entities.Evento;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;


/**
 * The activity in charge of handling all the operations
 * concerning comentario objects for a specific event
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class ComentariosActivity extends ListActivity {

	private Evento evento = null;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		
		setContentView(R.layout.comentarios_list_layout);
		//initComponents(intent);		
	}
	
	
	/**
	 * The class in charge of loading the Eventos object from the content
	 * provider and populate the list of the fragment.
	 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
	 *
	 */
	private class ComentariosLoader extends AsyncTask<Evento, Void, List<String>>{

		@Override
		protected void onPreExecute() {
			//showProgressBar();
		}

		@Override
		protected List<String> doInBackground(Evento... eventos) {
			if (eventos.length != 1){
				throw new RuntimeException("Can only load comments for one evento." +
						" Params Length: "+eventos.length);
			}
			Evento mesa = eventos[0];
			List<String> result = new ArrayList<String>();
			Cursor c = getContentResolver().query(
					Uri.parse(TechMunContentProvider.CONTENT_BASE_URI+"/"+mesa.getId()+"/eventos"), 
					null, null, null, null);
			if (c != null){
				while (c.moveToNext()){
					Evento e = new Evento();
					e.setId(c.getLong(c.getColumnIndex(Evento.EVENTO_ID_COL_NAME)));
					e.setMesaId(c.getLong(c.getColumnIndex(Evento.EVENTO_MESAID_COL_NAME)));
					e.setEvento(c.getString(c.getColumnIndex(Evento.EVENTO_EVENTO_COL_NAME)));
					e.setFecha(new Date(Date.parse(
							c.getString(c.getColumnIndex(Evento.EVENTO_FECHA_COL_NAME)))));
					//result.getEventos().add(e);
				}
				c.close();
			}
			return result;
		}

		@Override
		protected void onPostExecute(List<String> result) {
//			EventosListAdapter adapter = new EventosListAdapter(result);
//			eventosList.setAdapter(adapter);
//			adapter.notifyDataSetChanged();
//			hideProgressBar();
		}

	}
	
}
