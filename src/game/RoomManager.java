package game;

import project.*;
import fixtures.*;

/**
 * This class will be responsible for "loading" our rooms into memory. When game.Main is executed, it will invoke the init() 
 * method in this class that will instantiate all our Room objects, link them together as exits, and designate a startingRoom.
 * 
 * `Room startingRoom` : the room a player should start in.Room[] rooms : all the rooms in the house.
 * 
 * @author tlw8748253
 *
 */
public class RoomManager {
	private String sClass = "RoomManager(): ";
	private boolean bClassDebug = false;
		
	private Room arrRooms[] = new Room[Constants.ciNumberOfRoom];
	private Room objStartingRoom = new Room();
	
	private int iNumberOfRoomsInArray = 0;  //variable created for debugging purposes
	
	//variable for general use internally
	private String sRoomName = "";
	private String sShortDesc = "";
	private String sLongerDesc = "";
	
	
	
	// array locations for multi use boundary room
	private int iWallLoc = 0;
	private int iGlassWallLoc=0;
	private int iOutsideLoc = 0;
	private int iWoodFenceLoc = 0;
	
	//Room types
	private int iRoomTypeRoom = Constants.ciROOM;
	private int iRoomTypeBarrier = Constants.ciBARRIER;

	
	
	public void init() {
		String sMethod = "init(): ";
		
		Trace.trace(sClass, sMethod);
		
		createAllRoomsCtrl();
		addRoomExitsCtrl();		
		printAllRooms();
		
	}
	
	//###
	//static method to get starting room location for objects that do
	//no need to create a manager object.    
	public Room getStartingRoom() {return(objStartingRoom);}
	public Room getRoom(String sRoomName) {
		Room objRoom = null;
		
		//System.out.println("getRoom() sRoomName: " + sRoomName);
		
		int iLocated = locateRoom(sRoomName);
		if (iLocated != -1)
			objRoom = arrRooms[iLocated];
		
		return(objRoom);
	}
	
	//### Utility method to find room in the array
	private int locateRoom(String sRoomName) {
		String sMethod = "locateRoom(): ";
		
		Trace.trace(sClass, sMethod, "sRoomName: [" + sRoomName + "]");
		int iRetCtr = -1; //set to not found
		
		for (int iCtr=0; iCtr<Constants.ciNumberOfRoom; iCtr++)
		{
		  if(arrRooms[iCtr].getRoomName().equalsIgnoreCase(sRoomName))
		  {
			  //found the array position, break loop and return counter
			  iRetCtr = iCtr;
			  break;
		  }
		}
		Trace.debug(bClassDebug, sClass, sMethod, "sRoomName: [" + sRoomName + "] iRetCtr: [" + iRetCtr + "]");
		
		return (iRetCtr);
	}
	
	//### Utility method to locate repeatedly used Rooms in the arrary
	public void setCommonRoomValues() {
		String sMethod = "setCommonRoomValues(): ";
		
		Trace.trace(sClass, sMethod);

		iWallLoc = locateRoom(Constants.csWALL);
		iGlassWallLoc = locateRoom(Constants.csGLASS_WALL);
		iOutsideLoc = locateRoom(Constants.csOUTSIDE);;
		iWoodFenceLoc = locateRoom(Constants.csWOOD_FENCE);
		
	}
	
	//## Utility method to add the room exits to a given room
	public void addRoomExits(int iRoom, int iNorthExit, int iSouthExit, int iWestExit, int iEastExit) {
		String sMethod = "addRoomExits(): ";
		boolean bDebug = false;
		String sMsg = "iRoom: [" + iRoom + "] iNorthExit: [" + iNorthExit + "] iSouthExit: [" + iSouthExit + "] iWestExit: [" + iWestExit + "] iEastExit: [" + iEastExit +"]";
		
		Trace.trace(sClass, sMethod, sMsg);

		Room arExits[] = new Room[Constants.ciMaxExits];
		
		arExits[Constants.ciNORTH] = arrRooms[iNorthExit];
		
		sMsg = "arrRooms[iNorthExit]: [" + arrRooms[iNorthExit].toString() + "]";
		sMsg = "arExits[Constants.ciNORTH]: [" + arExits[Constants.ciNORTH].toString() + "]";
		
		arExits[Constants.ciSOUTH] = arrRooms[iSouthExit];
		arExits[Constants.ciWEST] = arrRooms[iWestExit];
		arExits[Constants.ciEAST] = arrRooms[iEastExit];
		
		if(bDebug) for(int iCtr=0; iCtr<Constants.ciMaxExits; iCtr++) {
					sMsg = "arExits[" + iCtr + "].toString(): [" + arExits[iCtr].toString() + "]";
					Trace.debug((bClassDebug || bDebug), sClass, sMethod, sMsg);
		}
		
		arrRooms[iRoom].setExits(arExits);
		
	}
	
