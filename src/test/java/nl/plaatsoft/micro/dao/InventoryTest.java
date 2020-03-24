package nl.plaatsoft.micro.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class InventoryTest extends BaseDaoTest {

	@Test
	public void testInventory() {
		
		Inventory inventory1 = inventoryDao.save(new Inventory("element1", "element1 description")).get();              
		assertEquals(1, inventory1.getIid());    
		
	}
}
