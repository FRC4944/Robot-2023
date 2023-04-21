// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autos;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

public class AutoEngage1 extends CommandBase {
  /** Creates a new AutoEngage1. */
  public static Swerve m_Swerve;

  public double distance;  
  public double kp = 0.02;

  //private long time;

  private final boolean auto;

  
  public AutoEngage1(Swerve m_Swerve, boolean auto) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.m_Swerve = m_Swerve;
    this.auto = auto;
    addRequirements(m_Swerve);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    distance = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    distance = m_Swerve.gyro.getPitch();

    if (m_Swerve.gyro.isConnected() == false){
      distance = 0;
    }

    double delta = 0 - distance;
    double drive = delta * kp;

    System.out.println("values are changing");
    if(auto){
    m_Swerve.drive(new Translation2d(drive, 0).times(Constants.Swerve.AutoEngageMaxspeed), 
    0 * Constants.Swerve.AutoAngleSpeed, 
    true, 
    true
    );

    System.out.println("this should be driving right now");
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