	//
	//### Controller method for adding the room exits to rooms defined in arrRooms[] data structure
	private void addRoomExitsCtrl() {
		String sMethod = "addRoomExits(): ";
		
		Trace.trace(sClass, sMethod);

		setCommonRoomValues(); // use class variables for some common items
		
		//Calling parameters are the array element numbers for calling:
		//addRoomExits(Room to set, North of room, South of room, West of room, East of room);		
		
		//  Foyer: north ==> music room, south ==> dining room, west ==> hallway, east ==> outside
		addRoomExits(locateRoom(Constants.csFOYER),locateRoom(Constants.csMUSIC),locateRoom(Constants.csDINING),locateRoom(Constants.csHALLWAY),iOutsideLoc);		
		Trace.debug(bClassDebug, sClass, sMethod, this.arrRooms[locateRoom(Constants.csFOYER)].toString());
		
		//  Dining: north ==> foyer, south ==> boundary wall, west ==> kitchen, east ==> conservatory
		addRoomExits(locateRoom(Constants.csDINING), locateRoom(Constants.csFOYER), iWallLoc, locateRoom(Constants.csKITCHEN), locateRoom(Constants.csCONSERVATORY));

		//  Music: north ==> boundary wall, south ==> foyer, west ==> den, east ==> patio
		addRoomExits(locateRoom(Constants.csMUSIC), iWallLoc, locateRoom(Constants.csFOYER), locateRoom(Constants.csDEN), locateRoom(Constants.csPATIO));
		
		//  Hallway: north ==> den, south ==> bathroom (NO ACCESS), west ==> Foyer, east ==> stairs
		addRoomExits(locateRoom(Constants.csHALLWAY), locateRoom(Constants.csDEN), locateRoom(Constants.csBATHROOM), locateRoom(Constants.csSTAIRS), locateRoom(Constants.csFOYER));
		
		//  Stairs: north ==> boundary wall, south ==> 2nd floor (NO ACCESS), west ==> boundary wall, east ==> hallway
		addRoomExits(locateRoom(Constants.csSTAIRS), iWallLoc, locateRoom(Constants.cs2ND_FLOOR), iWallLoc, locateRoom(Constants.csHALLWAY));

		//  Den: north ==> boundary wall, south ==> hallway, west ==> boundary wall, east ==> Music room
		addRoomExits(locateRoom(Constants.csDEN), iWallLoc, locateRoom(Constants.csHALLWAY), iWallLoc, locateRoom(Constants.csMUSIC));

		//  Kitchen: north ==> boundary wall, south ==> outside, west ==> boundary wall, east ==> Dining
		addRoomExits(locateRoom(Constants.csKITCHEN), iWallLoc, iOutsideLoc, iWallLoc, locateRoom(Constants.csDINING));

		//  Patio: north ==> fence, south ==> outside, west ==> Music Room, east ==> fence
		addRoomExits(locateRoom(Constants.csPATIO), iWoodFenceLoc, iOutsideLoc, locateRoom(Constants.csMUSIC), iWoodFenceLoc);

		//  Conservatory: north ==> boundary wall, south ==> boundary wall, west ==> Dining room, east ==> boundary wall
		addRoomExits(locateRoom(Constants.csCONSERVATORY), iGlassWallLoc, iGlassWallLoc, locateRoom(Constants.csDINING), iGlassWallLoc);


	}
	

	
	//*****************************************************************************************************************************************
	//### Controller method for creating and adding rooms to the arrRooms[] data structure
	private void createAllRoomsCtrl() {
		String sMethod = "createAllRooms(): ";		
		int iRoomCtr = 0;		
		
		Trace.trace(sClass, sMethod);
		
		//
		//Define the rooms of the building
		//
		
		//Room 0: the starting room for the program
		//  Foyer: north ==> music room, south ==> dining room, west ==> hallway, east ==> outside
		iRoomCtr = createRoomFoyer(iRoomCtr);	//Room 0
        this.objStartingRoom = this.arrRooms[(iRoomCtr - 1)];  //make this the starting room of the program
        //System.out.println("this.objStartingRoom: " + this.objStartingRoom.toString());
        
        //Room objRoom = this.arrRooms[(iRoomCtr - 1)];
        //objRoom.printRoom(objRoom);
        
        Trace.debug(bClassDebug, sClass, sMethod, this.arrRooms[(iRoomCtr - 1)].toString());
        Trace.debug(bClassDebug, sClass, sMethod, "Starting Room: [" + objStartingRoom.toString() + "]");        
 
        //Room 1: 
		//  Dining north ==> foyer, south ==> boundary wall, west ==> kitchen, east ==> conservatory
		iRoomCtr = createRoomDining(iRoomCtr);
		//Room 2: 
		//  Music: north ==> boundary wall, south ==> foyer, west ==> den, east ==> patio
		iRoomCtr = createRoomMusic(iRoomCtr);       
		//Room 3
		//  Hallway: north ==> den, south ==> bathroom (NO ACCESS), west ==> Foyer, east ==> stairs
		iRoomCtr = createRoomHallway(iRoomCtr);		//Room 4
		//  Stairs: north ==> boundary wall, south ==> 2nd floor (NO ACCESS), west ==> boundary wall, east ==> hallway
		iRoomCtr = createRoomStairs(iRoomCtr);		//Room 5
		//  Den: north ==> boundary wall, south ==> hallway, west ==> boundary wall, east ==> Music room
		iRoomCtr = createRoomDen(iRoomCtr);		//Room 6
		//  Kitchen: north ==> boundary wall, south ==> outside, west ==> boundary wall, east ==> Dining
		iRoomCtr = createRoomKitchen(iRoomCtr);
		//Room 7
		//  Patio: north ==> fence, south ==> outside, west ==> Music Room, east ==> fence
		iRoomCtr = createRoomPatio(iRoomCtr);
		//Room 8
		//  Conservatory: north ==> boundary wall, south ==> boundary wall, west ==> Dining room, east ==> boundary wall
		iRoomCtr = createRoomConservatory(iRoomCtr);

		//
		//Building boundaries, no exits once these are encountered
		//
		
		//Room 9
		//A wall with no door to exit 
		String[] arsLongDesc = new String[Constants.ciMaxLongDescription];
		arsLongDesc[0] = Constants.csWallLongDescription;
		iRoomCtr = createRoom(iRoomTypeBarrier, Constants.csWALL, Constants.csWallShortDescription, Constants.csWallLongDescription, arsLongDesc, iRoomCtr);
		//Set the wall exits to itself, it is a boundary		
		Room arrWallExits[] = new Room[Constants.ciMaxExits];
		for (int iCtr=0; iCtr<Constants.ciMaxExits; iCtr++)
			arrWallExits[iCtr] = arrRooms[iRoomCtr-1];			
		arrRooms[iRoomCtr-1].setExits(arrWallExits);
		
		//Room 10
		//A wall with no door to exit 
		arsLongDesc = new String[Constants.ciMaxLongDescription];
		arsLongDesc[0] = Constants.csGlassWallLongDescription;
		iRoomCtr = createRoom(iRoomTypeBarrier, Constants.csGLASS_WALL, Constants.csWallShortDescription, Constants.csWallLongDescription, arsLongDesc, iRoomCtr);
		//Set the glass wall exits to itself, it is a boundary		
		Room arrGlassWallExits[] = new Room[Constants.ciMaxExits];
		for (int iCtr=0; iCtr<Constants.ciMaxExits; iCtr++)
			arrGlassWallExits[iCtr] = arrRooms[iRoomCtr-1];			
		arrRooms[iRoomCtr-1].setExits(arrGlassWallExits);


		//Room 11
		//Once encountered the player is now outside and house tour ends
		arsLongDesc = new String[Constants.ciMaxLongDescription];
		arsLongDesc[0] = Constants.csOutsideLongDescription;
		iRoomCtr = createRoom(Constants.ciEXIT, Constants.csOUTSIDE, Constants.csOutsideLongDescription, Constants.csOutsideLongDescription, arsLongDesc, iRoomCtr);
		//Set the outside exits to itself, once outside the house your are done	
		Room arrOutsideExits[] = new Room[Constants.ciMaxExits];
		for (int iCtr=0; iCtr<Constants.ciMaxExits; iCtr++)
			arrOutsideExits[iCtr] = arrRooms[iRoomCtr-1];			
		arrRooms[iRoomCtr-1].setExits(arrOutsideExits);
		
		//Room 12
		//Another type of boundary
		arsLongDesc = new String[Constants.ciMaxLongDescription];
		arsLongDesc[0] = Constants.csWoodFenceDescription;
		iRoomCtr = createRoom(iRoomTypeBarrier, Constants.csWOOD_FENCE, Constants.csWoodFenceShortDescription, Constants.csWoodFenceDescription, arsLongDesc, iRoomCtr);
		//Set the wall exits to itself, it is a boundary		
		Room arrWoodFenceExits[] = new Room[Constants.ciMaxExits];
		for (int iCtr=0; iCtr<Constants.ciMaxExits; iCtr++)
			arrWoodFenceExits[iCtr] = arrRooms[iRoomCtr-1];			
		arrRooms[iRoomCtr-1].setExits(arrWoodFenceExits);

		//Room 13
		//Area not yet defined, cannot allow access
		arsLongDesc = new String[Constants.ciMaxLongDescription];
		arsLongDesc[0] = Constants.csNoAccessLongDescription;
		iRoomCtr = createRoom(Constants.ciNO_ACCESS, Constants.cs2ND_FLOOR, Constants.csNoAccessShortDescription, Constants.csNoAccessLongDescription, arsLongDesc, iRoomCtr);
		//Set no access exits to itself, no access in all directions	
		Room arrNoAccessExits[] = new Room[Constants.ciMaxExits];
		for (int iCtr=0; iCtr<Constants.ciMaxExits; iCtr++)
			arrNoAccessExits[iCtr] = arrRooms[iRoomCtr-1];			
		arrRooms[iRoomCtr-1].setExits(arrNoAccessExits);
		
		//Room 14
		//Area not yet defined, cannot allow access
		arsLongDesc = new String[Constants.ciMaxLongDescription];
		arsLongDesc[0] = Constants.csNoAccessLongDescription;
		iRoomCtr = createRoom(Constants.ciNO_ACCESS, Constants.csBATHROOM, Constants.csNoAccessShortDescription, Constants.csNoAccessLongDescription, arsLongDesc, iRoomCtr);
		//Set no access exits to itself, no access in all directions	
		//Room arrNoAccessExits[] = new Room[Constants.ciMaxExits];
		for (int iCtr=0; iCtr<Constants.ciMaxExits; iCtr++)
			arrNoAccessExits[iCtr] = arrRooms[iRoomCtr-1];			
		arrRooms[iRoomCtr-1].setExits(arrNoAccessExits);
		
		
		//use of this variable instead of max array allows us to know the actual number of elements loaded int the array
		//helpful when debugging when printing out all array elements
		this.iNumberOfRoomsInArray = iRoomCtr;
		
		sRoomName = "";
		sShortDesc = "";
		sLongerDesc = "";
		
	}
	
	
	
