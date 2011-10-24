package org.blanco.techmun.entities;

import java.io.Serializable;
import java.util.Date;

import android.graphics.Bitmap;

public class Mensaje implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1040659478527062185L;
	
	private long id;
	private String mensaje;
	private Date fecha;
	private Usuario autor;
	private String fotoUrl;
	private Bitmap foto;
	private boolean failedRetrieveFoto = false;
	
	public Bitmap getFoto() {
		return foto;
	}

	public void setFoto(Bitmap foto) {
		this.foto = foto;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Mensaje(String mensaje, Usuario autor) {
		super();
		this.mensaje = mensaje;
		this.autor = autor;
	}

	public Mensaje(String mensaje, Date fecha, Usuario autor, String fotoUrl) {
		super();
		this.mensaje = mensaje;
		this.fecha = fecha;
		this.autor = autor;
		this.fotoUrl = fotoUrl;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Usuario getAutor() {
		return autor;
	}
	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public String getFotoUrl() {
		return fotoUrl;
	}

	public void setFotoUrl(String fotoUrl) {
		this.fotoUrl = fotoUrl;
	}

	public boolean isFailedRetrieveFoto() {
		return failedRetrieveFoto;
	}

	public void setFailedRetrieveFoto(boolean failedRetrieveFoto) {
		this.failedRetrieveFoto = failedRetrieveFoto;
	}
	
}
