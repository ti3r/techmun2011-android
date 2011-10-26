package org.blanco.techmun.android;

import java.util.Date;

import org.blanco.techmun.android.misc.MensajeFotoLoader;
import org.blanco.techmun.android.misc.MensajeFotoLoader.MensajeFootLoaderExtraPostExecute;
import org.blanco.techmun.entities.Mensaje;
import org.blanco.techmun.entities.Usuario;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MensajeDetailsActivity extends Activity implements
	MensajeFootLoaderExtraPostExecute{

	protected static final String INTENT_ACTION = "org.blanco.techmun2011.MENSAJE_DTLS";

	ImageView img = null;
	Mensaje mensaje = null;
	MensajeFotoLoader loader  = null;
	ProgressBar progress = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.mensaje_details_layout);
		if (!getIntent().getExtras().containsKey("mensaje") && 
				!getIntent().getExtras().containsKey("id")	){
			finish();
		}
		init(getIntent().getExtras());
		super.onCreate(savedInstanceState);
	}
	
	public void init(Bundle extras){
		String sMsg = extras.getString("mensaje");
		mensaje = new Mensaje(sMsg, null);
		mensaje.setId(extras.getLong("id"));
		if (extras.containsKey("autor")){
			mensaje.setAutor((Usuario) extras.getSerializable("autor"));
		}
		mensaje.setFecha((Date) extras.getSerializable("fecha"));
		
		img = (ImageView) findViewById(R.id.mensaje_details_layout_image);
		
		TextView txtMensaje = (TextView) findViewById(R.id.mensaje_details_layout_mensaje);
		txtMensaje.setText(getIntent().getStringExtra("mensaje"));
		TextView txtAutor = (TextView) findViewById(R.id.mensaje_details_layout_txt_autor);
		if (mensaje.getAutor() != null){
			txtAutor.setText(mensaje.getAutor().getNombre());
		}
		TextView txtFecha = (TextView) findViewById(R.id.mensaje_details_layout_txt_fecha);
		txtFecha.setText(mensaje.getFecha().toLocaleString());
		progress = (ProgressBar) findViewById(R.id.mensaje_details_layout_loading_progress);
	}

	@Override
	protected void onStart() {
		if (loader != null && loader.getStatus().equals(AsyncTask.Status.RUNNING)){
			loader.cancel(true);
		}
		loader = new MensajeFotoLoader(mensaje, img, this);
		loader.execute();
		super.onStart();
	}

	@Override
	public void executeAfterOnPostExecute(Bitmap bitmap) {
		if (progress != null){
			progress.setVisibility(View.GONE);
		}
	}
	
}
