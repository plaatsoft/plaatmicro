package nl.plaatsoft.micro.core;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.plaatsoft.micro.dao.Inventory;
import nl.plaatsoft.micro.dao.Status;
import nl.plaatsoft.micro.dao.Subscription;
import nl.plaatsoft.micro.schema.MicroInventorySubscribe;
import nl.plaatsoft.micro.schema.MicroStatusPublish;
import nl.plaatsoft.micro.schema.MicroStatusSubscribe;

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
	 * Onramp.
	 *
	 * @param object the object
	 */
	public void onramp(Object object) {
		
		if (object.getClass().equals(MicroStatusSubscribe.class)) {
			
			MicroStatusSubscribe subscribe = (MicroStatusSubscribe) object;
			
			Subscription subscription = new Subscription(
					subscribe.getStatusSubscribe().getName(), 
					subscribe.getStatusSubscribe().getDescription(), 
					subscribe.getStatusSubscribe().getSource(),
					true, false);
			
			database.getSubscriptionDao().save(subscription);
			
		} else if (object.getClass().equals(MicroInventorySubscribe.class)) {
			
			MicroInventorySubscribe subscribe = (MicroInventorySubscribe) object;
			
			Subscription subscription = new Subscription(
					subscribe.getInventorySubscribe().getName(), 
					subscribe.getInventorySubscribe().getDescription(), 
					subscribe.getInventorySubscribe().getSource(),
					false, true);
			
			database.getSubscriptionDao().save(subscription);
			
		} else if (object.getClass().equals(MicroStatusPublish.class)) {
			
			MicroStatusPublish publish = (MicroStatusPublish) object;
			
			Optional<Inventory> inventory = database.getInventoryDao().findByName(publish.getStatusPublish().get(0).getInventoryId());
			if (inventory.isPresent()) {
				
				Status status = new Status();
				status.setInventory(inventory.get());
				status.setState(publish.getStatusPublish().get(0).getState());
				status.setTimestamp(Utils.toDate(publish.getStatusPublish().get(0).getDt()));
			}
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
							
							StatusReader reader2 = new StatusReader(config, subscription);
							statusObservable.deleteObserver(reader2);
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
					
					Inventory inventory2 = new Inventory("inventory2", "inventory2 description");         					
					Status status2 = new Status(new Date(), counter++, inventory2);     
					statusObservable.update(status2);
					inventoryObservable.update(inventory2);
										
					state=State.IDLE;
					break;
					
				case IDLE:
					try {
						sleep(1000);
						state=State.END;
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