	//### Helper methods *************************************************************************************************************************

	
	
	//###*****************************************************************************************************************************************
	//###createRoom() with longDescription array and with storyboard array
	//### Utility method called by many methods to create and store rooms in the Rooms array
	private int createRoom(int iRoomType, String sRoomName, String sShortRoomDesc, String sLongerRoomDesc, String[] arsLongRoomDesc, String[] arsStoryboard, int iRoomCtr) {
		String sMethod = "createRoom(): ";
		
		Trace.trace(sClass, sMethod);
		Room objThisRoom = new Room(iRoomType, sRoomName, sShortRoomDesc, sLongerRoomDesc);
		objThisRoom.setRoomLongDescriptionArray(arsLongRoomDesc);
		objThisRoom.setRoomStoryboardArray(arsStoryboard);
		this.arrRooms[iRoomCtr] = objThisRoom;
		
		
		//this.arrRooms[iRoomCtr].printRoom();
		
		 iRoomCtr++;
		 return(iRoomCtr);
	}

	//###createRoom() with longDescription array
	//### Utility method called by many methods to create and store rooms in the Rooms array
	private int createRoom(int iRoomType, String sRoomName, String sShortRoomDesc, String sLongerRoomDesc, String[] arsLongRoomDesc, int iRoomCtr) {
		String sMethod = "createRoom(): ";
		
		Trace.trace(sClass, sMethod);
		Room objThisRoom = new Room(iRoomType, sRoomName, sShortRoomDesc, sLongerRoomDesc);
		this.arrRooms[iRoomCtr] = objThisRoom;
		objThisRoom.setRoomLongDescriptionArray(arsLongRoomDesc);
		
		//this.arrRooms[iRoomCtr].printRoom();
		
		 iRoomCtr++;
		 return(iRoomCtr);
	}

	
	//###createRoom() without longDescription array and without storyboard array
	private int createRoom(int iRoomType, String sRoomName, String sShortRoomDesc, String sLongerRoomDesc, int iRoomCtr) {
		String sMethod = "createRoom(): ";
		
		Trace.trace(sClass, sMethod);
		this.arrRooms[iRoomCtr] = new Room(iRoomType, sRoomName, sShortRoomDesc, sLongerRoomDesc);
		
		//this.arrRooms[iRoomCtr].printRoom();
		
		 iRoomCtr++;
		 return(iRoomCtr);
	}

	
	//Methods to create the various rooms for the program
	//
	private int createRoomFoyer(int iRoomCtr) {
		String sMethod = "createRoomFoyer(): ";
		String[] arsLongDesc = new String[Constants.ciMaxLongDescription];
		String[] arsStoryboard = new String[Constants.ciMaxStoryboard];
		
		Trace.trace(sClass, sMethod);
		sRoomName = Constants.csFOYER;
		sShortDesc = "A small foyer";
		sLongerDesc = "The small entryway of a neo-colonial house.";
		arsLongDesc[0] = "A dining room is open to the south, where a large table can be seen.";
		arsLongDesc[1] = "Hardwood floor leads west into doorway, next to a staircase that leads up to a second floor.";
		//arsLongDesc[1] = "Hardwood floor leads west into doorway, next to a staircase that leads up to a second floor."
		//		+ " Lets make this at least two lines to send through formatting to see if it even works right??";
		arsLongDesc[2] = "To the north is a small room, where you can see a piano.";
		arsLongDesc[3] = "Heading east would return you outside.";
		
		return(createRoom(iRoomTypeRoom, sRoomName, sShortDesc, sLongerDesc, arsLongDesc, iRoomCtr));
		/*
		arsStoryboard[0] = "test0";
		arsStoryboard[1] = "test1";
		arsStoryboard[2] = "test2";
		arsStoryboard[3] = "test3";
		
		return(createRoom(iRoomTypeRoom, sRoomName, sShortDesc, sLongerDesc, arsLongDesc, arsStoryboard, iRoomCtr));
		*/
	}
	
