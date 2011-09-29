package org.blanco.techmun.android;

import org.blanco.techmun.entities.Evento;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
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
	
	public Evento getEvento(){
		return this.evento;
	}
}
