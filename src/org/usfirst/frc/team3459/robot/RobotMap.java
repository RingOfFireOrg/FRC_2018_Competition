package org.usfirst.frc.team3459.robot;

public final class RobotMap {

	//All PWMs for Robot
	public static final int CAN_LIFTER_1 = 7;
	public static final int CAN_LIFTER_2 = 8;
	public static final int CAN_CLIMBER_1 = 9;
	public static final int CAN_CLIMBER_2 = 13;
	
	//Digital I/O
	public static final int INPUT_UPPER_LIMIT_SW = 2;
	public static final int INPUT_LOWER_LIMIT_SW = 3;
	public static final int LIFT_ENCODER_A = 0;
	public static final int LIFT_ENCODER_B = 1;
	
	//Motor Speed
	public static final double DEFAULT_LIFT_SPEED = 0.5;
	public static final double MIN_LIFT_SPEED = 0.5;
	public static final double MAX_LIFT_SPEED = 1.0;
	
	public static final double DEFAULT_FIND_SPEED = 0.5;

	public static final int DRIVE_LEFT_STICK = 0;
	public static final int DRIVE_RIGHT_STICK = 1;
	public static final int MANIPULATOR_STICK = 2;
	//Joysticks
	public static final int LIFT_UP_BUTTON = 6;
	public static final int LIFT_DOWN_BUTTON = 4;
	public static final int CLIMB_BUTTON = 11;
	

	//Popcorn JoyStick Buttons
	public static final int POPCORN_OPEN = 1;
	public static final int POPCORN_CLOSE = 2;
		
	//Solenoids
	public static final int OPEN_SOLENOID = 0;
	public static final int CLOSE_SOLENOID = 1;



	
	
}
