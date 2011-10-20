package org.blanco.techmun.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * A class that represents a List of Comentario objects, copied
 * from the classes used in the web application but the
 * annotations and extra information was removed.
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class Comentarios implements Iterable<Comentario>{

	/**
	 * The list that will hold the elements
	 */
	private List<Comentario> comentarios = null;

	/**
	 * Default constructor creates and empty list
	 */
	public Comentarios(){
		this.comentarios = new ArrayList<Comentario>();
	}
	/**
	 * Constructor that builds the list with one element
	 * @param comentario The Comentario item to be added
	 */
	public Comentarios(Comentario comentario){
		this();
		this.comentarios.add(comentario);
	}
	/**
	 * Constructor that build the list with the elements in
	 * the passed colection
	 * @param comentarios The Comentario items to be added
	 */
	public Comentarios(Collection<Comentario> comentarios){
		this();
		this.comentarios.addAll(comentarios);
	}
	
	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	
	public Iterator<Comentario> iterator() {
		return this.comentarios.iterator();		
	}
		
	public void addComentario(Comentario comenratio){
		this.comentarios.add(comenratio);
	}
	
	public void addComentarios(Comentarios comentarios){
		this.comentarios.addAll(comentarios.getComentarios());
	}
	
}
