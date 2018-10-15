package dsaproject;

/**
 * Object to hold information about a new customer
 *
 * @author John Kubach
 * @version 2018.04.18
 */


public class Party {
    private String name;
    private int seats;
    private boolean hasPets;

/**
 * Party constructor
 *
 * @param name customer name
 * @param seats number of customers in the party
 * @param hasPets true if party has pets, false otherwise
 */
    public Party(String name, int seats, boolean hasPets) {
        this.name = name;
        this.seats = seats;
        this.hasPets = hasPets;
    }

    public String getName() {
        return name;
    }

    public int getSeats() {
        return seats;
    }

    public boolean isHasPets() {
        return hasPets;
    }

    @Override
    public String toString() {
        String result = "Customer " + name + " party of " + seats;
        if(hasPets)
            result+= "(Pet)";
        else
            result+= "(No Pet)";
        return result;
    }
}
