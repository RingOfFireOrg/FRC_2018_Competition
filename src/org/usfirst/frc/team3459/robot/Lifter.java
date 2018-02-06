package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

public class Lifter {

	//Encoder

	DigitalInput upperLimitSwitch = new DigitalInput(RobotMap.INPUT_UPPER_LIMIT_SW);
	DigitalInput lowerLimitSwitch = new DigitalInput(RobotMap.INPUT_LOWER_LIMIT_SW);
	
	Talon controller1 = new Talon(RobotMap.PWM_LIFTER_1);
	Talon controller2 = new Talon(RobotMap.PWM_LIFTER_2);
	
}
