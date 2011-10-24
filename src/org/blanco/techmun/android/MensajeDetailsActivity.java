package org.blanco.techmun.android;

import org.blanco.techmun.entities.Mensaje;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MensajeDetailsActivity extends Activity {

	protected static final String INTENT_ACTION = "org.blanco.techmun2011.MENSAJE_DTLS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.mensaje_details_layout);
		if (!getIntent().getExtras().containsKey("mensaje") ){
			finish();
		}
		Mensaje mensaje =
		(Mensaje) getIntent().getExtras().getSerializable("mensaje");
		init(mensaje);
		super.onCreate(savedInstanceState);
	}
	
	public void init(Mensaje mensaje){
		ImageView img = (ImageView) findViewById(R.id.mensaje_details_layout_image);
		if (mensaje.getFoto() != null){
			img.setImageBitmap(mensaje.getFoto());
		}
		TextView txtMensaje = (TextView) findViewById(R.id.mensaje_details_layout_mensaje);
		txtMensaje.setText(mensaje.getMensaje());
		TextView txtAutor = (TextView) findViewById(R.id.mensaje_details_layout_txt_autor);
		txtAutor.setText(mensaje.getAutor().getNombre());
		TextView txtFecha = (TextView) findViewById(R.id.mensaje_details_layout_txt_fecha);
		txtFecha.setText(mensaje.getFecha().toLocaleString());
	}
	
}
