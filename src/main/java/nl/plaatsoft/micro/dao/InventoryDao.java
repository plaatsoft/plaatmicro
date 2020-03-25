package nl.plaatsoft.micro.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class InventoryDao.
 * 
 * @author wplaat
 */
public class InventoryDao {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( InventoryDao.class);
	
    /** The entity manager. */
    private EntityManager entityManager;
    
    /**
     * Instantiates a new Inventory dao.
     *
     * @param entityManager the entity manager
     */
    public InventoryDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
        
    /**
     * Find all.
     *
     * @return the list
     */
    public List<Inventory> findAll() {
        return entityManager.createQuery("from Inventory").getResultList();
    }
   
    /**
     * Find by id.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Inventory> findById(long id) {
    	Inventory Inventory = entityManager.find(Inventory.class, id);
        if (Inventory != null) {
        	return Optional.of(Inventory);
        } else {
        	return Optional.empty();
        }
    }

       
    /**
     * Find by name.
     *
     * @param name the name
     * @return the single
     */
    public Optional<Inventory> findByName(String name) {
    	
    	 try {    		     	
    		 Inventory Inventory = entityManager.createQuery("SELECT a FROM Inventory a WHERE a.name=:name", Inventory.class)
                .setParameter("name", name)
                .setMaxResults(1)
                .getSingleResult();
   		
    		 return Optional.of(Inventory);
    		     		 
    	 } catch (Exception e) {
     		 return Optional.empty();
    	 }    	
    }
    
    
    /**
     * Save.
     *
     * @param inventory the inventory
     * @return the optional
     */
    public Optional<Inventory> save(Inventory inventory) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(inventory);
            entityManager.getTransaction().commit();
            return Optional.of(inventory);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }
    
    /**
     * Truncate.
     */
    public void truncate() {
    	List<Inventory> inventorys = findAll();
    	Iterator<Inventory> iter = inventorys.iterator();
 	    while (iter.hasNext()) {
 	    	Inventory inventory = iter.next();
 	    	entityManager.remove(inventory); 
 	    }
     }
}