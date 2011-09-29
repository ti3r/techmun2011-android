package org.blanco.techmun.android.cproviders;

import static org.blanco.techmun.android.cproviders.TechMunContentProvider.CONTENT_BASE_URI;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.blanco.techmun.android.misc.XmlParser;
import org.blanco.techmun.entities.Evento;
import org.blanco.techmun.entities.Eventos;
import org.json.JSONArray;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
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
				TechMunContentProvider.MESAS_REST_SERVICE_BSAE_URI+"/mesas/"+mesaId+"/eventos");
		Evento e = new Evento(); e.setDescripcion("evento de prueba"); 
		e.setFecha(new Date(System.currentTimeMillis())); e.setId(1L); e.setMesaId(1L); 
		e.setTitulo("evento de prueba");
		result.getEventos().add(e);
		return result;
//		HttpResponse response = null;
//		try {
//			response = httpClient.execute(request);
//			if (response.getStatusLine().getStatusCode() == 200){
//				JSONArray jsonEventos = XmlParser.parseJSONArrayFromHttpEntity(response.getEntity());
//				for(int i=0; i < jsonEventos.length(); i++){
//					Long id = jsonEventos.getJSONObject(i).getLong("id");
//					String titulo = jsonEventos.getJSONObject(i).getString("titulo");
//					String desc = jsonEventos.getJSONObject(i).getString("descripcion");
//					String fecha = jsonEventos.getJSONObject(i).getString("fecha");
//					Evento e = new Evento();
//					e.setId(id); e.setTitulo(titulo);
//					e.setMesaId(mesaId);
//					e.setFecha(new Date(Date.parse(fecha)));
//					e.setDescripcion(desc);
//					result.getEventos().add(e);
//				}
//			}
//		} catch (ClientProtocolException ex) {
//			// TODO Auto-generated catch block
//			ex.printStackTrace();
//		} catch (IOException ex) {
//			// TODO Auto-generated catch block
//			ex.printStackTrace();
//		} catch (Exception ex) {
//			// TODO Auto-generated catch block
//			ex.printStackTrace();
//		}
//		return result;
	}
	
}
