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
import java.util.Date;

/**
 * Main class that will be associated to something that happens
 * on a specific table (Mesa). The event will carry information
 * about what has happened in a certain place. 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class Evento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7043431726000945811L;
	public static final String EVENTO_ID_COL_NAME = "id";
	public static final String EVENTO_MESAID_COL_NAME = "mesaId";
	public static final String EVENTO_EVENTO_COL_NAME = "evento";
	public static final String EVENTO_FECHA_COL_NAME = "fecha";
	
	public static final String[] EVENTO_COL_NAMES = {
		EVENTO_ID_COL_NAME, EVENTO_MESAID_COL_NAME,
		EVENTO_EVENTO_COL_NAME, EVENTO_FECHA_COL_NAME
	};
	
	
	private Long id;
	private Long mesaId;
	private String titulo;
	private String descripcion;
	private Date fecha;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMesaId() {
		return mesaId;
	}

	public void setMesaId(Long mesaId) {
		this.mesaId = mesaId;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String evento) {
		this.titulo = evento;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
	
		return "Evento["+id+", mesaId="+mesaId+", evento="
				+titulo+", fecha="+fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
