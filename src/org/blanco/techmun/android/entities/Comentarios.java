package org.blanco.techmun.android.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.blanco.techmun.entities.Comentario;

public class Comentarios implements Iterable<Comentario> {

	private List<Comentario> comentarios;
	
	public Comentarios(){
		this.comentarios = new ArrayList<Comentario>();
	}

	public void addAll(Collection<Comentario> comentarios){
		this.comentarios.addAll(comentarios);
	}
	
	public void add(Comentario c){
		this.comentarios.add(c);
	}
	
	@Override
	public Iterator<Comentario> iterator() {
		return this.comentarios.iterator();
	}
	
	
	
}
