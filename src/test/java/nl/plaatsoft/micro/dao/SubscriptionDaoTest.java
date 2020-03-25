package nl.plaatsoft.micro.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * The Class SubscriptionDaoTest.
 * 
 * @author wplaat
 */
public class SubscriptionDaoTest extends BaseDaoTest {

	/**
	 * Test status.
	 */
	@Test
	public void testInsert() {				
		Subscription subscription1 = database.getSubscriptionDao().save(new Subscription("subscription1","subscription description", "destination1", true, true)).get();
		assertEquals(1, subscription1.getId());    
	}
}
