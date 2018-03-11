package org.usfirst.frc.team3459.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber {

	private DigitalInput limitSwitch = new DigitalInput(RobotMap.CLIMBER_LIMIT_SW);
	private TalonSRX climb0 = new TalonSRX(RobotMap.CAN_CLIMBER_1);
	private TalonSRX climb1 = new TalonSRX(RobotMap.CAN_CLIMBER_2);
	private Joystick manipulatorStick;

	public Climber(Joystick manipulatorStick) {
		this.manipulatorStick = manipulatorStick;
	}

	public void climb() {
		boolean switchOverride = manipulatorStick.getRawButton(RobotMap.CLIMB_LIMIT_OVERRIDE);
		boolean climbButton = manipulatorStick.getRawButton(RobotMap.CLIMB_BUTTON);
		double speed = manipulatorStick.getThrottle();
		debug(climbButton, speed);
		if (climbButton) {
			//if (limitSwitch.get() && !switchOverride) {
				//climb0.set(ControlMode.PercentOutput, 0.0);
				//climb1.set(ControlMode.PercentOutput, 0.0);
			//} else {
				climb0.set(ControlMode.PercentOutput, speed);
				climb1.set(ControlMode.PercentOutput, speed);
			//}
		} else {
			climb0.set(ControlMode.PercentOutput, 0.0);
			climb1.set(ControlMode.PercentOutput, 0.0);
		}
	}

	private void debug(boolean button, double speed) {
		SmartDashboard.putBoolean("climb button", button);
		SmartDashboard.putBoolean("climb sw", limitSwitch.get());
		SmartDashboard.putNumber("climb speed", speed);
	}

}