	//###
	private int createRoomDining(int iRoomCtr) {
		String sMethod = "createRoomDining(): ";
		String[] arsLongDesc = new String[Constants.ciMaxLongDescription];
		
		
		Trace.trace(sClass, sMethod);
		sRoomName = Constants.csDINING;
		sShortDesc = "a formal dining room";
		sLongerDesc = "A dining room where guest would gather and sit at the large table in the center of the room.";
		
		arsLongDesc[0] = "To the east a room with glass roof and walls can be seen.";
		arsLongDesc[1] = "To the south generation of family photos are hanging on both sides of a window on the wall.";
		arsLongDesc[2] = "From the west wonderful aromas feel the senses, almost like Thanksgiving dinner.";
		arsLongDesc[3] = "To the north is the Foyer.";
		
		return(createRoom(iRoomTypeRoom, sRoomName, sShortDesc, sLongerDesc, arsLongDesc, iRoomCtr));
	}
	
	
	//###
	private int createRoomMusic(int iRoomCtr) {
		String sMethod = "createRoomMusic(): ";
		String[] arsLongDesc = new String[Constants.ciMaxLongDescription];
		
		Trace.trace(sClass, sMethod);
		sRoomName = Constants.csMUSIC;
		sShortDesc = "quiet area to practice an instrument";
		sLongerDesc = "A room dedicated to listening and playing of musical instruments.";
		arsLongDesc[0] = "You see a baby grand piano with the player's bench on the north wall, a window lets in plenty of light to read sheet music";
		arsLongDesc[1] = "To the east there is an outside enclosed patio, strange noises are eminating.";
		arsLongDesc[2] = "To the west loud noises are coming from a dark room where there are flicker lights.";
		arsLongDesc[3] = "To the south is the Foyer.";

		return(createRoom(iRoomTypeRoom, sRoomName, sShortDesc, sLongerDesc, arsLongDesc, iRoomCtr));
	}
	

