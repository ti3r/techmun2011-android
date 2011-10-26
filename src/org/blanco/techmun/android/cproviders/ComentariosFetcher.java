package org.blanco.techmun.android.cproviders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.blanco.techmun.android.misc.XmlParser;
import org.blanco.techmun.entities.Comentario;
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
	
	//Back compatibility function to retrieve the first page of the comentarios if no pagina
	//is specified
	public FetchComentariosResult fetchComentarios(Long eventoId){
		return fetchComentarios(eventoId,0);
	}
	
	public FetchComentariosResult fetchComentarios(Long eventoId, Integer pagina){
		FetchComentariosResult result = new FetchComentariosResult();
		HttpPost request = new HttpPost(
				TechMunContentProvider.MESAS_REST_SERVICE_BSAE_URI+"/comentarios/"+eventoId);
		HttpResponse response = null;
		try {
			//prepare the entity for the request
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			parameters.add(new BasicNameValuePair("eventoId", eventoId.toString()));
			parameters.add(new BasicNameValuePair("pagina", pagina.toString()));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters,"utf-8");
			request.setEntity(entity);
			
			response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == 200){
				Comentarios comentarios = new Comentarios();
				
				//The response return a Json object with the next page available for the net fetch and the selected objects
				JSONObject jsonComents = XmlParser.parseJSONObjectFromHttpEntity(response.getEntity());
				pagina = jsonComents.getInt("pagina");
				boolean mas = jsonComents.getBoolean("mas");
				JSONArray coments = jsonComents.getJSONArray("comentarios");
				for(int i=0 ; i < coments.length(); i++){
					long id = coments.getJSONObject(i).getLong("id");
					String comentario = coments.getJSONObject(i).getString("comentario");
					String autor = "";
					if (coments.getJSONObject(i).has("autor")){
						autor = coments.getJSONObject(i).getString("autor");
					}
					String contacto = "";
					if (coments.getJSONObject(i).has("contacto")){
						contacto = coments.getJSONObject(i).getString("contacto");
					}
					Date fecha = new Date(Date.parse(coments.getJSONObject(i).getString("fecha")));
					Comentario c = new Comentario();
					c.setId(id); c.setAutor(autor); c.setComentario(comentario); c.setContacto(contacto);
					c.setFecha(fecha);
					//if all the parsing went well add the new object to the results
					comentarios.addComentario(c);
				}
				//Prepare the result
				result.comentarios = comentarios;
				result.pagina = pagina;
				result.mas = mas;
			}
		} catch (Exception ex) {
			Log.e("techmun", "Error posting comentario",ex);
		}
		return result;

	}

	public boolean publishComentario(ContentValues values) {
		HttpPost request = new HttpPost(
				"http://tec-ch-mun-2011.herokuapp.com/application/publicarcomentario");
		
		HttpResponse response = null;
		try {
			//prepare the entity for the request
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			parameters.add(new BasicNameValuePair("comentario.evento.id", values.getAsString("eventoId")));
			parameters.add(new BasicNameValuePair("comentario.comentario", values.getAsString("comentario")));
			if (values.containsKey("autor")){
				parameters.add(new BasicNameValuePair("comentario.autor", values.getAsString("autor")));
			}
			if (values.containsKey("contacto")){
				parameters.add(new BasicNameValuePair("comentario.contacto", values.getAsString("contacto")));
			}
			
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters);
			request.setEntity(entity);
			
			response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == 200){
				return true;
			}
		} catch (Exception ex) {
			Log.e("techmun", "Error publishComentario. Comment not sent",ex);
		}
		return false;
	}
	
}