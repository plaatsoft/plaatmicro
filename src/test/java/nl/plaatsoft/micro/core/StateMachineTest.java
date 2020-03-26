package nl.plaatsoft.micro.core;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import nl.plaatsoft.micro.schema.InfoRequest;
import nl.plaatsoft.micro.schema.InventoryPublish;
import nl.plaatsoft.micro.schema.InventorySubscribe;
import nl.plaatsoft.micro.schema.Meta;
import nl.plaatsoft.micro.schema.MicroInfoRequest;
import nl.plaatsoft.micro.schema.MicroInventoryPublish;
import nl.plaatsoft.micro.schema.MicroInventorySubscribe;
import nl.plaatsoft.micro.schema.MicroStatusPublish;
import nl.plaatsoft.micro.schema.MicroStatusSubscribe;
import nl.plaatsoft.micro.schema.StatusPublish;
import nl.plaatsoft.micro.schema.StatusSubscribe;

/**
 * The Class StateMachineTest.
 *
 * @author wplaat
 */
public class StateMachineTest {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( StateMachineTest.class);
		
	/** The database. */
	protected Config config;
	
	/** The database. */
	protected Database database;
		
	/**
	 * Setup.
	 */
	@Before
	public void setup() { 		
		
		try {			
			config = new Config();
			database = new Database();
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	/**
	 * Gets the status subscribe.
	 *
	 * @return the status subscribe
	 */
	private MicroStatusSubscribe getStatusSubscribe() {

		Meta meta = new Meta();
		meta.setDestination("destination1");
		meta.setSource("source1");
		meta.setMsgId(UUID.randomUUID().toString());
		meta.setDt(Utils.getXMLGregorianCalendarNow());

		StatusSubscribe statusSubscribe = new StatusSubscribe();
		statusSubscribe.setName("status1");
		statusSubscribe.setDescription("status1 description");
		statusSubscribe.setSource("source1");

		MicroStatusSubscribe subscribe = new MicroStatusSubscribe();
		subscribe.setMeta(meta);
		subscribe.setStatusSubscribe(statusSubscribe);	
		
		return subscribe;
	}
	
	/**
	 * Gets the status publish.
	 *
	 * @return the status publish
	 */
	private MicroStatusPublish getStatusPublish() {
		Meta meta = new Meta();
		meta.setDestination("destination1");
		meta.setSource("source1");
		meta.setMsgId(UUID.randomUUID().toString());
		meta.setDt(Utils.getXMLGregorianCalendarNow());
		
		StatusPublish statusPublish1 = new StatusPublish();
		statusPublish1.setDt(Utils.getXMLGregorianCalendarNow());
		statusPublish1.setInventoryId("inventory1");
		statusPublish1.setState(1);
		
		StatusPublish statusPublish2 = new StatusPublish();
		statusPublish2.setDt(Utils.getXMLGregorianCalendarNow());
		statusPublish2.setInventoryId("inventory2");
		statusPublish2.setState(2);
		
		MicroStatusPublish publish = new  MicroStatusPublish();
		publish.setMeta(meta);
		publish.getStatusPublish().add(statusPublish1);
		publish.getStatusPublish().add(statusPublish2);
		
		return publish;
	}
		
	/**
	 * Gets the inventory subscribe.
	 *
	 * @return the inventory subscribe
	 */
	private MicroInventorySubscribe getInventorySubscribe() {

		Meta meta = new Meta();
		meta.setDestination("destination1");
		meta.setSource("source1");
		meta.setMsgId(UUID.randomUUID().toString());
		meta.setDt(Utils.getXMLGregorianCalendarNow());
		
		InventorySubscribe InventorySubscribe = new InventorySubscribe();
		InventorySubscribe.setName("inventory1");
		InventorySubscribe.setDescription("inventory1 description");
		InventorySubscribe.setSource("source1");
				
		MicroInventorySubscribe subscribe = new  MicroInventorySubscribe();
		subscribe.setMeta(meta);
		subscribe.setInventorySubscribe(InventorySubscribe);
		
		return subscribe;
	}
		
	/**
	 * Gets the inventory publish.
	 *
	 * @return the inventory publish
	 */
	private MicroInventoryPublish getInventoryPublish() {
		Meta meta = new Meta();
		meta.setDestination("destination1");
		meta.setSource("source1");
		meta.setMsgId(UUID.randomUUID().toString());
		meta.setDt(Utils.getXMLGregorianCalendarNow());

		InventoryPublish inventoryPublish1 = new InventoryPublish();
		inventoryPublish1.setInventoryId(UUID.randomUUID().toString());
		inventoryPublish1.setName("inventory1");
		inventoryPublish1.setDescription("inventory1 description");
		
		InventoryPublish inventoryPublish2 = new InventoryPublish();
		inventoryPublish2.setInventoryId(UUID.randomUUID().toString());
		inventoryPublish2.setName("inventory2");
		inventoryPublish2.setDescription("inventory2 description");
		
		MicroInventoryPublish publish = new MicroInventoryPublish();
		publish.setMeta(meta);
		publish.getInventoryPublish().add(inventoryPublish1);
		publish.getInventoryPublish().add(inventoryPublish2);
		
		return publish;
	}
	
	/**
	 * Gets the info request.
	 *
	 * @return the info request
	 */
	private MicroInfoRequest getInfoRequest() {
		
		Meta meta = new Meta();
		meta.setDestination("destination1");
		meta.setSource("source1");
		meta.setMsgId(UUID.randomUUID().toString());
		meta.setDt(Utils.getXMLGregorianCalendarNow());

		InfoRequest infoRequest = new InfoRequest();
		infoRequest.setInventory(true);
		infoRequest.setStatus(true);
		
		MicroInfoRequest request = new MicroInfoRequest();
		request.setMeta(meta);
		request.setInfoRequest(infoRequest);
		
		return request;
	}
	
	/**
	 * Test state machine.
	 */
	@Test
	public void testStateMachine() {
						 		
		 StateMachine sm = new StateMachine(config, database);
	      	     		 
		 sm.onramp(getInventorySubscribe());
		 sm.onramp(getInventoryPublish());
		 
		 sm.onramp(getStatusSubscribe());		 
		 sm.onramp(getStatusPublish());
		 
		 sm.onramp(getInfoRequest());
		 		 
	     assertTrue( sm.start() );
	}
}
