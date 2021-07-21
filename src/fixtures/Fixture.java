package fixtures;

import project.Constants;

/**
 * * This abstract class will be used as a base for anything that can be looked
 * at or interacted with. This class should define (at least) the following
 * properties:
 * 
 * - 'String name` : a short name / title for the fixtureString -
 * `shortDescription` : a one-sentence-long description of a fixture, used to
 * briefly mention the fixture - `String longDescription` : a paragraph-long
 * description of the thing, displayed when the player investigates the fixture
 * thoroughly (looks at it, or enters a room)
 * 
 * 
 * @author tlw8748253
 *
 */
public abstract class Fixture {

	private String sFixtureName = "";
	private String sFixtureShortDescription = "";
	private String sFixtureLongerDescription = "";

	private int iFixtureType = Constants.ciNONE;
	private boolean bFixtureVisited = false;
	private String[] arsFixtureLongDescription = new String[Constants.ciMaxLongDescription];
	private String[] arsFixtureStoryboard = new String[Constants.ciMaxStoryboard]; // theme / story for this fixture

	public Fixture() {
	}

	public Fixture(int iFixtureType, String sFixtureName, String sFixtureShortDescription,
			String sFixtureLongDescription) {
		this.iFixtureType = iFixtureType;
		this.sFixtureName = sFixtureName;
		this.sFixtureShortDescription = sFixtureShortDescription;
		this.sFixtureLongerDescription = sFixtureLongDescription;
	}

	// setter methods
	protected void setFixtureName(String sFixtureName) {
		this.sFixtureName = sFixtureName;
	}

	protected void setFixtureShortDescription(String sFixtureShortDescription) {
		this.sFixtureShortDescription = sFixtureShortDescription;
	}

	protected void setFixtureLongerDescription(String sFixtureLongDescription) {
		this.sFixtureLongerDescription = sFixtureLongDescription;
	}

	protected void setFixtureType(int iFixtureType) {
		this.iFixtureType = iFixtureType;
	}

	protected void setFixtureVisited() {
		bFixtureVisited = true;
	}

	protected void setFixtureLongDescriptionArray(String[] arsFixtureLongDescription) {
		this.arsFixtureLongDescription = arsFixtureLongDescription;
	}

	protected void setFixtureLongDescriptionArray(int iPos, String sMsg) {
		if ((iPos >= 0) && (iPos < Constants.ciMaxLongDescription))
			this.arsFixtureLongDescription[iPos] = sMsg;
	}

	protected void setFixtureStoryboardArray(String[] arsFixtureStoryboard) {
		this.arsFixtureStoryboard = arsFixtureStoryboard;
	}

	protected void setFixtureStoryboardArray(int iPos, String sMsg) {
		if ((iPos >= 0) && (iPos < Constants.ciMaxLongDescription))
			this.arsFixtureStoryboard[iPos] = sMsg;
	}

	// getter methods
	protected String getFixtureName() {
		return (sFixtureName);
	}

	protected String getFixtureShortDescription() {
		return (sFixtureShortDescription);
	}

	protected String getFixtureLongerDescription() {
		return (sFixtureLongerDescription);
	}

	protected int getFixtureType() {
		return (iFixtureType);
	}

	protected boolean getFixtureVisited() {
		return (bFixtureVisited);
	}

	protected String[] getFixtureStoryboardArray() {
		return (arsFixtureStoryboard);
	}

	protected String getFixtureStoryboard(int iPos) {
		String sRetString = null;
		if ((iPos >= 0  ) && (iPos < Constants.ciMaxLongDescription))
			sRetString = arsFixtureStoryboard[iPos];
		return(sRetString);
	}

	protected String[] getFixtureLongDescriptionArray() {
		return (arsFixtureLongDescription);
	}

	protected String getFixtureLongDescription(int iPos) {
		String sRetString = null;
		if ((iPos >= 0) && (iPos < Constants.ciMaxLongDescription))
			sRetString = arsFixtureLongDescription[iPos];
		return (sRetString);

	}

	// ###
	public String toString() {
		String sObj = "";

		sObj = "\n\tFixtureName: [" + getFixtureName() + "]" + "\n\tFixtureShortDescription: ["
				+ getFixtureShortDescription() + "]" + "\n\tFixtureLongDescription: [" + getFixtureLongerDescription()
				+ "]" + "\n\tiFixtureType: [" + iFixtureType + "]" + "\n\tbFixtureVisited: [" + bFixtureVisited + "]"
				+ "\n\tarsFixtureLongDescription[]: ";

		for (int iCtr = 0; iCtr < Constants.ciMaxLongDescription; iCtr++)
			sObj += "[" + iCtr + "]: " + arsFixtureLongDescription[iCtr];

		sObj += "\n\tarsFixtureStoryboard[]: ";

		for (int iCtr = 0; iCtr < Constants.ciMaxStoryboard; iCtr++)
			sObj += "[" + iCtr + "]: " + arsFixtureStoryboard[iCtr];

		return (sObj);

	}

}
