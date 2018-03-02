package org.usfirst.frc.team3218.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.nio.DoubleBuffer;

import org.usfirst.frc.team3218.robot.subsystems.DriveTrain;

import com.ctre.phoenix.motorcontrol.*;
public class AutoAPI {


 //distances in inches
	 public final static int WALL_TO_SWITCH_CHANNEL = 67;
	 public final static int AUTOLINE = 84;
	 public final static int WALL_TO_SWITCH = 134;
	 public final static int WALL_TO_PLATFORM_CHANNEL = 210;
	 
	 public final static int MID_LINE = 240;
	 public final static int HORIZONTAL_FAR_SIDE = 162;
	 final static float TICKS_PER_INCH = 72; 
	 
	 public static double[] averages = new double[6];
	 static double[][] sensorValues = new double [6][200];
	 static double rateDelta;
	 static int autoPhase = 0;
	 public static  double ejectionTime;
	 /**
	 * @param distance in inches, positive forwards, negative, backwards
	 * @param speed in ticks, 0<s<1
	 */
	 	public static void driveStraight(int distance, int speed, int acceleration){
	 		resetDriveTrain();
			SmartDashboard.putString("autoState", "drive");
	 		distance *= TICKS_PER_INCH;
	 		//speed *= Math.signum(distance);// may not be needed
	 		autoPhase++;
	 		Robot.driveTrain.rightMidDrive.setSensorPhase(true);
	 		Robot.driveTrain.rightMidDrive.configMotionCruiseVelocity(speed, 0);
	 		Robot.driveTrain.rightMidDrive.configMotionAcceleration(acceleration, 0);
	 		
	 		Robot.driveTrain.rightMidDrive.set(ControlMode.MotionMagic, -distance);
	 		Robot.driveTrain.rightTopDrive.set(ControlMode.Follower,RobotMap.rightMidDriveID);
	 		Robot.driveTrain.rightBottomDrive.set(ControlMode.Follower,RobotMap.rightMidDriveID);
	 		
	 		
	 		Robot.driveTrain.leftMidDrive.configMotionCruiseVelocity(speed, 0);
	 		Robot.driveTrain.leftMidDrive.configMotionAcceleration(acceleration, 0);
	 		
	 		Robot.driveTrain.leftMidDrive.set(ControlMode.MotionMagic, distance);
	 		Robot.driveTrain.leftTopDrive.set(ControlMode.Follower,RobotMap.leftMidDriveID);
	 		Robot.driveTrain.leftBottomDrive.set(ControlMode.Follower,RobotMap.leftMidDriveID);
	 		
	 		while( Math.abs(Robot.driveTrain.rightMidDrive.getSelectedSensorPosition(0)) < distance &&
	 			  Math.abs(Robot.driveTrain.leftMidDrive.getSelectedSensorPosition(0)) < distance){
	 			double z =  -Robot.driveTrain.gyro.getAngle()/50;
	 			double y = 5500/speed;
	 			
	 			Robot.driveTrain.rightMidDrive.setSelectedSensorPosition( Robot.driveTrain.rightEnc.get(), 0, 0);
	 			Robot.driveTrain.leftMidDrive.setSelectedSensorPosition( Robot.driveTrain.leftEnc.get(), 0, 0);
	 			Robot.driveTrain.autoDrive(y, z);
	 			
	 			/*
	 			if(Robot.breakAuto){
	 				break;
	 			}
	 			
	 			
	 			Robot.driveTrain.rightMidDrive.setSelectedSensorPosition( Robot.driveTrain.rightEnc.get(), 0, 0);
	 			Robot.driveTrain.leftMidDrive.setSelectedSensorPosition( Robot.driveTrain.leftEnc.get(), 0, 0);
	 			if(Robot.driveTrain.gyro.getAngle() > 0){ 
	 				speed+=50;
	 				}
	 			else{
	 				speed-=50;
	 			}
	 			
	 			SmartDashboard.putNumber("speed", speed);
	 			Robot.driveTrain.rightMidDrive.configMotionCruiseVelocity(speed, 0);
	 			SmartDashboard.putNumber("Angle",Robot.driveTrain.gyro.getAngle());
	 		*/
	 		}
	 	}
		public static void simpleDrive(int distance){
			resetDriveTrain();
			SmartDashboard.putString("autoState", "simple");
			autoPhase++;
			distance *= TICKS_PER_INCH;
			while((Math.abs(Robot.driveTrain.leftEnc.get()) + Math.abs(Robot.driveTrain.rightEnc.get()))/2 < Math.abs(distance)){
		
				Robot.driveTrain.drive(.5*Math.signum(distance), 0);
			}
			Robot.driveTrain.drive(0, 0);
			
			
		}
	 	
