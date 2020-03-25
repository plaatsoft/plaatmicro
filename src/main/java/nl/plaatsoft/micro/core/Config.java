package nl.plaatsoft.micro.core;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class Config.
 * 
 * @author wplaat
 */
public class Config {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( Config.class);	
	
	/** The Constant FILENAME1. */
	private static final String FILENAME1 = "plaatmicro.properties";
	
	/** The Constant FILENAME2. */
	private static final String FILENAME2 = "plaatmicro.override.properties";
			
	/** The database driver. */
	private String databaseDriver;
	
	/** The database url. */
	private String databaseUrl;
	
	/** The database username. */
	private String databaseUsername;
	
	/** The database password. */
	private String databasePassword;
		
	/** The hibernate dialect. */
	private String hibernateDialect;
	
	/** The hibernate show sql. */
	private String hibernateShowSql;
	
	/** The hibernate hbm 2 ddl auto. */
	private String hibernateHbm2ddlAuto;
	
	/** The source id. */
	private String sourceId;
	
	
	/**
	 * Instantiates a new config.
	 */
	public Config() {
		
		try {
			Properties prop = new Properties();
			 
			InputStream inputStream1 = getClass().getClassLoader().getResourceAsStream(FILENAME1);
 
			if (inputStream1 != null) {
				prop.load(inputStream1);
			} else {
				throw new FileNotFoundException("property file '" + FILENAME1 + "' not found in the classpath");
			}
			
			InputStream inputStream2 = getClass().getClassLoader().getResourceAsStream(FILENAME2);
			if (inputStream2 != null) {
				prop.load(inputStream2);
			}
 	
			databaseUrl = prop.getProperty("databaseUrl");
			databaseUsername = prop.getProperty("databaseUsername");			
			databasePassword = prop.getProperty("databasePassword");
			databaseDriver = prop.getProperty("databaseDriver");
			
			hibernateDialect = prop.getProperty("hibernateDialect");
			hibernateShowSql = prop.getProperty("hibernateShowSql");
			hibernateHbm2ddlAuto = prop.getProperty("hibernateHbm2ddlAuto");
			
			sourceId = prop.getProperty("sourceId");
						
		} catch (Exception e) {
			log.error(e.getMessage());
		} 
	}

	/**
	 * Gets the database password.
	 *
	 * @return the database password
	 */
	public String getDatabasePassword() {
		return databasePassword;
	}

	/**
	 * Gets the database url.
	 *
	 * @return the database url
	 */
	public String getDatabaseUrl() {
		return databaseUrl;
	}

	/**
	 * Gets the database username.
	 *
	 * @return the database username
	 */
	public String getDatabaseUsername() {
		return databaseUsername;
	}

	/**
	 * Gets the database driver.
	 *
	 * @return the database driver
	 */
	public String getDatabaseDriver() {
		return databaseDriver;
	}

	/**
	 * Gets the hibernate dialect.
	 *
	 * @return the hibernate dialect
	 */
	public String getHibernateDialect() {
		return hibernateDialect;
	}

	/**
	 * Gets the hibernate show sql.
	 *
	 * @return the hibernate show sql
	 */
	public String getHibernateShowSql() {
		return hibernateShowSql;
	}

	/**
	 * Gets the hibernate hbm 2 ddl auto.
	 *
	 * @return the hibernate hbm 2 ddl auto
	 */
	public String getHibernateHbm2ddlAuto() {
		return hibernateHbm2ddlAuto;
	}

	/**
	 * Gets the source id.
	 *
	 * @return the source id
	 */
	public String getSourceId() {
		return sourceId;
	}
}
