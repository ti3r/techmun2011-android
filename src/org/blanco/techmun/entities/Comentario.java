package org.blanco.techmun.entities;

/**
 * Class that represents one comment on an event that
 * has happened. One Comentario should be associated
 * to one event on one table.
 * This is a copy of the class used in the web application
 * but without the annotations and extra meta information
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class Comentario {

	private Long id;
	private Long eventoId;
	private String comentario;
	
	public Comentario(){
		
	}
	
	public Comentario(Long id, Long eventoId, String comentario) {
		super();
		this.id = id;
		this.eventoId = eventoId;
		this.comentario = comentario;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEventoId() {
		return eventoId;
	}
	public void setEventoId(Long eventoId) {
		this.eventoId = eventoId;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
		
}
