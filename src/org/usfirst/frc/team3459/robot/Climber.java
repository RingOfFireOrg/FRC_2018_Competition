package org.usfirst.frc.team3459.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Climber {
public void climb(boolean button, double speed) {
	TalonSRX climb0 = new TalonSRX(RobotMap.CAN_CLIMBER_1);
	TalonSRX climb1 = new TalonSRX(RobotMap.CAN_CLIMBER_2);
	if (button) {
		climb0.set(ControlMode.PercentOutput, speed);
		climb1.set(ControlMode.PercentOutput, speed);
	} else {
		climb0.set(ControlMode.PercentOutput, 0.0);
		climb1.set(ControlMode.PercentOutput, 0.0);
	}
}
}
