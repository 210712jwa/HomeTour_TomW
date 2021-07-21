package game;

import fixtures.Room;
import project.Constants;

public class Display {
	private int iDisplayLineLen = 74;
	private int iMaxSentence = iDisplayLineLen - 4;
	private String sFormatChar = "*";
	private String sDblFormatChar = sFormatChar + sFormatChar;
	private int iDisplayOffset = (sDblFormatChar.length()) * 2;

	//
	//###printRoom()
	public void printRoom(Player objPlayer) {
		Room objCurrentRoom = objPlayer.getCurrentRoom();
		String sRoomName = objCurrentRoom.getRoomName();
		String sRoomShortDesc = objCurrentRoom.getRoomShortDescription();
		String sRoomLongDesc = objCurrentRoom.getRoomLongerDescription();

		objPlayer.setRoomVisited();

		System.out.println(formatFullLine());
		//System.out.println(formatLine("You are in:"));
		//System.out.println(formatLine("Room: " + sRoomName));
		System.out.println(formatLine(sRoomName));
		System.out.println(formatLine("  " + sRoomShortDesc));
		System.out.println(formatLine("  " + sRoomLongDesc));
		System.out.println(formatLine(" "));

		String[] arsLongDescpition = objPlayer.getRoomLongDescriptionArray();
		for (int iCtr = 0; iCtr < Constants.ciMaxLongDescription; iCtr++) {

			if (arsLongDescpition[iCtr] != null)
				System.out.println(formatLine("  " + arsLongDescpition[iCtr]));	
			else
				break;
		}

		System.out.println(formatLine(" "));
		System.out.println(formatFullLine());
		
		String[] arsStoryboard = objPlayer.getRoomStoryboardArray();
		for (int iCtr = 0; iCtr < Constants.ciMaxStoryboard; iCtr++) {

			if (arsStoryboard[iCtr] != null)
				System.out.println(formatLine("  " + arsStoryboard[iCtr]));	
			else
				break;
		}

		
		
		System.out.println(formatLine(" "));
		System.out.println(formatFullLine());

		// printInstrutions();
	}

	//###
	// Utility method to format an output string
	private String formatLine(String sLineToFormat) {
		// String sFormattedLine = sDblFormatChar;
		String sFormattedLine = "";
		int iLineLen = sLineToFormat.length();
		int iMaxLen = iMaxSentence; 

		if (iLineLen > iMaxLen) {
			// ISSUE with whole words being chopped
			String sChopLine = sLineToFormat.trim();
			sLineToFormat = "";
			while (iLineLen > iMaxLen) {

				// System.out.println("sLineToFormat: [" + sLineToFormat + "]");
				//System.out.println("formatLine(): sChopLine: [" + sChopLine + "]");

				String sNextString = buildSentence(sChopLine," ",iMaxSentence);
				int iNextStringLen  = sNextString.length();
				
				//System.out.println("formatLine(): sNextString: [" + sNextString + "]");

				//iNextStringLen++;  //increment past space add in buildSentence()				
				if (iNextStringLen < sChopLine.length())
					sChopLine = sChopLine.substring(iNextStringLen);
				
				//System.out.println("formatLine(): sChopLine: [" + sChopLine + "]");
				
				sNextString = "  " + sNextString;
				//System.out.println("formatLine(): sNextString: [" + sNextString + "]");
				sFormattedLine += formatedString(sNextString);
				//System.out.println("formatLine(): sFormattedLine: [" + sFormattedLine + "]");
				iLineLen = sChopLine.length();
				
				if (iLineLen > iMaxLen) {					
					sFormattedLine += "\n";
				}
				else {
					sFormattedLine += "\n" + formatedString("  " + sChopLine);
				}

			}
		} else {

			sFormattedLine = formatedString(sLineToFormat);
		}

		return (sFormattedLine);
	}

	//###
	private String buildSentence(String sUnformatedString, String sDelimiter, int iSentenceMaxLen) {
		String sRetSentence = "";
		String sChopSentence = sUnformatedString.trim();
		String sNextWord = "";
		int iRetSentenceLen = 0;
		int iNextWordLen  = 0;
		boolean bDone = false;
		
		//System.out.println("buildSentence(): sUnformatedString: [" + sUnformatedString + "]");
		
		do {
			sNextWord = getWord(sChopSentence,sDelimiter) + " ";
			iNextWordLen = sNextWord.length();			
			//System.out.println("buildSentence(): sNextWord: [" + sNextWord + "]");
			
			if ((iRetSentenceLen + iNextWordLen) > iSentenceMaxLen) {
				bDone = true;
			}
			else {
				sRetSentence += sNextWord;
				//System.out.println("buildSentence(): sRetSentence: [" + sRetSentence + "]");
				iRetSentenceLen = sRetSentence.length();
				//System.out.println("buildSentence(): sNextWord.length(): [" + sNextWord.length() + "]");
				if (iNextWordLen < sChopSentence.length())
					sChopSentence = sChopSentence.substring(iNextWordLen);
				sNextWord = "";
				//System.out.println("buildSentence(): sChopSentence: [" + sChopSentence + "]");
			}
			
		}while(!bDone);
				
		//System.out.println("buildSentence(): sRetSentence: [" + sRetSentence + "]");
		return(sRetSentence);
	}

