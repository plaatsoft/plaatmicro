package nl.plaatsoft.micro.core;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.plaatsoft.micro.dao.Inventory;
import nl.plaatsoft.micro.dao.Status;

/**
 * The Class Main.
 * 
 * @author wplaat
 */
public class Main {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( Main.class);
	
	/** The config. */
	private Config config;
	
	/** The database. */
	//private Database database;
	
	/** The state. */
	private State state= State.INIT;
	
	/** The terminate. */
	private boolean terminate = false;
	
	private StatusObservable statusObservable;
			
	/**
	 * Sleep.
	 *
	 * @param parameter the parameter
	 */
	private void sleep(long parameter) {	
		try {
			Thread.sleep(parameter);
		} catch (InterruptedException e) {
											
	    }      					   
	}
	/**
	 * State machine.
	 */
	private void stateMachine() {
	
		int counter = 0; 
		
		while (!terminate) {
		
			log.info("state={}", state);	
		
			switch (state) {
		
				case INIT: 											
					try  {
						config = new Config();						
						statusObservable = new StatusObservable();						
						//database = new Database();
						
						state=State.SUBSCRIBE;
						
					} catch (Exception e){
						
						state=State.END;
					}					
					break;
				
				case SUBSCRIBE:
					
					StatusReader reader1 = new StatusReader();
					statusObservable.addObserver(reader1);
					
					StatusReader reader2 = new StatusReader();
					statusObservable.addObserver(reader2);
					
					StatusReader reader3 = new StatusReader();
					statusObservable.addObserver(reader3);
					
					state=State.PUBLISH;
					break;
										
				case PUBLISH:					
					Inventory inventory1 = new Inventory("inventory1", "inventory1 description");         					
					Date now = new Date();
					Status status1 = new Status(now, counter++, inventory1);     
					statusObservable.update(status1);
					
					state=State.IDLE;
					break;
					
				case IDLE:
					sleep(1000);
					state=State.PUBLISH;
					break;
					
				case END: 
					terminate = true;
					break;						
			}
		}
	}
		
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		
		log.info("starting");	
		
        Main main = new Main();
        main.stateMachine();    
    }
	
}
