package org.blanco.techmun.android.cproviders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.blanco.techmun.entities.Mensaje;
import org.blanco.techmun.entities.Usuario;

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
	
	/**
	 * Default Constructor used to retrieve the mesa objects
	 * from the rest services if needed.
	 * 
	 * @param client The httpClient used to retrieve the mesa objects
	 * from the rest services in case of
	 */
	public MensajesFetcher(HttpClient client){
		this.client = client;
	}
	
	public List<Mensaje> getMensajes(){
		List<Mensaje> result = new ArrayList<Mensaje>();
		Mensaje m = new Mensaje("Hola Mundo Mensaje de prueba", new Usuario("alex", "alex@alex.com"));
		m.setFecha(new Date(System.currentTimeMillis()));
		m.setId(1);
		result.add(m);
		m = new Mensaje("Hola Mundo Mensaje de prueba 2", new Usuario("alex", "alex@alex.com"));
		m.setFecha(new Date(System.currentTimeMillis()));
		m.setId(2);
		m.setFecha(new Date(System.currentTimeMillis()));
		result.add(m);
		m = new Mensaje("Hola Mundo Mensaje de prueba 3", new Usuario("alex", "alex@alex.com"));
		m.setId(3);
		m.setFecha(new Date(System.currentTimeMillis()));
		result.add(m);
		m = new Mensaje("Hola Mundo Mensaje de prueba 4", new Usuario("alex", "alex@alex.com"));
		m.setId(4);
		m.setFecha(new Date(System.currentTimeMillis()));
		result.add(m);
		m = new Mensaje("Hola Mundo Mensaje de prueba muy largo para ver como se pone el multiline", new Usuario("alex", "alex@alex.com"));
		m.setId(5);
		m.setFecha(new Date(System.currentTimeMillis()));
		result.add(m);
//		HttpResponse response;
//		try {
//			HttpGet req = new HttpGet(TechMunContentProvider.MESAS_REST_SERVICE_BSAE_URI+"/mesas");
//			response = client.execute(req);
//			HttpEntity entity = response.getEntity();
//			JSONArray mesas = XmlParser.parseJSONArrayFromHttpEntity(entity);
//			for(int i = 0; i < mesas.length(); i++){
//				JSONObject mesaObject = mesas.getJSONObject(i);
//				Mesa mesa = new Mesa();
//				mesa.setId(mesaObject.getLong("id"));
//				mesa.setNombre(mesaObject.getString("nombre"));
//				mesa.setRepresentante(Usuario.fromJSONObject(
//						mesaObject.getJSONObject("representante"))
//						);
//				mesa.setColor(mesaObject.getString("color"));
//				result.getMesas().add(mesa);
//			}
//		} catch (ClientProtocolException e) {
//			Log.e("techmun2011", "Error retrieving Mesa objects",e);
//		} catch (IOException e) {
//			Log.e("techmun2011", "Error retrieving Mesa objects",e);
//		} catch (Exception e){
//			Log.e("techmun2011", "Error parsing Mesa objects",e);
//		}
		return result;
	}
	
}
