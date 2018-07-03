package org.usfirst.frc.team4130.robot;

import org.usfirst.frc.team4130.loops.TeleDrive;

import com.ctre.phoenix.schedulers.ConcurrentScheduler;

public class Loops {

	//Teleoperated Loops
	public static void sTeleop(ConcurrentScheduler teleop) {
		
		System.out.println("Scheduling Teleop.");
		
		//schedule all tasks for teleop
		teleop.add(new ElevatorTele());
		teleop.add(new TeleDrive());
		teleop.add(new ArmsTele());
	}
	
}
