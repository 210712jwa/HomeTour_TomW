package project;

public class Trace {
	public final static boolean cbTrace = false;
	public final static boolean cbDebug = false;
	public final static String csTrace = "TRACE: ";
	public final static String csDebug = "DEBUG: ";

	public Trace() {};
	
	public static void trace(String sClass, String sMethod) {
		if (cbTrace) System.out.println("\n" + csTrace + "Class: " + sClass + " Method: " + sMethod);
	}
	public static void trace(String sClass, String sMethod, String sMessage) {
		if (cbTrace) System.out.println("\n" + csTrace + "Class: " + sClass + " Method: " + sMethod + "\n" + sMessage);
	}

	public static void debug(String sClass, String sMethod, String sMessage) {
		if (cbDebug) System.out.println("\n" + csDebug + "Class: " + sClass + " Method: " + sMethod + "\n" + sMessage);
	}
	
	//allows methods to override and shut off debugging
	public static void debug(boolean bDebug, String sClass, String sMethod, String sMessage) {
		if (cbDebug && bDebug) System.out.println("\n" + csDebug + "Class: " + sClass + " Method: " + sMethod + "\n" + sMessage);
	}

	//allows methods to override and shut off debugging
	public static void debug(String sClass, String sMethod, String sMessage, boolean bDebugForce) {
		if (cbDebug || bDebugForce) System.out.println("\n" + csDebug + "Class: " + sClass + " Method: " + sMethod + "\n" + sMessage);
	}

}
