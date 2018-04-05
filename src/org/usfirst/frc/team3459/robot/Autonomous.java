package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous {
	private TankDrive driveTrain;
	private Lifter elevator;
	private Popcorn grabber;
	private boolean doingSwitch = false;
	private boolean doingScale = false;
	private double ninetyDegreeInches = 5 * Math.PI;
	private int autoStep = 0;
	private long time;
	private long startTime;
	
	//TODO make the turns all rely on the the same encoder?
	//TODO twenty foot test
	//left worked 

	public Autonomous(TankDrive drive, Lifter lifter, Popcorn popcorn) {
		driveTrain = drive;
		elevator = lifter;
		grabber = popcorn;
	}

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

	public void twentyInches() {
		if(driveTrain.getRightInches() <= 240) {
			driveTrain.driveStraight(0.3);
			//driveTrain.tankDrive(0.3, 0.3);
		} else {
			driveTrain.tankDrive(0, 0);
		}
	}
	
	public void testTurn(boolean right) {
		switch (autoStep) {
		case 0:
			if (right) {
				if (driveTrain.getLeftInches() <= ninetyDegreeInches) {
					driveTrain.pivotTurn(0.5);
				} else {
					driveTrain.resetEncoders();
					driveTrain.tankDrive(0, 0);
					time = System.currentTimeMillis();
					autoStep++;
				}
			} else {
				if (driveTrain.getRightInches() <= ninetyDegreeInches) {
					driveTrain.pivotTurn(-0.5);
				} else {
					driveTrain.resetEncoders();
					driveTrain.tankDrive(0, 0);
					autoStep++;
				}
			}
			break;
		case 1:
			driveTrain.tankDrive(0, 0);
			break;

		}
	}

	public void centerAuto() {
		double centerAutoSpeed = 0.3;
		SmartDashboard.putNumber("Auto Step: ", autoStep);
		switch (autoStep) {
		case 0:
			time = System.currentTimeMillis();
			startTime = System.currentTimeMillis();
			autoStep++;
			break;
		case 1: // drive forward
			if (System.currentTimeMillis() - time <= 250) {
				driveTrain.driveStraight(1);
			} else {
				driveTrain.tankDrive(0, 0);
				autoStep++;
				time = System.currentTimeMillis();
			}
			break;
		case 2:
			if (System.currentTimeMillis() - time <= 500) {
			} else {
				driveTrain.resetEncoders();
				autoStep++;
			}
			break;
		case 3: // turn 90 degrees
			if (FieldProperties.isLeftSwitchOurs()) {
				if (driveTrain.getRightInches() <= ninetyDegreeInches) {
					driveTrain.pivotTurn(-centerAutoSpeed);
				} else {
					driveTrain.resetEncoders();
					driveTrain.tankDrive(0, 0);
					autoStep++;
				}
			} else { // if(FieldProperties.isRightSwitchOurs())
				if (driveTrain.getLeftInches() <= ninetyDegreeInches) {
					driveTrain.pivotTurn(centerAutoSpeed);
				} else {
					driveTrain.resetEncoders();
					driveTrain.tankDrive(0, 0);
					autoStep++;
				}
			}
			break;
		case 4: // drive sideways
			if (FieldProperties.isLeftSwitchOurs()) {
				if (driveTrain.getRightInches() <= 72) {
					driveTrain.driveStraight(centerAutoSpeed);
				} else {
					driveTrain.resetEncoders();
					driveTrain.tankDrive(0, 0);
					autoStep++;
					time = System.currentTimeMillis();
				}
			} else { // if(FieldProperties.isRightSwitchOurs())
				if (driveTrain.getLeftInches() <= 36) {
					driveTrain.driveStraight(centerAutoSpeed);
				} else {
					driveTrain.resetEncoders();
					driveTrain.tankDrive(0, 0);
					autoStep++;
					time = System.currentTimeMillis();
				}
			}
			break;
		case 5:
			if(System.currentTimeMillis() - time >= 700) {
				driveTrain.resetEncoders();
				driveTrain.tankDrive(0, 0);
				autoStep++;
				time = System.currentTimeMillis();
			}
			driveTrain.tankDrive(0, 0);
			break;
		case 6: // turn towards switch
			if (FieldProperties.isRightSwitchOurs()) {
				if (driveTrain.getRightInches() <= ninetyDegreeInches) {
					driveTrain.pivotTurn(-centerAutoSpeed);
				} else {
					driveTrain.resetEncoders();
					driveTrain.tankDrive(0, 0);
					autoStep++;
					time = System.currentTimeMillis();
				}
			} else { // if(FieldProperties.isLeftSwitchOurs())
				if (driveTrain.getLeftInches() <= ninetyDegreeInches) {
					driveTrain.pivotTurn(centerAutoSpeed);
				} else {
					driveTrain.resetEncoders();
					driveTrain.tankDrive(0, 0);
					autoStep++;
					time = System.currentTimeMillis();
				}
			}
			break;
		case 7: // drive toward switch
			if (System.currentTimeMillis() - time <= 3000) {
				driveTrain.driveStraight(centerAutoSpeed);
			} else {
				driveTrain.resetEncoders();
				driveTrain.tankDrive(0, 0);
				autoStep++;
				time = System.currentTimeMillis();
			}
			break;
		case 8:
			// open arms
			if(System.currentTimeMillis() - time >= 3000) {
				driveTrain.resetEncoders();
				autoStep++;
				time = System.currentTimeMillis();
			}
			driveTrain.tankDrive(0, 0);
			break;
		case 9:
			if (System.currentTimeMillis() - time <= 500) {
				driveTrain.driveStraight(-centerAutoSpeed);
			} else {
				autoStep++;
			}
			break;
		case 10:
			driveTrain.tankDrive(0, 0);
		}

		switch (autoStep) {
		case 0:
		case 1:
		case 2:
			break;
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
			elevator.goTo("switch");
			break;
		case 8:
			grabber.open();
			break;
		case 9:
		case 10:
			elevator.stop();
		}
	}
	
	public void sideAuto(boolean switchPriority, boolean rightPosition) {
		double sideAutoSpeed = 0.5;
		SmartDashboard.putNumber("Auto Step: ", autoStep);
		switch (autoStep) {
		case 0:
			time = System.currentTimeMillis();
			startTime = System.currentTimeMillis();
			autoStep++;
			break;
		case 1:
			if (System.currentTimeMillis() - time <= 500) {
				driveTrain.driveStraight(1);
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
			if (driveTrain.getRightInches() <= 145) {
				driveTrain.driveStraight(sideAutoSpeed);
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
			if (driveTrain.getRightInches() <= 145) {
				driveTrain.driveStraight(sideAutoSpeed);
			} else {
				driveTrain.resetEncoders();
				driveTrain.tankDrive(0, 0);
				autoStep++;
				time = System.currentTimeMillis();
			}
			break;
		case 6:
			if(System.currentTimeMillis() - time >= 1000) {
				driveTrain.resetEncoders();
				autoStep++;
				time = System.currentTimeMillis();
			}
			driveTrain.tankDrive(0, 0);
			break;
		case 7: // turn 90 degrees toward target
			if (rightPosition) {
				if (driveTrain.getRightInches() <= ninetyDegreeInches) {
					driveTrain.pivotTurn(-0.5);
				} else {
					driveTrain.resetEncoders();
					driveTrain.tankDrive(0, 0);
					time = System.currentTimeMillis();
					autoStep++;
				}
			} else { // if(leftPosistion)
				if (driveTrain.getLeftInches() <= ninetyDegreeInches) {
					driveTrain.pivotTurn(0.5);
				} else {
					driveTrain.resetEncoders();
					driveTrain.tankDrive(0, 0);
					time = System.currentTimeMillis();
					autoStep++;
				}
			}
			/*
			 * if(System.currentTimeMillis() - startTime >= 8000) { time =
			 * System.currentTimeMillis(); autoStep = 11; }
			 */
			break;
		case 8: // back up
			if (doingScale) {
				if (System.currentTimeMillis() - time <= 1000) {
					driveTrain.driveStraight(-sideAutoSpeed);
				} else {
					driveTrain.resetEncoders();
					driveTrain.tankDrive(0, 0);
					time = System.currentTimeMillis();
					autoStep++;
				}
			} else {
				time = System.currentTimeMillis();
				autoStep++;
			}
			break;
		case 9: // drive towards target final navigation
			if (System.currentTimeMillis() - time <= 1000) {
				driveTrain.driveStraight(sideAutoSpeed);
			} else {
				driveTrain.resetEncoders();
				driveTrain.tankDrive(0, 0);
				autoStep++;
			}
			//if (System.currentTimeMillis() - startTime >= 9000) {
			//	autoStep++;
			//}
			break;
		case 10:
			if(System.currentTimeMillis() - time >= 3000) {
				driveTrain.resetEncoders();
				autoStep++;
				time = System.currentTimeMillis();
			}
			driveTrain.tankDrive(0, 0);
			break;

		case 11:
			// Progression from 11 to 12 controlled by elevator switch statement
			break;
		case 12:
			if (System.currentTimeMillis() - time >= 1000) {
				time = System.currentTimeMillis();
				autoStep++;
			}
			break;
		case 13:
			if (System.currentTimeMillis() - time <= 500) {
				driveTrain.driveStraight(-sideAutoSpeed);
			} else {
				autoStep++;
			}
			break;
		case 14:
			driveTrain.tankDrive(0, 0);
			break;
		}

		switch (autoStep) {
		case 0: // shake
		case 1: // shake
		case 2: // shake
			break;
		case 3: // across auto line
		case 4: // logic
			elevator.goTo("switch");
			break;
		case 5: // extra drive distance
		case 6:
		case 7:// turn
		case 8: // back against wall
		case 9:// last drive
		case 10:
			if (doingSwitch) {
				elevator.goTo("switch");
			} else { // if(doingScale)
				elevator.goTo("scale");
			}
			break;
		case 11:
			grabber.open();
			autoStep++;
			time = System.currentTimeMillis();
			break;
		case 12:
		case 13:
			break;
		case 14: // terminate everything case used for testing
			elevator.stop();
		}
	}

	public void defaultAuto() {
		if (driveTrain.getLeftInches() > 180 && driveTrain.getRightInches() > 180) {
			driveTrain.tankDrive(0, 0);
		} else {
			driveTrain.driveStraight(0.5);
		}
	}
}
