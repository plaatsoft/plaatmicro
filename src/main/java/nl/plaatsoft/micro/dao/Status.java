package nl.plaatsoft.micro.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The Class Status.
 * 
 * @author wplaat
 */
@Entity
@Table(name = "status")
public class Status {
	
    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    
    /** The timestamp. */
    private Date timestamp;

    /** The user. */
    @OneToOne(fetch = FetchType.EAGER)
    private Inventory inventory;
        
	/**
     * Instantiates a new status.
     */
    public Status() {    	  
    }
    
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		return "Status [id=" + id + ", timestamp=" + simpleDateFormat.format(timestamp) + ", state=" + state + "," + inventory+"]";
	}
		
    /**
     * Instantiates a new status.
     *
     * @param timestamp the timestamp
     * @param state the state
     * @param inventory the inventory
     */
    public Status( Date timestamp, int state, Inventory inventory) {
		super();
		this.timestamp = timestamp;
		this.state = state;
		this.inventory = inventory;
	}

    /**
     * Gets the inventory.
     *
     * @return the inventory
     */
    public Inventory getInventory() {
		return inventory;
	}

	/**
	 * Sets the inventory.
	 *
	 * @param inventory the new inventory
	 */
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
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

	/** The state. */
	private int state;

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp.
	 *
	 * @param timestamp the new timestamp
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(int state) {
		this.state = state;
	}
}
