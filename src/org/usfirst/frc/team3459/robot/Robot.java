/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3459.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
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
	TankDrive drive = new TankDrive();
	
	TalonSRX lift0 = new TalonSRX(4);
	TalonSRX lift1 = new TalonSRX(5);
	Victor intake0 = new Victor(4);
	Victor intake1 = new Victor(5);
	Victor foldout = new Victor(6);
	
	Joystick stick0 = new Joystick(0);
	Joystick stick1 = new Joystick(1);
	Joystick stick2 = new Joystick(2);
	/* end of list */

	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	private Popcorn popcorn;
	private Joystick stick;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		popcorn = new Popcorn();
		stick = new Joystick(RobotMap.POPCORN_JOYSTICK);
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
		drive.tankDrive(-stick0.getY(), -stick1.getY());
		
		lift0.set(ControlMode.PercentOutput, stick2.getY());
		lift1.set(ControlMode.PercentOutput, stick2.getY());
		
		if (stick2.getRawButton(3)) {
			intake0.set(1.0);
			intake1.set(1.0);			
		} else if (stick2.getRawButton(5)) {
			intake0.set(-1.0);
			intake1.set(-1.0);
		} else {
			intake0.set(0);
			intake1.set(0);
		}
		
		if (stick2.getRawButton(4)) {
			foldout.set(0.4);
		} else if (stick2.getRawButton(6)){
			foldout.set(-0.4);
		} else {
			foldout.set(0.0);
		}

		if (stick2.getRawButton(RobotMap.POPCORN_OPEN)) {
			popcorn.open();
		}
		if (stick2.getRawButton(RobotMap.POPCORN_CLOSE)) {
			popcorn.close();
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
