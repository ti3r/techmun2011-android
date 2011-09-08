package org.blanco.techmun.android.misc;

import java.util.Date;

import org.blanco.techmun.entities.Evento;
import org.blanco.techmun.entities.Eventos;

import android.net.Uri;

/**
 * Class in charge of retrieving the Evento Objects
 * for the application. This can be done from the 
 * Internet through a REST petition or from the 
 * cached records.
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public class EventosFetcher {

	public Eventos fetchEventos(Uri uri){
		Evento e = new Evento();
		e.setEvento("Evento de prueba");
		e.setId(100L);
		e.setMesaId(1L);
		e.setFecha(new Date());
		Eventos result = new Eventos(e);
		return result;
	}
	
}
