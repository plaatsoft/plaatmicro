package nl.plaatsoft.micro.core;

import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import nl.plaatsoft.micro.schema.InventorySubscribe;
import nl.plaatsoft.micro.schema.Meta;
import nl.plaatsoft.micro.schema.MicroInventorySubscribe;
import nl.plaatsoft.micro.schema.MicroStatusSubscribe;
import nl.plaatsoft.micro.schema.StatusSubscribe;

/**
 * The Class StateMachineTest
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
		
	public MicroInventorySubscribe getInventorySubscribe() {

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
	 * Test state machine.
	 */
	@Test
	public void testStateMachine() {
						 		
		 StateMachine sm = new StateMachine(config, database);
	      	     
		 sm.onramp(getStatusSubscribe());
		 sm.onramp(getInventorySubscribe());
	     assertTrue( sm.start() );
	}
}
