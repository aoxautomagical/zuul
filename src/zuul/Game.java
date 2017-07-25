package zuul;

/**
 *  This class is the main class of the "World of Zuul" application.
 *  "World of Zuul" is a very simple, text based adventure game.  Users
 *  can walk around some scenery. That's all. It should really be extended
 *  to make it more interesting!
 *
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 *
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 *
 * @author  Michael Kölling and David J. Barnes and Andrea Bowles
 * @version 2017.07.13
 * added printLocationInfo method
 * eliminated duplicate code
 * added look method
 * fixed makeBed method to print reminder
 * fixed class per CIS233J tutorial
 */

public class Game
{
    private Parser parser;
    private Room currentRoom;

    /**
     * Create the game and initialise its internal map.
     */
    public Game()
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room bedroom, bathroom, kitchen, dining, office, living, garage;

        // create the rooms
        bedroom = new Room("in the master bedroom of the house");
        bathroom = new Room("in a bathroom");
        kitchen = new Room("in the kitchen");
        dining = new Room("in the dining room");
        office = new Room("in the office");
        living = new Room("in the living room");
        garage = new Room("in the garage");

        // initialise room exits
        bedroom.setExit("north", bathroom);
        bedroom.setExit("west", living);
        bathroom.setExit("east", bedroom);
        kitchen.setExit("east", living);
        kitchen.setExit("south", living);
        dining.setExit("north", garage);
        dining.setExit("south", living);
        dining.setExit("west", kitchen);
        office.setExit("west", living);
        living.setExit("north", dining);
        living.setExit("east", bedroom);
        living.setExit("south", office);
        living.setExit("west", kitchen);
        garage.setExit("south", dining);

        // create the items
        Item apple, computer;
        apple = new Item("rotten old apple", .25);
        computer = new Item ("Surface Pro 3", 10);
        kitchen.addItem(apple);
        office.addItem(computer);

        currentRoom = living;  // start game living
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play()
    {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();

        printLocationInfo();

        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command)
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("makeBed")) {
            makeBed();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp()
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at home.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.getCommandList();
    }

    /**
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command)
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            printLocationInfo();
            System.out.println();
        }
    }

    /**
     * Prints the room description and room exits.
     */
    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
        System.out.println();
    }

    /**
     * Prints reminder to make the bed.
     */
    private void makeBed()
    {
        System.out.println("Don't forget to make the bed before you leave");
        System.out.println();
    }

    /**
     * Print current location information
     */
    private void printLocationInfo() {
        System.out.println(currentRoom.getLongDescription());
        System.out.println();
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command)
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
