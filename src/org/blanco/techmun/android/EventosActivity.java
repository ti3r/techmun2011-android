package org.blanco.techmun.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
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
	
	Long mesa = null;
	String nombre = null;
	String representante = null;
	ListView eventosList = null;
	
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
		mesa = intent.getLongExtra("mesa", -1);
		nombre = intent.getStringExtra("nombre");
		representante = intent.getStringExtra("representante");
		
		TextView tNombre = (TextView) 
			findViewById(R.id.mesa_header_event_layout_nombre);
		TextView tRepresentante = (TextView) 
			findViewById(R.id.mesa_header_event_layout_responsable);
		tNombre.setText(nombre);
		tRepresentante.setText(representante);
		eventosList = (ListView) findViewById(R.id.eventos_layout_eventos_list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.array.test_string_array);
		eventosList.setAdapter(adapter);
		
	}
	
}
