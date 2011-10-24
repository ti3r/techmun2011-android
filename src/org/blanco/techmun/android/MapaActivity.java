package org.blanco.techmun.android;

import android.app.Activity;
import android.os.Bundle;

public class MapaActivity extends Activity {

	
	public static final String ACTION_INTENT = "org.blanco.techmun2011.MAPA";
	
	@Override
	protected void onCreate(Bundle arg0) {
		setContentView(R.layout.mapa_layout);
		super.onCreate(arg0);
	}


}
