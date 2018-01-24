package org.usfirst.frc.team3459.robot;

public class CascadingLift {
	PTMotor motor;
CascadingLift(int channel){
	motor = new PTMotor(channel);
}
public void startUp() {
	motor.set(1.0);
}
public void startDown() {
	motor.set(-1.0);
}
public void stop() {
	motor.set(0.0);
}
}
