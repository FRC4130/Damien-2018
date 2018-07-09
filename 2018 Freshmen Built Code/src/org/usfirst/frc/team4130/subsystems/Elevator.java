package org.usfirst.frc.team4130.subsystems;

import org.usfirst.frc.team4130.robot.RobotMap;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Elevator {

	private TalonSRX elevator = RobotMap.elevator;
	private final int kTimeoutMS = 5;
	private final int kPosBandwith = 10;
	
	double targetHeightNativeUnits = 0;
	
	public Elevator() {
		elevator.configAllowableClosedloopError(0, 0, kTimeoutMS);
		
		/**Make sure to have a full range of motion**/
		elevator.configPeakOutputForward(1, kTimeoutMS);
		elevator.configNominalOutputForward(0, kTimeoutMS);
		elevator.configPeakOutputReverse(-1, kTimeoutMS);
		elevator.configNominalOutputReverse(0, kTimeoutMS);
		
		//TODO set these
		/**Up Gains**/
		elevator.config_kF(0, 0, kTimeoutMS);
		elevator.config_kP(0, 0, kTimeoutMS);
		
		//TODO set these
		/**Down Gains**/
		elevator.selectProfileSlot(1, 0);
		elevator.config_kF(0, 0, kTimeoutMS);
		elevator.config_kP(0, 0, kTimeoutMS);
		elevator.config_kI(0, 0, kTimeoutMS);
		elevator.config_kD(0, 0, kTimeoutMS);
		elevator.config_IntegralZone(0, 0, kTimeoutMS);
		
		/**Motion Magic Crap**/
		elevator.configMotionAcceleration(1322, kTimeoutMS);
		elevator.configMotionCruiseVelocity(1322, kTimeoutMS);
		
		/**These may need to be changed**/
		elevator.setInverted(false);
		elevator.setSensorPhase(false);
		
		elevator.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, kTimeoutMS);
		
		/**Forward is UP**/
		elevator.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, kTimeoutMS);
		elevator.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, kTimeoutMS);
		//Zero the Position on the bottom limit switch
		elevator.configSetParameter(ParamEnum.eClearPositionOnLimitR, 1, 0, 0, kTimeoutMS);
		
		//Current Limit to protect 775
		//Current setup:no more than 3 seconds before stalling
		elevator.configPeakCurrentLimit(30, kTimeoutMS);
		elevator.configPeakCurrentDuration(3000, kTimeoutMS);
		elevator.configContinuousCurrentLimit(5, kTimeoutMS);
		elevator.enableCurrentLimit(true);
		
		/**Soft Limits**/
		elevator.configReverseSoftLimitThreshold((int)chainHeightToNative(ElevatorPosition.ReverSoftLimit.value), kTimeoutMS);
		elevator.configReverseSoftLimitEnable(true, kTimeoutMS);
		
		elevator.configForwardSoftLimitThreshold((int)chainHeightToNative(ElevatorPosition.ForwardSoftLiit.value), kTimeoutMS);
		elevator.configForwardSoftLimitEnable(true, kTimeoutMS);
	}
	/**
	 * Set the height of the elevator(along the chain).
	 * in inches using the position enum.
	 * @param pos ElevatorPositionEum.
	 * @return True if at position
	 */
	
	public boolean setHeightInches(double inches) {
		
		return setHeight(chainHeightToNative(inches));
	}
	
	public boolean setHeight(double valueNativeUnits) {
		
		if(valueNativeUnits > targetHeightNativeUnits) {
			elevator.configMotionAcceleration(2000, kTimeoutMS);
			elevator.configMotionCruiseVelocity(1500, kTimeoutMS);
		}
		else if(valueNativeUnits < targetHeightNativeUnits) {
			elevator.configMotionAcceleration(6000, kTimeoutMS);
			elevator.configMotionCruiseVelocity(1400*6, kTimeoutMS);
		}
		
		targetHeightNativeUnits = valueNativeUnits;
		
		elevator.set(ControlMode.MotionMagic, valueNativeUnits);
		
		//TODO Debounce this
		if(elevator.getClosedLoopError(0) < kPosBandwith) {
			return true;
		}
		
		return false;
	}
	/**
	 * Setting the speed of the elevator
	 */
	public void driveDirect(double percentOutput) {
		
		elevator.set(ControlMode.PercentOutput, percentOutput);
	}
	
	/** Converts inches to native units**/
	public double chainHeightToNative(double inches) {
		return(inches/79)*36764;
	}
	
	public double nativeToChainHeight(double n) {
		return (n/36764)*79;
	}
	/**
	 * Set the elevator to home
	 */
	public boolean setHome() {
		elevator.set(ControlMode.PercentOutput, -0.075);
		if(elevator.getSensorCollection().isRevLimitSwitchClosed()) {
			elevator.setSelectedSensorPosition(0, 0, kTimeoutMS);
			return true;
		}
		return false;
	}
	
	public double getError() {
		
		return elevator.getSelectedSensorPosition(0)-targetHeightNativeUnits;
		
	}
	
	public double getCurrent() {
		return elevator.getOutputCurrent();
		}
	
	public double getPos() {
		return elevator.getSelectedSensorPosition(0);
	}
	
	public double getHeight() {
		return nativeToChainHeight(getPos());
	}
}
