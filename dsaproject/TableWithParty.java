package dsaproject;

/**
 * Object to hold a party object and a table object - used when a party is matched with a table
 *
 * @author John Kubach
 * @version 2018.04.18
 */

public class TableWithParty {
    private Table table;
    private Party party;

    public TableWithParty(Table table, Party party) {
       this.table = table;
       this.party = party;
    }

    public Table getTable() {
        return table;
    }

    public Party getParty() {
        return party;
    }
}