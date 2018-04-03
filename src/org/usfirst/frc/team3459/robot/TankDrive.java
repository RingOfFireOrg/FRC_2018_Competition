package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TankDrive extends DifferentialDrive {
	private Encoder leftEncoder = new Encoder(RobotMap.DRIVE_TRAIN_LEFT_ENCODER_A, RobotMap.DRIVE_TRAIN_LEFT_ENCODER_B,
			true, Encoder.EncodingType.k1X);
	private Encoder rightEncoder = new Encoder(RobotMap.DRIVE_TRAIN_RIGHT_ENCODER_A,
			RobotMap.DRIVE_TRAIN_RIGHT_ENCODER_B, false, Encoder.EncodingType.k1X);

	private double accumulatedError;

	TankDrive() {
		super(new SpeedControllerGroup(new Victor(RobotMap.MOTOR_FRONT_LEFT), new Victor(RobotMap.MOTOR_BACK_LEFT)),
				new SpeedControllerGroup(new Victor(RobotMap.MOTOR_FRONT_RIGHT),
						new Victor(RobotMap.MOTOR_BACK_RIGHT)));
		initEncoder(leftEncoder);
		initEncoder(rightEncoder);
	}

	public void setupEncoders() {
		initEncoder(leftEncoder);
		initEncoder(rightEncoder);
	}

	private void initEncoder(Encoder encoder) {
		encoder.reset();
		encoder.setSamplesToAverage(5); // noise reduction?
		// Set Distance to one tire diameter
		encoder.setDistancePerPulse(1.0 / 360); // should see 1 pulse per rotation
	}

	public void printEncoderValue() {
		// SmartDashboard.putNumber("left Encoder", leftEncoder.get());
		SmartDashboard.putNumber("left rotations", leftEncoder.getDistance());
		SmartDashboard.putNumber("left distance inches", getLeftInches());
		// SmartDashboard.putNumber("right Encoder", rightEncoder.get());
		SmartDashboard.putNumber("right rotations", rightEncoder.getDistance());
		SmartDashboard.putNumber("right distance inches", getRightInches());

	}

	public double getLeftInches() {
		// Tire Rotations divided by Tire Diameter = Inches
		return leftEncoder.getDistance() * RobotMap.TIRE_DIAMETER;
	}

	public double getRightInches() {
		// Tire Rotations divided by Tire Diameter = Inches
		return rightEncoder.getDistance() * RobotMap.TIRE_DIAMETER;
	}

	public void resetEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
		accumulatedError = 0.0;
	}

	public void driveStraight(double speed) {
		double difference = this.getLeftInches() - this.getRightInches();
		double speedDifference = difference / 10;
		accumulatedError = +difference;
		SmartDashboard.putNumber("Accumulated Error", accumulatedError);
		this.tankDrive(speed - speedDifference, speed, false);
	}

	public void pivotTurn(double speed) {
		double diff = getRightInches() + getLeftInches();
		double speedDiff = diff / 10;
		if (speed >= 0 && speedDiff <= 0) { // right turn left wheel too slow
			this.tankDrive(speed - speedDiff, -speed, false);
		} else if (speed >= 0 && speedDiff >= 0) { // right turn right wheel too slow
			this.tankDrive(speed, -speed - speedDiff, false);
		} else if (speed <= 0 && speedDiff <= 0) { // left turn left wheel too slow
			this.tankDrive(speed, -speed - speedDiff, false);
		} else { // left turn right wheel too slow
			this.tankDrive(speed - speedDiff, -speed, false);
		}
	}
}
