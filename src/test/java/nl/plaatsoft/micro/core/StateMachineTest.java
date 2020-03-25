package nl.plaatsoft.micro.core;

import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import nl.plaatsoft.micro.dao.Subscription;

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
	
	/**
	 * Test state machine.
	 */
	@Test
	public void testStateMachine() {
				
		 database.getSubscriptionDao().save(new Subscription("subscription1","subscription1 description", "destination1", true, false));
		 database.getSubscriptionDao().save(new Subscription("subscription2","subscription2 description", "destination2", false, true));
		 database.getSubscriptionDao().save(new Subscription("subscription3","subscription3 description", "destination3", true, true));
		 		
		 StateMachine sm = new StateMachine(config, database);
	      	     
	     assertTrue( sm.start() );
	}
}
