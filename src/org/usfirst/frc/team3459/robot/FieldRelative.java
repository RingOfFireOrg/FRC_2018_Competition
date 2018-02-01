package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.Joystick;
import com.kauailabs.navx.frc.AHRS;


public class FieldRelative {
	Joystick joystick = new Joystick(0);
	AHRS ahrs;
	
	double stickY = joystick.getY();
	double stickX = joystick.getX();
	double gyroAngleDegrees = ahrs.getAngle();
	
	double pi = 3.14159265;
	
	double gyroAngleRadians = gyroAngleDegrees * pi/180;
	
	public void makeFieldRelative() {
//		double temp = forward * Math.cos(gyroAngleRadians)
	}

	
	
}
