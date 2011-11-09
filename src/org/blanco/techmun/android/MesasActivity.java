/**
 * Tec ch mun 2011 for Android, is the android application used to 
 *  
 * review all the information that is generated during the event
 * Tec Ch Mun 2011 of the ITESM campus chihuahua.
 * You can use this application as an example of all the technologies
 * used in this app.
 * Copyright (C) 2011  Alexandro Blanco <ti3r.bubblenet@gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Visit http://tec-ch-mun-2011.herokuapps.com
 */
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
