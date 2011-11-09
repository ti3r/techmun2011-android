/**
 * Tec ch mun 2011 for Android, is the android application used to 
 *  
 * review all the information that is generated during the event
 * Tec Ch Mun 2011 of the ITESM campus chihuahua.
 * You can use this application as an example of all the technologies
 * used in this app.
 * Copyright (C) 2011  Alexandro Blanco <ti3r.bubblenet@gmail.com>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Visit http://tec-ch-mun-2011.herokuapps.com
 */
package org.blanco.techmun.android.cproviders;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.impl.client.DefaultHttpClient;
import org.blanco.techmun.android.misc.ObjectsCursor;
import org.blanco.techmun.entities.Comentario;
import org.blanco.techmun.entities.Eventos;
import org.blanco.techmun.entities.Mensaje;
import org.blanco.techmun.entities.Mesas;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
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
	MensajesFetcher mensajesFetcher = null;
	
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
			boolean success = comentsFetcher.publishComentario(values);
			if (success){
				return Uri.parse(CONTENT_BASE_URI+"/comentarios/");
			}else{
				return Uri.EMPTY;
			}
		}else if(uri.toString().matches(MESA_CONTENT_MENSAJES_PETITION_REG_EXP)){
			return Uri.parse(CONTENT_BASE_URI+"/mensajes/"+123);
		}else{
			throw new UnsupportedOperationException("Can't insert other elements that not " +
					"complain with comentarios expression");
		}
	}

	@Override
	public boolean onCreate() {
		httpClient = new DefaultHttpClient();
		eventosFeher = new EventosFetcher(httpClient,getContext());
		mesasFecher = new MesasFetcher(httpClient, getContext());
		comentsFetcher = new ComentariosFetcher(httpClient);
		mensajesFetcher = new MensajesFetcher(httpClient, getContext());
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
	public static String extractGroupFromPattern(String pattern, String input, 
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
	
	private Cursor getComentariosCursorForUri(Uri uri, String selection){
		String eventoId = 
		extractGroupFromPattern(MESA_CONTENT_COMENTARIOS_PETITION_REG_EXP, uri.toString(), 1);
		//Extracting the pagina selection part
		
		String pagina = "0";
		try{
			pagina = extractGroupFromPattern("pagina=(\\d)", selection, 1);
		}catch(Exception e){
			Log.e("techmun", "Unable to retrieve the pagina number from selection argument. " +
					"Pagina will be 0",e);
		}
		try{
			Long leId = Long.parseLong(eventoId);
			Integer lPagina = Integer.parseInt(pagina);
			FetchComentariosResult fetchResult =
					comentsFetcher.fetchComentarios(leId,lPagina);
			List<FetchComentariosResult> resultList = 
					new ArrayList<FetchComentariosResult>(1);
			resultList.add(fetchResult);
			ObjectsCursor result = new ObjectsCursor(resultList);
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
		if (uri.toString().matches(MESA_CONTENT_EVENTOS_PETITION_REG_EXP)){
			return getEventosCursorForUri(uri);			
		}else if (uri.toString().matches(MESA_CONTENT_COMENTARIOS_PETITION_REG_EXP)){
			return getComentariosCursorForUri(uri,selection);
		}else if (uri.toString().matches(MESA_CONTENT_MENSAJES_PETITION_REG_EXP)){
			return getMensajesCursorForUri(uri);
		}else if (uri.toString().matches(CONTENT_BASE_URI)){
			return getMesasCursorForUri(uri);
		}
		return null;
	}

	private Cursor getMensajesCursorForUri(Uri uri) {
		List<Mensaje> result = null;
		result = mensajesFetcher.getMensajes();
		ObjectsCursor cursor = new ObjectsCursor(result);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

}
