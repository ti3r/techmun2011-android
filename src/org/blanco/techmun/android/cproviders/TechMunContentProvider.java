package org.blanco.techmun.android.cproviders;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.impl.client.DefaultHttpClient;
import org.blanco.techmun.entities.Evento;
import org.blanco.techmun.entities.Eventos;
import org.blanco.techmun.entities.Mesa;
import org.blanco.techmun.entities.Mesas;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

public class TechMunContentProvider extends ContentProvider {

	protected static String MESAS_REST_SERVICE_BSAE_URI = 
			"https://techmun2011.appspot.com/rest/mesas";
	
	public static final String CONTENT_BASE_URI = 
			"content://org.blanco.techmun.android.mesasprovider";
	
	private static final String MESA_CONENT_PETION_REG_EXP =
			CONTENT_BASE_URI+"/(\\d+)";
	
	private static final String MESA_CONTENT_EVENTOS_PETITION_REG_EXP =
			CONTENT_BASE_URI+"/(\\d+)/eventos";
	
	
	DefaultHttpClient httpClient = null;
	EventosFetcher eventosFeher = null;
	MesasFetcher mesasFecher = null;
	
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
		mesasFecher = new MesasFetcher(httpClient);
		return true;
	}

	/**
	 * Extracts the dessired group value from the passed input if matches 
	 * the provided pattern.
	 * 
	 * @param pattern The String pattern to match against the input
	 * @param input The Stirng input value where to extract the group from
	 * @param groupNo The int group index
	 * @return The String value of the pointed group
	 */
	private String extractGroupFromPattern(String pattern, String input, 
			int groupNo) throws IllegalStateException{
		String s = null;
		
		Pattern p = Pattern.compile(MESA_CONTENT_EVENTOS_PETITION_REG_EXP);
		Matcher matcher = p.matcher(input);
		if (matcher.matches()){
			s = matcher.group(groupNo);
			if (s == null){
				throw new IllegalStateException("The group passed returned null");
			}
		}else{
			throw new IllegalStateException("The input does not match the " +
					"passed pattern");
		}
		return s;
	}
	
	/**
	 * Returns a Cursor with the Eventos object based on the passed
	 * content Uri
	 * @param uri The content Uri uri where to extract the info to match the Eventos
	 * 
	 * @return Cursor with the information 
	 */
	private Cursor getEventosCursorForUri(Uri uri){
		if (uri == null){
			return null;
		}
		
		Cursor result = null;
		//get The mesa id
		try{
			String mesaId = extractGroupFromPattern(MESA_CONTENT_EVENTOS_PETITION_REG_EXP, 
					uri.toString(), 1);
			Eventos eventos = eventosFeher.fetchEventos(Long.getLong(mesaId));
			//Build the cursor
			MatrixCursor cursor = new MatrixCursor(Evento.EVENTO_COL_NAMES);
			for( Evento evento : eventos.getEventos() ){
				List<Object> row = new ArrayList<Object>();
				row.add(evento.getId());
				row.add(evento.getMesaId());
				row.add(evento.getEvento());
				row.add(evento.getFecha());
				cursor.addRow(row);
			}
			result = cursor;
		}catch(IllegalStateException ex){
			Log.i("Techmun CP", "Unable to fectch eventos from URI"+uri, ex);
		}
		return result;
	}
	
	private Cursor getMesasCursorForUri(Uri uri){
		//The cursor must contain an _id column of the CursorAdapters won't work.
		Mesas mesas = mesasFecher.getMesas();
		MatrixCursor result = new MatrixCursor(new String[]{"_id","nombre","representante"});
		for(Mesa m : mesas.getMesas()){
			List<Object> row = new ArrayList<Object>();
			row.add(m.getId());
			row.add(m.getNombre());
			row.add(m.getRepresentante());
			result.addRow(row);
		}
		return result;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		//HttpGet req = new HttpGet(MESAS_REST_SERVICE_BSAE_URI);
		try {
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (uri.toString().matches(MESA_CONTENT_EVENTOS_PETITION_REG_EXP)){
			return getEventosCursorForUri(uri);			
		}else if (uri.toString().matches(CONTENT_BASE_URI)){
			return getMesasCursorForUri(uri);
		}
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
