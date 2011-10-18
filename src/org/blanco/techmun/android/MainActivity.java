package org.blanco.techmun.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public class MainActivity extends FragmentActivity {
    
	View mesasMenuItem = null;
	View mapaMenuItem = null;
	View mensajesMenuItem = null;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initComponents();
    }
    
    /**
     * It initialises the visual components of the 
     * main activity in order to attach the appropriate
     * event handlers
     */
    public void initComponents(){
    	mesasMenuItem = findViewById(R.id.main_layout_mesas_icon);
    	mesasMenuItem.setOnClickListener(new View.OnClickListener() {
			//Launch the Mesas Activity
			public void onClick(View v) {
				Intent intent = new Intent(MesasActivity.ACTION_INTENT);
				startActivity(intent);
			}
		});
    	mapaMenuItem = findViewById(R.id.main_layout_mapa_icon);
    	mapaMenuItem.setOnClickListener(new View.OnClickListener() {
			//Launch the Mapa Activity
			public void onClick(View v) {
				
			}
		});
    	mensajesMenuItem = findViewById(R.id.menu_item_mensajes);
    	mensajesMenuItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MensajesActivity.INTENT_ACTION));
			}
		});
    }
}