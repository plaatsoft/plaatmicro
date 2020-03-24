package nl.plaatsoft.micro.dao;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Before;

import nl.plaatsoft.micro.core.Config;

/**
 * The Class GeneralDaoTest.
 * 
 * @author wplaat
 */
public class BaseDaoTest {
	
	protected InventoryDao inventoryDao;
	
	protected StatusDao statusDao;
	
	/**
	 * Setup.
	 */
	@Before
	public void setup() { 		
		
		Config config = new Config();
		
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("javax.persistence.jdbc.driver", config.getDatabaseDriver());
		properties.put("javax.persistence.jdbc.url", config.getDatabaseUrl());
		properties.put("javax.persistence.jdbc.user", config.getDatabaseUsername());
		properties.put("javax.persistence.jdbc.password", config.getDatabasePassword());
				
		properties.put("hibernate.dialect", config.getHibernateDialect());
		properties.put("hibernate.hbm2ddl.auto", config.getHibernateHbm2ddlAuto());
		properties.put("hibernate.show_sql", config.getHibernateShowSql());
						
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PlaatMicro", properties);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
       
        statusDao = new StatusDao(entityManager);
        inventoryDao = new InventoryDao(entityManager); 
	    	    
        statusDao.truncate();
        inventoryDao.truncate();
	}
}
