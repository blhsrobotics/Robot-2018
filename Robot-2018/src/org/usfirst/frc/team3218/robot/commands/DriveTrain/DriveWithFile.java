package org.usfirst.frc.team3218.robot.commands.DriveTrain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormat;

import org.usfirst.frc.team3218.robot.AutoAPI;
import org.usfirst.frc.team3218.robot.Robot;
import org.usfirst.frc.team3218.robot.subsystems.CubeControl;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveWithFile extends Command {
	boolean setter = false;
	private File folder;
	private DecimalFormat format = new DecimalFormat("#");
	private File yValues;
	private File zValues;
	private File collectionValues;
	private File liftValues;
	private FileReader yReader;
	private FileReader zReader;
	private FileReader collectionReader;
	private FileReader liftReader;
	private BufferedReader bufReadY;
	private BufferedReader bufReadZ;
	private BufferedReader bufReadCollection;
	private BufferedReader bufReadLift;
	private double numberY;
	private double numberZ;
	private String numberCollect;
	private String numberLift;
	private String startingLoc = "/home/lvuser/"+Robot.autoFile;
	private int i = 0;
	private double speedLeft = 0;
	private double speedRight = 0;
	private int encPerPer = 0;
	public DriveWithFile() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);

		requires(Robot.driveTrain);
		requires(Robot.lift);
		requires(Robot.cubeControl);
	}


    // Called just before this Command runs the first time
    protected void initialize() {
    	setter=false;

    	System.out.println("DriveStartingLoc = " + startingLoc);
    	yValues = new File(startingLoc+"/yValues.txt");
        zValues = new File(startingLoc+"/zValues.txt");
        collectionValues = new File(startingLoc+"/collectionValues.txt");
        liftValues = new File(startingLoc+"/liftValues.txt");
        if(zValues.exists()){
   	   System.out.println("file exists");
      }
      
        yReader = null;
        zReader = null;
         collectionReader = null;
        liftReader = null;
       
       System.out.println("init readers & files");
       try{
       	yReader = new FileReader(yValues);
       	zReader = new FileReader(zValues);
       	collectionReader = new FileReader(collectionValues);
       	liftReader = new FileReader(liftValues);
       System.out.println("made readers");
       }
       catch(IOException e){
       	e.printStackTrace();
       	System.out.println("didnt make readers");
       }
        bufReadY = new BufferedReader(yReader);
        bufReadZ = new BufferedReader(zReader);
        bufReadCollection = new BufferedReader(collectionReader);
        bufReadLift = new BufferedReader(liftReader);
       
       System.out.println("Finished init");
}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    		
		try {
			numberY = Double.parseDouble(bufReadY.readLine());
			numberZ = Double.parseDouble(bufReadZ.readLine());
			numberCollect = bufReadCollection.readLine();
			numberLift = bufReadLift.readLine();
			System.out.println(i);
			i++;
					
			if(i==600){
				System.out.println("LIFT ENC: " +Robot.lift.liftEnc.get());
			}
					
			speedLeft = (numberY-Robot.driveTrain.leftEnc.get())/encPerPer;
			speedRight = (numberZ-Robot.driveTrain.rightEnc.get())/encPerPer;
					AutoAPI.sideDrive(speedLeft, Robot.driveTrain.leftDrive);
					AutoAPI.sideDrive(speedRight, Robot.driveTrain.rightDrive);
					Robot.cubeControl.cubeControlFile(Double.parseDouble(numberCollect));
					
					Robot.lift.setPosition(Robot.lift.positionArray[(int)Double.parseDouble(numberLift)]);
					
			
			
			
		} catch (NumberFormatException | IOException | NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			setter=true;
			   System.out.println(Robot.driveTrain.rightEnc.get());
			    System.out.println(Robot.driveTrain.leftEnc.get());
			System.out.println("values went to null");
		}
		
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       if(setter){
    	   return true;
       }
       else{
    	   return false;
       }
    }

    // Called once after isFinished returns true
    protected void end() {
    	 
    	 i=0;
        
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
