package org.blanco.techmun.android.cproviders;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.blanco.techmun.android.misc.XmlParser;
import org.blanco.techmun.entities.Comentario;
import org.blanco.techmun.entities.Comentarios;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * Class in charge of fetching Comentario objects from the
 * rest services or the cache. This class will be used
 * by the content provider of the application.
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class ComentariosFetcher {

	HttpClient client = null;

	public ComentariosFetcher(HttpClient client) {
		super();
		this.client = client;
	}

	private Comentarios getComentariosFromXMLDocument(Document document){
		Comentarios result = new Comentarios();
		//Get the first child of the doc
		NodeList eventoNodes = document.getFirstChild().getChildNodes();
		for (int i=0; i < eventoNodes.getLength(); i++){
			String id =
			eventoNodes.item(i).getAttributes().getNamedItem("id").getNodeValue();
			String eId =
					eventoNodes.item(i).getAttributes().getNamedItem("eventoId").getNodeValue();
			String comentario =
					eventoNodes.item(i).getAttributes().getNamedItem("comentario").getNodeValue();
			//Build the new comentario object
			Comentario c = new Comentario();
			c.setId(Long.getLong(id));
			c.setEventoId(Long.getLong(eId));
			c.setComentario(comentario);
			result.addComentario(c);
		}
		return result;
	}
	
	private Comentarios getMoreComentarios(String mesaId, String eventoId,
			String lastComentarioId){
		
		return new Comentarios();
	}
	
	public Comentarios fetchComentarios(String mesaId, String eventoId){
		
		Comentarios result = null;
		
		HttpGet request = new HttpGet(
				TechMunContentProvider.MESAS_REST_SERVICE_BSAE_URI+"/"+mesaId+"/eventos"
				+"/"+eventoId+"/comentarios");
		
		HttpResponse response;
		try {
			response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == 200){
				Document doc = 
				XmlParser.parseHttpEntity(response.getEntity());
				result = getComentariosFromXMLDocument(doc);
				return result;
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
		result = new Comentarios();
		return result;
	}
	
}
