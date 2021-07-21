package game;

import fixtures.Room;

/**
 * 
 * This class represents the player moving through these rooms. The Player class has these properties:
 * - `Room currentRoom` : the room the player is currently in.
 * 
 * @author tlw8748253
 *
 */
public class Player {
	private String sClass="Player(): ";
	private Room objCurrentRoom = new Room();
	private int iActionCommand = 0;
	private int iActionTarget = 0;
	
	public Player() {}
	public Player(Room objCurrentRoom) {
		String sMethod = "Player(): ";
		this.objCurrentRoom = objCurrentRoom;
		}
	
	public Room getNextRoom(int iDirection) {
		return(objCurrentRoom.getExit(iDirection));		
	}
	
	
	public Room getCurrentRoom() {return(objCurrentRoom);}
	public int getActionCommand() {return(iActionCommand);}
	public int getActionTarget() {return(iActionTarget);}
	public String[] getRoomLongDescriptionArray() {return(objCurrentRoom.getRoomLongDescriptionArray());}
	public String[] getRoomStoryboardArray() {return(objCurrentRoom.getRoomStoryboardArray());}
	
	public void setCurrentRoom(Room objCurrentRoom) {this.objCurrentRoom = objCurrentRoom;}
	public void setActionCommand(int iActionCommand) {this.iActionCommand = iActionCommand;}
	public void setActionTarget(int iActionTarget) {this.iActionTarget = iActionTarget;}
	public void setRoomVisited() {objCurrentRoom.setRoomVisited();}
	
	public boolean getRoomVisited() {return(objCurrentRoom.getRoomVisited());}
	
	
	public void printLn() {
		System.out.println(sClass + "objCurrentRoom: " + this.objCurrentRoom.toString());
	}
	public String toString() {return(sClass + this.objCurrentRoom.toString());}

}
