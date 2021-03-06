
package org.usfirst.frc.team3218.robot;

import org.usfirst.frc.team3218.robot.commands.ExampleCommand;
import org.usfirst.frc.team3218.robot.commands.Auto.Nothing;
import org.usfirst.frc.team3218.robot.commands.DriveTrain.DriveWithJoystick;
import org.usfirst.frc.team3218.robot.subsystems.CubeControl;
import org.usfirst.frc.team3218.robot.subsystems.DriveTrain;
import org.usfirst.frc.team3218.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team3218.robot.subsystems.Lift;
import org.usfirst.frc.team3218.robot.subsystems.Vision;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final DriveTrain driveTrain = new DriveTrain();
	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static final Vision vision = new Vision();
	public static final Lift lift =  new Lift();
	public static final CubeControl cubeControl = new CubeControl();
	public static OI oi;
	
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		chooser.addDefault("Nothing", new Nothing());
		chooser.addObject("Cross Auto Line", new DriveWithJoystick());
		
		
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
		
		lift.liftPIDConfig();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		driveTrain.leftEnc.setDistancePerPulse(1/73);
		driveTrain.rightEnc.setDistancePerPulse(1/73);

		autonomousCommand = chooser.getSelected();
		
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		driveTrain.rightEnc.reset();
		driveTrain.leftEnc.reset();
		driveTrain.gyro.reset();
		
	
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		dashboardOutputs();
		
		if(driveTrain.compressor.getPressureSwitchValue() == true){
			driveTrain.compressor.stop();
		}
		else{
			driveTrain.compressor.start();
		} 
	}
	
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	public void dashboardOutputs(){		
		SmartDashboard.putNumber("joystickY", OI.getJoystickY());
    	SmartDashboard.putNumber("joystickZ", OI.getJoystickZ());
    	SmartDashboard.putString("Drive Command", driveTrain.getCurrentCommandName());
		SmartDashboard.putNumber("Left Encoder:", driveTrain.leftEnc.get());
		SmartDashboard.putNumber("Right Encoder:", driveTrain.rightEnc.get());
		SmartDashboard.putNumber("Left Rate", driveTrain.leftEnc.getRate());
		SmartDashboard.putNumber("Right Rate", driveTrain.rightEnc.getRate());
		SmartDashboard.putNumber("GyroAngle", driveTrain.gyro.getAngle());
		SmartDashboard.putNumber("GyroRate", driveTrain.gyro.getRate());
		SmartDashboard.putNumber("Left EncoderRate:", driveTrain.leftEnc.getRate());
		SmartDashboard.putNumber("Right EncoderRate:", driveTrain.rightEnc.getRate());
		SmartDashboard.putNumber("AngleAverage",AutoAPI.sensorAverage(Robot.driveTrain.gyro.getAngle(), "gyro"));
	    SmartDashboard.putNumber("Gyro Angle", Robot.driveTrain.gyro.getAngle());
		SmartDashboard.putNumber("Left Encoder Average", AutoAPI.sensorAverage(DriveTrain.leftEnc.getRate(), "leftEnc"));
	    SmartDashboard.putNumber("Right Encoder Average", AutoAPI.sensorAverage(DriveTrain.rightEnc.getRate(), "rightEnc"));
	   // SmartDashboard.putNumber("Accelerometer Average", AutoAPI.sensorAverage(driveTrain.accelerometer.get(), "accelerometer"));
	    SmartDashboard.putNumber("Lift Encoder Average", AutoAPI.sensorAverage(Lift.liftEnc.getRate(), "liftEnc"));
	    SmartDashboard.putBoolean("Pressure Switch State", driveTrain.compressor.getPressureSwitchValue());
	    SmartDashboard.putNumber("Compressor Current", driveTrain.compressor.getCompressorCurrent());
		}
}
