package org.blanco.techmun.android;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.blanco.techmun.android.cproviders.TechMunContentProvider;
import org.blanco.techmun.android.misc.ExpandAnimation;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;

public class MensajesActivity extends Activity {

	public static final String INTENT_ACTION = "org.blanco.techmun2011.MENSAJES";

	PullToRefreshListView list = null;
	Button btnAceptar = null;
	Button btnCancel = null;
	RelativeLayout forma = null;
	TextView formHeader = null;
	boolean isFormExtended = false;
	MensajesLoader loader = null;
	TextView mensajesHeader = null;
	View actionBar = null;
	ImageButton btnAddImage = null;
	Bitmap attachImage = null;
	EditText edtTitulo = null;
	EditText edtMensaje = null;
	
	@Override
	protected void onCreate(Bundle arg0) {
		setContentView(R.layout.mensajes);
		init();
		super.onCreate(arg0);
	}
	
	
	private void init(){
		list = (PullToRefreshListView) findViewById(R.id.mensajes_list);
		list.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				loadMensajes();
			}
		});
		btnAceptar = (Button) findViewById(R.id.mensaje_post_mensaje_btn_acept);
		btnAceptar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendMensaje();
			}
		});
		btnCancel = (Button) findViewById(R.id.mensaje_post_mensaje_btn_cancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		formHeader = (TextView) findViewById(R.id.mensajes_post_mensaje_header);
		formHeader.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				animate();
			}
		});
		forma = (RelativeLayout) findViewById(R.id.mensaje_post_mensaje_post_form);
		mensajesHeader = (TextView) findViewById(R.id.mensajes_mensaje_header);
		actionBar = findViewById(R.id.mensajes_action_bar);
		btnAddImage = (ImageButton) findViewById(R.id.mensajes_post_mensaje_add);
		btnAddImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				attachImage();
			}
		});
		edtMensaje = (EditText) findViewById(R.id.mensajes_post_mensaje_edt_mensaje);
		edtTitulo = (EditText) findViewById(R.id.mensajes_post_mensaje_edt_titulo);
		
	}
	
	@Override
	protected void onStart() {
		loadMensajes();
		super.onStart();
	}
	
	protected void loadMensajes(){
		if (loader != null && loader.getStatus().equals(AsyncTask.Status.RUNNING)){
			loader.cancel(true);
		}
		loader = new MensajesLoader();
		loader.execute();
	}

	private void attachImage(){
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0 && resultCode == RESULT_OK){
			Cursor c = 	managedQuery(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 
					null, "_id = "+1, null, null);
			if (c != null && c.moveToNext()){
				Uri selectedImage = data.getData();
	            String[] filePathColumn = {MediaStore.Images.Media.DATA};

	            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
	            cursor.moveToFirst();

	            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	            String filePath = cursor.getString(columnIndex);
	            cursor.close();


	            attachImage = BitmapFactory.decodeFile(filePath);
	            Log.i("techmun", "selected image"+filePath+" Loaded.");

			}
			Log.d("techmun", "Returned from image pick");
			
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	private void animate(){
		int endHeight = (isFormExtended)? formHeader.getHeight() : 
			getWindowManager().getDefaultDisplay().getHeight() - 
			(actionBar.getHeight() + mensajesHeader.getHeight());
		isFormExtended = !isFormExtended;
		ExpandAnimation animation = new ExpandAnimation(forma, forma.getHeight() , endHeight);
		forma.startAnimation(animation);
	}
	
	private void sendMensaje(){
		ContentValues values = new ContentValues();
		values.put("titulo", edtTitulo.getText().toString());
		values.put("mensaje", edtMensaje.getText().toString());
		if (attachImage != null){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			attachImage.compress(CompressFormat.JPEG, 80, baos);
			values.put("foto", baos.toByteArray());
		}
		Uri msgUri = 
				getContentResolver().insert(Uri.parse(TechMunContentProvider.CONTENT_BASE_URI+"/mensajes"), 
						values);
		if (!Uri.EMPTY.equals(msgUri)){
			Toast.makeText(getBaseContext(), "Mensaje Enviado", 500).show();
		}
	}
	
	class MensajesLoader extends AsyncTask<Void, Void, ListAdapter>{

		@Override
		protected ListAdapter doInBackground(Void... params) {
			Cursor c = managedQuery(Uri.parse("content://org.blanco.techmun.android.mesasprovider/mensajes"), 
					null, null, null, null);
			List<String> mensajes = new ArrayList<String>();
			while(c.moveToNext()){
				mensajes.add(c.getString(c.getColumnIndex("mensaje")));
			}
			ArrayAdapter<String> adapter = 
					new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1,mensajes); 
			return adapter;
		}

		@Override
		protected void onPostExecute(ListAdapter result) {
			list.setAdapter(result);	
			list.onRefreshComplete();
		}
	}
	
}
