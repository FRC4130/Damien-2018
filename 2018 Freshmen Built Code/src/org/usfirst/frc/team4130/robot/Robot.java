/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4130.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {


	@Override
	public void robotInit() {
		
		RobotMap.init();
		Subsystems.init();
	}

	@Override
	public void autonomousInit() {
//		m_autoSelected = m_chooser.getSelected();
//		// autoSelected = SmartDashboard.getString("Auto Selector",
//		// defaultAuto);
//		System.out.println("Auto selected: " + m_autoSelected);
	}


//	@Override
//	public void autonomousPeriodic() {
//		switch (m_autoSelected) {
//			case kCustomAuto:
//				// Put custom auto code here
//				break;
//			case kDefaultAuto:
//			default:
//				// Put default auto code here
//				break;
//		}
//	}


	@Override
	public void teleopPeriodic() {
		
	}

	@Override
	public void testPeriodic() {
	}
}
