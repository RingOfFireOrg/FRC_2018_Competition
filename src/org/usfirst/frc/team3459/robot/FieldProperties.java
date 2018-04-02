package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.DriverStation;

public class FieldProperties {

	private static boolean switchLeft = false;
	private static boolean switchRight = false;
	private static boolean scaleLeft = false;
	private static boolean scaleRight = false;

	public static boolean initialize(String input) {
		switchLeft = false;
		switchRight = false;
		scaleLeft = false;
		scaleRight = false;
		if (input == null || input.length() < 3) {
			DriverStation.reportError("Unable to Determine Field Properties from: " + input, false);
			return false;
		}
		if (input.charAt(0) == 'L') {
			switchLeft = true;
		} else if (input.charAt(0) == 'R') {
			switchRight = true;
		} else {
			DriverStation.reportError("Unable to Determine Switch position from: " + input.charAt(0), false);
		}
		if (input.charAt(1) == 'L') {
			scaleLeft = true;
		} else if (input.charAt(1) == 'R') {
			scaleRight = true;
		} else {
			DriverStation.reportError("Unable to Determine Scale position from: " + input.charAt(1), false);
		}
		DriverStation.reportError("Setup as " + switchLeft + " " + switchRight + " " + scaleLeft + " " + scaleRight,
				false);
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
