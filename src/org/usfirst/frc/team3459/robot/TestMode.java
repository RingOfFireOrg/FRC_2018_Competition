package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestMode {

	TankDrive driveTrain;
	Lifter elevator;
	Popcorn grabber;
	long endTime;

	public TestMode(TankDrive drive, Lifter lifter, Popcorn popcorn) {
		driveTrain = drive;
		elevator = lifter;
		grabber = popcorn;

		String gameData = DriverStation.getInstance().getGameSpecificMessage();

		if (gameData.length() > 0) {
			FieldProperties.initialize(gameData);
		}
	}

	double targetAngle = 0;
	int autoStep = 0;
	double encoder90Value = 18.8;

	public void initialize() {
		autoStep = 0;
		driveTrain.resetEncoders();
		defaultInit();
	}

	public void run() {

		SmartDashboard.putNumber("step", autoStep);
		driveTrain.printEncoderValue();

//		switch (autoStep) {
//		case 0: // move away from wall
//			if (driveTrain.getLeftDistance() > 6 && driveTrain.getRightDistance() > 6) {
//				driveTrain.tankDrive(0, 0);
//				autoStep++;
//				driveTrain.resetEncoders();
//			} else {
//				driveTrain.tankDrive(0.7, 0.7);
//			}
//			break;
//		case 1:// turn 90 degrees
//			if (FieldProperties.isRightSwitchOurs()) {
//				if (driveTrain.getLeftDistance() >= encoder90Value) {
//					driveTrain.tankDrive(0, 0);
//					autoStep++;
//					driveTrain.resetEncoders();
//				} else {
//					driveTrain.tankDrive(-0.7, 0.7);
//				}
//			} else {
//				if (driveTrain.getRightDistance() >= encoder90Value) {
//					driveTrain.tankDrive(0, 0);
//					autoStep++;
//					driveTrain.resetEncoders();
//				} else {
//					driveTrain.tankDrive(0.7, -0.7);
//				}
//			}
//			break;
//		case 2: // drive forward 36 inches if right switch and 72 inches if left switch
//			if (FieldProperties.isRightSwitchOurs()) {
//				if (driveTrain.getLeftDistance() >= 36 && driveTrain.getRightDistance() >= 36) {
//					driveTrain.tankDrive(0, 0);
//					autoStep++;
//					driveTrain.resetEncoders();
//				} else {
//					driveTrain.tankDrive(0.7, 0.7);
//				}
//			} else {
//				if (driveTrain.getLeftDistance() > 72 && driveTrain.getRightDistance() > 72) {
//					driveTrain.tankDrive(0, 0);
//					autoStep++;
//					driveTrain.resetEncoders();
//				} else {
//					driveTrain.tankDrive(0.7, 0.7);
//				}
//			}
//			break;
//
//		case 3: // turn 90 degrees towards switch
//			if (FieldProperties.isRightSwitchOurs()) {
//				if (driveTrain.getRightDistance() >= encoder90Value) {
//					driveTrain.tankDrive(0, 0);
//					autoStep++;
//					driveTrain.resetEncoders();
//				} else {
//					driveTrain.tankDrive(0.7, -0.7);
//				}
//
//			} else {
//				if (driveTrain.getLeftDistance() >= encoder90Value) {
//					driveTrain.tankDrive(0, 0);
//					autoStep++;
//					driveTrain.resetEncoders();
//				} else {
//					driveTrain.tankDrive(-0.7, 0.7);
//				}
//			}
//			break;
//		case 4: // raise the lift
//			// break;
//		case 5: // drive up against the switch
//			if (driveTrain.getLeftDistance() > 84.5 && driveTrain.getRightDistance() > 84.5) {
//				driveTrain.tankDrive(0, 0);
//				// autoStep++;
//				autoStep = 6;
//				driveTrain.resetEncoders();
//			} else {
//				driveTrain.tankDrive(0.7, 0.7);
//			}
//			break;
//		case 6:
//		default:
//			break;
//
//		}
	}

	public void defaultInit() {
		long start = System.currentTimeMillis();
		endTime = start + 4500;
	}

	public void defaultAuto() {
		long time = System.currentTimeMillis();

		if (time < endTime) {
			driveTrain.tankDrive(0.6, 0.6);
		} else {
			driveTrain.tankDrive(0, 0);
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
