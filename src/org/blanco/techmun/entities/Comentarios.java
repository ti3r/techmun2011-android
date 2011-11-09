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
public class Comentarios implements Iterable<Comentario>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6330956612447729039L;
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
