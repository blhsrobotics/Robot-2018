package org.usfirst.frc.team3218.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	
	
	// Driver Station USB Ports
	public static int joyStickPort1 = 0;
	public static int guitarPort = 1;
	
	//CAN bus channels
	public static final int lift1ID= 10;
	public static final int lift2ID= 11;
	public static final int leftBackDriveID = 0;
	public static final int leftMidDriveID = 1;
	public static final int leftFrontDriveID = 2;
	public static final int rightBackDriveID = 3;
	public static final int rightMidDriveID = 4;
	public static final int rightFrontDriveID = 5;
	
	//PWM Channels
	public static int leftDrive1Port = 0;
	public static int leftDrive2Port = 6;//1
	public static int leftDrive3Port = 7;//2
	public static int rightDrive1Port = 3;
	public static int rightDrive2Port = 4;
	public static int rightDrive3Port = 5;

	public static int leftCollection1Port = 1;
	public static int rightCollection1Port = 8;

	//Digital Channels
	public static int encoderLeftPortA = 0;
	public static int encoderLeftPortB = 1;
	public static int encoderRightPortA = 2;
	public static int encoderRightPortB = 3;
	public static int liftEncoderPortA = 8;
	public static int liftEncoderPortB = 9;
	

	public static int limitSwitchPortA = 7;

	//Analog Channels
	public static int sonarAPort = 0;
	public static int gyroPort = 1;
	public static int accelerometerPort = 2;
	//PCM Channels
	public static int leftKoreyPort = 0;
	public static int rightKoreyPort = 1;
	public static int pistonKoreyPort = 2;
	public static int leftGearShiftPort = 3;
	public static int rightGearShiftPort = 4;
	public static int climbGearPort = 5;
	
	
	
}
