package org.blanco.techmun.android.misc;

import org.blanco.techmun.entities.Mesa;

/**
 * Interface that must implement the items of the
 * Mesas List in order to pass the selected item
 * to the next process step.
 * 
 * @author Alexandro Blanco <ti3r.bubblenet@gmail.com>
 *
 */
public interface MesaListItemClickListener {

	public void MesaListItemClicked(Mesa mesa);
	
}
