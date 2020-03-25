package nl.plaatsoft.micro.core;

import java.util.Observable;

import nl.plaatsoft.micro.dao.Status;

/**
 * The Class StatusObservable.
 * 
 * @author wplaat
 */
public class StatusObservable extends Observable {

	/**
	 * Update.
	 *
	 * @param status the status
	 */
	void update(Status status) {
		
		setChanged();
		notifyObservers(status);
	}
}
