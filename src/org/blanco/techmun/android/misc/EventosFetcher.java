package org.blanco.techmun.android.misc;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.blanco.techmun.entities.Evento;
import org.blanco.techmun.entities.Eventos;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import android.net.Uri;
import static org.blanco.techmun.android.cproviders.TechMunContentProvider.CONTENT_BASE_URI;
/**
 * Class in charge of retrieving the Evento Objects
 * for the application. This can be done from the 
 * Internet through a REST petition or from the 
 * cached records.
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class EventosFetcher {

	HttpClient httpClient = null;
	
	public EventosFetcher(HttpClient client){
		this.httpClient = client;
	}
	
	public Eventos fetchEventos(Long mesaId){
		Eventos result = new Eventos();
		
		HttpGet request = new HttpGet(
				CONTENT_BASE_URI+"/"+mesaId+"/eventos");
		
		HttpResponse response;
		try {
			response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == 200){
				Document doc = 
				XmlParser.parseHttpEntity(response.getEntity());
				//Get the first child of the doc
				NodeList eventoNodes = doc.getFirstChild().getChildNodes();
				for (int i=0; i < eventoNodes.getLength(); i++){
					String id =
					eventoNodes.item(i).getAttributes().getNamedItem("id").getNodeValue();
					String mId =
							eventoNodes.item(i).getAttributes().getNamedItem("mesaId").getNodeValue();
					String evento =
							eventoNodes.item(i).getAttributes().getNamedItem("evento").getNodeValue();
					String fecha =
							eventoNodes.item(i).getAttributes().getNamedItem("fecha").getNodeValue();
					//Build the Evento Object
					Evento e = new Evento();
					e.setId(Long.getLong(id));
					e.setId(Long.getLong(mId));
					e.setEvento(evento);
					e.setFecha(new Date(Date.parse(fecha)));
					result.getEventos().add(e);
				}
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
}
