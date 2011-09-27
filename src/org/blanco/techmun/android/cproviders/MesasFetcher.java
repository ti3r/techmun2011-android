package org.blanco.techmun.android.cproviders;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	
	/**
	 * Default Constructor used to retrieve the mesa objects
	 * from the rest services if needed.
	 * 
	 * @param client The httpClient used to retrieve the mesa objects
	 * from the rest services in case of
	 */
	public MesasFetcher(HttpClient client){
		this.client = client;
	}
	
	public Mesas getMesas(){
		Mesas result = new Mesas();
//		Mesa mesa = new Mesa();
//		mesa.setId(1L); mesa.setNombre("Mesa de Prueba"); mesa.setRepresentante("alex");
//		result.getMesas().add(mesa);
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
				mesa.setRepresentante(Usuario.fromJSONObject(
						mesaObject.getJSONObject("representante"))
						);
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
		return result;
	}
	
}
