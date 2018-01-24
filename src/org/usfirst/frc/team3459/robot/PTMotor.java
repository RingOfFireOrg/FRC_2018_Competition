package org.usfirst.frc.team3459.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class PTMotor extends TalonSRX{
   PTMotor(int channel){
	   super(channel);
   }
   void set(double speed) {
	   set(ControlMode.PercentOutput, speed);
   }
}
