package nl.plaatsoft.micro.dao;

import javax.persistence.Column;
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
	
	@Column(name = "everything")
    private boolean all;
	
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
	 * @param all the all
	 * @param inventory the inventory
	 */
	public Subscription(String name, String description, Boolean all) {
		super();
		this.name = name;
		this.description = description;
		this.all = all;
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Subscription [id="+id+", name=" + name + ", description=" + description + ", all=" + all + "]";
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
	
	 public boolean getAll() {
		return all;
	}

	public void setAll(boolean all) {
		this.all = all;
	}
}
