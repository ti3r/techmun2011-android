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

import org.blanco.techmun.android.fragments.EventosListFragment;
import org.blanco.techmun.entities.Mesa;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
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
		
		tRepresentante.setText((mesa.getRepresentante() != null)?
				mesa.getRepresentante().getNombre(): "");
		TextView tDescripcion = (TextView) 
				findViewById(R.id.mesa_header_event_layout_descripcion);
		tDescripcion.setText((mesa.getDescripcion() != null)?mesa.getDescripcion() : "");
		String scolor = mesa.getColor();
		int color = Color.parseColor(scolor);
		LinearLayout img = ((LinearLayout)findViewById(R.id.eventos_layout_mesa_bar));
		img.setBackgroundColor(color);
		
		eventosListFragment = (EventosListFragment) getSupportFragmentManager().findFragmentById(
						R.id.eventos_layout_eventos_list_fragment);
		Log.i("techmun2011", "fragment loaded");
		eventosListFragment.setMesa(mesa);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = new MenuInflater(getBaseContext());
		inflater.inflate(R.menu.eventos_list_fragment_options_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.eventos_list_menu_item_refresh:
			eventosListFragment.postRefresh();
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
