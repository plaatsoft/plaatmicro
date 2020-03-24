package nl.plaatsoft.micro.dao;

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
	
    /** The sid. */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long sid;
    
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
		return "Status [sid=" + sid + ", timestamp=" + timestamp + ", state=" + state + "]";
	}
		
    /**
     * Instantiates a new status.
     *
     * @param sid the sid
     * @param timestamp the timestamp
     * @param state the state
     * @param inventory
     */
    public Status(long sid, Date timestamp, int state, Inventory inventory) {
		super();
		this.sid = sid;
		this.timestamp = timestamp;
		this.state = state;
		this.inventory = inventory;
	}

    public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	/**
	 * Gets the sid.
	 *
	 * @return the sid
	 */
	public long getSid() {
		return sid;
	}

	/**
	 * Sets the sid.
	 *
	 * @param sid the new sid
	 */
	public void setSid(long sid) {
		this.sid = sid;
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
