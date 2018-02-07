/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3459.robot;

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
	private static final String kCustomAuto = "My Auto";
	CascadingLift lift = new CascadingLift(4, 5);
	TankDrive drive = new TankDrive();
	Joystick stick = new Joystick(1);
	double stickX, stickY;
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
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
	}

	/**
	 * This function is called once when we go into the teleop mode
	 */
	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during operator control (approx 20ms)
	 */
	@Override
	public void teleopPeriodic() {
		if (stick.getY() > 0.05 || stick.getY() < -0.05) {
			stickY = stick.getY()*stick.getY()*stick.getY();
		}
		if (stick.getX() > 0.05 || stick.getX() < -0.05) {
			stickX = stick.getX()*stick.getX()*stick.getX();
		}
		drive.arcadeDrive(-stick.getY(), stick.getX());
		
		if (stick.getTrigger()) {
			lift.startUp();
		} else if (stick.getRawButton(3)) {
			lift.startDown();
		} else {
			lift.stop();
		}
	}

	/**
	 * This function is called once when we go into the test mode
	 */
	@Override
	public void testInit() {

	}

	/**
	 * This function is called periodically during test mode, approximate every 20ms
	 */
	@Override
	public void testPeriodic() {
		if (stick.getRawButtonPressed(5)) {
			lift.startUp();
		}
		if (stick.getRawButtonPressed(3)) {
			lift.startDown();
		}
		if (stick.getTrigger()) {
			lift.stop();
		}
	}

	/**
	 * This function is called once when we go into the Autonomous mode
	 */
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();

		System.out.println("Auto selected: " + m_autoSelected);
	}

	/**
	 * This function is called periodically during autonomous control (approx 20ms)
	 */
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
		case kCustomAuto:
			// Put custom auto code here
			break;
		case kDefaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}
}
