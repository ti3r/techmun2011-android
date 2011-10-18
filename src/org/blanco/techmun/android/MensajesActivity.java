package org.blanco.techmun.android;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MensajesActivity extends FragmentActivity {

	public static final String INTENT_ACTION = "org.blanco.techmun2011.MENSAJES";

	@Override
	protected void onCreate(Bundle arg0) {
		setContentView(R.layout.mensajes);
		super.onCreate(arg0);
	}
	
}
