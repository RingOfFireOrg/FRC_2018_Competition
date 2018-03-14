/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
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
	private static final String kMiddleAuto = "Middle Auto";
	private static final String kRightAuto = "Right Auto";
	private static final String kSideAuto = "Side Auto";
	private Lifter lifter;
	private Popcorn popcorn;
	private Climber climber;
	private Autonomous auto;
	private TestMode testMode;

	TankDrive drive = new TankDrive();

	Joystick leftStick = new Joystick(RobotMap.DRIVE_LEFT_STICK);
	Joystick rightStick = new Joystick(RobotMap.DRIVE_RIGHT_STICK);
	Joystick manipulatorStick = new Joystick(RobotMap.MANIPULATOR_STICK);

	/* end of list */

	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("Left Auto", kLeftAuto);
		m_chooser.addObject("Middle Auto", kMiddleAuto);
		m_chooser.addObject("Right Auto", kRightAuto);
		m_chooser.addObject("Elims Left", kLeftAuto);
		m_chooser.addObject("Elims Right Auto", kLeftAuto);
		m_chooser.addObject("Side Auto", kSideAuto);
		SmartDashboard.putData("Auto choice", m_chooser);
		
		lifter = new Lifter(manipulatorStick);
		popcorn = new Popcorn();
		climber = new Climber(manipulatorStick);

		CameraServer.getInstance().startAutomaticCapture(); // camera code: NEEDS TO BE TESTED
		
		
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
		drive.tankDrive(-leftStick.getY(), -rightStick.getY());
		drive.printEncoderValue();

		boolean upPressed = manipulatorStick.getRawButton(RobotMap.LIFT_UP_BUTTON);
		boolean downPressed = manipulatorStick.getRawButton(RobotMap.LIFT_DOWN_BUTTON);

		double lifterSpeed = -manipulatorStick.getY();
		if (lifterSpeed > 0) {
			lifter.up(Math.min(lifterSpeed, RobotMap.MAX_LIFT_SPEED));
		} else if (lifterSpeed < 0) {
			lifter.down(Math.max(lifterSpeed, -RobotMap.MAX_LIFT_SPEED));
		} else {
			if (upPressed) {	//TODO: Consider 4 case if statements to make behavior explicit
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
	}

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
		case kMiddleAuto:
			auto.middleAuto();
			break;

		case kLeftAuto:
			auto.leftAuto();
			break;

		case kRightAuto:
			auto.rightAuto();
			break;

		case kDefaultAuto:
		default:
			auto.defaultAuto();
			break;
		}

	}
}
