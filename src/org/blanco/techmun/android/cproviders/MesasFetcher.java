package org.blanco.techmun.android.cproviders;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.blanco.techmun.android.misc.XmlParser;
import org.blanco.techmun.entities.Mesa;
import org.blanco.techmun.entities.Mesas;
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
public class MesasFetcher {

	HttpClient client = null;
	Context context = null;
	
	/**
	 * Default Constructor used to retrieve the mesa objects
	 * from the rest services if needed.
	 * 
	 * @param client The httpClient used to retrieve the mesa objects
	 * from the rest services in case of
	 */
	public MesasFetcher(HttpClient client, Context ctx){
		if (client == null || ctx == null){
			throw new IllegalArgumentException("HttpClient and Context can not be null");
		}
		this.client = client;
		this.context = ctx;
	}
	
	private void saveOnCache(Context context, Mesas mesas){
		try {
			FileOutputStream mesasFOS = context.openFileOutput("mesas.df", Context.MODE_PRIVATE);
			ObjectOutputStream mesasOOS = new ObjectOutputStream(mesasFOS);
			mesasOOS.writeObject(mesas);
			mesasOOS.close();
			PreferenceManager.getDefaultSharedPreferences(context).edit().
			putLong("mesas_last_cache_saved", System.currentTimeMillis()).commit();
		} catch (IOException e) {
			Log.e("tachmun", "Error opening cache file for mesas in content provider. " +
					"No cache will be saved",e);
		}
	}
	
	private Mesas tryToLoadFromCache(Context context){
		try {
			FileInputStream mesasFIS = context.openFileInput("mesas.df");
			ObjectInputStream mesasOIS = new ObjectInputStream(mesasFIS);
			Mesas result = (Mesas) mesasOIS.readObject();
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
	
	public Mesas getMesas(){
		long lastFromCache = PreferenceManager.getDefaultSharedPreferences(context).
				getLong("mesas_last_cache_saved", 0);
		long diff = System.currentTimeMillis() - lastFromCache;
		Mesas result = null;
		//If the last cache from mesas is less than 3 hours load mesas from cache
		if (diff < 10800000){
			result = tryToLoadFromCache(context);
		}
		//Mesa mesa = new Mesa();
		//mesa.setId(1L); mesa.setNombre("Mesa de Prueba"); 
		//mesa.setRepresentante(new Usuario("alex","alex@alex.com"));
		//mesa.setColor("#123456");
		//result.getMesas().add(mesa);
		if (result != null){
			Log.d("techmun", "Mesas loaded from cache");
			return result;
		}
		result = new Mesas();
		HttpResponse response;
		try {
			HttpGet req = new HttpGet(TechMunContentProvider.MESAS_REST_SERVICE_BSAE_URI+"/mesas");
			response = client.execute(req);
			HttpEntity entity = response.getEntity();
			JSONArray mesas = XmlParser.parseJSONArrayFromHttpEntity(entity);
			for(int i = 0; i < mesas.length(); i++){
				JSONObject mesaObject = mesas.getJSONObject(i);
				Mesa mesa = new Mesa();
				mesa.setId(mesaObject.getLong("id"));
				mesa.setNombre(mesaObject.getString("nombre"));
				//Representante can be optional at the begining of the event
				if (mesaObject.has("representante")){
					mesa.setRepresentante(Usuario.fromJSONObject(
						mesaObject.getJSONObject("representante"))
						);
				}
				if (mesaObject.has("descripcion")){
					mesa.setDescripcion(mesaObject.getString("descripcion"));
				}
				mesa.setColor(mesaObject.getString("color"));
				result.getMesas().add(mesa);
			}
		} catch (ClientProtocolException e) {
			Log.e("techmun2011", "Error retrieving Mesa objects",e);
		} catch (IOException e) {
			Log.e("techmun2011", "Error retrieving Mesa objects",e);
		} catch (Exception e){
			Log.e("techmun2011", "Error parsing Mesa objects",e);
		}
		//save the retrieved objects in the cache file
		saveOnCache(context, result);
		
		return result;
	}
	
}
