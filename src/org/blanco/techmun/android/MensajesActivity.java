package org.blanco.techmun.android;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.blanco.techmun.android.misc.ExpandAnimation;
import org.blanco.techmun.android.misc.MensajesListAdapter;
import org.blanco.techmun.entities.Mensaje;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HeaderViewListAdapter;
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
	EditText edtMensaje = null;
	String userName = null;
	
	@Override
	protected void onCreate(Bundle arg0) {
		setContentView(R.layout.mensajes);
		userName = getIntent().getStringExtra("user-name");
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
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int index,
					long itemId) {
				Mensaje Mensaje = (org.blanco.techmun.entities.Mensaje) 
						list.getAdapter().getItem(index);
				Intent i = new Intent(MensajeDetailsActivity.INTENT_ACTION);
				
				i.putExtra("mensaje",  Mensaje);
				startActivity(i);
				Log.i("techmun", "Display details for mensaje"+Mensaje);
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
		edtMensaje.clearFocus();
		//Hide the post mensaje form if no user is specified
		if (userName == null){
			forma.setVisibility(View.GONE);
		}
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
	            //Mark the button as image attached
	            btnAddImage.setBackgroundColor(Color.YELLOW);
	            Log.i("techmun", "selected image"+filePath+" Loaded.");

			}
			Log.d("techmun", "Returned from image pick");
			
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	
	
	@Override
	public Object onRetainNonConfigurationInstance() {
		if (list.getAdapter() != null){
			HeaderViewListAdapter adapter = (HeaderViewListAdapter) list.getAdapter(); 
			return ((MensajesListAdapter)adapter.getWrappedAdapter()).getMensajesList();
		}else{
			return super.onRetainNonConfigurationInstance();
		}
	}

	@Override
	protected void onStart() {
		list.requestFocus();
		Object mensajes = getLastNonConfigurationInstance();
		if (mensajes != null){
			list.setAdapter(new MensajesListAdapter((List<Mensaje>) mensajes));
		}else{
			loadMensajes();
		}
		super.onStart();
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
		Toast.makeText(getBaseContext(), getString(R.string.mensaje_sending), Toast.LENGTH_SHORT)
		.show();
		ContentValues values = new ContentValues();
		values.put("nombre", "alex");
		values.put("mensaje", edtMensaje.getText().toString());
		if (attachImage != null){
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			attachImage.compress(CompressFormat.PNG, 50, baos);
			values.put("foto",baos.toByteArray());
			values.put("foto-format", "png");
		}
		MensajeSender sender = new MensajeSender();
		sender.execute(values);
		animate();
	}
	
	class MensajesLoader extends AsyncTask<Void, Void, ListAdapter>{

		@Override
		protected ListAdapter doInBackground(Void... params) {
			Cursor c = managedQuery(Uri.parse("content://org.blanco.techmun.android.mesasprovider/mensajes"), 
					null, null, null, null);
			List<Mensaje> mensajes = new ArrayList<Mensaje>();
			for(int x=0; x < c.getCount(); x++){
				ByteArrayInputStream bytesInputStream = new ByteArrayInputStream(c.getBlob(x));
				ObjectInputStream ois;
				try {
					ois = new ObjectInputStream(bytesInputStream);
					Object mObject = ois.readObject();
					if (mObject != null && mObject instanceof Mensaje){
						mensajes.add((Mensaje)mObject);
					}else{
						Log.e("techmun", "Object retrieved from Cursor is not a Mensaje object. " +
								"It will be ignored. "+mObject);
					}
				} catch(Exception e){
					Log.e("techmun", "Error retrieving Mensaje object from cursor.",e);
				}
			}
			ListAdapter adapter = new MensajesListAdapter(mensajes);
			return adapter;
		}

		@Override
		protected void onPostExecute(ListAdapter result) {
			list.setAdapter(result);	
			list.onRefreshComplete();
		}
	}
	
	public void clearSendForm(){
		edtMensaje.setText("");
		attachImage = null;
		btnAddImage.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.btn_default));
	}
	
	class MensajeSender extends AsyncTask<ContentValues, Void, Boolean>{

		@Override
		protected void onPostExecute(Boolean result) {
			if (result = true){
				MensajesActivity.this.clearSendForm();
				Toast.makeText(getBaseContext(), getString(R.string.mensaje_sent), 500).show();
			}else{
				Toast.makeText(getBaseContext(), getString(R.string.mensaje_not_sent), 500).show();
			}
		}

		@Override
		protected Boolean doInBackground(ContentValues... arg0) {
			if (arg0.length != 1){
				throw new IllegalArgumentException("ContentValues bundle must be exactly one");
			}
			HttpClient client = new DefaultHttpClient();
			HttpPost postReq = new HttpPost("http://tec-ch-mun-2011.herokuapp.com/application/publicarmensaje");
			
			MultipartEntity entity = new MultipartEntity();
			try {
				String nombre = arg0[0].getAsString("nombre");
				String mensaje = arg0[0].getAsString("mensaje");
				entity.addPart("mensaje.autor.nombre",new StringBody(nombre));
				entity.addPart("mensaje.mensaje",new StringBody(mensaje));
				if (arg0[0].containsKey("foto")){
					//ByteArrayOutputStream baos = new ByteArrayOutputStream();
					//attachImage.compress(CompressFormat.JPEG, 80, baos);
					entity.addPart("mensaje.foto",new ByteArrayBody(arg0[0].getAsByteArray("foto"), 
							"image/"+arg0[0].getAsString("foto-format"),"foto"+System.currentTimeMillis()+".png"));
				}
				postReq.setEntity(entity);
				HttpResponse response = client.execute(postReq);
				if (response.getStatusLine().getStatusCode() == 200){
					return true;
				}else{
					Log.i("techmun", "Error sending Mensaje, response returned code"+response.getStatusLine().getStatusCode());
				}
			} catch (UnsupportedEncodingException e) {
				Log.e("techmun","Unable to create MultipartEntity to send the message",e);
			}  catch (Exception e) {
				Log.e("techmun", "Error sending Mensaje to server",e);
			}
			return false;
		}
		
	}
	
}
