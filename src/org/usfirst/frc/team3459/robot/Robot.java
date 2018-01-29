package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.AnalogInput;
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
	
	Joystick commandStick = new Joystick(0);
	
	Jaguar driveFrontRight = new Jaguar(2);
	Jaguar driveFrontLeft = new Jaguar(0);
	Jaguar driveBackRight = new Jaguar(6);
	Jaguar driveBackLeft = new Jaguar(4);

	Talon steerFrontRight = new Talon(1);
	Talon steerFrontLeft = new Talon(3);
	Talon steerBackRight = new Talon(7);
	Talon steerBackLeft = new Talon(5);
	
	
	AbsoluteAnalogEncoder steerEncoderFrontRight = new AbsoluteAnalogEncoder(0);
	AbsoluteAnalogEncoder steerEncoderFrontLeft = new AbsoluteAnalogEncoder(1);
	AbsoluteAnalogEncoder steerEncoderBackRight = new AbsoluteAnalogEncoder(2);
	AbsoluteAnalogEncoder steerEncoderBackLeft = new AbsoluteAnalogEncoder(3);
	
	
	//^numbers?????

	SwerveModule frontLeft = new SwerveModule(driveFrontLeft, steerFrontLeft, steerEncoderFrontLeft, 5);
	SwerveModule frontRight = new SwerveModule(driveFrontRight, steerFrontRight, steerEncoderFrontRight, 50);
	SwerveModule backLeft = new SwerveModule(driveBackLeft, steerBackLeft, steerEncoderBackLeft, 22);
	SwerveModule backRight = new SwerveModule(driveBackRight, steerBackRight, steerEncoderBackRight, 180);

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		steerBackLeft.setInverted(true);
		steerBackRight.setInverted(true);
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

	
		double speed = Math.pow(commandStick.getMagnitude(), 2);
		double direction = commandStick.getDirectionDegrees();
		SmartDashboard.putNumber("Joystick output", direction);
		
//		frontRight.setAngle(0);
//		frontLeft.setAngle(0);
//		backRight.setAngle(0);
//		backLeft.setAngle(0); 
		frontRight.control(speed, direction);
		frontLeft.control(speed, direction);
		backRight.control(speed, direction);
		backLeft.control(speed, direction);
		
		//steerFrontRight.set(1);
	
		SmartDashboard.putNumber("front right encoder: ", steerEncoderFrontRight.getAngle());
		SmartDashboard.putNumber("front left encoder: ", steerEncoderFrontLeft.getAngle());
		SmartDashboard.putNumber("back right encoder: ", steerEncoderBackRight.getAngle());
		SmartDashboard.putNumber("back left encoder: ", steerEncoderBackLeft.getAngle());
		
		SmartDashboard.putNumber("Corrected angle FR", frontRight.convertToRobotRelative(steerEncoderFrontRight.getAngle()));
		SmartDashboard.putNumber("Corrected angle FL", frontLeft.convertToRobotRelative(steerEncoderFrontLeft.getAngle()));
		SmartDashboard.putNumber("Corrected angle BR", backRight.convertToRobotRelative(steerEncoderBackRight.getAngle()));
		SmartDashboard.putNumber("Corrected angle BL", backLeft.convertToRobotRelative(steerEncoderBackLeft.getAngle()));
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

