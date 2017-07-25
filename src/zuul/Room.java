package zuul;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * A "Room" represents one location in the scenery of the game.  It is
 * connected to other rooms via exits.  The exits are labelled north,
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 *
 * @author  Michael Kölling and David J. Barnes and Andrea Bowles
 * @version 2017.07.24
 * added getExit method
 * added HashMap
 * removed setExits method
 * added getLongDescription method
 * fixed class per CIS233J tutorial
 */

import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class Room
{
    //public String description;
    private String description;
    private HashMap<String, Room> exits;
    //private HashSet items;
    private Item item; //ex 6.20

    public Room northExit;
    public Room southExit;
    public Room eastExit;
    public Room westExit;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     * Room item is null by detault
     */
    public Room(String description)
    {
        this.description = description;
        exits = new HashMap<String, Room>();
        // items = new HashSet();

    }

    public Room getExit(String direction)
    {
        return exits.get(direction);
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        //return "You are " + description + ".\n" + getExitString() + ".\n" + "getItemString()";
        String descrip = "You are " + description + ".\n";
        if (item != null) { //ex 6.20
            descrip += "This room contains " + item.getDescription() + ".\n";
        }
        return descrip + getExitString();
    }

    /**
     * Return a description of the room's exits,
     * for example, "Exits: north west".
     * @return A description of the available exits.
     */

    public String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }

        return returnString;
    }

    /**
     * Return a description of the room's item,
     * @return A description of the available item.
     */

    public String getItemString()
    {
        return getItemString();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    /**
     * Put an item into the room.
     * ex 6.20
     */
    public void addItem(Item item)
    {
        //items.add(item);
        this.item = item;
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
}
