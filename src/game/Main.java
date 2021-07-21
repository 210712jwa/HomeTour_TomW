package game;

import java.util.Scanner; // Import the Scanner class
import fixtures.Room;
import project.Constants;

/**
 * This class will store the main(String[]) method for our game (and of course,
 * it will be the only class that has a main(String[]) method). This is where
 * the game-loop will go, where we'll display a prompt, collect input, and parse
 * that input
 * 
 * - The `printRoom(Player)` method will print a prompt to the console for the
 * player's current room, similar to the above image.
 * 
 * - The `collectInput()` method will use a Scanner object to collect console
 * input from the user, and then will divide that input into multiple parts.
 * Generally those parts will look like this:
 * 
 * - An action - The target of an action (if any)
 * 
 * > For example, "go east" -> "go" is the command, "east" is the target. This
 * method will break the input into a String[], and return that. - The
 * parse(String[], Player) method will take the output of the above
 * collectInput() method and a player object, and will resolve that command.
 * This can actually be simpler than it sounds - the first index of the
 * passed-in String[] should be the action, so you can switch on that and handle
 * the target differently for each case. The Player object is there so you can
 * modify it if needed (like changing the Player's currentRoom based on the
 * direction moved)
 * 
 * @author tlw8748253
 * @version 1.0
 * 
 * 
 * 20210720 - Release Version 1.0 completed.  Fully functional home tour with 9 completed rooms.
 * 				GO <direction> commands allows travel between rooms.
 * 					No travel through a barrier (walls, fences, ...)
 * 					No Access areas defined for rooms in the home tour but not defined in the program.
 * 					Secret command Teleport
 *
 */
public class Main {

	private static Scanner objScanner = new Scanner(System.in); // Create a Scanner object
	private static RoomManager objRoomMgr = new RoomManager();
	private static Player objPlayer = new Player();
	private static Display objDisplay = new Display();

	public static void main(String[] args) {
		String[] arsCommands = new String[2];
		Room objNextPlayerRoom = new Room();

		// Initialize the rooms of the house
		objRoomMgr.init();
		objPlayer.setCurrentRoom(objRoomMgr.getStartingRoom());

		objDisplay.displayOpeningScreen();
		objDisplay.displayPressEnterContiue();
		objScanner.nextLine();

		boolean bContinueTour = true;
		boolean bValidActionTarget;
		int iActionTargetDirection;

		do {

			bValidActionTarget = true;
			iActionTargetDirection = Constants.ciActionTargetError;

			if (objPlayer.getCurrentRoom().getRoomType() == Constants.ciROOM) {
				objDisplay.printRoom(objPlayer);
				System.out.println("Please Enter a command: ");
				arsCommands = collectInput();
			}

			parseInput(arsCommands, objPlayer);

			// swtich statement on Player's action

			switch (objPlayer.getActionCommand()) {

			case Constants.ciActionGO:
				
				iActionTargetDirection = processGoCommand(objPlayer.getActionTarget());
				if (iActionTargetDirection == Constants.ciActionTargetError) {
					bValidActionTarget = false;
					displayCommandError(arsCommands);
				}
				break;

			case Constants.ciActionTargetTELEPORT:
				processTeleportRequest(arsCommands[1]);
				break;

			case Constants.ciActionQUIT:
				bContinueTour = false;
				break;

			case Constants.ciActionHELP:
				objDisplay.printHelp();
				System.out.println("Press Enter to Continue.");
				objScanner.nextLine();
				break;

			default:
				displayCommandError(arsCommands);
				arsCommands[0] = null;
				arsCommands[1] = null;
				break;
			}

			if ((objPlayer.getActionCommand() == Constants.ciActionGO) && (bValidActionTarget)) {
				objNextPlayerRoom = objPlayer.getNextRoom(iActionTargetDirection);
				if (validNextRoom(objNextPlayerRoom)) {
					objPlayer.setCurrentRoom(objPlayer.getNextRoom(iActionTargetDirection));
				} else {
					if (exitCondition(objNextPlayerRoom)) {
						bContinueTour = false;
						System.out.println("\nYou have reached " + objNextPlayerRoom.getRoomName()
								+ " this is an exit condition." + "\n The home tour will now end.");
						System.out.println("Press Enter to Continue.");
						objScanner.nextLine();
					} else {
						processInvalidRoomEvent(objNextPlayerRoom);
					}

				}
			}

		} while (bContinueTour);

		objDisplay.displayClosingScreen();
		objScanner.close(); // only close when exiting the program, or System.in will be closed as well
	}

	//
	// ###
	private static int processGoCommand(int iActionTargetDirection) {
		
		switch (iActionTargetDirection) {
		case Constants.ciActionTargetNORTH:
			iActionTargetDirection = Constants.ciNORTH;
			break;
		case Constants.ciActionTargetSOUTH:
			iActionTargetDirection = Constants.ciSOUTH;
			break;
		case Constants.ciActionTargetEAST:
			iActionTargetDirection = Constants.ciEAST;
			break;
		case Constants.ciActionTargetWEST:
			iActionTargetDirection = Constants.ciWEST;
			break;
		default:
			iActionTargetDirection = Constants.ciActionTargetError;
			break;
		}

			return(iActionTargetDirection);
	}

	
	//
	// ###
	private static void processTeleportRequest(String sCommand1) {

		String sTeleportToRoom = sCommand1;
		if (sTeleportToRoom != null) {
			Room objTeleportToRoom = objRoomMgr.getRoom(sTeleportToRoom);
			if (objTeleportToRoom != null) {
				if (objTeleportToRoom.getRoomType() == Constants.ciROOM) {
					if (objTeleportToRoom.getRoomVisited()) {

						System.out.println("Room was visited, you will now teleport.");
						objPlayer.setCurrentRoom(objTeleportToRoom);
						objDisplay.displayPressEnterContiue();
						objScanner.nextLine();
					} else {
						System.out.println("You can only teleport to a room you visited.");
						objDisplay.displayPressEnterContiue();
						objScanner.nextLine();
					}
				} else {
					System.out
							.println("You can only teleport to a room.  This is not a room: [" + sTeleportToRoom + "]");
					objDisplay.displayPressEnterContiue();
					objScanner.nextLine();
				}

			} else {
				System.out.println("Room entered was not found in Home Tour room: [" + sTeleportToRoom + "]");
				objDisplay.displayPressEnterContiue();
				objScanner.nextLine();
			}
		} else {
			System.out.println("To teleport please enter a room you have visited: " + "\n\tTeleport <room name>");
			objDisplay.displayPressEnterContiue();
			objScanner.nextLine();
		}
	}

