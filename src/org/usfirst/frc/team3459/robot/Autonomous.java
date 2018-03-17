package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
	TankDrive driveTrain;
	Lifter elevator;
	Popcorn grabber;
	long endTime;
	boolean doingSwitch = false;
	boolean doingScale = false;

	public Autonomous(TankDrive drive, Lifter lifter, Popcorn popcorn) {
		driveTrain = drive;
		elevator = lifter;
		grabber = popcorn;
	}

	double targetAngle = 0;
	int autoStep = 0;
	long time;
	// changed from 18.8 to 17 to 8 to

	public void initialize() {
		autoStep = 0;
		time = 0;
		doingSwitch = false;
		doingScale = false;
		driveTrain.resetEncoders();

		String gameData = DriverStation.getInstance().getGameSpecificMessage();

		if (gameData.length() > 0) {
			FieldProperties.initialize((gameData));

		}
	}

	double ninetyValue = 56;

	public void sideAuto(boolean switchPriority, boolean rightPosition) {
		SmartDashboard.putNumber("Auto Step", autoStep);

		switch (autoStep) {	
		case 0:
			time = System.currentTimeMillis();
			autoStep++;
			
		case 1:
			if (System.currentTimeMillis() - time <= 500) {
				driveTrain.tankDrive(1, 1);
			} else {
				driveTrain.tankDrive(0, 0);
				autoStep++;
				time = System.currentTimeMillis();
			}
			break;
			
		case 2:
			if (System.currentTimeMillis() - time <= 500) {
			} else {
				autoStep++;
			}
			break;
		
		case 3: // drive past auto line to correct position for switch
			if (driveTrain.getRightInches() <= 107) {
				driveTrain.tankDrive(0.5, 0.5, false);
			} else {
				driveTrain.resetEncoders();
				driveTrain.tankDrive(0, 0);
				autoStep++;
			}
			break;

		case 4: // logic for what to do now
			if (switchPriority) {
				if (rightPosition) {
					if (FieldProperties.isRightSwitchOurs()) {
						doingSwitch = true;
						autoStep = 6;
					} else if (FieldProperties.isRightScaleOurs()) {
						doingScale = true;
						autoStep = 5;
					}
				} else if (!rightPosition) {
					if (FieldProperties.isLeftSwitchOurs()) {
						doingSwitch = true;
						autoStep = 6;
					} else if (FieldProperties.isLeftScaleOurs()) {
						doingScale = true;
						autoStep = 5;
					}
				}
			} else {// if (!switchPriority) {
				if (rightPosition) {
					if (FieldProperties.isRightScaleOurs()) {
						doingScale = true;
						autoStep = 5;
					} else if (FieldProperties.isRightSwitchOurs()) {
						doingSwitch = true;
						autoStep = 6;
					}
				} else if (!rightPosition) {
					if (FieldProperties.isLeftScaleOurs()) {
						doingScale = true;
						autoStep = 5;
					} else if (FieldProperties.isLeftSwitchOurs()) {
						doingSwitch = true;
						autoStep = 6;
					}
				}
			}
			break;

		case 5: // extra drive distance for scale only
			if (driveTrain.getRightInches() <= 160) {
				driveTrain.tankDrive(0.5, 0.5, false);
			} else {
				driveTrain.resetEncoders();
				driveTrain.tankDrive(0, 0);
				autoStep++;
			}
			break;

		case 6: // turn 90 degrees toward target
			if (rightPosition) {
				if (driveTrain.getRightInches() <= ninetyValue) {
					driveTrain.tankDrive(-0.5, 0.5, false);
				} else {
					driveTrain.resetEncoders();
					driveTrain.tankDrive(0, 0);
					time = System.currentTimeMillis();
					autoStep++;
				}
			} else {
				if (driveTrain.getLeftInches() <= ninetyValue) {
					driveTrain.tankDrive(0.5, -0.5, false);
				} else {
					driveTrain.resetEncoders();
					driveTrain.tankDrive(0, 0);
					time = System.currentTimeMillis();
					autoStep++;
				}
			}
			break;
			
		case 7: // drive towards target final navigation
			if (System.currentTimeMillis() - time <= 1000) {
				driveTrain.tankDrive(0.5, 0.5, false);
			} else {
				driveTrain.resetEncoders();
				driveTrain.tankDrive(0, 0);
				autoStep++;
			}
			break;
		case 8:
			driveTrain.tankDrive(0, 0);
		}

		switch (autoStep) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
			break;
		case 5:
		case 6:
			if (doingSwitch) {
				elevator.goTo("switch");
			} else //if (doingScale) 
			{
				elevator.goTo("scale");
			}
			break;
		case 7:
			grabber.open();
			autoStep++;
			break;
		case 8:
			elevator.stop();
		}
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
