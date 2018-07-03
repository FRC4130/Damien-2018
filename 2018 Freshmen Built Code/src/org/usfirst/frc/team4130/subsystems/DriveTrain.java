package org.usfirst.frc.team4130.subsystems;

import org.usfirst.frc.team4130.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain {
	
	private TalonSRX leftDrive = RobotMap.leftDrive;
	private TalonSRX leftFollow = RobotMap.leftFollow;
	private TalonSRX rightDrive = RobotMap.rightDrive;
	private TalonSRX rightFollow = RobotMap.rightFollow;
	private PigeonIMU pigeon = RobotMap.pigeon;
	private Joystick driver = RobotMap.driver;
	private final int kTimeoutMS = 10;
	public double kPosError = 15;
	private double highRampRate = 0;
	private double lowRampRate = 0;
	
	public DriveTrain() {
		
		/***Making Basics to make robot run***/
		System.out.println("[INFO] Drive train init has ran.");
		
		leftFollow.follow(leftDrive);
		rightFollow.follow(rightDrive);
		
		leftDrive.configAllowableClosedloopError(0, 0, kTimeoutMS);
		rightDrive.configAllowableClosedloopError(0, 0, kTimeoutMS);
		
		leftDrive.setInverted(true); 
		leftFollow.setInverted(true);
		rightDrive.setInverted(false);
		rightFollow.setInverted(false);
		
		pigeon.setFusedHeading(0, 10);
		
		/**Motion Magic/Speed Control**/
		leftDrive.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, kTimeoutMS);
		leftDrive.setSensorPhase(false);
		
		rightDrive.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, kTimeoutMS);
		rightDrive.setSensorPhase(false);
		
		leftDrive.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, kTimeoutMS);
		leftDrive.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, kTimeoutMS);
		
		leftDrive.configNominalOutputForward(0, kTimeoutMS);
		leftDrive.configNominalOutputReverse(0, kTimeoutMS);
		leftDrive.configPeakOutputForward(1, kTimeoutMS);
		leftDrive.configPeakOutputReverse(-1, kTimeoutMS);
		
		rightDrive.configNominalOutputReverse(0, kTimeoutMS);
		rightDrive.configPeakOutputForward(1, kTimeoutMS);
		rightDrive.configPeakOutputReverse(-1, kTimeoutMS);
		
		//Slot 0
		leftDrive.selectProfileSlot(0,0);
		leftDrive.config_kF(0, 0, kTimeoutMS);
		leftDrive.config_kP(0, 0, kTimeoutMS);
		leftDrive.config_kI(0, 0, kTimeoutMS);
		leftDrive.config_kD(0, 0, kTimeoutMS);
		leftDrive.config_IntegralZone(0, 0, kTimeoutMS);
		
		rightDrive.selectProfileSlot(0,0);
		rightDrive.config_kF(0, 0, kTimeoutMS);
		rightDrive.config_kP(0, 0, kTimeoutMS);
		rightDrive.config_kI(0, 0, kTimeoutMS);
		rightDrive.config_kD(0, 0, kTimeoutMS);
		rightDrive.config_IntegralZone(0, 0, kTimeoutMS);
	}
	
	public void setRampRate(double secondshigh, double secondslow) {
		
		highRampRate = secondshigh;
		lowRampRate = secondslow;	
	}
	
	/***Cruising Velocity***/
	public void setMagicDefault( ) {
		
		leftDrive.configMotionCruiseVelocity(10000, kTimeoutMS);
		leftDrive.configMotionAcceleration(10000, kTimeoutMS);
		
		rightDrive.configMotionCruiseVelocity(10000, kTimeoutMS);
		rightDrive.configMotionAcceleration(10000, kTimeoutMS);
	}
	
	public void setMagic(int cruisevelocity, int acceleration) {
		
		leftDrive.configMotionCruiseVelocity(cruisevelocity, kTimeoutMS);
		leftDrive.configMotionAcceleration(acceleration, kTimeoutMS);
		
		rightDrive.configMotionCruiseVelocity(cruisevelocity, kTimeoutMS);
		rightDrive.configMotionAcceleration(acceleration, kTimeoutMS);
	}
	
	public void putDash() {
		
		SmartDashboard.putNumber("Left Val", leftDrive.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Left Tarjectory Vel", leftDrive.getActiveTrajectoryVelocity());
		SmartDashboard.putNumber("Left Trajectory Position", leftDrive.getActiveTrajectoryPosition());
		SmartDashboard.putNumber("Left Target Pos", leftDrive.getClosedLoopTarget(0));
		SmartDashboard.putNumber("Left Pos", leftDrive.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Left Closed Loop Error", leftDrive.getClosedLoopError(0));
		
		SmartDashboard.putNumber("Right Val", rightDrive.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Right Tarjectory Vel", rightDrive.getActiveTrajectoryVelocity());
		SmartDashboard.putNumber("Right Trajectory Position", rightDrive.getActiveTrajectoryPosition());
		SmartDashboard.putNumber("Right target Pos", rightDrive.getClosedLoopTarget(0));
		SmartDashboard.putNumber("Right pos", rightDrive.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("Right Closed Loop Error", rightDrive.getClosedLoopError(0));
	}
	
	public void setNeutralMode(NeutralMode nm) {
		
		leftDrive.setNeutralMode(nm);
		leftFollow.setNeutralMode(nm);
		
		rightDrive.setNeutralMode(nm);
		rightFollow.setNeutralMode(nm);
	}
	
	public void DirectDrive(double percentOutputLeft, double percentOutputRight) {
		
		leftDrive.set(ControlMode.PercentOutput, percentOutputLeft);
		rightDrive.set(ControlMode.PercentOutput, percentOutputRight);
	}
	
	public void setPosLeft(double nativeUnits) {
		
		leftDrive.set(ControlMode.MotionMagic, nativeUnits);
	}
	
	public void setPosRight(double nativeUnits) {
		
		rightDrive.set(ControlMode.MotionMagic, nativeUnits);
	}
	
	public double distanceToRotations(double inches) {
		
		return ( ( (2048*25) * inches ) / 92)*(51.25/17);
	}
	
	public double getLeftPos() {
		
		return leftDrive.getSelectedSensorPosition(0);
	}
	
	public double getRightPos() {
		
		return rightDrive.getSelectedSensorPosition(0);
	}
	
	public double getHeading() {
		
		return pigeon.getFusedHeading();
	}
	
	public void resetHeading() {
		
		pigeon.setFusedHeading(0, kTimeoutMS);
	}
	
	public void arcade(double throttle, double turn) {
		
		DirectDrive(throttle+turn, throttle-turn);
	}
	
	public void resetSensors() {
		
		leftDrive.setSelectedSensorPosition(0, 0, 0);
		rightDrive.setSelectedSensorPosition(0, 0, 0);
	}
}
