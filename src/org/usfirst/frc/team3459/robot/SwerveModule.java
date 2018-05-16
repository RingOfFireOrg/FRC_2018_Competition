package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;

public class SwerveModule {
	Jaguar drive;
	Talon steer;
	AbsoluteAnalogEncoder turnEncoder;
	// PIDController pid = new PIDController(1, 0, 0, turnEncoder.getAngle,
	// turnSpeed);
	double speed;
	double turnSpeed;
	double diff;
	double angleGoal;
	double currentAngle;
	double zeroValue;
	PIDController pid;
	double tolerance = 5;

	public SwerveModule(Jaguar driveMotor, Talon steerMotor, AbsoluteAnalogEncoder steerEncoder, double zeroValue) {
		this.zeroValue = zeroValue;
		drive = driveMotor;
		steer = steerMotor;
		turnEncoder = steerEncoder;
		// pid = new PIDController(0.1, 0, 0, steerEncoder, steerMotor);
		// pid.enable();
	}

	public void setpidsetpoint(double input) {
		pid.setSetpoint(input);
	}

	private void setSpeed(double driveSpeed) {
		speed = driveSpeed;
		drive.set(speed);
	}

	public double convertToAbsolute(double wheelAngleGoal) {
		return ((wheelAngleGoal + zeroValue) + 720) % 360;
		// this method will eventually use some gyro stuff to change a field relative
		// command to a wheel module relative command
	}
	
	public void setAngle(double wheelAngleGoal) {
		angleGoal = convertToAbsolute(wheelAngleGoal);
		currentAngle = turnEncoder.getAngle();
		diff = angleGoal - currentAngle;
		// turnSpeed = pid.get();
		// SmartDashboard.putNumber("pid ", turnSpeed);

		if (Math.abs(diff) < tolerance || Math.abs(diff) > (360-tolerance)) {
			// stop
			steer.set(0);
		} else {
			// pid.setSetpoint(angle);
			// pid.enable
			// SmartDashboard.putNumber("diff value", diff);
			if ((diff > 0 && diff < 180) || diff < -180) {
				steer.set(0.6);
			} else {
				steer.set(-0.6);
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
