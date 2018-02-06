package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class Lifter {

	// Encoder
	private Encoder encoder = new Encoder(RobotMap.LIFT_ENCODER_A, RobotMap.LIFT_ENCODER_B);
	private double lastEncoderValue = 0;
	private int rotation = 0;

	private DigitalInput upperLimitSwitch = new DigitalInput(RobotMap.INPUT_UPPER_LIMIT_SW);
	private DigitalInput lowerLimitSwitch = new DigitalInput(RobotMap.INPUT_LOWER_LIMIT_SW);

	private Talon controller1 = new Talon(RobotMap.PWM_LIFTER_1);
	private Talon controller2 = new Talon(RobotMap.PWM_LIFTER_2);
	
	public void up() {
		if (upperLimitSwitch.get()) {
			return;
		}
		double currentValue = encoder.get();
		if (currentValue < lastEncoderValue) {
			rotation++;
		}
		lastEncoderValue = currentValue;
		controller1.set(RobotMap.DEFAULT_LIFT_SPEED);
		controller2.set(RobotMap.DEFAULT_LIFT_SPEED);
	}

	public void down() {
		if (lowerLimitSwitch.get()) {
			rotation = 0;
			lastEncoderValue = 0;
			encoder.reset();
			return;
		}
		double currentValue = encoder.get();
		if (currentValue > lastEncoderValue) {
			rotation--;
		}
		lastEncoderValue = currentValue;
		controller1.set(-RobotMap.DEFAULT_LIFT_SPEED);
		controller2.set(-RobotMap.DEFAULT_LIFT_SPEED);
	}

	public void stop() {
		controller1.set(0);
		controller2.set(0);
	}
}
