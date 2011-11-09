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
package org.blanco.techmun.entities;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Entity that reprsents one registed user in the system
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class Usuario implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String correo;
	private String alta;
	
	public Usuario(){
		this(null,null);
	}
	
	public Usuario(String nombre, String correo){
		this.nombre = nombre;
		this.correo = correo;
		this.alta = "";
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getAlta() {
		return alta;
	}
	public void setAlta(String alta) {
		this.alta = alta;
	}
	
	public static Usuario fromJSONObject(JSONObject jsonRep) throws JSONException{
		Usuario u = new Usuario();
		u.setNombre(jsonRep.getString("nombre"));
		u.setCorreo(jsonRep.getString("correo"));
		u.setAlta(jsonRep.getString("alta"));
		return u;
	}

}
