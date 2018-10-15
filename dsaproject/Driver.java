package dsaproject;
import java.util.Scanner;

/**
 * Main class
 * <p>
 *     This is a pet friendly restaurant using Linked List data structures.
 * </p>
 */
public class Driver {
    Scanner in = new Scanner(System.in);
public static void main(String[] args) {
        int userSelection;
        Driver ui = new Driver();

        ListCDLSBased<Table> availableTables = new ListCDLSBased<>();
        ListCDLSBased<Party> waitingParties = new ListCDLSBased<>();
        ListCDLSBased<TableWithParty> occupiedTables = new ListCDLSBased<>();

        ui.askForPet(availableTables);
        ui.askForNonPet(availableTables);
        ui.sortAvailableTables(availableTables);
        ui.listOptions();

        do {
            userSelection = ui.menuSelection();
            System.out.println(userSelection);
            switch (userSelection) {
                case 0:
                    ui.exitProgram();
                    break;
                case 1:
                    ui.addParty(waitingParties, occupiedTables);
                    break;
                case 2:
                    ui.seatParty(waitingParties, availableTables, occupiedTables);
                    break;
                case 3:
                    ui.partyLeaves(waitingParties, availableTables, occupiedTables);
                    break;
                case 4:
                    ui.addTable(availableTables);
                    break;
                case 5:
                    ui.removeTable(availableTables, occupiedTables);
                    break;
                case 6:
                    ui.displayAvailableTables(availableTables);
                    break;
                case 7:
                    ui.displayWaitingParties(waitingParties);
                    break;
                case 8:
                    ui.displayPartiesAtTables(occupiedTables);
                    break;
            }
        } while(userSelection !=0);
    }



    /*
     *
     * ADD METHODS
     *
     */

    /**
     * Add a party to the waiting line
     *
     * @param waitingParties list of parties waiting
     * @param occupiedTables list of parties at tables
     */
    public void addParty(ListCDLSBased<Party> waitingParties, ListCDLSBased<TableWithParty> occupiedTables) {
        Party newParty;
        String name;
        int seats;
        int index;
        char yesNo;
        boolean pets = false;
        boolean partyExists;

        do {
            System.out.print(">> Enter customer name: ");
            name = in.next().trim();
            System.out.println(name);
            index = checkParty(waitingParties, name);
            if (index >=0) {
                System.out.println("Party already exists!");
                partyExists = true;
            }
            else {
                index = checkOccupiedTable(occupiedTables, name);
                if (index >= 0) {
                    System.out.println("Party already exists!");
                    partyExists = true;
                }
                else {
                    partyExists = false;
                }
            }
        } while (partyExists);

        System.out.print(">> Enter number of seats: ");
        seats = Integer.parseInt(in.next().trim());
        System.out.println(seats);

        System.out.print(">> Does your party have pets? (Y/N)");
        yesNo = in.next().trim().toUpperCase().charAt(0);
        System.out.println(yesNo);
        if (yesNo == 'Y') {
            pets = true;
        }

        newParty = new Party(name, seats, pets);
        waitingParties.add(waitingParties.size(), newParty);
    }


    /**
     * Add a table to list of available tables
     * @param availableTables list of available tables
     */
    public void addTable(ListCDLSBased<Table> availableTables) {
        String name;
        int seats;
        int index;
        char yesNo;
        boolean pets = false;
        boolean tableExists;
        Table newTable;

        System.out.print(">> To which section would you like to add this to? (P/N)");
        yesNo = in.next().trim().toUpperCase().charAt(0);
        System.out.println(yesNo);
        if ( yesNo == 'P') {
            pets = true;
        }

        do {
            System.out.print(">> Enter table name: ");
            name = in.next();
            System.out.println(name);
            newTable = new Table(name, pets);
            index = checkTable(availableTables, newTable);
            if (index >= 0) {

                System.out.println("Table already exists!");
                tableExists = true;
            }
            else {
                tableExists = false;
            }

        } while(tableExists);

        System.out.print(">> Enter number of seats: ");
        seats = Integer.parseInt(in.next().trim());
        System.out.println(seats);

        newTable = new Table(name, seats, pets);
        availableTables.add(availableTables.size(), newTable);
        sortAvailableTables(availableTables);
    }


