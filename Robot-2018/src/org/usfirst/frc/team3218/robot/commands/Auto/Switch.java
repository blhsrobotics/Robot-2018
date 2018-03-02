package org.usfirst.frc.team3218.robot.commands.Auto;

import javax.swing.text.html.FormSubmitEvent;

import org.usfirst.frc.team3218.robot.AutoAPI;
import org.usfirst.frc.team3218.robot.Robot;
import org.usfirst.frc.team3218.robot.commands.CubeControl.CubeControlXbox;
import org.usfirst.frc.team3218.robot.commands.CubeControl.CubeEjectionAuto;
import org.usfirst.frc.team3218.robot.subsystems.Lift;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Switch extends Command {

    public Switch() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	requires(Robot.cubeControl);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	String sendableChosenString = Robot.position.getSelected() + Robot.gameData.substring(0,1);
		switch(sendableChosenString){
		case "1L": 
		
		AutoAPI.driveStraight(AutoAPI.WALL_TO_SWITCH, 2000, 250);
		AutoAPI.rotate(92, 300, 300);
		AutoAPI.moveToHeight(2);
		AutoAPI.simpleDrive(6);
		Robot.cubeControl.cubeEjection();
		Timer.delay(4);
		Robot.cubeControl.cubeOff();
		AutoAPI.simpleDrive(-36);
		AutoAPI.moveToHeight(0);
		
	
		//AutoAPI.moveToHeight(0);
		break;
		case "1R": 
		AutoAPI.driveStraight(AutoAPI.WALL_TO_PLATFORM_CHANNEL, 2000, 250);
		AutoAPI.rotate(92, 300, 300);
		AutoAPI.moveToHeight(1);
		//reduce by 50
		AutoAPI.driveStraight(AutoAPI.HORIZONTAL_FAR_SIDE,2000,250);//drive across field
		AutoAPI.rotate(92,300,300);
		AutoAPI.moveToHeight(2);
		AutoAPI.simpleDrive(10);
		break;
		case "3R":
			AutoAPI.driveStraight(AutoAPI.WALL_TO_SWITCH, 2000, 250);
			AutoAPI.rotate(-90, 300, 300);
			AutoAPI.driveStraight(12,300,300);
			AutoAPI.moveToHeight(2);
		break;
		case "3L":
			AutoAPI.driveStraight(AutoAPI.WALL_TO_PLATFORM_CHANNEL, 2000, 250);
			AutoAPI.rotate(-90, 300, 300);
			AutoAPI.moveToHeight(2);
			AutoAPI.driveStraight(AutoAPI.HORIZONTAL_FAR_SIDE,2000,250);//drive across field
			AutoAPI.rotate(-95,300,300);
			AutoAPI.driveStraight(4,2000,250);
		break;
		/* "2CloseLeft": methodCall;
		break;
		case "2FarLeft": methodCall;
		break;
		case "2CloseRight": methodCall;
		break;
		case "2FarRight": methodCall;
		break;
		case "3CloseLeft": methodCall;
		break;
		case "3FarLeft": methodCall;
		break;
		default:nothing;*/
		}
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    //	new CubeControlXbox().start();
    	Robot.lift.gearLow();  
    	Robot.lift.liftMaster.set(0);
    	Robot.lift.lift2.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
