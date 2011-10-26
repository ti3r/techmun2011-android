package org.blanco.techmun.android.cproviders;

import java.io.Serializable;

import org.blanco.techmun.entities.Comentarios;

public class FetchComentariosResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3963576818642076119L;
	public Comentarios comentarios = null;
	public Integer pagina = null;
	public boolean mas = false;
}