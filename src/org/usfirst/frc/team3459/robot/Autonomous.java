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

		String gameData = DriverStation.getInstance().getGameSpecificMessage();

		if (gameData.length() > 0) {
			//FieldProperties.initialize((gameData));

		}
	}

	double targetAngle = 0;
	int autoStep = 0;
	double encoder90Value = 18.8;

	public void initialize()
	{
		autoStep = 0;
		driveTrain.resetEncoders();
		defaultInit();
	}

	public void middleAuto() {

		SmartDashboard.putNumber("step", autoStep);
		
		switch (autoStep) { //TODO: driveTrain.resetEncoders() after each step
		case 0: // move away from wall
			driveTrain.printEncoderValue();
			if (driveTrain.getLeftDistance() > 17 && driveTrain.getRightDistance() > 17) {
				driveTrain.tankDrive(0, 0);
				autoStep++;
				driveTrain.resetEncoders();
			} else {
				driveTrain.tankDrive(0.7, 0.7);
			}
			break;
		case 1://turn 90 degrees
			if (FieldProperties.isRightSwitchOurs()) {
				if (driveTrain.getLeftDistance() >= encoder90Value) {
					driveTrain.tankDrive(0, 0);
					autoStep++;
					driveTrain.resetEncoders();
				} else {
					driveTrain.tankDrive(0.7, -0.7);
				}
			} else {
				if (driveTrain.getRightDistance() >= encoder90Value) {
					driveTrain.tankDrive(0, 0);
					autoStep++;
					driveTrain.resetEncoders();
				} else {
					driveTrain.tankDrive(-0.7, 0.7);
				}
			}
			break;
		case 2: //drive forward 36 inches if right switch and 72 inches if left switch
			if (FieldProperties.isRightSwitchOurs()) {
				if (driveTrain.getLeftDistance() >= 36 && driveTrain.getRightDistance() >= 36) {
					driveTrain.tankDrive(0, 0);
					autoStep++;
					driveTrain.resetEncoders();
				} else {
					driveTrain.tankDrive(0.7, 0.7);
				}
			} else {
				if (driveTrain.getLeftDistance() > 72 && driveTrain.getRightDistance() > 72) {
					driveTrain.tankDrive(0, 0);
					autoStep++;
					driveTrain.resetEncoders();
				} else {
					driveTrain.tankDrive(0.7, 0.7);
				}
			}
			break;

		case 3: //turn 90 degrees towards switch
			if (FieldProperties.isRightSwitchOurs()) {
				if (driveTrain.getRightDistance() >= encoder90Value) {
					driveTrain.tankDrive(0, 0);
					autoStep++;
					driveTrain.resetEncoders();
				} else {
					driveTrain.tankDrive(-0.7, 0.7);
				}

			} else {
				if (driveTrain.getLeftDistance() >= encoder90Value) {
					driveTrain.tankDrive(0, 0);
					autoStep++;
					driveTrain.resetEncoders();
				} else {
					driveTrain.tankDrive(0.7, -0.7);
				}
			}
			break;
		case 4: //raise the lift
			//break;
		case 5: //drive up against the switch
			if (driveTrain.getLeftDistance() > 84.5 && driveTrain.getRightDistance() > 84.5) {
				driveTrain.tankDrive(0, 0);
				//autoStep++;
				autoStep = 6; 
				driveTrain.resetEncoders();
			} else {
				driveTrain.tankDrive(0.7, 0.7);
			}
			break;
		case 6:
		default:
			break;

		}
/*
		
		switch (autoStep) {
		case 0: // move away from wall
		case 1: //first turn
			break;

		case 2: //traverse
		case 3: //second turn
			elevator.goTo("switch");
			break;

		case 4: // move away from wall
			elevator.goTo("switch");
			if (Math.abs(elevator.getCurrentOutputPercent()) < 0.05)
			{
				autoStep++;
			}
			break;

		case 5:
			break;

		case 6: //drop the cube
			grabber.open();
			break;

		default:
			break;
		}
	*/
		

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

	public void leftAuto() {
		if (driveTrain.getLeftDistance() > 180 && driveTrain.getRightDistance() > 180) {
			driveTrain.tankDrive(0, 0);
			autoStep++;
		} else {
			driveTrain.tankDrive(0.7, 0.7);
		}

		/*
		 * if(!FieldProperties.isLeftSwitchOurs()) //will be part of case step 1 {
		 * //drive forward 15 ft //set step to 5 }
		 */
		/*
		 * step one: lift to switch height and drive forward 8 ft step two: turn right
		 * 90 degrees step three: drive forward 2 ft step four: drop cube step five:
		 * stop
		 */
	}

	public void rightAuto() {
		if (driveTrain.getLeftDistance() > 180 && driveTrain.getRightDistance() > 180) {
			driveTrain.tankDrive(0, 0);
			autoStep++;
		} else {
			driveTrain.tankDrive(0.7, 0.7);
		}
	}
	
	public void defaultInit() {
		long start = System.currentTimeMillis();
		endTime = start + 4500;
	}
	
	public void defaultAuto() {
		long time = System.currentTimeMillis();
		
		if (time < endTime) {
			driveTrain.tankDrive(0.6, 0.6);
		}
		else {
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
