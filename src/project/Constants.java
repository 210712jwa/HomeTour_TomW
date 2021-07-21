package project;

public final class Constants {

	public final static int ciNumberOfRoom = 15;
	public final static int ciMaxExits = 4;
	public final static int ciMaxLongDescription = 10;
	public final static int ciMaxStoryboard = 10;
	
	//Exit directions
	public final static int ciNORTH = 0;
	public final static int ciSOUTH = 1;
	public final static int ciEAST = 2;
	public final static int ciWEST = 3;
	
	//Travel Direction
	public final static String csNORTH = "NORTH";
	public final static String csSOUTH = "SOUTH";
	public final static String csEAST = "EAST";
	public final static String csWEST = "WEST";
	public final static String csaDirections[] = {csNORTH,csSOUTH, csEAST, csWEST};
	
	//Types of fixtures
	public final static int ciNONE = 0;
	public final static int ciBARRIER = 1;
	public final static int ciNO_ACCESS = 2;
	public final static int ciROOM = 3;
	public final static int ciEXIT = 4;
	
	//Fixed Fixtures
	public final static String csWALL = "WALL";
	public final static String csWallShortDescription = "a wall in the room";
	public final static String csWallLongDescription = "This is a common wall in the room. Cannot exit here.";
	//
	public final static String csGLASS_WALL = "GLASS WALL";
	public final static String csGlassWallShortDescription = "a wall in the room made of glass";
	public final static String csGlassWallLongDescription = "This is a wall made of glass in the room. Cannot exit here.";
	//
	public final static String csOUTSIDE = "OUTSIDE";
	public final static String csOutsideShortDescription = "the outside world";
	public final static String csOutsideLongDescription = "You have left the building and are now outside.";
	//
	public final static String csWOOD_FENCE = "WOOD FENCE";
	public final static String csWoodFenceShortDescription = "an ordinary wooden fence";
	public final static String csWoodFenceDescription = "A seven foot high wooden fence built for privacy.";
	//
	public final static String csNO_ACCESS = "NO ACCESS";
	public final static String csNoAccessShortDescription = "restricted area";
	public final static String csNoAccessLongDescription = "A restricted area where no access is allowed."
			                                            + "\nThe area is under construction or is otherwise dangerous";
	
	//Room / fixture names
	public final static String csFOYER = "Foyer";
	public final static String csDINING = "Dining";	
	public final static String csMUSIC = "Music";
	public final static String csHALLWAY = "Hallway";
	public final static String csSTAIRS = "Stairs";
	public final static String csDEN = "Den";
	public final static String csKITCHEN = "Kitchen";
	public final static String csPATIO = "Patio";
	public final static String csCONSERVATORY = "Conservatory";
	public final static String csBATHROOM = "Bathroom";
	public final static String cs2ND_FLOOR = "Second Floor";
	
	
	//First Level User command moves
	public final static String csGO = "GO";
	public final static String csQUIT = "QUIT";
	public final static String csHELP = "HELP";
	public final static String csTELEPORT = "TELEPORT";
	
	//First Level User command moves
	public final static int ciActionError = -10;
	public final static int ciActionGO = 10;
	public final static int ciActionQUIT = 20;
	public final static int ciActionHELP = 30;
	//
	//Target Level User command moves
	public final static int ciActionTargetError = -10;
	public final static int ciActionTargetNORTH = 10;
	public final static int ciActionTargetSOUTH = 20;
	public final static int ciActionTargetEAST = 30;
	public final static int ciActionTargetWEST = 40;
	public final static int ciActionTargetTELEPORT = 100;
	
	
	
	
	
	
	
	
	
	
	
	
	
	


}
