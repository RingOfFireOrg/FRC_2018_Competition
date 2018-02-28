package org.usfirst.frc.team3459.robot;

public class FieldProperties {
	
	private static boolean switchLeft = false;
	private static boolean switchRight = false;
	private static boolean scaleLeft = false;
	private static boolean scaleRight = false;
	
	public static boolean initialize(String input) {
		if (input == null || input.length() < 3)
		{
			return false;
		}
		
		if (input.charAt(0) == 'L')
		{
			switchLeft = true;
			switchRight = false;
		}
		
		if (input.charAt(1) == 'L')
		{
			scaleLeft = true;
			scaleRight = false;
		}
		
		return true;
		
	}
	
	public static boolean isLeftSwitchOurs() {
		return switchLeft;
	}
	
	public static boolean isRightSwitchOurs() {
		return switchRight;
	}
	
	public static boolean isLeftScaleOurs() {
		return scaleLeft;
	}
	
	public static boolean isRightScaleOurs() {
		return scaleRight;
	}
	
	
}

