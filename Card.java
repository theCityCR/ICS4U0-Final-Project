package culminating;

/**
 * @author Alex Li
 * 
 *         Teacher: Mrs. Krasteva
 * 
 *         Date: 2023-05-15
 * 
 *         This class is the cards.
 */

public class Card {
	/**
	 * the information
	 */
    private String info;
    
    /**
     * whether read
     */
    private Boolean read;
    
    /**
     * Creates a new Card object
     * 
     * @param info	information
     */
    public Card(String info){
        this.info = info;
        read = false;
    }

    /**
     * Read card
     */
    public void hasRead(){
        read = true;
    }

    /**
     * Whether read
     * 
     * @return	whether card has been read
     */
    public Boolean getRead(){
        return read;
    }
    
    /**
     * Gets the information
     * 
     * @return	the information
     */
    public String getInfo(){
        return info;
    }
}
