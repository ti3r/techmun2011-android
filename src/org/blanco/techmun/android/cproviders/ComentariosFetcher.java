package org.blanco.techmun.android.cproviders;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.blanco.techmun.android.entities.Comentarios;
import org.blanco.techmun.android.misc.XmlParser;
import org.blanco.techmun.entities.Evento;
import org.blanco.techmun.entities.Eventos;
import org.json.JSONArray;


/**
 * Class in charge of fetching Comentario objects from the
 * rest services or the cache. This class will be used
 * by the content provider of the application.
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class ComentariosFetcher {

	HttpClient httpClient = null;
	
	public ComentariosFetcher(HttpClient client){
		this.httpClient = client;
	}
	
	public Comentarios fetchComentarios(Long eventoId){
		Comentarios result = new Comentarios();
		HttpGet request = new HttpGet(
				TechMunContentProvider.MESAS_REST_SERVICE_BSAE_URI+"/comentarios/"+eventoId);
		
		HttpResponse response = null;
		try {
			response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == 200){
				JSONArray jsonComents = XmlParser.parseJSONArrayFromHttpEntity(response.getEntity());
				for(int i=0; i < jsonComents.length(); i++){
					
				}
			}
		} catch (ClientProtocolException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return result;

	}
	
}