package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lifter {

	// Encoder
	private Encoder encoder = new Encoder(RobotMap.LIFT_ENCODER_A, RobotMap.LIFT_ENCODER_B);
	private double lastEncoderValue = 0;
	private int rotation = 0;
	private int totalRotations = 0;

	private DigitalInput upperLimitSwitch = new DigitalInput(RobotMap.INPUT_UPPER_LIMIT_SW);
	private DigitalInput lowerLimitSwitch = new DigitalInput(RobotMap.INPUT_LOWER_LIMIT_SW);

	private Talon controller1 = new Talon(RobotMap.PWM_LIFTER_1);
	private Talon controller2 = new Talon(RobotMap.PWM_LIFTER_2);

	Lifter() {
		encoder.reset();
	}

	private void debug() {
		SmartDashboard.putNumber("lift Encoder", encoder.get());
		SmartDashboard.putNumber("lift rotations", rotation);
		SmartDashboard.putBoolean("upper sw", upperLimitSwitch.get());
		SmartDashboard.putBoolean("lower sw", lowerLimitSwitch.get());
	}

	public void up() {
		debug();

		if (upperLimitSwitch.get()) {
			stop();
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
		debug();

		if (lowerLimitSwitch.get()) {
			rotation = 0;
			lastEncoderValue = 0;
			encoder.reset();
			stop();
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
		debug();
		controller1.set(0);
		controller2.set(0);
	}
	public void findTop() {
		if (upperLimitSwitch.get()) {
			stop();
			totalRotations = rotation;
		}

		double currentValue = encoder.get();
		if (currentValue < lastEncoderValue) {
			rotation++;
		}
		lastEncoderValue = currentValue;

		controller1.set(RobotMap.DEFAULT_FIND_SPEED);
		controller2.set(RobotMap.DEFAULT_FIND_SPEED);		
	}
}
