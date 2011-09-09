package org.blanco.techmun.android;

import org.blanco.techmun.android.fragments.EventosListFragment;
import org.blanco.techmun.entities.Mesa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
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
	EventosListFragment eventosListFragment = null;
	
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
		eventosListFragment = (EventosListFragment) getSupportFragmentManager().findFragmentById(
				R.id.eventos_layout_eventos_list_fragment);
		Log.i("techmun2011", "fragment loaded");
		eventosListFragment.setMesa(mesa);
	}
	
}
