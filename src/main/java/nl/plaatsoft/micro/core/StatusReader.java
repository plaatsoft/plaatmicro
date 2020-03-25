package nl.plaatsoft.micro.core;

import java.util.Observable;
import java.util.Observer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.plaatsoft.micro.dao.Status;

public class StatusReader implements Observer {
	
	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( StatusReader.class);
	
	public void update(Observable obj, Object arg) {
        log.info("publish {}", (Status) arg);
    }
}
