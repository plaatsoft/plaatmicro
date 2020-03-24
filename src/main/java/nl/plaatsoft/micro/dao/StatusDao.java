package nl.plaatsoft.micro.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class StatusDao.
 * 
 * @author wplaat
 */
public class StatusDao {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( StatusDao.class);
	
    /** The entity manager. */
    private EntityManager entityManager;
    
    /**
     * Instantiates a new Status dao.
     *
     * @param entityManager the entity manager
     */
    public StatusDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
        
    /**
     * Find all.
     *
     * @return the list
     */
    public List<Status> findAll() {
        return entityManager.createQuery("from Status").getResultList();
    }
   
    /**
     * Find by id.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Status> findById(long id) {
    	Status status = entityManager.find(Status.class, id);
        if (status != null) {
        	return Optional.of(status);
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
    public Optional<Status> findByName(String name) {
    	
    	 try {    		     	
    		 Status status = entityManager.createQuery("SELECT a FROM Status a WHERE a.name=:name", Status.class)
                .setParameter("name", name)
                .setMaxResults(1)
                .getSingleResult();
   		
    		 return Optional.of(status);
    		     		 
    	 } catch (Exception e) {
     		 return Optional.empty();
    	 }    	
    }
    
    
    /**
     * Save.
     *
     * @param Status the Status
     * @return the optional
     */
    public Optional<Status> save(Status status) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(status);
            entityManager.getTransaction().commit();
            return Optional.of(status);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }
    
    /**
     * Truncate.
     */
    public void truncate() {
    	List<Status> statuses = findAll();
    	Iterator<Status> iter = statuses.iterator();
 	    while (iter.hasNext()) {
 	    	Status status = iter.next();
 	    	entityManager.remove(status); 
 	    }
     }
}