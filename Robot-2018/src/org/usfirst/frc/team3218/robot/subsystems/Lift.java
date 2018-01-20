package org.usfirst.frc.team3218.robot.subsystems;

import org.usfirst.frc.team3218.robot.Robot;
import org.usfirst.frc.team3218.robot.RobotMap;
import org.usfirst.frc.team3218.robot.commands.Lift.ManualLiftControl;
import org.usfirst.frc.team3218.robot.commands.Lift.SetLiftPositionBottom;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Lift extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	//these values need testing
	int CruiseVelocity; //encoderticks per 100ms
	int Acceleration; //encoderticks per 100ms per second
	final int HOLD_POSITION_POWER = 0; //power required for arm to stay at position
	final int GUITAR_MANUAL_UP = 0;
	final int GUITAR_MANUAL_DOWN = 180;
	final int MANUAL_UP_POWER = 1;
	final int MANUAL_DOWN_POWER = -1;
	
	public float ticksPerInch;
	
	public int[] positionArray = new int[]{0,0,0,0,0,0};//array of positions for the lift in inches
	
	public  WPI_TalonSRX liftCIM = new WPI_TalonSRX(RobotMap.liftCIMID);
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new ManualLiftControl());
    	
    }
    
    public void liftPIDConfig(){
    	//timeouts and PIDidx are 0
    
    	liftCIM.set(ControlMode.MotionMagic, 0);
    	liftCIM.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
    	liftCIM.configMotionCruiseVelocity(CruiseVelocity, 0);
    	liftCIM.configMotionAcceleration(Acceleration, 0);
    	
    }
    
    public  void setPosition(int position){
    		liftCIM.set(ControlMode.MotionMagic, 0);
        	Robot.lift.liftCIM.set(Robot.lift.positionArray[position] * Robot.lift.ticksPerInch);
   
    }
    
    public void manual(){
    	//0 is up -1 is hold 180 is down
    	//Cim values need to be checked against the actual motor
		switch (Robot.oi.guitar.getPOV()) {

		case GUITAR_MANUAL_UP:
			liftCIM.set(MANUAL_UP_POWER);
			break;

		case GUITAR_MANUAL_DOWN:
			liftCIM.set(MANUAL_DOWN_POWER);
			break;

		default:
			liftCIM.set(HOLD_POSITION_POWER);
			break;

    	}
    	
    }
    
}

