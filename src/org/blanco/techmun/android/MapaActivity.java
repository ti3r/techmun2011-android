package org.blanco.techmun.android;

import android.app.Activity;
import android.os.Bundle;

public class MapaActivity extends Activity {

	protected static final String ACTION_INTENT = "org.blanco.techmun2011.MAPA";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.mapa_layout);
		super.onCreate(savedInstanceState);
	}

	
}
