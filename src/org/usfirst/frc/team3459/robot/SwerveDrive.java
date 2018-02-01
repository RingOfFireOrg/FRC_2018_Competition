package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveDrive {
	public static final int DRIVE_FRONT_LEFT_MOTOR = 0;
	public static final int DRIVE_FRONT_RIGHT_MOTOR = 2;
	public static final int DRIVE_BACK_LEFT_MOTOR = 4;
	public static final int DRIVE_BACK_RIGHT_MOTOR = 6;

	public static final int STEER_FRONT_LEFT_MOTOR = 3;
	public static final int STEER_FRONT_RIGHT_MOTOR = 1;
	public static final int STEER_BACK_LEFT_MOTOR = 5;
	public static final int STEER_BACK_RIGHT_MOTOR = 7;

	public static final int ENCODER_CORRECTION_FRONT_LEFT = 5;
	public static final int ENCODER_CORRECTION_FRONT_RIGHT = 50;
	public static final int ENCODER_CORRECTION_BACK_LEFT = 22;
	public static final int ENCODER_CORRECTION_BACK_RIGHT = 180;

	public static final int ENCODER_FRONT_LEFT = 1;
	public static final int ENCODER_FRONT_RIGHT = 0;
	public static final int ENCODER_BACK_LEFT = 3;
	public static final int ENCODER_BACK_RIGHT = 2;

	SwerveModule frontLeft;
	SwerveModule frontRight;
	SwerveModule backLeft;
	SwerveModule backRight;

	SwerveDrive() {
		Talon steerBackLeft = new Talon(STEER_BACK_LEFT_MOTOR);
		Talon steerBackRight = new Talon(STEER_BACK_RIGHT_MOTOR);
		
		frontLeft = new SwerveModule(new Jaguar(DRIVE_FRONT_LEFT_MOTOR), new Talon(STEER_FRONT_LEFT_MOTOR),
				new AbsoluteAnalogEncoder(ENCODER_FRONT_LEFT), ENCODER_CORRECTION_FRONT_LEFT);
		frontRight = new SwerveModule(new Jaguar(DRIVE_FRONT_RIGHT_MOTOR), new Talon(STEER_FRONT_RIGHT_MOTOR),
				new AbsoluteAnalogEncoder(ENCODER_FRONT_RIGHT), ENCODER_CORRECTION_FRONT_RIGHT);
		backLeft = new SwerveModule(new Jaguar(DRIVE_BACK_LEFT_MOTOR), steerBackLeft,
				new AbsoluteAnalogEncoder(ENCODER_BACK_LEFT), ENCODER_CORRECTION_BACK_LEFT);
		backRight = new SwerveModule(new Jaguar(DRIVE_BACK_RIGHT_MOTOR), steerBackRight,
				new AbsoluteAnalogEncoder(ENCODER_BACK_RIGHT), ENCODER_CORRECTION_BACK_RIGHT);

		steerBackLeft.setInverted(true);
		steerBackRight.setInverted(true);

	}
	void syncroDrive(double driveSpeed, double driveAngle, double twist) {
		if(twist > .5){
			frontRight.control(0.6, 45);
			frontLeft.control(0.6, 315);
			backRight.control(-0.6, 315);
			backLeft.control(0.6, 45);
		}else if(twist < -.5){
			frontRight.control(-0.6, 45);
			frontLeft.control(-0.6, 315);
			backRight.control(0.6, 315);
			backLeft.control(-0.6, 45);
		} else {
			frontRight.control(driveSpeed, driveAngle);
			frontLeft.control(driveSpeed, driveAngle);
			backRight.control(driveSpeed, driveAngle);
			backLeft.control(driveSpeed, driveAngle);
		}
		
		
		//steerFrontRight.set(1);
	
		SmartDashboard.putNumber("front right encoder: ", frontRight.getAngle());
		SmartDashboard.putNumber("front left encoder: ", frontLeft.getAngle());
		SmartDashboard.putNumber("back right encoder: ", backRight.getAngle());
		SmartDashboard.putNumber("back left encoder: ", backLeft.getAngle());
		
		SmartDashboard.putNumber("Corrected angle FR", frontRight.convertToRobotRelative(frontRight.getAngle()));
		SmartDashboard.putNumber("Corrected angle FL", frontLeft.convertToRobotRelative(frontLeft.getAngle()));
		SmartDashboard.putNumber("Corrected angle BR", backRight.convertToRobotRelative(backRight.getAngle()));
		SmartDashboard.putNumber("Corrected angle BL", backLeft.convertToRobotRelative(backLeft.getAngle()));
	}
	
	void setpidsetpoint(double input) {
		frontRight.setpidsetpoint(input);
		frontLeft.setpidsetpoint(input);
		backRight.setpidsetpoint(input);
		backLeft.setpidsetpoint(input);
	}
}
