// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;


import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.SwerveModule;
import frc.robot.subsystems.Swerve;

public class Drive_Back_Command extends CommandBase {
  /** Creates a new Drive_Back_Command. */

  private long time;

  private final boolean auto;
  public double xdistance;
  public double xrotation;


  public double kp = Constants.AutoConstants.kPThetaController;
  public double power;
  public double time1;

  
  //private static final double MAX_POWER = 1;
  private final Swerve m_swerve;

  public SwerveModule m_swervemodule;

  public Drive_Back_Command(Swerve swerve, double xDistance, double xRotation, double power, double time1, boolean auto) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.m_swerve = swerve;
    this.auto = auto;
    this.xdistance = xDistance;
    this.xrotation = xRotation;
    this.power = power;
    this.time1 = time1;


  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (auto) {
      time = (long) (System.currentTimeMillis() + time1);
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
        
    if (auto) {
      m_swerve.drive(
        new Translation2d(xdistance, 0).times(power), 
         xrotation * Constants.Swerve.AutoAngleSpeed, 
        true, 
        true
    );
  }
} 

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Swere is done driving");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (auto) {
      return System.currentTimeMillis() > time;
    }else {
      return false;

    }
    }
  }