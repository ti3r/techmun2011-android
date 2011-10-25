package org.blanco.techmun.android.cproviders;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.blanco.techmun.android.misc.XmlParser;
import org.blanco.techmun.entities.Comentarios;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentValues;
import android.util.Log;


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
		HttpPost request = new HttpPost(
				TechMunContentProvider.MESAS_REST_SERVICE_BSAE_URI+"/comentarios/");
		
		HttpResponse response = null;
		try {
			//prepare the entity for the request
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			parameters.add(new BasicNameValuePair("eventoId", eventoId.toString()));
			parameters.add(new BasicNameValuePair("pagina", "1"));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters,"utf-8");
			request.setEntity(entity);
			
			response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == 200){
				JSONArray jsonComents = XmlParser.parseJSONArrayFromHttpEntity(response.getEntity());
				for(int i=0; i < jsonComents.length(); i++){
					
				}
			}
		} catch (Exception ex) {
			Log.e("techmun", "Error posting comentario",ex);
		}
		return result;

	}

	public long publishComentario(ContentValues values) {
		HttpPost request = new HttpPost(
				TechMunContentProvider.MESAS_REST_SERVICE_BSAE_URI+"/comentarios/add");
		
		HttpResponse response = null;
		try {
			//prepare the entity for the request
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			parameters.add(new BasicNameValuePair("eventoId", values.getAsString("eventoId")));
			parameters.add(new BasicNameValuePair("comentario", values.getAsString("comentario")));
			if (values.containsKey("autor")){
				parameters.add(new BasicNameValuePair("autor", values.getAsString("autor")));
			}
			if (values.containsKey("contacto")){
				parameters.add(new BasicNameValuePair("contacto", values.getAsString("contacto")));
			}
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters,"utf-8");
			request.setEntity(entity);
			
			response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == 200){
				JSONObject jsonComents = XmlParser.parseJSONObjectFromHttpEntity(response.getEntity());
				long id = jsonComents.getLong("id");
				return id;
			}
		} catch (Exception ex) {
			Log.e("techmun", "Error publishComentario. Comment not sent",ex);
		}
		return -1;
	}
	
}