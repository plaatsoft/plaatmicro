package nl.plaatsoft.micro.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *  
 * The Class Main.
 * 
 * @author wplaat
 */
public class Main {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( Main.class);

	/**
	 * Inits the.
	 *
	 * @throws Exception the exception
	 */
	private void init() throws Exception {
				
		Config config = new Config();
		Database database = new Database();
		StateMachine stateMachine = new StateMachine(config, database);
		stateMachine.start();		
	}
		
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		
		log.info("starting");	
				
        Main main = new Main();
        main.init();
    }
	
}
