package org.blanco.techmun.entities;

import java.io.Serializable;
import java.util.Date;

/** Class
*/
public class Comentario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6943750923161350971L;
	private long id;
	private Evento evento;
	private String comentario;
	private String autor;
	private String contacto;
	private Date fecha;
	
	public Evento getEvento() {
		return evento;
	}
	public void setEvento(Evento evento) {
		this.evento = evento;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getContacto() {
		return contacto;
	}
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
		
}
