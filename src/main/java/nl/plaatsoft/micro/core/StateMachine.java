package nl.plaatsoft.micro.core;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.plaatsoft.micro.dao.Inventory;
import nl.plaatsoft.micro.dao.Status;
import nl.plaatsoft.micro.dao.Subscription;

/**
 * The Class StateMachine.
 * 
 * @author wplaat
 */
public class StateMachine {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( StateMachine.class);
	
	/** The state. */
	private State state= State.INIT;
	
	/** The config. */
	private Config config;
		
	/** The database. */
	private Database database;
	
	/** The terminate. */
	private boolean terminate = false;
	
	/** The status observable. */
	private StatusObservable statusObservable;
	
	/** The inventory observable. */
	private InventoryObservable inventoryObservable;
	
	/**
	 * Instantiates a new state machine.
	 *
	 * @param config the config
	 * @param database the database
	 */
	public StateMachine(Config config, Database database) {
		super();
		this.config = config;
		this.database = database;
	}
	
	/**
	 * Sleep.
	 *
	 * @param parameter the parameter
	 * @throws InterruptedException the interrupted exception
	 */
	private void sleep(long parameter) throws InterruptedException {	
		try {
			Thread.sleep(parameter);
		} catch (InterruptedException e) {
			log.error(e.getMessage());			
			throw e;
	    }      					   
	}
	
	/**
	 * State machine.
	 *
	 * @return true, if successful
	 */
	public boolean start() {
	
		int counter = 0; 
		
		while (!terminate) {
		
			log.info("state={}", state);	
		
			switch (state) {
		
				case INIT: 											
					statusObservable = new StatusObservable();
					inventoryObservable = new InventoryObservable();				
					state=State.SUBSCRIBE;	
					break;
				
				case SUBSCRIBE:
					
					List <Subscription> subscriptions = database.getSubscriptionDao().findAll();
					
					Iterator <Subscription> iter = subscriptions.iterator();
					while (iter.hasNext()) {
						Subscription subscription = iter.next();	
						if (subscription.isStatus()) {
							StatusReader reader = new StatusReader(config, subscription);
							statusObservable.addObserver(reader);
						}
						if (subscription.isInventory()) {
							InventoryReader reader = new InventoryReader(config, subscription);
							inventoryObservable.addObserver(reader);
						}
					}
					state=State.PUBLISH;
					break;
										
				case PUBLISH:					
					Inventory inventory1 = new Inventory("inventory1", "inventory1 description");         					
					Status status1 = new Status(new Date(), counter++, inventory1);     
					statusObservable.update(status1);
					inventoryObservable.update(inventory1);
					
					state=State.END;
					break;
					
				case IDLE:
					try {
						sleep(1000);
						state=State.PUBLISH;
					} catch (Exception e) {
						state=State.END;
					}
					break;
					
				case END: 
					terminate = true;
					break;						
			}
		}
		return true;
	}
}
