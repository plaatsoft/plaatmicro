package nl.plaatsoft.micro.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;

import nl.plaatsoft.micro.core.Database;

/**
 * The Class GeneralDaoTest.
 * 
 * @author wplaat
 */
public abstract class BaseDaoTest {
	
	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( BaseDaoTest.class);
	
	/** The database. */
	protected Database database;
		
	/**
	 * Setup.
	 */
	@Before
	public void setup() { 		
		
		try {
			database = new Database();
	    	    
			database.getStatusDao().truncate();
			database.getInventoryDao().truncate();
			database.getSubscriptionDao().truncate();
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
