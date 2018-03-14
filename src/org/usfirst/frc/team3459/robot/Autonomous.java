package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
	TankDrive driveTrain;
	Lifter elevator;
	Popcorn grabber;
	long endTime;

	public Autonomous(TankDrive drive, Lifter lifter, Popcorn popcorn) {
		driveTrain = drive;
		elevator = lifter;
		grabber = popcorn;
	}

	double targetAngle = 0;
	int autoStep = 0;
	double encoder90Value = 12;
	// changed from 18.8 to 17 to 8 to

	public void initialize() {
		autoStep = 0;
		driveTrain.resetEncoders();
		defaultInit();

		String gameData = DriverStation.getInstance().getGameSpecificMessage();

		if (gameData.length() > 0) {
			FieldProperties.initialize((gameData));

		}
	}

	double ninetyDegrees = 0;
	public void sideAuto(boolean switchPriority, boolean rightPosition) {
		switch(autoStep) {
		case 0: //drive past auto line no matter what
			if (driveTrain.getRightInches() <= 141) {
				driveTrain.tankDrive(0.7, 0.7);
			} else {
				driveTrain.resetEncoders();
				driveTrain.tankDrive(0, 0);
				autoStep++;
			}
			break;
		case 1: //logic for what to do now
			if(switchPriority) {
				if(rightPosition){
					if(FieldProperties.isRightSwitchOurs()) {
						autoStep = 4;
					} else if(FieldProperties.isRightScaleOurs()) {
						autoStep = 3;
					}
				}
			} else if(!switchPriority) {
				if(!rightPosition){
					if(FieldProperties.isLeftScaleOurs()) {
						autoStep = 3;
					} else if(FieldProperties.isLeftSwitchOurs()) {
						autoStep = 2;
					}
				}
			}
		case 2:
			if(rightPosition) {
				if() {
					
				}
			}
		
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void middleAuto() {

		SmartDashboard.putNumber("step", autoStep);
		driveTrain.printEncoderValue();

		/*
		 * This switch statement controls robot speed and turning
		 */
		if (FieldProperties.isRightSwitchOurs()) {
			switch (autoStep) {
			case 0:
				if (driveTrain.getLeftInches() <= 54) {
					driveTrain.tankDrive(0.7, 0.46);
				} else {
					driveTrain.resetEncoders();
					driveTrain.tankDrive(0, 0);
					autoStep++;
				}
				break;
			case 1:
				if (driveTrain.getRightInches() <= 54) {
					driveTrain.tankDrive(0.46, 0.7);
				} else {
					driveTrain.resetEncoders();
					driveTrain.tankDrive(0, 0);
					autoStep++;
				}
				break;
			case 2:
				if (driveTrain.getLeftInches() <= 20) {
					driveTrain.tankDrive(0.5, 0.5);
				}
				break;
			}
		} else if (FieldProperties.isLeftSwitchOurs()) {
			switch (autoStep) {
			case 0:
				if (driveTrain.getLeftInches() <= 82) {
					driveTrain.tankDrive(0.7, 0.46);
				} else {
					driveTrain.resetEncoders();
					driveTrain.tankDrive(0, 0);
					autoStep++;
				}
				break;
			case 1:
				if (driveTrain.getRightInches() <= 82) {
					driveTrain.tankDrive(0.46, 0.7);
				} else {
					driveTrain.resetEncoders();
					driveTrain.tankDrive(0, 0);
					autoStep++;
				}
				break;
			case 2:
				if (driveTrain.getLeftInches() <= 20) {
					driveTrain.tankDrive(0.5, 0.5);
				}
				break;
			}
		}

		// This switch statement handles the lifter
		switch (autoStep) {
		case 0:
			// fall through and raise lift to switch height
		case 1:
			//ditto
		case 2:
			elevator.goTo("switch");
			break;

		case 3:
			grabber.open();
			break;

		default:
			break;
		}

		/*
		 * switch(step) { case 0: if(ultrasonic.getDistance() >= 20) { step++; break;
		 * }else { drive.tankDrive(0.7, 0.7); break; } case 1:
		 * if(FieldProperties.isLeftSwitchOurs() ) { targetAngle = 30; //random made up
		 * angle do math to find out }else { targetAngle = -30; //ditto }
		 * if(Math.abs(normalizeAngle(ahrs.getAngle() - targetAngle)) < 1) { step++;
		 * break; }else { break; } case 2: if() { step++; break; }else { break; } case
		 * 3: if() { step++; break; }else { break; } case 4: if() { step++; break; }else
		 * { break; }
		 * 
		 * }
		 */

	}

	public void eliminations(boolean leftPosition) {
		switch(autoStep) {
		case 0:
			//120 inches to auto line our robot is 49" long 120 - 49 = 71
			if(driveTrain.getLeftInches() <= 75) {
				driveTrain.tankDrive(0.7, 0.7);
			} else {
				driveTrain.tankDrive(0, 0);
				if((FieldProperties.isLeftSwitchOurs() && leftPosition == true) || (FieldProperties.isRightSwitchOurs() && leftPosition == false)) {
					autoStep++;
				}
			}
			break;
		case 1:
			if(Math.abs(elevator.getCurrentOutputPercent()) < 0.5) {
				elevator.goTo("switch");
			} else {
				autoStep++;
			}
			break;
		case 2:
			if(driveTrain.getLeftInches() <= 15) {
				driveTrain.tankDrive(0.7, 0.7);
			} else {
					autoStep++;
			}
			break;
		case 3: 
				grabber.open();
				break;
		}
	}
	
	public void leftAuto() {
		switch(autoStep) {
		case 0:
			//120 inches to auto line our robot is 49" long 120 - 49 = 71
			if(driveTrain.getLeftInches() <= 75) {
				driveTrain.tankDrive(0.7, 0.7);
			} else {
				driveTrain.tankDrive(0, 0);
				if(FieldProperties.isLeftSwitchOurs()) {
					autoStep++;
				}
			}
			break;
		case 1:
			if(Math.abs(elevator.getCurrentOutputPercent()) < 0.5) {
				elevator.goTo("switch");
			} else {
				autoStep++;
			}
			break;
		case 2:
			if(driveTrain.getLeftInches() <= 15 && driveTrain.getRightInches() <= 15) {
				driveTrain.tankDrive(0.7, 0.7);
			} else {
				driveTrain.tankDrive(0, 0);	
				autoStep++;
			}
			break;
		case 3: 
				grabber.open();
				break;
		}
	}

	public void rightAuto() {
		switch (autoStep) {
		case 1:
			if (driveTrain.getLeftInches() > 180 && driveTrain.getRightInches() > 180) {
				driveTrain.tankDrive(0, 0);
				autoStep++;
			} else {
				driveTrain.tankDrive(0.7, 0.7);
			}
		}
	}

	public void defaultInit() {
		long start = System.currentTimeMillis();
		endTime = start + 4500;
	}

	public void defaultAuto() {
		if (driveTrain.getLeftInches() > 180 && driveTrain.getRightInches() > 180) {
			driveTrain.tankDrive(0, 0);
		} else {
			driveTrain.tankDrive(0.5, 0.5);
		}
	}

	public static double normalizeAngle(double input) {
		double output = input;
		while (output > 180) {
			output = output - 360;
		}
		while (output < -180) {
			output = output + 360;
		}
		return output;
	}
}
