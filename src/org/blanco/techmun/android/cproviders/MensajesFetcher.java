package org.blanco.techmun.android.cproviders;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.blanco.techmun.android.misc.XmlParser;
import org.blanco.techmun.entities.Mensaje;
import org.blanco.techmun.entities.Usuario;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * Class in charge of retrieving the information about the
 * Mesa objects from the rest services or the cached objects
 * This class is a helper for the content provider.
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class MensajesFetcher {

	HttpClient client = null;
	Context context = null;
	
	/**
	 * Default Constructor used to retrieve the mesa objects
	 * from the rest services if needed.
	 * 
	 * @param client The httpClient used to retrieve the mesa objects
	 * from the rest services in case of
	 */
	public MensajesFetcher(HttpClient client, Context context){
		this.client = client;
		this.context = context;
	}
	
	private void saveOnCache(Context context, List<Mensaje> mensajes){
		try {
			FileOutputStream mesasFOS = context.openFileOutput("mensajes.df", Context.MODE_PRIVATE);
			ObjectOutputStream mesasOOS = new ObjectOutputStream(mesasFOS);
			mesasOOS.writeObject(mensajes);
			mesasOOS.close();
			PreferenceManager.getDefaultSharedPreferences(context).edit()
				.putLong("mensajes_last_cache", System.currentTimeMillis());
		} catch (IOException e) {
			Log.e("tachmun", "Error opening cache file for mesas in content provider. " +
					"No cache will be saved",e);
		}
	}
	
	private List<Mensaje> tryToLoadFromCache(Context context, boolean forceCacheLoad){
		Long lastCache = 
				PreferenceManager.getDefaultSharedPreferences(context)
				.getLong("mensajes_last_cache", 1);
		long diff = System.currentTimeMillis() - lastCache;
		if (diff < 180000 && !forceCacheLoad){
			//if the cache of the mensajes is greater than 3 minutes launch a new refresh
			return null;
		}
		try {
			FileInputStream mesasFIS = context.openFileInput("mensajes.df");
			ObjectInputStream mesasOIS = new ObjectInputStream(mesasFIS);
			List<Mensaje> result = (List<Mensaje>) mesasOIS.readObject();
			mesasOIS.close();
			return result;
		} catch (IOException e) {
			Log.e("tachmun", "Error opening cache file for mesas in content provider. " +
					"No cache will be restored",e);
			return null;
		} catch (ClassNotFoundException e) {
			Log.e("tachmun", "Error in cache file for mesas in content provider. " +
					"Something is there but is not a Mesas object. Cache no restored",e);
			return null;
		}
	}
	
//	private Bitmap tryToLoadFotoFromCache(Mensaje mensaje){
//		Bitmap result = null;
//		if (mensaje == null){
//			throw new IllegalArgumentException("Parameters can't be null");
//		}
//		if (mensaje.getFoto() == null){ //Just when foto not loaded 
//			try{
//				FileInputStream fisFoto = context.openFileInput("mensaje_foto_"+mensaje.getId());
//				ByteArrayOutputStream bos = new ByteArrayOutputStream();
//				int b = -1;
//				do{
//					b = fisFoto.read();
//					bos.write(b);
//				}while (b != -1);
//				result = BitmapFactory.decodeByteArray(bos.toByteArray(), 0, bos.size());
//			}catch (Exception e) {
//				Log.e("techmun","Error loaginf foto from file",e);
//			}
//		}
//		return result;
//	}
	
//	public static void tryToSaveFotosOnCache(Context context, Mensaje mensaje){
//		if (mensaje == null || context == null){
//			throw new IllegalArgumentException("Parameters can't be null");
//		}
//		//Mensaje has a valid picture
//		if (mensaje.getFoto() != null && !mensaje.isFailedRetrieveFoto() ){
//			try {
//				OutputStream fosFoto = context.openFileOutput("mensaje_foto_"+mensaje.getId(), Context.MODE_PRIVATE);
//				mensaje.getFoto().compress(CompressFormat.PNG, 100, fosFoto );
//				fosFoto.close();
//			} catch (IOException e) {
//				Log.i("techmun", "Error while storing image on cache. " +
//						"Picture for mensaje "+mensaje.getId()+" will not be saved",e);
//			}
//		}else{
//			Log.i("techmun", "Mensaje has not valid picture. Picture for mensaje "+mensaje.getId()+" will not be saved");
//		}
//		
//	}
	
	public List<Mensaje> getMensajes(){
		List<Mensaje> result = null;
		
		//Do not force the cache load first in order to know if the cache is old or not
		//	result = tryToLoadFromCache(context, false);
		//	if (result != null){
		//		//If loaded from cache
		//		return result;
		//	}
		
		HttpResponse response;
		boolean retrieved = true;
		try {
			result = new ArrayList<Mensaje>();
			HttpGet req = new HttpGet(TechMunContentProvider.MESAS_REST_SERVICE_BSAE_URI+"/mensajes");
			response = client.execute(req);
			HttpEntity entity = response.getEntity();
			JSONArray mensajes = XmlParser.parseJSONArrayFromHttpEntity(entity);
			for(int i=0; i < mensajes.length(); i++){
				JSONObject joMensaje = mensajes.getJSONObject(i);
				String sMensaje = joMensaje.getString("mensaje");
				long id = joMensaje.getLong("id");
				String sFecha = joMensaje.getString("fecha");
				Usuario autor = null;
				if (joMensaje.has("autor")){
					JSONObject joUsuario = joMensaje.getJSONObject("autor");
					String saNombre = joUsuario.getString("nombre");
					String saCorreo = joUsuario.getString("correo");
					autor = new Usuario(saNombre, saCorreo);
				}
				Mensaje mensaje = new Mensaje(sMensaje, autor);
				mensaje.setFecha(new Date(Date.parse(sFecha)));
				mensaje.setId(id);
				result.add(mensaje);
			}
		} catch (Exception e){
			Log.e("techmun2011", "Error Retrieving Mensajes from internet",e);
			retrieved = false;
		}
		//If there is an error with the connection try to load mensajes from cache
		if (!retrieved){
			result = tryToLoadFromCache(context, true);
			return result;
		}
		//saveOnCache(context, result);
		return result;
	}
	
}
