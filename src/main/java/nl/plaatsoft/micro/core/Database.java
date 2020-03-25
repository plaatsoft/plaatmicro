package nl.plaatsoft.micro.core;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import nl.plaatsoft.micro.dao.InventoryDao;
import nl.plaatsoft.micro.dao.StatusDao;
import nl.plaatsoft.micro.dao.SubscriptionDao;

/**
 * The Class Database.
 * 
 * @author wplaat
 */
public class Database {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( Database.class);
	
	/** The config. */
	private Config config = new Config();
	
	/** The inventory dao. */
	private InventoryDao inventoryDao;
	
	/** The status dao. */
	private StatusDao statusDao;
	
	/** The subscription dao. */
	private SubscriptionDao subscriptionDao;
	
	/**
	 * Init.
	 * @throws Exception 
	 */
	public Database() throws Exception {		
		
		log.info("Init database {}", config.getDatabaseUrl());
		
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("javax.persistence.jdbc.driver", config.getDatabaseDriver());
		properties.put("javax.persistence.jdbc.url", config.getDatabaseUrl());
		properties.put("javax.persistence.jdbc.user", config.getDatabaseUsername());
		properties.put("javax.persistence.jdbc.password", config.getDatabasePassword());
				
		properties.put("hibernate.dialect", config.getHibernateDialect());
		properties.put("hibernate.hbm2ddl.auto", config.getHibernateHbm2ddlAuto());
		properties.put("hibernate.show_sql", config.getHibernateShowSql());
						
		try {
			EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PlaatMicro", properties);
			EntityManager entityManager = entityManagerFactory.createEntityManager();
        
			statusDao = new StatusDao(entityManager);
			inventoryDao = new InventoryDao(entityManager);			
			subscriptionDao = new SubscriptionDao(entityManager);
		} catch (Exception e ) {
			log.error(e.getMessage());
			throw e;
		}
	}

	/**
	 * Gets the inventory dao.
	 *
	 * @return the inventory dao
	 */
	public InventoryDao getInventoryDao() {
		return inventoryDao;
	}

	/**
	 * Gets the status dao.
	 *
	 * @return the status dao
	 */
	public StatusDao getStatusDao() {
		return statusDao;
	}

	/**
	 * Gets the subscription dao.
	 *
	 * @return the subscription dao
	 */
	public SubscriptionDao getSubscriptionDao() {
		return subscriptionDao;
	}
}