		public static void rotate(int angle, int speed, int acceleration){
		    resetDriveTrain();
			SmartDashboard.putString("autoState", "rotate");
			autoPhase++;
		    Robot.driveTrain.rightMidDrive.setSensorPhase(false);
			Robot.driveTrain.rightMidDrive.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
	 		Robot.driveTrain.rightMidDrive.configMotionCruiseVelocity(speed, 0);
	 		Robot.driveTrain.rightMidDrive.configMotionAcceleration(acceleration, 0);
	 		
	 		Robot.driveTrain.rightMidDrive.set(ControlMode.MotionMagic, angle);
	 		Robot.driveTrain.rightTopDrive.set(ControlMode.Follower,RobotMap.rightMidDriveID);
	 		Robot.driveTrain.rightBottomDrive.set(ControlMode.Follower,RobotMap.rightMidDriveID);
	 		
	 		
	 		Robot.driveTrain.leftMidDrive.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 0);
	 		Robot.driveTrain.leftMidDrive.configMotionCruiseVelocity(speed, 0);
	 		Robot.driveTrain.leftMidDrive.configMotionAcceleration(acceleration, 0);
	 		
	 		Robot.driveTrain.leftMidDrive.set(ControlMode.MotionMagic, angle);
	 		Robot.driveTrain.leftTopDrive.set(ControlMode.Follower,RobotMap.leftMidDriveID);
	 		Robot.driveTrain.leftBottomDrive.set(ControlMode.Follower,RobotMap.leftMidDriveID);
	 		
	 		while(Math.abs(Robot.driveTrain.leftMidDrive.getSelectedSensorPosition(0)) <Math.abs(angle)){
	 			
	 	 		Robot.driveTrain.rightMidDrive.setSelectedSensorPosition((int) Robot.driveTrain.gyro.getAngle(), 0, 0);
	 	 		Robot.driveTrain.leftMidDrive.setSelectedSensorPosition((int) Robot.driveTrain.gyro.getAngle(), 0, 0);
	 	 		}
		}
		
		
		
		public static void moveToHeight(int position){
			SmartDashboard.putString("autoState", "lift");
			resetDriveTrain();
			autoPhase++;
			Robot.lift.gearLow();
			Robot.lift.setPosition(Robot.lift.positionArray[position]);
			Robot.lift.liftMaster.setSelectedSensorPosition(Robot.lift.liftEnc.get(),0,0);
			while(Robot.lift.liftMaster.getSelectedSensorPosition(0) < Robot.lift.positionArray[position]-300 ||Robot.lift.liftMaster.getSelectedSensorPosition(0) == Robot.lift.positionArray[position]){
			
				Robot.lift.setPosition(Robot.lift.positionArray[position]);
			}
			Robot.lift.gearLow();
		}
		
		public static void resetDriveTrain()
		{		
			Robot.driveTrain.rightMidDrive.setSelectedSensorPosition(0, 0, 0);
	 		Robot.driveTrain.leftMidDrive.setSelectedSensorPosition(0, 0, 0);
	 		Robot.driveTrain.leftBottomDrive.set(0);
	 		Robot.driveTrain.leftMidDrive.set(0);
	 		Robot.driveTrain.leftMidDrive.set(0);
	 		Robot.driveTrain.rightBottomDrive.set(0);
	 		Robot.driveTrain.rightMidDrive.set(0);
	 		Robot.driveTrain.rightMidDrive.set(0);
	 		Robot.driveTrain.leftEnc.reset();
	 		Robot.driveTrain.rightEnc.reset();
	 		Robot.driveTrain.gyro.reset();
		
		}
		
		
		
		//methods specific to this year
		
		
		
		
		
		
		
		
		
	
	
	
}





