package fixtures;

import project.*;

/**
 * 
 *  This class represents a room in the house. It will extend fixtures.Fixture, and so will inherit the descriptive properties. 
 *   The Room will also have the following properties:
 *   
 *   - `Room[] exits` : the rooms adjacent to this one. You might decide that a room in a particular direction always uses a 
 *   certain index, e.g. a north exit always goes in index 0, an east exit always goes in index 1, etc. If so, then the size of 
 *   this array depends on how many directions you want to support.
 *   
 *   The Room class should also have a constructor that accepts a name, shortDescription, and longDescription. You might also 
 *   find it convenient to create a getter not just for all the exits, but for a particular exit given a direction.
 *   
 * @author tlw8748253
 *
 */
public class Room extends Fixture {
	private String sClass = "Room(): ";

	private Room arExits[] = new Room[Constants.ciMaxExits]; //storage for this Room's exits

	
	//###
	public Room() {// empty constructor
		super();
//		for (int iCtr = 0; iCtr < Constants.ciMaxExits; iCtr++)
	//		arExits[iCtr] = new Room();
	
	} 
	//###
	public Room(int iRoomType, String sRoomName, String sRoomShortDescription, String sRoomLongerDescription) {
		super(iRoomType, sRoomName, sRoomShortDescription, sRoomLongerDescription);	
	}

	
	//###setters
	public void setRoomName(String sFixtureName) {super.setFixtureName(sFixtureName);}
	public void setRoomShortDescription(String sFixtureShortDescription) {super.setFixtureName(sFixtureShortDescription);}
	public void setRoomLongerDescription(String sFixtureLongDescription) {super.setFixtureName(sFixtureLongDescription);}
	public void setRoomType(int iRoomType) {super.setFixtureType(iRoomType);}
	public void setRoomLongDescriptionArray(String[] arsRoomLongDescriptionArray) {super.setFixtureLongDescriptionArray(arsRoomLongDescriptionArray);}
	public void setRoomStoryboardArray(String[] arsRoomStoryboardArray) {super.setFixtureStoryboardArray(arsRoomStoryboardArray);}
	//
	//###
	public void setRoomVisited() {
		if(super.getFixtureType() == Constants.ciROOM) //set only if it is a room
			super.setFixtureVisited();
		}
	
	//### Set individual exit for this room
	public void setExit(int iDirection, Room objExit) {
		this.arExits[iDirection] = objExit;
		this.arExits[iDirection].printRoom();
	}
	
	public void setExits(Room arExits[]) {
		String sMethod = "setExits() ";
		boolean bDebug = true;
		
		try
		{
		Trace.trace(sClass, sMethod, "Adding exits to: [" + this.getFixtureName() + "]");
		Trace.debug(bDebug, sClass, sMethod, "this.arExits[Constants.ciNORTH]: xxx[" + arExits[Constants.ciNORTH].exitsToString() + "]");
		
		//loading this rooms exits of room objects
		this.arExits[Constants.ciNORTH] = arExits[Constants.ciNORTH];
		this.arExits[Constants.ciSOUTH] = arExits[Constants.ciSOUTH];
		this.arExits[Constants.ciEAST] = arExits[Constants.ciEAST];
		this.arExits[Constants.ciWEST] = arExits[Constants.ciWEST];
		
		Trace.debug(bDebug, sClass, sMethod, "this.arExits[Constants.ciNORTH]: [" + this.arExits[Constants.ciNORTH].exitsToString() + "]");

		}
		catch(java.lang.NullPointerException objExc)
		{
			System.out.println("this.getFixtureName(): [" + this.getFixtureName() + "]");
			System.out.println(sClass + sMethod + "NullPointerException: [" + objExc.getMessage() + "]");

		}
		
	}
	
	
	
	//###getters
	public String getRoomName() {return (super.getFixtureName());}
	public String getRoomShortDescription() {return (super.getFixtureShortDescription());}
	public String getRoomLongerDescription() {return (super.getFixtureLongerDescription());}
	public int getRoomType() {return (super.getFixtureType());}
	public boolean getRoomVisited() {return(super.getFixtureVisited());}	
	
	public Room getExit(int iExitDirection) {return (arExits[iExitDirection]);}
	public Room[] getExits() {return (arExits);}
	public String[] getRoomLongDescriptionArray() {return(super.getFixtureLongDescriptionArray());}
	public String[] getRoomStoryboardArray() {return(super.getFixtureStoryboardArray());}
	
	

	//###
	public void printRoom() {
		System.out.println(toString());
	}
	
	//###
	public String toString() {
		String sRetString = "";
		String sFixture = super.toString();
		String sExits = exitsToString();
		
		sRetString = "Room details: [" + sFixture + "\n" + sExits + "]";
		
		return(sRetString);		
	}	
	
	//###
	public String exitsToString() {
		String sExits = "Room Exits: ";
		
		
		for (int iCtr=0; iCtr<Constants.ciMaxExits; iCtr++)
		{
			if (arExits[iCtr] != null) {
			sExits += "\n\t[" + Constants.csaDirections[iCtr] + ": " + arExits[iCtr].getFixtureName() + "]";
			}
			else {
				sExits += "\n\t[" + Constants.csaDirections[iCtr] + ": " + "[null]";
			}
		}
		
		return(sExits);		
	}

}
