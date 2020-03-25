package nl.plaatsoft.micro.core;

import java.util.Observable;

import nl.plaatsoft.micro.dao.Status;

public class StatusObservable extends Observable {

	void update(Status status) {
		
		setChanged();
		notifyObservers(status);
	}
}
