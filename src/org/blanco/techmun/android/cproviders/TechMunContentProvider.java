package org.blanco.techmun.android.cproviders;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.impl.client.DefaultHttpClient;
import org.blanco.techmun.android.misc.ObjectsCursor;
import org.blanco.techmun.entities.Comentario;
import org.blanco.techmun.entities.Comentarios;
import org.blanco.techmun.entities.Eventos;
import org.blanco.techmun.entities.Mesas;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

public class TechMunContentProvider extends ContentProvider {

	protected static String MESAS_REST_SERVICE_BSAE_URI = 
			"http://tec-ch-mun-2011.herokuapp.com/rest";
	
	public static final String CONTENT_BASE_URI = 
			"content://org.blanco.techmun.android.mesasprovider";
	
	private static final String MESA_CONENT_PETION_REG_EXP =
			CONTENT_BASE_URI+"/(\\d+)";
	
	private static final String MESA_CONTENT_EVENTOS_PETITION_REG_EXP =
			CONTENT_BASE_URI+"/(\\d+)/eventos";
	
	private static final String MESA_CONTENT_COMENTARIOS_PETITION_REG_EXP = 
			CONTENT_BASE_URI+"/comentarios/(\\d+)";

	private static final String MESA_CONTENT_COMENTARIOS_INSERT_PETITION_REG_EXP =
			CONTENT_BASE_URI+"/comentarios/add";
	
	private static final String MESA_CONTENT_MENSAJES_PETITION_REG_EXP = 
			CONTENT_BASE_URI+"/mensajes";
	
	DefaultHttpClient httpClient = null;
	EventosFetcher eventosFeher = null;
	MesasFetcher mesasFecher = null;
	ComentariosFetcher comentsFetcher = null;
	
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
		} else if (uri.toString().matches(MESA_CONTENT_COMENTARIOS_PETITION_REG_EXP)){
			Comentario.class.getCanonicalName();
		}
		//Petition not recognized, return object as default.
		return "java.lang.Object";
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if (uri.toString().matches(MESA_CONTENT_COMENTARIOS_INSERT_PETITION_REG_EXP)){
			return Uri.parse(CONTENT_BASE_URI+"/comentarios/"+123);
		}else{
			throw new UnsupportedOperationException("Can't insert other elements that not " +
					"complain with comentarios expression");
		}
	}

	@Override
	public boolean onCreate() {
		httpClient = new DefaultHttpClient();
		eventosFeher = new EventosFetcher(httpClient);
		mesasFecher = new MesasFetcher(httpClient);
		comentsFetcher = new ComentariosFetcher(httpClient);
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
		
		Pattern p = Pattern.compile(pattern);
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
			Eventos eventos = eventosFeher.fetchEventos(Long.parseLong(mesaId));
			//Build the cursor
			ObjectsCursor cursor = new ObjectsCursor(eventos.getEventos());
			result = cursor;
		}catch(IllegalStateException ex){
			Log.i("Techmun CP", "Unable to fectch eventos from URI"+uri, ex);
		}
		return result;
	}
	
	private Cursor getMesasCursorForUri(Uri uri){
		//The cursor must contain an _id column of the CursorAdapters won't work.
		Mesas mesas = mesasFecher.getMesas();
		ObjectsCursor result = new ObjectsCursor(mesas.getMesas());
		return result;
	}
	
	private Cursor getComentariosCursorForUri(Uri uri){
		String eventoId = 
		extractGroupFromPattern(MESA_CONTENT_COMENTARIOS_PETITION_REG_EXP, uri.toString(), 1);
		try{
			Long leId = Long.parseLong(eventoId);
			Comentarios comentarios =
					comentsFetcher.fetchComentarios(leId);
			ObjectsCursor result = new ObjectsCursor(comentarios.getComentarios());
			return result;
		}catch(Exception e){
			Log.e("techmun", "Error retrieving comentarios",e);
		}
		return null;
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
		}else if (uri.toString().matches(MESA_CONTENT_COMENTARIOS_PETITION_REG_EXP)){
			return getComentariosCursorForUri(uri);
		}else if (uri.toString().matches(MESA_CONTENT_MENSAJES_PETITION_REG_EXP)){
			MatrixCursor cursor = new MatrixCursor(new String[]{"id","mensaje"});
			cursor.addRow(new Object[]{"1","Mensaje de Pueba"+Math.random()});
			cursor.addRow(new Object[]{"2","Mensaje de Pueba"+Math.random()});
			cursor.addRow(new Object[]{"3","Mensaje de Pueba"+Math.random()});
			cursor.addRow(new Object[]{"4","Mensaje de Pueba"+Math.random()});
			cursor.addRow(new Object[]{"5","Mensaje de Pueba"+Math.random() });
			cursor.addRow(new Object[]{"6","Mensaje de Pueba"+Math.random()});
			cursor.addRow(new Object[]{"7","Mensaje de Pueba"+Math.random()});
			cursor.addRow(new Object[]{"8","Mensaje de Pueba"+Math.random()});
			cursor.addRow(new Object[]{"9","Mensaje de Pueba"+Math.random()});
			return cursor;
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
