package org.blanco.techmun.android;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * The activity that will handle the operations with the
 * Mesa objects. 
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class MesasActivity extends FragmentActivity {

	/** The intent action for this Activity as defined in the manifest */
	public static final String ACTION_INTENT = "org.blanco.techmun2011.MESAS";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mesas_layout);
	}
	
}
