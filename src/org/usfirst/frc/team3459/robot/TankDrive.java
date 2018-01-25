package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class TankDrive extends DifferentialDrive {
	TankDrive() {
		// left then right
		super(new SpeedControllerGroup(new Victor(2), new Victor(3)),
				new SpeedControllerGroup(new Victor(0), new Victor(1)));

	}

}
