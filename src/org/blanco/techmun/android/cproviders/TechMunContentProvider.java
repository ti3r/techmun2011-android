package org.blanco.techmun.android.cproviders;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.blanco.techmun.android.misc.EventosFetcher;
import org.blanco.techmun.entities.Evento;
import org.blanco.techmun.entities.Eventos;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

public class TechMunContentProvider extends ContentProvider {

	private static String MESAS_REST_SERVICE_BSAE_URI = 
			"https://techmun2011.appspot.com/rest/mesas";
	
	public static final String CONTENT_BASE_URI = 
			"content://org.blanco.techmun.android.mesasprovider";
	
	private static final String MESA_CONENT_PETION_REG_EXP =
			CONTENT_BASE_URI+"/\\d+";
	
	private static final String MESA_CONTENT_EVENTOS_PETITION_REG_EXP =
			CONTENT_BASE_URI+"/\\d+/eventos";
	
	
	DefaultHttpClient httpClient = null;
	EventosFetcher eventosFeher = null;
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public String getType(Uri uri) {
		//User wants to retrieve mesa objects
		if (uri.toString().equalsIgnoreCase(MESAS_REST_SERVICE_BSAE_URI)
				||
				uri.toString().matches(MESA_CONENT_PETION_REG_EXP)){
			return "org.blanco.techmun.entities.Mesa";
			//User wants to retrieve 
		} else if (uri.toString().matches(MESA_CONTENT_EVENTOS_PETITION_REG_EXP)) {			
			return "org.blanco.techmun.entities.Evento";
		}
		//Petition not recognized, return object as default.
		return "java.lang.Object";
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public boolean onCreate() {
		httpClient = new DefaultHttpClient();
		eventosFeher = new EventosFetcher(httpClient);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		HttpGet req = new HttpGet(MESAS_REST_SERVICE_BSAE_URI);
		try {
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (uri.toString().matches(MESA_CONTENT_EVENTOS_PETITION_REG_EXP)){
			//get The mesa id
			Pattern p = Pattern.compile(MESA_CONTENT_EVENTOS_PETITION_REG_EXP);
			Matcher matcher = p.matcher(uri.toString());
			String mesaId = matcher.group(1);
			Eventos eventos = eventosFeher.fetchEventos(Long.getLong(mesaId));
			MatrixCursor cursor = new MatrixCursor(Evento.EVENTO_COL_NAMES);
			for( Evento evento : eventos.getEventos() ){
				List<Object> row = new ArrayList<Object>();
				row.add(evento.getId());
				row.add(evento.getMesaId());
				row.add(evento.getEvento());
				row.add(evento.getFecha());
				cursor.addRow(row);
			}
			return cursor;
		}
		//The cursor must contain an _id column of the CursorAdapters won't work.
		MatrixCursor result = new MatrixCursor(new String[]{"_id","nombre","representante"});
//		List<String> projections = Arrays.asList((projection != null)?projection:new String[]{});
//		HttpResponse response;
//		try {
//			response = httpClient.execute(req);
//			HttpEntity entity = response.getEntity();
//			Document doc = XmlParser.parseHttpEntity(entity);
//			//get the parent node "mesas"
//			NodeList mesaNodes = doc.getFirstChild().getChildNodes();
//			for(int i=0; i < mesaNodes.getLength(); i++){
//				List<Object> row = new ArrayList<Object>();
//				if (projections.isEmpty() || projections.contains("id")){
//					row.add(mesaNodes.item(i).getAttributes().getNamedItem("id").getNodeValue());
//				}
//				if (projections.isEmpty() || projections.contains("nombre")){
//					row.add(mesaNodes.item(i).getAttributes().getNamedItem("nombre").getNodeValue());
//				}
//				if (projections.isEmpty() || projections.contains("id")){
//					row.add(mesaNodes.item(i).getAttributes().getNamedItem("representante").getNodeValue());
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
		List<Object> row = new ArrayList<Object>();
		row.add(1); row.add("pruebas"); row.add("alexandro.blanco@gmail.com");
		List<Object> row2 = new ArrayList<Object>();
		row2.add(3); row2.add("pruebas3"); row2.add("alexandro.blanco@gmail.com2");
		List<Object> row3 = new ArrayList<Object>();
		row3.add(2); row3.add("pruebas2"); row3.add("alexandro.blanco@gmail.com3");
		result.addRow(row); result.addRow(row2); result.addRow(row3);
		return result;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
