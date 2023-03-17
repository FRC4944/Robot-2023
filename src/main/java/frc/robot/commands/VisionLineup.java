// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.lang.Math;
import frc.robot.Constants;
import frc.robot.subsystems.CANDle;
import frc.robot.subsystems.Swerve;

public class VisionLineup extends CommandBase {
    private final Swerve m_swerve;
    private final CANDle m_candle;
    private int m_pipeline;
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
    NetworkTableEntry ta = table.getEntry("ta");
    double kp = 0.02;
    
    public VisionLineup(Swerve swerve, CANDle candle, int pipeline) {
      // Use addRequirements() here to declare subsystem dependencies.
      this.m_swerve = swerve;
      this.m_candle = candle;
      this.m_pipeline = pipeline;
    }
    
  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // pipeline 1 is april tags and 2 is reflective
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(m_pipeline);

    //read values periodically
    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);

    //post to smart dashboard periodically
    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);
    

    System.out.print("Limelight is working");
  

    m_swerve.drive(
      new Translation2d(0, -x).times(Constants.Swerve.lm3maxSpeed), 
       0, 
      false, 
      true
    );
    while(Math.abs(x) < 0.3) {
      m_candle.candleOn(10, 200, 20);
    }
  }


  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}