	//
	// ####
	private static void processInvalidRoomEvent(Room objNextPlayerRoom) {

		if (objNextPlayerRoom.getRoomType() == Constants.ciBARRIER) {
			System.out.println("You have encountered a barrier: [" + objNextPlayerRoom.getRoomName() + "]."
					+ "\n Enter a different direction to continue.");
		} else if (objNextPlayerRoom.getRoomType() == Constants.ciNO_ACCESS) {
			System.out.println("You have encountered an no access area: [" + objNextPlayerRoom.getRoomName() + "]."
					+ "\n Enter a different direction to continue.");
		} else {
			System.out.println("An unexpected event has happened.  \nTry entering a different direction to continue.");
		}
		System.out.println("Press Enter to Continue.");
		objScanner.nextLine();
	}

	//
	// ####
	private static boolean validNextRoom(Room objNextPlayerRoom) {
		boolean bValidNextRoom = false;
		if (objNextPlayerRoom.getRoomType() == Constants.ciROOM)
			bValidNextRoom = true;
		return (bValidNextRoom);
	}

	private static boolean exitCondition(Room objNextPlayerRoom) {
		boolean bExitCondition = false;
		if (objNextPlayerRoom.getRoomType() == Constants.ciEXIT)
			bExitCondition = true;
		return (bExitCondition);
	}

	//
	// ####
	private static void displayCommandError(String[] arsCommands) {

		System.out.println("The command or direction is not recongized.  Please try again." + "\nCommand: ["
				+ arsCommands[0] + "]  Direction: [" + arsCommands[1] + "]");
		System.out.println("Press Enter to Continue.");
		objScanner.nextLine();
	}

	//
	// ###
	/*
	 * - The `collectInput()` method will use a Scanner object to collect console
	 * input from the user, and then will divide that input into multiple parts.
	 * Generally those parts will look like this:
	 * 
	 * - An action - The target of an action (if any)
	 */
	private static String[] collectInput() {
		String sCommand[] = new String[2];
		String sUserInput = "";

		sUserInput = objScanner.nextLine();

		sUserInput = sUserInput.toUpperCase();

		int iStringAt = sUserInput.indexOf(" "); // look for 1st space

		if (iStringAt > 0) {
			// Expected user input format: command direction
			// example: GO WEST
			sCommand[0] = sUserInput.substring(0, iStringAt); // get the command to the first space
			iStringAt++;
			sCommand[1] = sUserInput.substring(iStringAt); // get the rest of the string
		} else {
			// Possible one word command
			sCommand[0] = sUserInput;
			sCommand[1] = null;
		}

		return (sCommand);
	}

	/*
	 * - The parse(String[], Player) method will take the output of the above
	 * collectInput() method and a player object, and will resolve that command.
	 * This can actually be simpler than it sounds - the first index of the
	 * passed-in String[] should be the action, so you can switch on that and handle
	 * the target differently for each case. The Player object is there so you can
	 * modify it if needed (like changing the Player's currentRoom based on the
	 * direction moved)
	 * 
	 */
	private static void parseInput(String[] arsCommands, Player objPlayer) {
		boolean bHasActionTarget = true;

		if (arsCommands[0] == null) { // No first level command was entered
			objPlayer.setActionTarget(Constants.ciActionError);
			bHasActionTarget = false;
		} else {

			if (arsCommands[0].equals((Constants.csGO))) {
				// GO command was entered check for direction
				objPlayer.setActionCommand(Constants.ciActionGO);
				if (bHasActionTarget) {
					if (arsCommands[1].equals((Constants.csNORTH))) {
						objPlayer.setActionTarget(Constants.ciActionTargetNORTH);
					}
					if (arsCommands[1].equals((Constants.csSOUTH))) {
						objPlayer.setActionTarget(Constants.ciActionTargetSOUTH);
					}
					if (arsCommands[1].equals((Constants.csEAST))) {
						objPlayer.setActionTarget(Constants.ciActionTargetEAST);
					}
					if (arsCommands[1].equals((Constants.csWEST))) {
						objPlayer.setActionTarget(Constants.ciActionTargetWEST);
					}
				}
			} // End if Constants.csGO

			if (arsCommands[0].equals((Constants.csTELEPORT))) {
				// Set Teleporter command
				objPlayer.setActionCommand(Constants.ciActionTargetTELEPORT);
			}

			if (arsCommands[0].equals((Constants.csQUIT))) {
				// Set Quit command
				objPlayer.setActionCommand(Constants.ciActionQUIT);
			}

			if (arsCommands[0].equals((Constants.csHELP))) {
				// Set HELP command
				objPlayer.setActionCommand(Constants.ciActionHELP);
			}

		}
	}

}// end class
