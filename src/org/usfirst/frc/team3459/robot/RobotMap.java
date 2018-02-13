package org.usfirst.frc.team3459.robot;

public final class RobotMap {

	//All PWMs for Robot
	public static final int PWM_LIFTER_1 = 3;
	public static final int PWM_LIFTER_2 = 1;
	
	//Digital I/O
	public static final int INPUT_UPPER_LIMIT_SW = 2;
	public static final int INPUT_LOWER_LIMIT_SW = 3;
	public static final int LIFT_ENCODER_A = 0;
	public static final int LIFT_ENCODER_B = 1;
	
	//Motor Speed
	public static final double DEFAULT_LIFT_SPEED = 0.5;
	public static final double MIN_LIFT_SPEED = 0.1;
	public static final double MAX_LIFT_SPEED = 1.0;
	
	public static final double DEFAULT_FIND_SPEED = 0.5;

	//Joysticks
	public static final int LIFT_JOYSTICK = 5;
	public static final int POPCORN_JOYSTICK = 1;
	
	//Lift JoyStick Buttons
	public static final int LIFT_UP_BUTTON = 3;
	public static final int LIFT_DOWN_BUTTON = 2;

	//Popcorn JoyStick Buttons
	public static final int POPCORN_OPEN = 11;
	public static final int POPCORN_CLOSE = 10;
	
	//Solenoids
	public static final int OPEN_SOLENOID = 0;
	public static final int CLOSE_SOLENOID = 1;

	
}
