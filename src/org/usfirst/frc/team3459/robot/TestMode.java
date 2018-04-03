package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TestMode {

	TankDrive driveTrain;
	Lifter elevator;
	Popcorn grabber;
	long endTime;
	private Joystick manipulatorStick;
	public double targetHeight = 0;

	public TestMode(Joystick manipulatorStick, TankDrive drive, Lifter lifter, Popcorn popcorn) {
		this.manipulatorStick = manipulatorStick;
		driveTrain = drive;
		elevator = lifter;
		grabber = popcorn;
		String gameData = DriverStation.getInstance().getGameSpecificMessage();

		if (gameData.length() > 0) {
			FieldProperties.initialize(gameData);
		}
	}

	public void initialize() {
		driveTrain.resetEncoders();
		defaultInit();
	}
	
	public void run() {
		driveTrain.printEncoderValue();
		SmartDashboard.putNumber("target Lift Location", targetHeight);
		
		if (manipulatorStick.getRawButton(RobotMap.TEST_UP)) {
			targetHeight++;
		}
		if (manipulatorStick.getRawButton(RobotMap.TEST_DOWN)) {
			targetHeight--;
		}
	}

	public void defaultInit() {
		long start = System.currentTimeMillis();
		endTime = start + 4500;
	}
}