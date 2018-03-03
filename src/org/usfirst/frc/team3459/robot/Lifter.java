package org.usfirst.frc.team3459.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lifter {

	private static final double BOTTOM_LIFTER_VALUE = 0;
	private static final double TOP_LIFTER_VALUE = 3;
	
	// Encoder.
	private Encoder encoder = new Encoder(RobotMap.LIFT_ENCODER_A, RobotMap.LIFT_ENCODER_B, false,Encoder.EncodingType.k1X);
	private double totalRotations = 0;

	private DigitalInput upperLimitSwitch = new DigitalInput(RobotMap.INPUT_UPPER_LIMIT_SW);
	private DigitalInput lowerLimitSwitch = new DigitalInput(RobotMap.INPUT_LOWER_LIMIT_SW);

	private TalonSRX controller1 = new TalonSRX(RobotMap.CAN_LIFTER_1);
	private TalonSRX controller2 = new TalonSRX(RobotMap.CAN_LIFTER_2);
	
	

	Lifter() {
		encoder.reset();
		encoder.setSamplesToAverage(5); // noise reduction?
		encoder.setDistancePerPulse(1.0/360); // should see 1 pulse per rotation
		controller1.setNeutralMode(NeutralMode.Brake);
		controller2.setNeutralMode(NeutralMode.Brake);
	}

	private void debug() {
		SmartDashboard.putNumber("lift Encoder", encoder.get());
		SmartDashboard.putNumber("lift rotations", encoder.getDistance());
		SmartDashboard.putNumber("top", totalRotations);
		
		SmartDashboard.putBoolean("upper sw", upperLimitSwitch.get());
		SmartDashboard.putBoolean("lower sw", lowerLimitSwitch.get());
	}

	public void up() {
		debug();
		if (upperLimitSwitch.get()) {
			stop();
			totalRotations = encoder.getDistance();
			return;
		}

		double speed = getSpeed(true);
		controller1.set(ControlMode.PercentOutput, speed);
		controller2.set(ControlMode.PercentOutput, speed);
	}
	
	private double getSpeed(boolean goingUp) {
		double speed = RobotMap.DEFAULT_LIFT_SPEED;
		if(goingUp) {
			if(totalRotations == 0) {
				speed = RobotMap.DEFAULT_FIND_SPEED;
			}
			else {
			 speed = (encoder.getDistance() * ((RobotMap.MIN_LIFT_SPEED - RobotMap.MAX_LIFT_SPEED) / totalRotations))+RobotMap.MAX_LIFT_SPEED;
			}
		}
		else {
			speed = (encoder.getDistance() * (RobotMap.MAX_LIFT_SPEED - RobotMap.MIN_LIFT_SPEED));
		}
		if(speed < RobotMap.MIN_LIFT_SPEED) {
			speed = RobotMap.MIN_LIFT_SPEED;
		}
		if(!goingUp) {
			speed = speed * -1;
		}
		return speed;
	}

	public void down() {
		debug();

		if (lowerLimitSwitch.get()) {
			encoder.reset();
			stop();
			return;
		}
		double speed = getSpeed(false);
		controller1.set(ControlMode.PercentOutput, speed);
		controller2.set(ControlMode.PercentOutput, speed);
	}
	
	private double encoderHeight;
	
	public void goTo(String position) {
		switch(position) {
		case "floor":
			encoderHeight = 0;
			break;
		case "scale":
			encoderHeight = 0;
			break;
		case "switch":
			encoderHeight = 0;
			break;
		}
		if(encoder.getDistance() < encoderHeight++) {
			up();
		} else if (encoder.getDistance() > encoderHeight--) {
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
	int calibrationStep = 0;
	double bottomValue;
	double topValue;
	int calibrateTimes;
	
	public void calibrate() {
		switch(calibrationStep) {
		case 0:
			if(lowerLimitSwitch.get()) {
				bottomValue += encoder.getDistance();
				calibrationStep++;
			}else {
				down();
			}
			break;
		case 1:
			if(upperLimitSwitch.get()) {
				topValue += encoder.getDistance();
				if(calibrateTimes <= 3) {
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
			bottomValue = bottomValue/3;
			topValue = topValue/3;
			SmartDashboard.putNumber("bottom value", bottomValue);
			SmartDashboard.putNumber("top value", topValue);
			break;
				
		}	
	}
}
