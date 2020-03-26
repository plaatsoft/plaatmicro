package nl.plaatsoft.micro.core;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.plaatsoft.micro.dao.Inventory;
import nl.plaatsoft.micro.dao.Status;
import nl.plaatsoft.micro.dao.Subscription;
import nl.plaatsoft.micro.schema.InfoReply;
import nl.plaatsoft.micro.schema.InventoryPublish;
import nl.plaatsoft.micro.schema.Metareply;
import nl.plaatsoft.micro.schema.MicroInfoReply;
import nl.plaatsoft.micro.schema.MicroInfoRequest;
import nl.plaatsoft.micro.schema.MicroInventoryPublish;
import nl.plaatsoft.micro.schema.MicroInventorySubscribe;
import nl.plaatsoft.micro.schema.MicroStatusPublish;
import nl.plaatsoft.micro.schema.MicroStatusSubscribe;
import nl.plaatsoft.micro.schema.StatusPublish;

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
		
		log.info("RX: {}", object);
		
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
			
			for (int i=0; i<publish.getStatusPublish().size(); i++) {
					
				Optional<Inventory> inventory = database.getInventoryDao().findByName(publish.getStatusPublish().get(i).getInventoryId());
				if (inventory.isPresent()) {
					Status status = new Status();
					status.setInventory(inventory.get());
					status.setState(publish.getStatusPublish().get(i).getState());
					status.setTimestamp(Utils.toDate(publish.getStatusPublish().get(i).getDt()));
					
					database.getStatusDao().save(status);					
				}				
			}
			
		} else if (object.getClass().equals(MicroInventoryPublish.class)) {
			
			MicroInventoryPublish publish = (MicroInventoryPublish) object;
			
			for (int i=0; i<publish.getInventoryPublish().size(); i++) {
				
				Inventory inventory = new Inventory();
				inventory.setName(publish.getInventoryPublish().get(i).getName());
				inventory.setDescription(publish.getInventoryPublish().get(i).getDescription());
				
				database.getInventoryDao().save(inventory);
			}
			
		} else if (object.getClass().equals(MicroInfoRequest.class)) {
			
			MicroInfoRequest request = (MicroInfoRequest) object;
			
			Metareply metareply = new Metareply();
			metareply.setDestination(request.getMeta().getSource());
			metareply.setSource(request.getMeta().getDestination());
			metareply.setRequestId(request.getMeta().getMsgId());
			metareply.setMsgId(UUID.randomUUID().toString());
			metareply.setDt(Utils.getXMLGregorianCalendarNow());
			
			InfoReply infoReply = new InfoReply();
					
			List <Inventory> inventories = database.getInventoryDao().findAll();
			Iterator <Inventory> iter2 = inventories.iterator();
			while (iter2.hasNext()) {
				
				Inventory inventory = iter2.next();	
				
				InventoryPublish inventoryPublish = new InventoryPublish();
				inventoryPublish.setName(inventory.getName());
				inventoryPublish.setDescription(inventory.getDescription());
				
				infoReply.getInventoryPublish().add(inventoryPublish);				
			}
						
			List <Status> statusses = database.getStatusDao().findAll();
			Iterator <Status> iter3 = statusses.iterator();
			while (iter3.hasNext()) {
				
				Status status = iter3.next();	
				
				StatusPublish statusPublish = new StatusPublish();
				//statusPublish.setDt(status.getTimestamp())
				statusPublish.setInventoryId(status.getInventory().getName());
				statusPublish.setState(status.getState());
								
				infoReply.getStatusPublish().add(statusPublish);				
			}
			
			MicroInfoReply reply = new MicroInfoReply();
			reply.setMetareply(metareply);
			reply.setInfoReply(infoReply);
			
			try {
				JAXBContext jaxbContext = JAXBContext.newInstance(MicroInfoReply.class);
				Marshaller marshaller = jaxbContext.createMarshaller();
				marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

				StringWriter sw = new StringWriter();
				marshaller.marshal(reply, sw);

				log.info(sw.toString());
				
			} catch (Exception e) {
				log.error(e.getMessage());
			}			
			
		} else {
			log.error("RX: unknown message received");
		}
	}
	
	
	/**
	 * State machine.
	 *
	 * @return true, if successful
	 */
	public boolean start() {
			
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
					
					Iterator <Subscription> iter1 = subscriptions.iterator();
					while (iter1.hasNext()) {
						Subscription subscription = iter1.next();	
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
					
					List <Inventory> inventories = database.getInventoryDao().findAll();
					Iterator <Inventory> iter2 = inventories.iterator();
					while (iter2.hasNext()) {
						Inventory inventory = iter2.next();	
						inventoryObservable.update(inventory);
					}
					
					List <Status> statusses = database.getStatusDao().findAll();
					Iterator <Status> iter3 = statusses.iterator();
					while (iter3.hasNext()) {
						Status status = iter3.next();	
						statusObservable.update(status);
					}
										
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
