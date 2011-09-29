package org.blanco.techmun.entities;

import java.io.Serializable;
import java.util.Date;

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