	//###
	private int createRoomHallway(int iRoomCtr) {
		String sMethod = "createRoomHallway(): ";
		String[] arsLongDesc = new String[Constants.ciMaxLongDescription];
		
		Trace.trace(sClass, sMethod);
		sRoomName = Constants.csHALLWAY;
		sShortDesc = "a simple passage way between rooms";
		sLongerDesc = "Wood flooring lines the hallway between the Foyer and some stairs.";
		
		arsLongDesc[0] = "At the west end of the hallway are stairs leading to a second floor.";
		arsLongDesc[1] = "North side of the hallway is a closed door with loud noises being emitted.";
		arsLongDesc[2] = "South of the hallway is a bathroom.  The bathroom is occupied.";
		arsLongDesc[3] = "To the east is the Foyer.";
    
		return(createRoom(iRoomTypeRoom, sRoomName, sShortDesc, sLongerDesc, arsLongDesc, iRoomCtr));
	}
	
	//###
	private int createRoomStairs(int iRoomCtr) {
		String sMethod = "createRoomStairs(): ";
		String[] arsLongDesc = new String[Constants.ciMaxLongDescription];
		
		Trace.trace(sClass, sMethod);
		sRoomName = Constants.csSTAIRS;
		sShortDesc = "stairs between house levels";
		sLongerDesc = "Stairs connecting the first and second levels of the house.";
		
		arsLongDesc[0] = "The bottom of the stairs are on the north side.";
		arsLongDesc[1] = "The second floor is at the top of the stairs heading south.";
		arsLongDesc[2] = "Family photos line both walls on the sides of the stairs.";
		arsLongDesc[3] = "The 2nd floor is under heavy construction due to termite damage.  There is no access.";
    
		return(createRoom(iRoomTypeRoom, sRoomName, sShortDesc, sLongerDesc, arsLongDesc, iRoomCtr));
	}
	
