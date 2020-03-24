package nl.plaatsoft.micro.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class Inventory.
 * 
 * @author wplaat
 */
@Entity
@Table(name = "inventory")
public class Inventory {

	/** The iid. */
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long iid;
	
	/** The name. */
	private String name;
	
	/** The description. */
	private String description;
	
	/**
	 * Instantiates a new inventory.
	 */
	public Inventory() {
		super();
	}

	/**
	 * Instantiates a new inventory.
	 *
	 * @param name the name
	 * @param description the description
	 */
	public Inventory(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Inventory [iid=" + iid + ", name=" + name + ", description=" + description + "]";
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
	 * Gets the iid.
	 *
	 * @return the iid
	 */
	public long getIid() {
		return iid;
	}

	/**
	 * Sets the iid.
	 *
	 * @param iid the new iid
	 */
	public void setIid(long iid) {
		this.iid = iid;
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
}
