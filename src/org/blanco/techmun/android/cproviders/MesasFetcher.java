package org.blanco.techmun.android.cproviders;

import org.apache.http.client.HttpClient;
import org.blanco.techmun.entities.Mesa;
import org.blanco.techmun.entities.Mesas;

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
		Mesa mesa = new Mesa();
		mesa.setId(1L); mesa.setNombre("Mesa de Prueba"); mesa.setRepresentante("alex");
		result.getMesas().add(mesa);
		//List<String> projections = Arrays.asList((projection != null)?projection:new String[]{});
//		HttpResponse response;
//		try {
//			response = httpClient.execute(req);
//			HttpEntity entity = response.getEntity();
//			Document doc = XmlParser.parseHttpEntity(entity);
//			//get the parent node "mesas"
//			NodeList mesaNodes = doc.getFirstChild().getChildNodes();
//			for(int i=0; i < mesaNodes.getLength(); i++){
//				Mesa mesa = new Mesa();
//				if (projections.isEmpty() || projections.contains("id")){
//					
//					mesa.setId(Long.getLong(
//							row.add(mesaNodes.item(i).getAttributes().getNamedItem("id").getNodeValue()
//									));
//				}
//				if (projections.isEmpty() || projections.contains("nombre")){
//					mesa.setNombre(mesaNodes.item(i).getAttributes().getNamedItem("nombre").getNodeValue());
//				}
//				if (projections.isEmpty() || projections.contains("id")){
//					mesa.setRepresentante(mesaNodes.item(i).getAttributes().getNamedItem("representante").getNodeValue());
//				}
//				result.addRow(row);
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
