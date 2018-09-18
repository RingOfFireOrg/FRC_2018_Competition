package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Talon;

public class SwerveModule {
	Jaguar drive;
	Talon steer;
	AbsoluteAnalogEncoder turnEncoder;
	// PIDController pid = new PIDController(1, 0, 0, turnEncoder.getAngle,
	// turnSpeed);
	double speed;
	double turnSpeed;
	double angleGoal;
	double currentAngle;
	double zeroValue;

	public SwerveModule(Jaguar driveMotor, Talon steerMotor, AbsoluteAnalogEncoder steerEncoder, double zeroValue) {
		this.zeroValue = zeroValue;
		drive = driveMotor;
		steer = steerMotor;
		turnEncoder = steerEncoder;
	}

	public double convertToAbsolute(double wheelAngleGoal) {
		return ((wheelAngleGoal + zeroValue) + 720) % 360;
	}

	public double convertToRobotRelative(double wheelAngleGoal) {
		return ((wheelAngleGoal - zeroValue) + 720) % 360;
	}

	public double getAngle() {
		return turnEncoder.getAngle();
	}

	public void control(double driveSpeed, double wheelAngle) {
		angleGoal = convertToAbsolute(wheelAngle);
		currentAngle = turnEncoder.getAngle();
		double diff = ((angleGoal - currentAngle) + 720) % 360; // diff is a number between -359.9 and 359.9 (720 range)
		double newDiff;

		if (Math.abs(diff) < 5 || Math.abs(diff) > 355) {
			// stop steering
			steer.set(0);
			drive.set(driveSpeed);
		} else if (Math.abs(diff) > 175 && Math.abs(diff) < 185){
			//stop steering
			steer.set(0);
			drive.set(-driveSpeed);
		} else {
			// pid.setSetpoint(angle);
			// pid.enable
			// SmartDashboard.putNumber("diff value", diff);

			/*
			 * ^ front of robot | 4 | 1 | ------+------ | 3 | 2 |
			 * 
			 */

			if (diff > 90 && diff < 270) // for quadrants 2 & 3
			{
				if (diff > 90 && diff < 180) {
					newDiff = (diff - 180); // converting angles from quadrant 2 to quad 4
				} else {
					newDiff = (diff - 180); // converting from quad 3 to quad 1
				}
				steer.set(newDiff);
				drive.set(-driveSpeed);// go backwards
			} else // quads 1 & 4
			{
				if (diff >= 270) // quad 4
				{
					newDiff = (diff - 360); // converting from large + to small -
				} else {
					newDiff = diff; // quad 1, no change
				}
				steer.set(newDiff);
				drive.set(driveSpeed);// forward
			}
		}
	}
}
