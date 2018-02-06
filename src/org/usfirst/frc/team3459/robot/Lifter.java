package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.Talon;

public class Lifter {

	//Encoder
	//Upper Limit
	//Lower Limit
	
	//Motor Controller 1
	Talon controller1 = new Talon(RobotMap.PWM_LIFTER_1);
	
	//Motor Controller 2
	Talon controller2 = new Talon(RobotMap.PWM_LIFTER_2);
	
}