    /**
     * Match a party with a table that has an appropriate amount of seats to contain the party members. Add this pair to the occupiedTables list
     *
     * @param waitingParties list of parties in line
     * @param availableTables list of un occupied tables
     * @param occupiedTables list of tables with parties
     */
    public void seatParty (ListCDLSBased<Party> waitingParties, ListCDLSBased<Table> availableTables, ListCDLSBased<TableWithParty> occupiedTables) {
        Table searchTable;
        Party searchParty;
        boolean seated = false;
        int size = waitingParties.size();

        if (size == 0) {
            System.out.println("No parties to seat!");
        }
        else {
            for (int i = 0; !seated && i < size; i++) {
                searchParty = waitingParties.get(i);
                searchTable = findTable(availableTables, searchParty);

                if (searchTable != null) {
                    System.out.println("Serving " + searchParty.toString() + " at " + searchTable);
                    occupiedTables.add(occupiedTables.size(), new TableWithParty(searchTable, searchParty));
                    waitingParties.remove(i);
                    seated = true;
                }
                else {
                    System.out.println(searchParty.toString() + " could not be seated");
                }
            }
        }
    }




    /*
     *
     * REMOVE METHODS
     *
     */

    /**
     * If possible, remove requested party with table from occupiedTables list. Add table back into availableTables list.
     *
     * @param waitingParties list of parties in line
     * @param availableTables list of unoccupied tables
     * @param occupiedTables list of tables with parties
     */
    public void partyLeaves(ListCDLSBased<Party> waitingParties, ListCDLSBased<Table> availableTables, ListCDLSBased<TableWithParty> occupiedTables) {
        String partyName;
        int index;
        System.out.print("Enter the name of the customer that wants to leave: ");
        partyName = in.next().trim();
        System.out.println(partyName);

        index = checkParty(waitingParties, partyName);

        if (index >= 0) {
            System.out.println("Customer " + partyName + " is not being served but waiting to be seated");
        }
        else {
            index = checkOccupiedTable(occupiedTables, partyName);

            if (index >= 0) {
                TableWithParty foundTable = occupiedTables.get(index);
                System.out.println(foundTable.getTable().toString() + " has been freed");
                System.out.println(foundTable.getParty().toString() + " is leaving the restaurant" );

                availableTables.add(0, foundTable.getTable());
                occupiedTables.remove(index);
                sortAvailableTables(availableTables);
            }
            else {
                System.out.println("Party name not found!");
            }
        }
    }


    /**
     * If possible, remove requested table from either availableTables list or occupiedTables list
     *
     * @param availableTables list of unoccupied tables
     * @param occupiedTables list of tables with parties
     */
    public void removeTable(ListCDLSBased<Table> availableTables, ListCDLSBased<TableWithParty> occupiedTables) {
        Table searchTable;
        String tableName;
        char yesNo;
        boolean searchPets = false;
        int tableIndex;

        System.out.println(">> You are now removing a table");
        System.out.print(">> To which section would you like to remove this from? (P/N)");
        yesNo = in.next().trim().toUpperCase().charAt(0);
        System.out.println(yesNo);
        if ( yesNo == 'P') {
            searchPets = true;
        }

        System.out.print(">> Enter table name: ");
        tableName = in.next().trim();
        System.out.println(tableName);

        tableIndex = checkOccupiedTable(occupiedTables, tableName);
        if (tableIndex != -1)
        {
            System.out.println("Can't remove table that is in use!");
        }
        else {
            searchTable = new Table(tableName, searchPets);
            tableIndex = checkTable(availableTables, searchTable);
            if (tableIndex >= 0) {
                System.out.println("Table " + tableName + " has been removed");

                availableTables.remove(tableIndex);
                sortAvailableTables(availableTables);
            }
            else {
                System.out.print("Table not found in ");
                if (searchPets) {
                    System.out.print("pet friendly section\n");
                }
                else {
                    System.out.print("non-pet friendly section\n");
                }
            }
        }
    }



    /*
     *
     * DISPLAY METHODS
     *
     */

    /**
     * Display the contents of waitingParties list.
     *
     * @param waitingParties list of parties in line
     */
    public void displayWaitingParties (ListCDLSBased<Party> waitingParties) {
        if (waitingParties.size() == 0) {
            System.out.println("No parties waiting!");
        }
        else {
            System.out.println(waitingParties.toString());
        }
    }


