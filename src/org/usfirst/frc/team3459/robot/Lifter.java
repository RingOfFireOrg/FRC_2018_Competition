package org.usfirst.frc.team3459.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lifter {

	private Encoder encoder = new Encoder(RobotMap.LIFT_ENCODER_A, RobotMap.LIFT_ENCODER_B, true,
			Encoder.EncodingType.k1X);
	private double totalRotations = 0;
	private DigitalInput upperLimitSwitch = new DigitalInput(RobotMap.INPUT_UPPER_LIMIT_SW);
	private DigitalInput lowerLimitSwitch = new DigitalInput(RobotMap.INPUT_LOWER_LIMIT_SW);
	private TalonSRX controller1 = new TalonSRX(RobotMap.CAN_LIFTER_1);
	private TalonSRX controller2 = new TalonSRX(RobotMap.CAN_LIFTER_2);
	private Joystick manipulatorStick;
	private double encoderHeight = 0;

	Lifter(Joystick manipulatorStick) {
		this.manipulatorStick = manipulatorStick;
		encoder.reset();
		encoder.setSamplesToAverage(5); // noise reduction?
		encoder.setDistancePerPulse(1.0 / 360); // should see 1 pulse per rotation
		controller1.setNeutralMode(NeutralMode.Brake);
		controller2.setNeutralMode(NeutralMode.Brake);
	}

	public double getSpeed(boolean goingUp) {
		double speed = RobotMap.DEFAULT_LIFT_SPEED;
		if (goingUp) {
			if (totalRotations == 0) {
				speed = RobotMap.DEFAULT_FIND_SPEED;
			} else {
				speed = (encoder.getDistance() * ((RobotMap.MIN_LIFT_SPEED - RobotMap.MAX_LIFT_SPEED) / totalRotations))
						+ RobotMap.MAX_LIFT_SPEED;
			}
		} else {
			speed = (encoder.getDistance() * (RobotMap.MAX_LIFT_SPEED - RobotMap.MIN_LIFT_SPEED));
		}
		if (speed < RobotMap.MIN_LIFT_SPEED) {
			speed = RobotMap.MIN_LIFT_SPEED;
		}
		if (!goingUp) {
			speed = speed * -1;
		}
		return speed;
	}
	
	public double getCurrentOutputPercent() {
		return controller1.getMotorOutputPercent();
	}

	public double getEncoderValue() {
		return encoder.getDistance();
	}
	
	public void up(double speed) {
		debug();
		//TODO Modify Number here (for power limit)
		if (upperLimitSwitch.get() && !manipulatorStick.getRawButton(RobotMap.LIFT_LIMIT_OVERRIDE)) {
			stop();
			totalRotations = encoder.getDistance();
			return;
		}
		controller1.set(ControlMode.PercentOutput, speed);
		controller2.set(ControlMode.PercentOutput, speed);
	}

	
	public void up() {
		up(getSpeed(true));
	}

	public void down(double speed) {
		debug();
		//TODO Modify Number here (for power limit)

		if (lowerLimitSwitch.get() && !manipulatorStick.getRawButton(RobotMap.LIFT_LIMIT_OVERRIDE)) {
			encoder.reset();
			stop();
			return;
		}
		controller1.set(ControlMode.PercentOutput, speed);
		controller2.set(ControlMode.PercentOutput, speed);
	}

	public void down() {
		down(getSpeed(false));
	}

	// TODO: Consider redefining goTo(String) to goTo(double) and having callers
	public void goTo(String position) {
		switch (position) {
		case "floor":
			down();
			break;
		case "switch":
			encoderHeight = 6;
			if (encoder.getDistance() < encoderHeight - 1) {
				up();
			} else if (encoder.getDistance() > encoderHeight + 1) {
				down();
			} else {
				stop();
			}
			break;
		case "scale":
			up();
		}
	}

	public void findTop() {
		if (upperLimitSwitch.get()) {
			stop();
			totalRotations = encoder.getDistance();
		}
		controller1.set(ControlMode.PercentOutput, RobotMap.DEFAULT_FIND_SPEED);
		controller2.set(ControlMode.PercentOutput, RobotMap.DEFAULT_FIND_SPEED);
	}
	
	public void stop() {
		debug();
		controller1.set(ControlMode.PercentOutput, 0);
		controller2.set(ControlMode.PercentOutput, 0);
	}
	
	private void debug() {
		SmartDashboard.putNumber("lift Encoder", encoder.get());
		SmartDashboard.putNumber("lift rotations", encoder.getDistance());
		SmartDashboard.putNumber("top", totalRotations);
		SmartDashboard.putBoolean("upper sw", upperLimitSwitch.get());
		SmartDashboard.putBoolean("lower sw", lowerLimitSwitch.get());
	}
}
