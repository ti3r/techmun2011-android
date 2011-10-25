package org.blanco.techmun.android;

import org.blanco.techmun.android.fragments.ComentariosListFragment;
import org.blanco.techmun.android.fragments.ComentariosPublishFragment;
import org.blanco.techmun.entities.Evento;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;


/**
 * The activity in charge of handling all the operations
 * concerning comentario objects for a specific event
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class ComentariosActivity extends FragmentActivity {

	public static final String ACTION_INTENT = "org.blanco.techmun2011.COMENTARIOS";
	
	private Evento evento = null;
	ComentariosPublishFragment publishFragment = null;
	ComentariosListFragment listFragment = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		if (!intent.getExtras().containsKey("evento")){
			Log.e("techmun", "Evento for ComentariosActivity is not set");
			finish();
		}
		this.evento = (Evento) intent.getExtras().get("evento");
		setContentView(R.layout.comentarios_layout);
		initComponents();		
	}
	
	public void initComponents(){
		TextView txtTitle = (TextView) findViewById(R.id.comentarios_layout_evento_title);
		txtTitle.setText(evento.getTitulo());
		TextView txtDesc = (TextView) findViewById(R.id.comentarios_layout_evento_desc);
		txtDesc.setText(evento.getDescripcion());
	}
	
	@Override
	protected void onStart() {
		//retrieve the publish comentario fragment
		if (publishFragment == null){
			publishFragment = (ComentariosPublishFragment) getSupportFragmentManager().
				findFragmentById(R.id.comentarios_layout_publish_comentario_fragment);
			publishFragment.setEventoId(this.evento.getId());
		}
		if (listFragment == null){
			listFragment = (ComentariosListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.comentarios_layout_comentarios_list_fragment);
		}
		if (listFragment != null && publishFragment != null){
			publishFragment.setListFragment(listFragment);
		}
		super.onStart();
	}

	public Evento getEvento(){
		return this.evento;
	}
}
