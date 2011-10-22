package org.blanco.techmun.android.misc;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.blanco.techmun.android.R;
import org.blanco.techmun.entities.Mensaje;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * The List adapter that populates a list view based on the 
 * passed Mensajes.
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class MensajesListAdapter extends BaseAdapter {

	List<Mensaje> mensajes = null;
	
	public MensajesListAdapter(List<Mensaje> mensajes){
		this.mensajes = mensajes;
	}
	/**
	 * Establishes the values of the view with the properties of 
	 * the passed Mensaje. The View must be an inflate of the 
	 * R.layout.mensajes_list_item_layout layout.
	 * 
	 * @param view The View object where to set the values of the Evento
	 * @param evento The Evento object where to get the properties from
	 */
	private void setViewValues(View view, Mensaje mensaje){
		TextView txtMensaje =  (TextView) view.findViewById(R.id.mensajes_list_item_mensaje);
		txtMensaje.setText(mensaje.getMensaje());
		ImageView foto =  (ImageView) view.findViewById(R.id.mensajes_list_item_foto);
		if (mensaje.getFoto() == null){
			foto.setImageDrawable(view.getContext().getResources().getDrawable(
				android.R.drawable.progress_indeterminate_horizontal)
				);
		}else{
			foto.setImageBitmap(mensaje.getFoto());
		}
		TextView footer = (TextView) view.findViewById(R.id.mensajes_list_item_footer);
		StringBuilder sFooter = new StringBuilder();
		if (mensaje.getAutor() != null){
			sFooter.append(mensaje.getAutor().getNombre()).append(" - ");
		}
		sFooter.append(DateFormat.getDateFormat(view.getContext()).format(mensaje.getFecha()));
		footer.setText(sFooter);
		if (mensaje.getFoto() == null){
			//launch the foto loader for this mensaje
			MensajeFotoLoader loader = new MensajeFotoLoader(mensaje, foto);
			loader.execute();
		}
	}

	public List<Mensaje> getMensajesList(){
		return this.mensajes;
	}
	
	public int getCount() {
		return (this.mensajes != null) ? this.mensajes.size() : 0;
	}
	
	public Object getItem(int position) {
		
		return (this.mensajes != null && this.mensajes.size() >= position ) ?
				this.mensajes.get(position) : null ;
	}
	
	public long getItemId(int position) {
		Mensaje mensaje = (Mensaje) getItem(position);
		return (mensaje != null)? mensaje.getId() : 0;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		if ( convertView == null ) { //we need to inflate a new view
			convertView = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.mensajes_list_item, null);
		}
		setViewValues(convertView, (Mensaje)getItem(position));
		return convertView;
	}
	@Override
	public boolean isEmpty() {
		return this.mensajes.isEmpty();
	}	
		
	
	class MensajeFotoLoader extends AsyncTask<Void, Void, Bitmap>{

		private Mensaje mensajeId;
		private ImageView view = null;
		
		public MensajeFotoLoader(Mensaje mensaje, ImageView view){
			this.mensajeId = mensaje;
			this.view = view;
		}

		@Override
		protected Bitmap doInBackground(Void... arg0) {
			AndroidHttpClient client = AndroidHttpClient.newInstance("fotoloader");
			//HttpGet getReq = new HttpGet("http://blog.leadcritic.com/wp-content/uploads/1998_google.jpg");
			HttpGet getReq = new HttpGet("http://tec-ch-mun-2011.herokuapp.com/admin/mensajes/"+
			/*mensajeId*/14
			+"/foto");
			getReq.setHeader("Accept", "image/*");
			try {
				HttpResponse response = client.execute(getReq);
				byte[] imageBytes = EntityUtils.toByteArray(response.getEntity());
				Bitmap result = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
				mensajeId.setFoto(result);
				client.close();
				return result;
			} catch (Exception e) {
				Log.i("tech-mun", "Error retrieving image for mensaje "+mensajeId,e);
				return null;
			} 
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if (result != null && view != null){
				//set the current image to the drawable
				view.setImageBitmap(result);
			}else{
				//set the default value
				view.setImageDrawable(view.getContext()
						.getResources().getDrawable(android.R.drawable.stat_sys_warning));
			}
			super.onPostExecute(result);
		}
		
		
		
	}
}
