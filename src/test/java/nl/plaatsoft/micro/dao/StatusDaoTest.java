package nl.plaatsoft.micro.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

/**
 * The Class StatusDaoTest.
 * 
 * @author wplaat
 */
public class StatusDaoTest extends BaseDaoTest {

	/**
	 * Test status.
	 */
	@Test
	public void testInsert() {
		
		Inventory inventory1 = database.getInventoryDao().save(new Inventory("element1", "element1 description")).get();
		
		Date now = new Date();
		
		Status status1 = database.getStatusDao().save(new Status(now, 1, inventory1)).get();       
		assertEquals(2, status1.getId());    		
	}
}
