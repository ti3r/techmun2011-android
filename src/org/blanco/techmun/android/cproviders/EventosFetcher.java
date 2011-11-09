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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.blanco.techmun.android.misc.XmlParser;
import org.blanco.techmun.entities.Evento;
import org.blanco.techmun.entities.Eventos;
import org.json.JSONArray;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;
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
	Context context = null;
	
	public EventosFetcher(HttpClient client, Context ctx){
		if (client == null || ctx == null){
			throw new IllegalArgumentException("HttpClient and Context can not be null");
		}
		this.httpClient = client;
		this.context = ctx;
	}
	
	private void saveOnCache(Context context, Eventos eventos, long mesaId){
		try {
			FileOutputStream mesasFOS = context.openFileOutput("eventos_"+mesaId+".df", 
					Context.MODE_PRIVATE);
			ObjectOutputStream mesasOOS = new ObjectOutputStream(mesasFOS);
			mesasOOS.writeObject(eventos);
			mesasOOS.close();
			PreferenceManager.getDefaultSharedPreferences(context).edit()
				.putLong("eventos_last_refresh", System.currentTimeMillis()).commit();
			//We just cached the events set the preference to not force the refresh again until expiration time
			PreferenceManager.getDefaultSharedPreferences(context).edit()
			.putBoolean("force_eventos_refresh_"+mesaId,false).commit();
		} catch (IOException e) {
			Log.e("tachmun", "Error opening cache file for mesas in content provider. " +
					"No cache will be saved",e);
		}
	}
	
	private Eventos tryToLoadFromCache(Context context, long mesaId){
		try {
			FileInputStream mesasFIS = context.openFileInput("eventos_"+mesaId+".df");
			ObjectInputStream mesasOIS = new ObjectInputStream(mesasFIS);
			Eventos result = (Eventos) mesasOIS.readObject();
			mesasOIS.close();
			
			return result;
		} catch (IOException e) {
			Log.e("tachmun", "Error opening cache file for mesas in content provider. " +
					"No cache will be restored",e);
			return null;
		} catch (ClassNotFoundException e) {
			Log.e("tachmun", "Error in cache file for mesas in content provider. " +
					"Something is there but is not a Mesas object. Cache no restored",e);
			return null;
		}
	}
	
	public Eventos fetchEventos(Long mesaId){
		Eventos result = null;
		//Check the preference to reload eventos list and the expiration of the cache
		boolean forceRefresh = PreferenceManager.getDefaultSharedPreferences(context)
				.getBoolean("force_eventos_refresh_"+mesaId,true); 
		if (!forceRefresh)
		{
			long lastRefresh = PreferenceManager.getDefaultSharedPreferences(context).getLong("eventos_last_refresh",-1);
			//if the last refresh ocurred no more than 3 minutes load from cache
			long diff = System.currentTimeMillis() - lastRefresh; 
			if (diff < 180000 ){
				result = tryToLoadFromCache(context, mesaId);
				if (result != null && !result.getEventos().isEmpty()){
					return result;
				}
			}
		}
		result = new Eventos();
		HttpGet request = new HttpGet(
				TechMunContentProvider.MESAS_REST_SERVICE_BSAE_URI+"/mesas/"+mesaId+"/eventos");
//		Evento e = new Evento(); e.setDescripcion("evento de prueba"); 
//		e.setFecha(new Date(System.currentTimeMillis())); e.setId(1L); e.setMesaId(1L); 
//		e.setTitulo("evento de prueba");
//		result.getEventos().add(e);
//		return result;
		HttpResponse response = null;
		try {
			response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == 200){
				JSONArray jsonEventos = XmlParser.parseJSONArrayFromHttpEntity(response.getEntity());
				for(int i=0; i < jsonEventos.length(); i++){
					Long id = jsonEventos.getJSONObject(i).getLong("id");
					String titulo = jsonEventos.getJSONObject(i).getString("titulo");
					String desc = jsonEventos.getJSONObject(i).getString("descripcion");
					String fecha = jsonEventos.getJSONObject(i).getString("fecha");
					Evento e = new Evento();
					e.setId(id); e.setTitulo(titulo);
					e.setMesaId(mesaId);
					e.setFecha(new Date(Date.parse(fecha)));
					e.setDescripcion(desc);
					result.getEventos().add(e);
				}
			}
		} catch (ClientProtocolException ex) {
			Log.e("techmun", "Error retrieving eventos from server",ex);
		} catch (IOException ex) {
			Log.e("techmun", "Error reading eventos from server",ex);
		} catch (Exception ex) {
			Log.e("techmun", "Error retrieving eventos from server",ex);
		}
		if (result != null){
			saveOnCache(context, result, mesaId);
		}
		return result;
	}
	
}
