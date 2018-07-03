package org.usfirst.frc.team4130.loops;

import org.usfirst.frc.team4130.robot.RobotMap;
import org.usfirst.frc.team4130.robot.Subsystems;
import org.usfirst.frc.team4130.subsystems.DriveTrain;

import com.ctre.phoenix.ILoopable;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Joystick;

public class TeleDrive implements ILoopable {

	DriveTrain _drive;;
	Joystick _gamepad;
	
	double taretNativeLeft = 0;
	double targetNativeRight = 0;
	
	boolean rampRateLimited = true;
	
	public TeleDrive() {
		
		_drive = Subsystems.driveTrain;
		_gamepad = RobotMap.driver;
		
	}
	@Override
	public void onStart() {

		System.out.println("[Info] Starting Driving in Teleop");
		
		_drive.setNeutralMode(NeutralMode.Coast);
		_drive.setRampRate(0, 0);
	}

	@Override
	public void onLoop() {
		
		//Manage Ramp Rate
		if (Susbsystems.elevator.getHeight() > ElevatorPosition.MaXStable.value && !rampRateLimited) {
			System.out.println("[Info Ramp rate is limited");
			_drive.setRampRate(0.5, 0.5);
			rampRateLimited = false;
		}
		
		//Driver Speed Input
		if(_gamepad.getRawButtonPressed(2)) {
			_drive.setNeutralMode(NeutralMode.Brake);
		}
		else if (_gamepad.getRawButton(2)) {
			_drive.DirectDrive(0, 0);
		}
		else if(_gamepad.getRawButtonReleased(2)) {
			_drive.setNeutralMode(NeutralMode.Brake);
		}
		else if(_gamepad.getRawButton(1)) {
			_drive.DirectDrive(_gamepad.getRawAxis(1)*-1, _gamepad.getRawAxis(1)*-1);
		}
		else if(_gamepad.getRawButton(3)) {
			_drive.arcade(_gamepad.getRawAxis(1)*-1, _gamepad.getRawAxis(5)*-1);
		}
	}
		

	
	@Override
	public boolean isDone() {
		return false;
	}

	@Override
	public void onStop() {

		System.out.println("[WARNING] Drive in Teleoperated Has Stopped, Mechanical Issuse!!");
		
		_drive.setNeutralMode(NeutralMode.Brake);
		_drive.setRampRate(0, 0);
		_drive.DirectDrive(0, 0);
		System.out.print("[INFO] Ramp Rate limit removed");
		
		rampRateLimited = false;
	}

}