    /**
     * Print contents of availableTables list
     * This output comes in two parts,
     * the tables in the pet friendly section.
     * and the tables in the non-pet friendly section.
     *
     * @param availableTables list of unoccupied tables
     */
    public void displayAvailableTables(ListCDLSBased<Table> availableTables) {
        Table currentTable;
        int petTables = 0;
        int noPetTables = 0;
        int size = availableTables.size();
        String petResult = "";
        String noPetResult = "";

        if (size == 0) {
            System.out.println("No tables are available!");
        }
        else {
            for (int i = 0; i < size; i++) {
                currentTable = availableTables.get(i);
                if (currentTable.isPetFriendly()) {
                    petResult += currentTable.toString();
                    petTables++;
                } else {
                    noPetResult += currentTable.toString();
                    noPetTables++;
                }
            }

            System.out.println("The following " + petTables + " tables are available in the pet-friendly section:");
            System.out.println(petResult);

            System.out.println("The following " + noPetTables + " tables are available in the non-pet-friendly section:");
            System.out.println(noPetResult);
        }
    }


    /**
     * Print contents of occupiedTables list
     * This output comes in two parts,
     * the tables in the pet friendly section.
     * and the tables in the non-pet friendly section.
     *
     * @param occupiedTables list of tables with seated parties
     */
    public void displayPartiesAtTables(ListCDLSBased<TableWithParty> occupiedTables) {
        TableWithParty currentOccupiedTable;
        String petResult = "";
        String noPetResult = "";
        int size = occupiedTables.size();

        if (size == 0) {
            System.out.println("No parties are seated!");
        }
        else {
            for (int i = 0; i < size; i++) {
                currentOccupiedTable = occupiedTables.get(i);
                if (currentOccupiedTable.getTable().isPetFriendly()) {
                    petResult += currentOccupiedTable.getParty().toString() + " at " + currentOccupiedTable.getTable().toString();
                } else {
                    noPetResult += currentOccupiedTable.getParty().toString() + " at " + currentOccupiedTable.getTable().toString();
                }

            }
            System.out.println("The following " + "tables are occupied in the pet-friendly section:");
            System.out.println(petResult);

            System.out.println("The following " + "tables are occupied in the non-pet-friendly section:");
            System.out.println(noPetResult);
        }
    }




    /*
     *
     *  SORT METHOD
     *
     */

    /**
     * Sort the list of available tables
     * This is used for two purposes,
     * Formatting of output,
     * and making sure a party is matched with a table that has the minimum number of required seats.
     *
     * @param availableTables list of unoccupied tables
     */
    public void sortAvailableTables(ListCDLSBased<Table> availableTables) {
        Table temp;
        int min;
        int size = availableTables.size();
        for (int i = 0; i < size - 1; i++) {
            min = i;
            for (int j = (i + 1); j < size; j++) {
                if (availableTables.get(j).getSeats() < availableTables.get(min).getSeats()) {
                    min = j;
                }
            }
            if (i != min) {
                temp = availableTables.get(i);
                availableTables.add(i, availableTables.get(min));
                availableTables.remove(i+1);
                availableTables.add(min, temp);
                availableTables.remove(min+1);
            }
        }
    }




    /*
     *
     * HELPER METHODS
     *
     */

    /**
     * Iterate through the waitingParties list to search for a given string
     *
     * @param waitingParties list of parties in line
     * @param name name to be compared against
     * @return index if a match is found, otherwise -1
     */
    public int checkParty(ListCDLSBased<Party> waitingParties, String name) {
        Party currentParty;
        int size = waitingParties.size();

        for (int i = 0; i < size; i++) {
            currentParty = waitingParties.get(i);
            if (name.equals(currentParty.getName())) {
                return i;
            }
        }

        return -1;
    }


