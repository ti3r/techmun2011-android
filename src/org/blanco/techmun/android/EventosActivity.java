package org.blanco.techmun.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * The fragment in charge of retrieving and displaying
 * the Events of a designated table
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class EventosActivity extends Activity {

	public static final String ACTION_INTENT = "org.blanco.techmun2011.EVENTOS";
	
	Long mesa = null;
	
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
		Toast.makeText(this, mesa.toString(), 500);
	}
	
}
