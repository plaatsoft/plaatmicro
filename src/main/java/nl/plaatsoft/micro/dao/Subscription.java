package nl.plaatsoft.micro.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Status.
 * 
 * @author wplaat
 */
@Entity
@Table(name = "subscription")
public class Subscription {

	/** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
            
    /** The name. */
	private String name;
	
	/** The description. */
	private String description;
	
	/** The destination. */
	private String destination;
	
	/** The status. */
	private boolean status;
	
	/** The inventory. */
	private boolean inventory;
	
	/**
	 * Instantiates a new subscription.
	 */
	public Subscription() {
		super();
	}

	/**
	 * Instantiates a new subscription.
	 *
	 * @param name the name
	 * @param description the description
	 * @param destination the destination
	 * @param status the status
	 * @param inventory the inventory
	 */
	public Subscription(String name, String description, String destination, boolean status, boolean inventory) {
		super();
		this.name = name;
		this.description = description;
		this.destination = destination;
		this.status = status;
		this.inventory = inventory;
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Subscription [id="+id+", name="+name+", description="+description+", status="+status+", inventory="+inventory+"]";
	}
		
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	} 
	
	/**
	 * Gets the destination.
	 *
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * Sets the destination.
	 *
	 * @param destination the new destination
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * Checks if is status.
	 *
	 * @return true, if is status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * Checks if is inventory.
	 *
	 * @return true, if is inventory
	 */
	public boolean isInventory() {
		return inventory;
	}

	/**
	 * Sets the inventory.
	 *
	 * @param inventory the new inventory
	 */
	public void setInventory(boolean inventory) {
		this.inventory = inventory;
	}
}
