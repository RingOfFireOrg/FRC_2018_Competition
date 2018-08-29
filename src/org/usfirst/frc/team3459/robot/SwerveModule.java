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

	private void setSpeed(double driveSpeed) {
		speed = driveSpeed;
		drive.set(speed);
	}

	public double convertToAbsolute(double wheelAngleGoal) {
		return ((wheelAngleGoal + zeroValue) + 720) % 360;
	}

	public double convertToRobotRelative(double wheelAngleGoal) {
		return ((wheelAngleGoal - zeroValue) + 720) % 360;
	}

	
	public void setAngle(double wheelAngleGoal) {
		angleGoal = convertToAbsolute(wheelAngleGoal);
		currentAngle = turnEncoder.getAngle();
		double diff = angleGoal - currentAngle;
		double newDiff;
		
		if (Math.abs(diff) < 5 || Math.abs(diff) > 355) {
			// stop
			steer.set(0);
		} else {
			// pid.setSetpoint(angle);
			// pid.enable
			//SmartDashboard.putNumber("diff value", diff);
		
			/*
			 *          ^ front of robot
			 *          |
			 *      4   |  1
			 *          |
			 *    ------+------
			 *          |
			 *       3  |  2
			 *          |
			 * 
			 */
			
			if (diff > 90 && diff < 270) //for quadrants 2 & 3
			{
				if (diff > 90 && diff < 180) 
				{
					newDiff = (diff - 180); //converting angles from quadrant 2 to quad 4
				} 
				else 
				{
					newDiff = (180 - diff); //converting from quad 3 to quad 1
				}
				steer.set(newDiff); 
				drive.set(-.6);//go backwards
			} 
			else //quads 1 & 4
			{
				if (diff > 180) //quad 4
				{
					newDiff = (diff - 360); //converting from large + to small -
				}
				else 
				{
					newDiff = diff; //quad 1, no change
				}
				steer.set(newDiff); 
				drive.set(.6);//forward
			}
		}


	}
	
	public double getAngle() {
		return turnEncoder.getAngle();
	}

	public void control(double driveSpeed, double wheelAngle) {
		setAngle(wheelAngle);
		setSpeed(driveSpeed);
	}
}
