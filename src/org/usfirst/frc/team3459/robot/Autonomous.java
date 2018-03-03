package org.usfirst.frc.team3459.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C;

public class Autonomous {

	private AHRS ahrs;
	private TankDrive drive;
	private Lifter lifter;
	private Popcorn popcorn;
	int step = 0;
	UltrasonicSensor ultrasonic = new UltrasonicSensor(8);
	
	
	public Autonomous(TankDrive drive, Lifter lifter, Popcorn popcorn) {
		// TODO Auto-generated constructor stub
		this.drive = drive;
		this.lifter = lifter;
		this.popcorn = popcorn;
		
		String gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		if (gameData.length() > 0)
		{
			FieldProperties.initialize((gameData));
			
		}
		
		try {
			ahrs = new AHRS(I2C.Port.kOnboard);
			ahrs.reset();
			ahrs.setAngleAdjustment(0.0);
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
		}
	}
	
	double targetAngle = 0;
	public void middleAuto()
	{
		/*switch(step) {
		case 0:
			if(ultrasonic.getDistance() >= 20) {
				step++;
				break;
			}else {
				drive.tankDrive(0.7, 0.7);
				break;
			}
		case 1:
			if(FieldProperties.isLeftSwitchOurs() ) {
				targetAngle = 30;
				//random made up angle do math to find out
			}else {
				targetAngle = -30;
				//ditto
			}
			if(Math.abs(normalizeAngle(ahrs.getAngle() - targetAngle)) < 1) {
				step++;
				break;
			}else {
				break;
			}
		case 2:
			if() {
				step++;
				break;
			}else {
				break;
			}
		case 3:
			if() {
				step++;
				break;
			}else {
				break;
			}
		case 4:
			if() {
				step++;
				break;
			}else {
				break;
			}	
		
		}
		*/
		
}
	
	public void leftAuto()
	{
		if(!FieldProperties.isLeftSwitchOurs()) //will be part of case step 1
		{
			//drive forward 15 ft 
			//set step to 5
		}
		
		/*step one: lift to switch height and drive forward 8 ft 
		 * step two: turn right 90 degrees
		 * step three: drive forward 2 ft
		 * step four: drop cube
		 * step five: stop
		 */
	}
	
	public void rightAuto() 
	{
		
		switch(step) {
		case 0: //drive to switch and or past auto line
			if (ultrasonic.getDistance() >= 180){
				drive.tankDrive(0, 0);
				if(!FieldProperties.isRightSwitchOurs()) {
					step++;
				}else {
					step = 5;
				}
			}
			else {
				drive.tankDrive(0.7, 0.7);
			}
			break;
		case 1: //turn towards switch
			if (ahrs.getAngle() >= -90){
				drive.tankDrive(0, 0);
				drive.resetEncoders();
				step++;
			}
			else{
				drive.tankDrive(-0.7, 0.7);
				//probably too fast?  maybe proportional turning?  can we make a proportional method?
			}
			break;
			
		//where is case 2?????
		case 2:
			lifter.goTo("switch");
			break;
		case 3: //drive forward to switch
			if (drive.getLeftDistance() >= 24)
			{
				drive.tankDrive(0, 0);
				drive.resetEncoders();
				step++;
			}
			else
			{
				drive.tankDrive(0.7, 0.7);
			}
				break;
		case 4: //open the grabber
			popcorn.open();
			break; 
			
		}
	}
	
	public static double normalizeAngle(double input) {
		double output = input;
		while (output > 180) {
			output = output - 360;
		}
		while (output < -180) {
			output = output + 360;
		}
		return output;
	}
}