	//###
	private int createRoomDen(int iRoomCtr) {
		String sMethod = "createRoomDen(): ";
		String[] arsLongDesc = new String[Constants.ciMaxLongDescription];
		
		Trace.trace(sClass, sMethod);
		sRoomName = Constants.csDEN;
		sShortDesc = "a dark room with the TV on full volume playing Rock n Roll videos";
		sLongerDesc = "This is a den where the family will gather to watch TV.";
		
		arsLongDesc[0] = "On the north side of the room is a window and wall to wall book shelves overflowing with hardbound and paperback books.";
		arsLongDesc[1] = "There is a door on the south side that leads to a hallway.";
		arsLongDesc[2] = "The east side of the room leads to the Music room.";
		arsLongDesc[3] = "The west side of the room houses wall to wall entertainment center with the TV currently on at full volume.";
      
		return(createRoom(iRoomTypeRoom, sRoomName, sShortDesc, sLongerDesc, arsLongDesc, iRoomCtr));
	}
	
	//###
	private int createRoomKitchen(int iRoomCtr) {
		String sMethod = "createRoomKitchen(): ";
		String[] arsLongDesc = new String[Constants.ciMaxLongDescription];
		
		Trace.trace(sClass, sMethod);
		sRoomName = Constants.csKITCHEN;
		sShortDesc = "where meals are prepared";
		sLongerDesc = "A family gathering place to prepare and consume meals.";
		
		arsLongDesc[0] = "To the east is the formal dining room for larger friends and family gatherings.";
		arsLongDesc[1] = "The north side of the kitchen has wall to wall cabinet space and counter top for dishes and food storage.";
		arsLongDesc[2] = "The west side contains a nook with a small eating area and a big bay window.";
		arsLongDesc[3] = "The South side of the kitchen has a window over the sink, a stove, refrigerator, and a door to the outside.";
        
		return(createRoom(iRoomTypeRoom, sRoomName, sShortDesc, sLongerDesc, arsLongDesc, iRoomCtr));
	}
	
