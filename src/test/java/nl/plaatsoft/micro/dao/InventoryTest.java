package nl.plaatsoft.micro.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * The Class InventoryTest.
 * 
 * @author wplaat
 */
public class InventoryTest extends BaseDaoTest {

	/**
	 * Test inventory.
	 */
	@Test
	public void testInsert() {
		
		Inventory inventory1 = database.getInventoryDao().save(new Inventory("element1", "element1 description")).get();              
		assertEquals(1, inventory1.getId());    		
	}
}
