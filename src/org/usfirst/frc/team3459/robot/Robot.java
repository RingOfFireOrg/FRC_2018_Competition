package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Encoder;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	
	Joystick commandstick = new Joystick(0);
	
	Jaguar driveFrontRight = new Jaguar(2);
	Jaguar driveFrontLeft = new Jaguar(0);
	Jaguar driveBackRight = new Jaguar(6);
	Jaguar driveBackLeft = new Jaguar(4);

	Talon steerFrontRight = new Talon(1);
	Talon steerFrontLeft = new Talon(3);
	Talon steerBackRight = new Talon(5);
	Talon steerBackLeft = new Talon(7);
	
	AbsoluteAnalogEncoder steerEncoderFrontRight = new AbsoluteAnalogEncoder(0);
	AbsoluteAnalogEncoder steerEncoderFrontLeft = new AbsoluteAnalogEncoder(1);
	AbsoluteAnalogEncoder steerEncoderBackRight = new AbsoluteAnalogEncoder(2);
	AbsoluteAnalogEncoder steerEncoderBackLeft = new AbsoluteAnalogEncoder(3);
	
	//^numbers?????
	
	SwerveModule frontLeft = new SwerveModule(driveFrontLeft, steerFrontLeft, steerEncoderFrontLeft);
	SwerveModule frontRight = new SwerveModule(driveFrontRight, steerFrontRight, steerEncoderFrontRight);
	SwerveModule backLeft = new SwerveModule(driveBackLeft, steerBackLeft, steerEncoderBackLeft);
	SwerveModule backRight = new SwerveModule(driveBackRight, steerBackRight, steerEncoderBackRight);
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		/*
		double speed = commandStick.getThrottle();
		double direction = commandStick.getDirectionDegrees();
		frontRight.control(speed, direction - 255.791);
		frontLeft.control(speed, direction - 180.703);
		backRight.control(speed, direction - 199.160);
		backLeft.control(speed, direction - 3.340);
		*/
		
	/*	frontRight.setAngle(180 - 255.791);
		frontLeft.setAngle(180 - 180.703);
		backRight.setAngle(180  - 199.160);
		backLeft.setAngle(180 - 199.160); */
		steerFrontRight.set(1);
		
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

