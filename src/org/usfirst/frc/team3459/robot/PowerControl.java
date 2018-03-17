/* package org.usfirst.frc.team3459.robot;

import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class PowerControl {
	private PowerDistributionPanel powerDistributionPanel;
	
	public PowerControl() {
		powerDistributionPanel = new PowerDistributionPanel(RobotMap.PDP);
	}

	public double correctForBrownout(double motorSpeed) {
		if (powerDistributionPanel.getTotalPower() > RobotMap.MAX_POWER) {
			return motorSpeed * RobotMap.BROWN_OUT_SCALE;
		}
		return motorSpeed;
	}
}
*/