	//###
	private int createRoomPatio(int iRoomCtr) {
		String sMethod = "createRoomPatio(): ";
		String[] arsLongDesc = new String[Constants.ciMaxLongDescription];
		
		Trace.trace(sClass, sMethod);
		sRoomName = Constants.csPATIO;
		sShortDesc = "an outdoor enclosed space";
		sLongerDesc = "An outdoor enclosed fenced in area for relaxing and sun bathing.";
		
		arsLongDesc[0] = "In the northeast corner of the yard is a hot tub with its jets on making a rackect.";
		arsLongDesc[1] = "The north and east areas are completely fenced in.";
		arsLongDesc[2] = "There is a gate leading to the outside in the south fences.";
		arsLongDesc[3] = "The west side has a door between the patio and music room.";
        
		return(createRoom(iRoomTypeRoom, sRoomName, sShortDesc, sLongerDesc, arsLongDesc, iRoomCtr));
	}
	
	//###
	private int createRoomConservatory(int iRoomCtr) {
		String sMethod = "createRoomConservatory(): ";
		String[] arsLongDesc = new String[Constants.ciMaxLongDescription];
		
		Trace.trace(sClass, sMethod);
		sRoomName = Constants.csCONSERVATORY;
		sShortDesc = "room with a glass roof and walls";
		sLongerDesc = "This glass enclosed room makes for the family greenhouse or for a little sun bathing when cold outside.";
		arsLongDesc[0] = "The sun shining through the south side glass walls makes for an extended growing season.";
		arsLongDesc[1] = "The rising sun in the east glass walls provides early morning warmth.";
		arsLongDesc[2] = "The north glass walls and roof provides light as the sun travels through the sky.";
		arsLongDesc[3] = "The door to the west is the passageway between the Dining room and Conservatory";
        
		return(createRoom(iRoomTypeRoom, sRoomName, sShortDesc, sLongerDesc, arsLongDesc, iRoomCtr));
	}
	

	//###
	//Utility method to print all object in rooms array
	public void printAllRooms(){
		//arrRooms[] = new Room[Constants.ciNumberOfRoom];
		String sMethod = "printAllRooms(): ";		
		String sMsg = "";
		
		Trace.trace(sClass, sMethod);		
		for (int iCtr=0; iCtr<iNumberOfRoomsInArray; iCtr++) {
			sMsg = "\narrRooms[" + iCtr + "] [" + arrRooms[iCtr].toString() + "]\n";
			Trace.debug(sClass, sMethod, sMsg);
			//System.out.println("\narrRooms[" + iCtr + "] [" + arrRooms[iCtr].toString() + "]\n");
		}
			
			
			
	}
	
	
	
}
