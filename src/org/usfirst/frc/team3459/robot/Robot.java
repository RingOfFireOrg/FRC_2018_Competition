/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3459.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Don't change the name of this or it won't work. (The manifest looks for
 * "Robot")
 */
public class Robot extends IterativeRobot {
	/* list of autonomous choices go here */
	private static final String kDefaultAuto = "Default";
	private static final String kLeftAuto = "Left Auto";
	private static final String kRightAuto = "Right Auto";
	private static final String kMiddleAuto = "Middle Auto";
	private static final String kTestStraight = "Twenty inches forward";
	private static final String kSwitch = "switch";
	private static final String kScale = "scale";
	private static final String kTurnRight = "right turn";
	private static final String kTurnLeft = "left turn";
	private static final String kJustSwitch = "just switch";
	private static final String kNormalLogic = "normal logic";

	private Lifter lifter;
	private Popcorn popcorn;
	private Climber climber;
	private Autonomous auto;
	private TestMode testMode;
	// private PowerControl powerControl;

	TankDrive drive = new TankDrive();

	Joystick leftStick = new Joystick(RobotMap.DRIVE_LEFT_STICK);
	Joystick rightStick = new Joystick(RobotMap.DRIVE_RIGHT_STICK);
	Joystick manipulatorStick = new Joystick(RobotMap.MANIPULATOR_STICK);

	/* end of list */

	private String m_autoSelected;
	private String m_preferenceSelected;
	private String m_exclusionOfScale;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	private SendableChooser<String> m_preference = new SendableChooser<>();
	private SendableChooser<String> m_exclusion = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("Left Auto", kLeftAuto);
		m_chooser.addObject("Right Auto", kRightAuto);
		m_chooser.addObject("Middle Auto", kMiddleAuto);
		// m_chooser.addObject("Right Turn", kTurnRight);
		// m_chooser.addObject("Left Turn", kTurnLeft);
		// m_chooser.addObject("20 Inches Straight Test", kTestStraight);
		SmartDashboard.putData("Auto choice", m_chooser);

		m_preference.addDefault("Switch Priority", kSwitch);
		m_preference.addObject("Scale Priority", kScale);

		m_exclusion.addDefault("Normal Logic", kNormalLogic);
		m_exclusion.addObject("Switch ONLY", kJustSwitch);

		SmartDashboard.putData("Auto Priorities", m_preference);

		SmartDashboard.putData("Scale Exclusion", m_exclusion);

		lifter = new Lifter(manipulatorStick);
		popcorn = new Popcorn();
		climber = new Climber(manipulatorStick);
		// powerControl = new PowerControl();

		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(320, 240);
	}

	/**
	 * This function is called once when we go into the teleop mode
	 */
	@Override
	public void teleopInit() {
		targetHeight = 0;
	}

	public double targetHeight = 0;

	/**
	 * This function is called periodically during operator control (approx 20ms)
	 */
	@Override
	public void teleopPeriodic() {
		double leftSpeed = -leftStick.getY();
		double rightSpeed = -rightStick.getY();

		if (leftStick.getRawButton(RobotMap.ENCODER_RESET)) {
			drive.resetEncoders();
		}
		boolean isSpeedControlRequested = leftStick.getRawButton(RobotMap.LEFT_SPEED_CONTROL)
				&& rightStick.getRawButton(RobotMap.RIGHT_SPEED_CONTROL);
		if (isSpeedControlRequested || lifter.getSpeed(true) >= 0.2 || lifter.getSpeed(false) >= 0.2) {
			// this does the triggers as well as preventing the brownouts by slowing down
			// while the lift is running
			leftSpeed *= 0.8;
			rightSpeed *= 0.8;
		}

		// TODO Modify speed for brown out
		// leftSpeed = powerControl.correctForBrownout(leftSpeed);
		// rrightSpeed = powerControl.correctForBrownout(rightSpeed);
		drive.tankDrive(leftSpeed, rightSpeed);
		drive.printEncoderValue();

		boolean upPressed = manipulatorStick.getRawButton(RobotMap.LIFT_UP_BUTTON);
		boolean downPressed = manipulatorStick.getRawButton(RobotMap.LIFT_DOWN_BUTTON);

		double lifterSpeed = -manipulatorStick.getY();
		if (lifterSpeed > 0) {
			lifter.up(Math.min(lifterSpeed, RobotMap.MAX_LIFT_SPEED));
		} else if (lifterSpeed < 0) {
			lifter.down(Math.max(lifterSpeed, -RobotMap.MAX_LIFT_SPEED));
		} else {
			if (upPressed) { // TODO: Consider 4 case if statements to make behavior explicit
				lifter.up();
			} else if (downPressed) {
				lifter.down();
			} else {
				lifter.stop();
			}
		}
		if (manipulatorStick.getRawButton(RobotMap.POPCORN_OPEN)) {
			popcorn.open();
		}
		if (manipulatorStick.getRawButton(RobotMap.POPCORN_CLOSE)) {
			popcorn.close();
		}

		climber.climb();
	}

	/**
	 * This function is called once when we go into the test mode
	 */
	@Override
	public void testInit() {
		testMode = new TestMode(manipulatorStick, drive, lifter, popcorn);
		testMode.initialize();
		drive.resetEncoders();
		step = 0;
	}

	int step = 0;
	double time = 0;

	/**
	 * This function is called periodically during test mode, approximate every 20ms
	 */
	@Override
	public void testPeriodic() {
		drive.printEncoderValue();
		testMode.run();
	}

	/**
	 * This function is called once when we go into the Autonomous mode
	 */
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		m_preferenceSelected = m_preference.getSelected();
		m_exclusionOfScale = m_exclusion.getSelected();

		System.out.println("Auto selected: " + m_autoSelected);

		auto = new Autonomous(drive, lifter, popcorn);
		auto.initialize();
		popcorn.close();
	}

	/**
	 * This function is called periodically during autonomous control (approx 20ms)
	 */

	// center of switch 14 ft from alliance station
	@Override
	public void autonomousPeriodic() {
		drive.printEncoderValue();

		switch (m_autoSelected) {

		case kDefaultAuto:
			auto.defaultAuto();
			break;

		case kLeftAuto:
			switch (m_preferenceSelected) {
			case kSwitch:
				switch (m_exclusionOfScale) {
				case kJustSwitch:
					auto.sideAuto(true, false, true);
					break;
				case kNormalLogic:
					auto.sideAuto(true, false, false);
					break;
				}
				break;
			case kScale:
				auto.sideAuto(false, false, false);
				break;
			}
			break;

		case kRightAuto:
			switch (m_preferenceSelected) {
			case kSwitch:
				switch (m_exclusionOfScale) {
				case kJustSwitch:
					auto.sideAuto(true, true, true);
					break;
				case kNormalLogic:
					auto.sideAuto(true, true, false);
					break;
				}
				break;
			case kScale:
				auto.sideAuto(false, true, false);
				break;
			}
			break;

		case kMiddleAuto:
			auto.centerAuto();
			break;

		case kTurnRight:
			auto.testTurn(true);
			break;

		case kTurnLeft:
			auto.testTurn(false);
			break;

		case kTestStraight:
			auto.twentyInches();
			break;
		}

	}
}
