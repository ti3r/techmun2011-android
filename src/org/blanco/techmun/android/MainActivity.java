package org.blanco.techmun.android;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {
    
	View mesasMenuItem = null;
	View mapaMenuItem = null;
	View mensajesMenuItem = null;
	View logInMenuItem = null;
	View calendarioMenuItem = null;
	String user = null;
	TextView txtUser = null;
	
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
				String uri = getString(R.string.initial_geo_uri);
				Uri geoUri = Uri.parse(uri);
				Intent intent = new Intent(Intent.ACTION_VIEW,geoUri);
				startActivity(intent);
			}
		});
    	mensajesMenuItem = findViewById(R.id.menu_item_mensajes);
    	mensajesMenuItem.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MensajesActivity.INTENT_ACTION);
				if (user != null && !user.equals(""))
					intent.putExtra("user-name", user);
				startActivity(intent);
			}
		});
    	logInMenuItem = findViewById(R.id.menu_item_login);
    	logInMenuItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(LoginActivity.ACTION_INTENT), 0);
			}
		});
    	
    	calendarioMenuItem = findViewById(R.id.menu_item_calendario);
    	calendarioMenuItem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				Intent i = new Intent(Intent.ACTION_EDIT);
//				i.setType("vnd.android.cursor.item/event");
//				i.putExtra("title", "some title");
//				i.putExtra("description", "Some description");
//				i.putExtra("beginTime", System.currentTimeMillis());
//				i.putExtra("endTime", System.currentTimeMillis()+4000);
//				startActivity(i);
			}
		});
    	txtUser = (TextView) findViewById(R.id.main_menu_user);
    	setUser();
    }

    
    private void setUser(){
    	user = PreferenceManager.getDefaultSharedPreferences(getBaseContext())
    	.getString("user", null);
    	if (user != null && !user.equals("")){
    		txtUser.setText(getString(R.string.hola)+" "+user);
    		txtUser.setVisibility(View.VISIBLE);
    	}else{
    		txtUser.setVisibility(View.GONE);
    	}
    }
	@Override
	protected void onActivityResult(int reqCode, int resCode, Intent data) {
		if (reqCode == 0 && resCode == Activity.RESULT_OK){
			//set the user name for the application
			setUser();
		}
		super.onActivityResult(reqCode, resCode, data);
	}
    
    
}