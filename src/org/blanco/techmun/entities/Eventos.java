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
import java.util.List;

/**
 * A class that represents a List of Evento objects, copied
 * from the classes used in the web application but the
 * annotations and extra information was removed.
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */

public class Eventos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8814952043918363006L;
	private List<Evento> eventos = null;
	
	/**
	 * Default constructor to build an empty list of evento 
	 * objects
	 */
	public Eventos(){
		this.eventos = new ArrayList<Evento>();
	}
	/**
	 * Constructor to build a list of Evento objects
	 * with one element
	 * @param e The Evento element to be added to the list
	 */
	public Eventos(Evento e){
		this();
		this.eventos.add(e);
	}
	
	/**
	 * Constructor to build a list of Evento objects with the
	 * Evento elements of the eventos Collection
	 * @param eventos The Collection of Evento elements
	 */
	public Eventos(Collection<Evento> eventos) {
		super();
		this.eventos = new ArrayList<Evento>(eventos);
	}

	
	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}
	
}
