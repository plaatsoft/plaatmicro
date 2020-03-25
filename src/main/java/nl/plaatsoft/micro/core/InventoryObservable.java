package nl.plaatsoft.micro.core;

import java.util.Observable;

import nl.plaatsoft.micro.dao.Inventory;

/**
 * The Class InventoryObservable.
 * 
 * @author wplaat
 */
public class InventoryObservable extends Observable {

	/**
	 * Update.
	 *
	 * @param status the status
	 */
	void update(Inventory inventory) {
		
		setChanged();
		notifyObservers(inventory);
	}
}
