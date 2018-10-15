package dsaproject;

/**
 * Object to hold information about a new table
 *
 * @author John Kubach
 * @version 2018.04.18
 */

public class Table {
    private String name;
    private int seats;
    private boolean petFriendly;

    /**
     * Main Table constructor
     *
     * @param name customer name
     * @param seats number of customers in the party
     * @param petFriendly true if table will be in pet friendly section, false otherwise
     */
    public Table(String name, int seats, boolean petFriendly) {
        this.name = name;
        this.seats = seats;
        this.petFriendly = petFriendly;
    }

    /**
     * Secondary Table constructor
     * Used for checking against existing tables,
     * the number of seats is irrelevant for this purpose.
     *
     * @param name customer name
     * @param petFriendly true if table will be in pet friendly section, false otherwise
     */
    public Table(String name, boolean petFriendly) {
        this.name = name;
        this.petFriendly = petFriendly;
    }
    public String getName() {
        return name;
    }

    public int getSeats() {
        return seats;
    }

    public boolean isPetFriendly() {
        return petFriendly;
    }

    @Override
    public String toString() {
        return "Table " + name + " with " + seats + " seats\n";
    }
}
