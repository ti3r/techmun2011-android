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
import android.widget.Toast;

public class MensajeDetailsActivity extends Activity implements
	MensajeFootLoaderExtraPostExecute{

	protected static final String INTENT_ACTION = "org.blanco.techmun2011.MENSAJE_DTLS";

	ImageView img = null;
	Mensaje mensaje = null;
	MensajeFotoLoader loader  = null;
	ProgressBar progress = null;
	StringBuilder caption = new StringBuilder();
	
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
		img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showCaption();
			}
		});
		caption.append(getIntent().getStringExtra("mensaje"));
		if (mensaje.getAutor() != null){
			caption.append("\n").append(mensaje.getAutor().getNombre());
		}
		caption.append("\n").append(mensaje.getFecha().toLocaleString());
		progress = (ProgressBar) findViewById(R.id.mensaje_details_layout_loading_progress);
	}

	private void showCaption(){
		Toast.makeText(getBaseContext(), caption.toString(), 7000).show();
	}
	
	@Override
	protected void onStart() {
		if (loader != null && loader.getStatus().equals(AsyncTask.Status.RUNNING)){
			loader.cancel(true);
		}
		loader = new MensajeFotoLoader(mensaje, img, this);
		loader.execute();
		showCaption();
		super.onStart();
	}

	@Override
	public void executeAfterOnPostExecute(Bitmap bitmap) {
		if (progress != null){
			progress.setVisibility(View.GONE);
		}
	}
	
}
