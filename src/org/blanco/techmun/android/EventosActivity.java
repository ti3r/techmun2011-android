package org.blanco.techmun.android;

import java.util.Date;

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
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;
import android.widget.TextView;

/**
 * The fragment in charge of retrieving and displaying
 * the Events of a designated table
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class EventosActivity extends FragmentActivity {

	public static final String ACTION_INTENT = "org.blanco.techmun2011.EVENTOS";
	
	Mesa mesa = null;
	ListView eventosList = null;
	EventosLoader loader = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		Object mesa = intent.getExtras().containsKey("mesa");
		if (mesa == null){
			finish();
		}
		setContentView(R.layout.eventos_layout);
		initComponents(intent);		
	}
	
	/**
	 * It initialises the components of this activity
	 */
	public void initComponents(Intent intent){
		mesa = (Mesa) intent.getExtras().get("mesa");
		
		TextView tNombre = (TextView) 
			findViewById(R.id.mesa_header_event_layout_nombre);
		TextView tRepresentante = (TextView) 
			findViewById(R.id.mesa_header_event_layout_responsable);
		tNombre.setText(mesa.getNombre());
		tRepresentante.setText(mesa.getRepresentante());
		eventosList = (ListView) findViewById(R.id.eventos_layout_eventos_list);
	}
		
	/**
	 * On start method of the activity.
	 * Note: start loading the events for the mesa object
	 */
	@Override
	protected void onStart() {
		super.onStart();
		loader = new EventosLoader();
		loader.execute();
	}

	private class EventosLoader extends AsyncTask<Void, Void, Eventos>{

		@Override
		protected Eventos doInBackground(Void... params) {
			Eventos result = new Eventos();
			Cursor c = getContentResolver().query(
					Uri.parse(TechMunContentProvider.CONTENT_BASE_URI+"/"+mesa.getId()+"/eventos"), 
					null, null, null, null);
			
			while (c.moveToNext()){
				Evento e = new Evento();
				e.setId(c.getLong(c.getColumnIndex(Evento.EVENTO_ID_COL_NAME)));
				e.setMesaId(c.getLong(c.getColumnIndex(Evento.EVENTO_MESAID_COL_NAME)));
				e.setEvento(c.getString(c.getColumnIndex(Evento.EVENTO_EVENTO_COL_NAME)));
				e.setFecha(new Date(Date.parse(
						c.getString(c.getColumnIndex(Evento.EVENTO_FECHA_COL_NAME)))));
				result.getEventos().add(e);
			}
			return result;
		}

		@Override
		protected void onPostExecute(Eventos result) {
			EventosListAdapter adapter = new EventosListAdapter(EventosActivity.this,
					R.layout.eventos_list_item_layout,
					result.getEventos().toArray(new Evento[]{}));
			eventosList.setAdapter(adapter);
		}

	}
	
}