	//###
	private String getWord(String sWordString, String sDelimiter) {
		String sRetWord = "";		
		
		sWordString  = sWordString.trim();
		int iStringAt = sWordString.indexOf(sDelimiter); // look for 1st delimiter
		//System.out.println("getWord(): sWordString: [" + sWordString + "]");

		if (iStringAt > 0) {
			sRetWord = sWordString.substring(0, iStringAt);
		} else
			sRetWord = sWordString;

		//System.out.println("getWord(): sRetWord: [" + sRetWord + "]");
		return (sRetWord);
	}

	//###
	private String formatFullLine() {
		String sHeader = "";
		for (int iCtr = 0; iCtr < iDisplayLineLen + iDisplayOffset; iCtr++)
			sHeader += sFormatChar;

		return (sHeader);
	}

	//
	//###formatedString()
	private String formatedString(String sStringToPad) {
		String sFormattedString = sDblFormatChar;
		int iLineLen = sStringToPad.length();

		sFormattedString += sStringToPad;
		int iSpaces = iDisplayLineLen - iLineLen;
		for (int iCtr = 0; iCtr < iSpaces; iCtr++)
			sFormattedString += " ";
		sFormattedString += sDblFormatChar;

		return (sFormattedString);
	}

	//###
	// Utility method to center and format an output string
	private String formatCenterLine(String sLineToFormat) {
		String sFormattedLine = sDblFormatChar;
		int iLineLen = sLineToFormat.length();
		int iLineLenDiv2 = iLineLen / 2;
		int iRemainder = iLineLen % 2;

		int iLineTotLen = iDisplayLineLen + iDisplayOffset - iLineLen;
		int iLineHalfLen = iLineTotLen / 2;
		int iSpaceCnt = iLineHalfLen - iLineLenDiv2 + iRemainder;

		iSpaceCnt = (iDisplayLineLen + iDisplayOffset - iLineLen) / 2;

		for (int iCtr = 0; iCtr < iSpaceCnt; iCtr++) // pad spaces before text
			sFormattedLine += " ";

		sFormattedLine += sLineToFormat;

		iLineLen = sFormattedLine.length();

		iSpaceCnt = iDisplayLineLen - iLineLen + sDblFormatChar.length();

		for (int iCtr = 0; iCtr < iSpaceCnt; iCtr++) // pad spaces after text
			sFormattedLine += " ";

		sFormattedLine += sDblFormatChar;

		return (sFormattedLine);
	}

	//###
	public void displayPressEnterContiue() {
		System.out.println("Press Enter to Continue.");
	}

	//###
	public void displayPressEnterExit() {
		System.out.println("Press Enter to Exit.");
	}

	//###
	public void displayClosingScreen() {
		System.out.println(formatFullLine());
		System.out.println(formatCenterLine("The tour of this home has concluded."));
		System.out.println(formatCenterLine("Thank You for playing my game."));
		System.out.println(formatFullLine());
	}

	//###
	public void displayOpeningScreen() {

		System.out.println(formatFullLine());
		System.out.println(formatLine(" "));
		System.out.println(formatCenterLine("Welcome to a tour of a home"));
		System.out.println(formatLine(" "));

		printFullInstrutions();

		System.out.println(formatLine(" "));
		System.out.println(formatCenterLine("Have FUN and try not to get lost!!!"));
		System.out.println(formatCenterLine("We are not responsible for any injuries."));
		System.out.println(formatLine(" "));
		System.out.println(formatFullLine());
		System.out.println(formatFullLine());
	}

	public void printHelp() {
		System.out.println(formatFullLine());
		printInstrutions();
		System.out.println(formatFullLine());
	}

	private void printInstrutions() {
		System.out.println(formatLine("  To exit the tour go through a door that leads to the outside."));
		System.out.println(formatLine("  To explore type GO and a direction [NORTH, SOUTH, EAST, WEST]"));
		System.out.println(formatLine("  To quit type Quit"));
		System.out.println(formatFullLine());
		System.out.println(formatLine("  Secret command:"));
		System.out.println(formatLine("       Teleport <Room>"));
		System.out.println(formatFullLine());
		System.out.println(formatLine("  Type Help to see commands again."));
	}

	public void printFullInstrutions() {

		System.out.println(formatFullLine());
		System.out.println(formatLine(""));
		System.out.println(formatLine("  To Explore an area type in any of the following commands:"));
		System.out.println(formatLine("    GO North or GO South or GO East or GO West"));
		System.out.println(formatLine("      then press the Enter key:"));
		System.out.println(formatLine(""));
		System.out.println(formatLine("  If that area is open to exploring you will be sent there"));
		System.out.println(formatLine("    and shown more about the area."));
		System.out.println(formatLine(""));
		System.out.println(formatLine("  If that area is not available to explore you will need to choose"));
		System.out.println(formatLine("    a different direction."));
		System.out.println(formatLine(""));
		System.out.println(formatLine("  If you wish to stop exploring, find a location that leads outside."));
		System.out.println(formatLine(""));
		System.out.println(formatLine("  You may also enter the following command to stop exploring:"));
		System.out.println(formatLine("    Quit"));
		System.out.println(formatLine(""));
		System.out.println(formatLine("  Type Help to see a refresher of the commands."));
		System.out.println(formatLine(""));
		System.out.println(formatFullLine());
	}

}
