package nl.plaatsoft.micro.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class SubscriptionDao.
 * 
 * @author wplaat
 */
public class SubscriptionDao {

	/** The Constant log. */
	private static final Logger log = LogManager.getLogger( SubscriptionDao.class);
	
    /** The entity manager. */
    private EntityManager entityManager;
    
    /**
     * Instantiates a new Subscription dao.
     *
     * @param entityManager the entity manager
     */
    public SubscriptionDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
        
    /**
     * Find all.
     *
     * @return the list
     */
    public List<Subscription> findAll() {
        return entityManager.createQuery("from Subscription").getResultList();
    }
   
    /**
     * Find by id.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Subscription> findById(long id) {
    	Subscription subscription = entityManager.find(Subscription.class, id);
        if (subscription != null) {
        	return Optional.of(subscription);
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
    public Optional<Subscription> findByName(String name) {
    	
    	 try {    		     	
    		 Subscription subscription = entityManager.createQuery("SELECT a FROM Subscription a WHERE a.name=:name", Subscription.class)
                .setParameter("name", name)
                .setMaxResults(1)
                .getSingleResult();
   		
    		 return Optional.of(subscription);
    		     		 
    	 } catch (Exception e) {
     		 return Optional.empty();
    	 }    	
    }
    
    
    /**
     * Save.
     *
     * @param subscription the subscription
     * @return the optional
     */
    public Optional<Subscription> save(Subscription subscription) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(subscription);
            entityManager.getTransaction().commit();
            return Optional.of(subscription);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }
    
    /**
     * Truncate.
     */
    public void truncate() {
    	List<Subscription> subscriptions = findAll();
    	Iterator<Subscription> iter = subscriptions.iterator();
 	    while (iter.hasNext()) {
 	    	Subscription subscription = iter.next();
 	    	entityManager.remove(subscription); 
 	    }
     }
}
