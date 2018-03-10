package org.usfirst.frc.team3459.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lifter {

	private static final double BOTTOM_LIFTER_VALUE = 0;
	private static final double TOP_LIFTER_VALUE = 3;
	
	private Encoder encoder = new Encoder(RobotMap.LIFT_ENCODER_A, RobotMap.LIFT_ENCODER_B, true, Encoder.EncodingType.k1X);
	private double totalRotations = 0;
	
	private DigitalInput upperLimitSwitch = new DigitalInput(RobotMap.INPUT_UPPER_LIMIT_SW);
	private DigitalInput lowerLimitSwitch = new DigitalInput(RobotMap.INPUT_LOWER_LIMIT_SW);
	
	private TalonSRX controller1 = new TalonSRX(RobotMap.CAN_LIFTER_1);
	private TalonSRX controller2 = new TalonSRX(RobotMap.CAN_LIFTER_2);

	private Joystick manipulatorStick;

	Lifter(Joystick manipulatorStick) {
		this.manipulatorStick = manipulatorStick;
		encoder.reset();
		encoder.setSamplesToAverage(5); // noise reduction?
		encoder.setDistancePerPulse(1.0 / 360); // should see 1 pulse per rotation
		controller1.setNeutralMode(NeutralMode.Brake);
		controller2.setNeutralMode(NeutralMode.Brake);
	}

	// TODO: Consider reorganizing methods
	private void debug() {
		SmartDashboard.putNumber("lift Encoder", encoder.get());
		SmartDashboard.putNumber("lift rotations", encoder.getDistance());
		SmartDashboard.putNumber("top", totalRotations);

		SmartDashboard.putBoolean("upper sw", upperLimitSwitch.get());
		SmartDashboard.putBoolean("lower sw", lowerLimitSwitch.get());
	}

	public void up(double speed) {
		debug();
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

	private double getSpeed(boolean goingUp) {
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

	public void down(double speed) {
		debug();

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

	double encoderHeight = 0;
	// TODO: Consider redefining goTo(String) to goTo(double) and having callers
	public void goTo(String position) {
		
		switch (position) {
		case "floor":
			encoderHeight = 0;
			break;
		case "switch":
			encoderHeight = 6;
			break;
		case "scale":
			encoderHeight = 0;
			//needs a real value here not 0
			break;
		}
		goTo(encoderHeight);
	}

	public void goTo(double encoderHeight) {
		if (encoder.getDistance() < encoderHeight - 1) {
			up();
		} else if (encoder.getDistance() > encoderHeight + 1) {
			down();
		} else {
			stop();
		}
	}

	public void stop() {
		debug();
		controller1.set(ControlMode.PercentOutput, 0);
		controller2.set(ControlMode.PercentOutput, 0);
	}

	public void findTop() {
		if (upperLimitSwitch.get()) {
			stop();
			totalRotations = encoder.getDistance();
		}
		controller1.set(ControlMode.PercentOutput, RobotMap.DEFAULT_FIND_SPEED);
		controller2.set(ControlMode.PercentOutput, RobotMap.DEFAULT_FIND_SPEED);
	}

	public double getCurrentOutputPercent() {
		return controller1.getMotorOutputPercent();
	}

	private int calibrationStep = 0;
	private double bottomValue = 0.0;
	private double topValue = 0.0;
	private int calibrateTimes;

	public void calibrate() {
		switch (calibrationStep) {
		case 0:
			if (lowerLimitSwitch.get()) {
				bottomValue += encoder.getDistance();
				calibrationStep++;
			} else {
				down();
			}
			break;
		case 1:
			if (upperLimitSwitch.get()) {
				topValue += encoder.getDistance();
				if (calibrateTimes <= 3) {
					calibrationStep = 0;
					calibrateTimes++;
				} else {
					calibrationStep = 2;
				}
			} else {
				up();
			}
			break;
		case 2:
			bottomValue = bottomValue / 4;
			topValue = topValue / 4;
			calibrationStep++;
			break;
		case 3:
			SmartDashboard.putNumber("bottom value", bottomValue);
			SmartDashboard.putNumber("top value", topValue);
		}
	}
	
	public double getEncoderValue()
	{
		return encoder.getDistance();
	}
}
