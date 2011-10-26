package org.blanco.techmun.android.misc;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.blanco.techmun.android.R;
import org.blanco.techmun.entities.Mensaje;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class MensajeFotoLoader extends AsyncTask<Void, Void, Bitmap>{

	private Mensaje mensajeId;
	private ImageView view = null;
	private MensajeFootLoaderExtraPostExecute listener = null;
	
	public MensajeFotoLoader(Mensaje mensaje, ImageView view){
		this(mensaje,view,null);
	}
	
	public MensajeFotoLoader(Mensaje mensaje, ImageView view, 
			MensajeFootLoaderExtraPostExecute listener){
		this.mensajeId = mensaje;
		this.view = view;
		this.listener = listener;
	}

	@Override
	protected Bitmap doInBackground(Void... arg0) {
		AndroidHttpClient client = AndroidHttpClient.newInstance("fotoloader");
		//HttpGet getReq = new HttpGet("http://blog.leadcritic.com/wp-content/uploads/1998_google.jpg");
		HttpGet getReq = new HttpGet("http://tec-ch-mun-2011.herokuapp.com/admin/mensajes/"+
		mensajeId.getId()+"/foto");
		getReq.setHeader("Accept", "image/*");
		try {
			HttpResponse response = client.execute(getReq);
			Bitmap result = null;
			if (response.getStatusLine().getStatusCode() == 200){
				byte[] imageBytes = EntityUtils.toByteArray(response.getEntity());
				result = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
			}
			return result;
		} catch (Exception e) {
			mensajeId.setFailedRetrieveFoto(true);
			Log.i("tech-mun", "Error retrieving image for mensaje "+mensajeId,e);
			return null;
		} finally{
			client.close();
		}
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		if (result != null && view != null){
			//set the current image to the drawable
			view.setImageBitmap(result);
			mensajeId.setFoto(result);
			//MensajesFetcher.tryToSaveFotosOnCache(view.getContext(), mensajeId);
		}else if (view != null){
			//set the default value
			BitmapDrawable drawable = (BitmapDrawable) view.getContext()
					.getResources().getDrawable(R.drawable.icon); 
			view.setImageDrawable(drawable);
			mensajeId.setFailedRetrieveFoto(true);
			mensajeId.setFoto(drawable.getBitmap());
		}
		if (listener != null){
			listener.executeAfterOnPostExecute(result);
		}
		super.onPostExecute(result);
	}
	
	public interface MensajeFootLoaderExtraPostExecute{
		public void executeAfterOnPostExecute(Bitmap bitmap);
	}
	
}