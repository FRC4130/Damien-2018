package org.usfirst.frc.team4130.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.Joystick;

public class RobotMap {
	
	public static TalonSRX leftDrive;
	public static TalonSRX leftFollow;
	
	public static TalonSRX rightDrive;
	public static TalonSRX rightFollow;
	
	public static TalonSRX elevator;
	
	public static PigeonIMU pigeon;

	public static Joystick driver;
	public static Joystick operator;
	

	
	public static void init() {
		
	leftDrive = new TalonSRX(1);
	leftFollow = new TalonSRX(2);
	
	rightDrive = new TalonSRX(3);
	rightFollow = new TalonSRX(4);
	
	pigeon = new PigeonIMU(rightFollow);
	
	driver = new Joystick(0);
	operator = new Joystick(1);
	
	elevator = new TalonSRX(5);
		
	}

}