    /**
     * Iterate through the availableTables list to search for a given string
     *
     * @param availableTables list of unoccupied tables
     * @param name name of table to search for
     * @return index if match is found, otherwise -1
     */
    public int checkTable(ListCDLSBased<Table> availableTables, Table name) {
        Table currentTable;
        int size = availableTables.size();
        for (int i = 0; i < size; i++) {
            currentTable = availableTables.get(i);
            if (currentTable.getName().equals(name.getName()) && currentTable.isPetFriendly() == name.isPetFriendly()) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Iterate through the occupiedTables list to search for a party name
     *
     * @param occupiedTables list of tables with parties
     * @param name name of party to search for
     * @return index is match is found, otherwise -1
     */
    public int checkOccupiedTable(ListCDLSBased<TableWithParty> occupiedTables, String name) {
        TableWithParty currentOccupiedTable;
        int size = occupiedTables.size();

        for (int i = 0; i < size; i++) {
            currentOccupiedTable = occupiedTables.get(i);
            if (name.equals(currentOccupiedTable.getParty().getName()) || name.equals(currentOccupiedTable.getTable().getName())) {
                return i;
            }
        }
        return -1;
    }


    /**
     * Find a suitable table for a given party
     *
     * @param availableTables list of unoccupied tables
     * @param partyToSeat Party object to be seated
     * @return Table object if successful, otherwise null
     */
    public Table findTable(ListCDLSBased<Table> availableTables, Party partyToSeat) {
        Table currentTable;
        int size = availableTables.size();
        for (int i = 0; i < size; i++) {
            currentTable = availableTables.get(i);
            if (currentTable.getSeats() >= partyToSeat.getSeats() && (currentTable.isPetFriendly() == partyToSeat.isHasPets()) ) {
                availableTables.remove(i);
                sortAvailableTables(availableTables);
                return currentTable;
            }
        }
        return null;
    }


    /**
     * Create a list of unoccupied tables at startup
     *
     * @param availableTables list of unoccupied tables
     * @param numberOfTables user requested number of tables
     * @param hasPets true if called from askForPet, false if called from askForNonPet
     */
    public void initialPopulation(ListCDLSBased<Table> availableTables, int numberOfTables, boolean hasPets) {
        Table newTable;
        String name;
        int seats;
        int index;

        while (numberOfTables > 0) {
            System.out.print(">> Enter table name: ");
            name = in.next().trim();
            System.out.println(name);
            newTable = new Table(name, 0, hasPets);

            index = checkTable(availableTables, newTable);
            if (index >=0) {

                System.out.println("Table already exists!");
            }
            else{
                System.out.print(">> Enter number of seats ");
                seats = Integer.parseInt(in.next());
                System.out.println(seats);

                newTable = new Table(name, seats, hasPets);
                availableTables.add(availableTables.size(), newTable);
                numberOfTables--;
            }
        }
    }


    /**
     * Get selection for menu items
     *
     * @return integer inputted by the user
     */
    public int menuSelection() {
        System.out.print("Make your menu selection now: ");
        return Integer.parseInt(in.next().trim());
    }




    /*
     *
     * ONE TIME METHODS
     *
     */

    /**
     * Ask user for initial population of pet friendly tables
     *
     * @param availableTables list of unoccupied tables
     */
    public void askForPet(ListCDLSBased<Table> availableTables) {
        int numberOfTables;

        System.out.print("How many tables does your pet friendly section have? ");
        numberOfTables = Integer.parseInt(in.next());
        System.out.println(numberOfTables);

        initialPopulation(availableTables, numberOfTables, true);
    }


    /**
     * Ask user for initial population of non-pet friendly tables
     *
     * @param availableTables list of unoccupied tables
     */
    public void askForNonPet(ListCDLSBased<Table> availableTables) {
        int numberOfTables;

        System.out.print("How many tables does your non-pet friendly section have? ");
        numberOfTables = Integer.parseInt(in.next());
        System.out.println(numberOfTables);

        initialPopulation(availableTables, numberOfTables, false);
    }


    /**
     * List menu options
     */
    public void listOptions(){
        System.out.println("Select from the following menu:");
        System.out.println("    0. Close the restaurant");
        System.out.println("    1. Customer party enters the restaurant");
        System.out.println("    2. Customer party is seated and served");
        System.out.println("    3. Customer party leaves the restaurant");
        System.out.println("    4. Add a table");
        System.out.println("    5. Remove a table");
        System.out.println("    6. Display available tables");
        System.out.println("    7. Display info about waiting customer parties");
        System.out.println("    8. Display info about customer parties being served");
    }


    /**
     * Close restaurant
     */
    void exitProgram(){
        System.out.println("We are closing the restaurant...Good Bye");
    }
}
