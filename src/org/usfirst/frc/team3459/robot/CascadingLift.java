package org.usfirst.frc.team3459.robot;

public class CascadingLift {
	PTMotor motor1;
	PTMotor motor2;
CascadingLift(int channel1, int channel2){
	motor1 = new PTMotor(channel1);
	motor2 = new PTMotor(channel2);
}
public void startUp() {
	motor1.set(0.60);
	motor2.set(0.60);
}
public void startDown() {
	motor1.set(-0.5);
	motor2.set(-0.5);
}
public void stop() {
	motor1.set(0.0);
	motor2.set(0.0);
}
}
