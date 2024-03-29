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
 * A class that represents a List of Mesa objects, used to 
 * marshall and unmarshall xml lists of Mesa Objects
 * A copy of the Mesas object used in the web application
 * with out the annotations and extra requiered flags
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */

public class Mesas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7813036714358229572L;
	/**
	 * The List that will hold the Mesa objects
	 */
	private List<Mesa> mesas = null;
	
	/**
	 * Constructor to build a new Mesas object based
	 * on a collection of Mesa objects
	 * @param mesas The collection of Mesa objects that
	 * will be used as base of the new Mesas object.
	 */
	public Mesas(Collection<Mesa> mesas){
		this.mesas = new ArrayList<Mesa>(mesas);
	}
	/**
	 * Default constructor, it creates a new Mesas object
	 * with an empty list of Mesa objects
	 */
	public Mesas(){
		this.mesas = new ArrayList<Mesa>();
	}
	/**
	 * Constructor to create a new Mesas object with one
	 * Mesa element in the list.
	 * 
	 * @param mesa The Mesa object that will be attached
	 * to the list.
	 */
	public Mesas(Mesa mesa){
		this();
		this.mesas.add(mesa);
	}

	/**
	 * Returns the list of Mesa objects associated
	 * @return List of Mesa objects stored in the object
	 */
	public List<Mesa> getMesas() {
		return this.mesas;
	}

	/**
	 * Sets the List of Mesa objects
	 * @param mesas The List of Mesa objects that will be 
	 * associated with this object.
	 */
	public void setMesas(List<Mesa> mesas) {
		this.mesas = mesas;
	}
		
}
