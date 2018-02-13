package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.Solenoid;

public class Popcorn {

	private Solenoid openSolenoid = new Solenoid(RobotMap.OPEN_SOLENOID);
	private Solenoid closeSolenoid = new Solenoid(RobotMap.CLOSE_SOLENOID);

	public void open() {
		closeSolenoid.set(false);
		openSolenoid.set(true);
	}

	public void close() {
		openSolenoid.set(false);
		closeSolenoid.set(true);
	}
}
