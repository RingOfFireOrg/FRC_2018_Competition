package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class Lifter {

	// Encoder
	Encoder encoder = new Encoder(RobotMap.LIFT_ENCODER_A, RobotMap.LIFT_ENCODER_B);
	double lastEncoderValue = 0;
	int rotation = 0;

	DigitalInput upperLimitSwitch = new DigitalInput(RobotMap.INPUT_UPPER_LIMIT_SW);
	DigitalInput lowerLimitSwitch = new DigitalInput(RobotMap.INPUT_LOWER_LIMIT_SW);

	Talon controller1 = new Talon(RobotMap.PWM_LIFTER_1);
	Talon controller2 = new Talon(RobotMap.PWM_LIFTER_2);

	public void up() {
		if (upperLimitSwitch.get()) {
			return;
		}
		double currentvalue = encoder.get();
		if (currentvalue < lastEncoderValue) {
			rotation++;
		}
		controller1.set(RobotMap.DEFAULT_LIFT_SPEED);
		controller2.set(RobotMap.DEFAULT_LIFT_SPEED);
	}

	public void down() {
		if (lowerLimitSwitch.get()) {
			rotation = 0;
			encoder.reset();
			return;
		}
		double currentvalue = encoder.get();
		if (currentvalue > lastEncoderValue) {
			rotation--;
		}
		controller1.set(-RobotMap.DEFAULT_LIFT_SPEED);
		controller2.set(-RobotMap.DEFAULT_LIFT_SPEED);
	}

	public void stop() {
		controller1.set(0);
		controller2.set(0);
	}
}
