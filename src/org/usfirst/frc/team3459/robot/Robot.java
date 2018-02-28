/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3459.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DriverStation;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;



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
	private Lifter lifter;
	private Popcorn popcorn;
	private Climber climber;
	int step = 0;
	UltrasonicSensor ultrasonic = new UltrasonicSensor(0);

	TankDrive drive = new TankDrive();

	Joystick leftStick = new Joystick(RobotMap.DRIVE_LEFT_STICK);
	Joystick rightStick = new Joystick(RobotMap.DRIVE_RIGHT_STICK);
	Joystick manipulatorStick = new Joystick(RobotMap.MANIPULATOR_STICK);
	/* end of list */
	
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		lifter = new Lifter();
		popcorn = new Popcorn();
		climber = new Climber();
		
		CameraServer.getInstance().startAutomaticCapture(); //camera code: NEEDS TO BE TESTED
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
		drive.tankDrive(-leftStick.getY(), -rightStick.getY());
		drive.printEncoderValue();
		
		boolean upPressed = manipulatorStick.getRawButton(RobotMap.LIFT_UP_BUTTON);
		boolean downPressed = manipulatorStick.getRawButton(RobotMap.LIFT_DOWN_BUTTON);
		
		if (upPressed) {
			lifter.up();
		} else if (downPressed) {
			lifter.down();
		} else {
			lifter.stop();
		}
		if (manipulatorStick.getRawButton(RobotMap.POPCORN_OPEN)) {
			popcorn.open();
		}
		if (manipulatorStick.getRawButton(RobotMap.POPCORN_CLOSE)) { 
			popcorn.close();
		}
		boolean IsClimbPressed = manipulatorStick.getRawButton(RobotMap.CLIMB_BUTTON);
		double climbSpeed = manipulatorStick.getThrottle();
		
		climber.climb(IsClimbPressed, climbSpeed);
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
		
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if (gameData.length() > 0)
		{
			FieldProperties.initialize((gameData));
			
		}
	}

	/**
	 * This function is called periodically during autonomous control (approx
	 * 20ms)
	 */
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case kMiddleAuto:
				middleAuto(); 
				break;
				
			case kLeftAuto:
				leftAuto();
				break;
				
			case kDefaultAuto:
			default:
				// Put default auto code here
				break;
		}
		
	}
	public void middleAuto()
	{
		switch(step) {
		case 0:
			if(ultrasonic.getDistance() >= 20) {
				step++;
				break;
			}else {
				drive.tankDrive(0.7, 0.7);
				break;
			}
		case 1:
			if( ) {
				double targetAngle = 30;
				//random made up angle do math to find out
			}else {
				double targetAngle = -30;
				//ditto
			}
			if(Math.abs(normalizeAngle(ahrs.getAngle() - targetAngle)) < 1) {
				step++;
				break;
			}else {
				break;
			}
		case 2:
			if() {
				step++;
				break;
			}else {
				break;
			}
		case 3:
			if() {
				step++;
				break;
			}else {
				break;
			}
		case 4:
			if() {
				step++;
				break;
			}else {
				break;
			}	
		
		}
		
		
}
	public void leftAuto()
	{
		if(!FieldProperties.isLeftSwitchOurs()) //will be part of case step 1
		{
			//drive forward 15 ft 
			//set step to 5
		}
		
		/*step one: drive forward 8 ft and lift to switch height
		 * step two: turn right 90 degrees
		 * step three: drive forward 2 ft
		 * step four: drop cube
		 * step five: stop
		 */
	}
	
	public void rightAuto() 
	{
		switch(step) {
		case 0:
			if(!FieldProperties.isRightSwitchOurs()) //will be part of case step 1
			{
				if (ultrasonic.getDistance() >= 180)
				{
					step = 5;
					drive.tankDrive(0, 0);
				}
				else {
					drive.tankDrive(0.7, 0.7);
				}
			}
				
			else 
			{
				if (ultrasonic.getDistance() >= 96)
				{
					step++;
					drive.tankDrive(0, 0);
					//raise lifter to switch height
				}
				else {
					drive.tankDrive(0.7, 0.7);
				}
			}
			break;
			
		case 1:
			//step two: turn left 90 degrees
			break;
			
		case 2:
			//step three: go forward two feet
				break;
		case 3:
			//step four: open arms
			break; 
			
		case 4:
			//stop
		break;
		}
	}
}


