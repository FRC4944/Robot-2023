// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autos;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

public class AutoEngage2 extends CommandBase {
  /** Creates a new AutoEngage1. */
  public static Swerve m_Swerve;

  public double distance;  
  public double power;
  public double drivekp = 0.1;
  public double kp = 0.016;

  //private long time;

  private final boolean auto;

  
  public AutoEngage2(Swerve m_Swerve, boolean auto) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.m_Swerve = m_Swerve;
    this.auto = auto;
    addRequirements(m_Swerve);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    distance = 0;
    power = 0;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    distance = m_Swerve.gyro.getPitch();

    double delta = 0 - distance;
    double driveDisance = delta * kp;

    double power = delta * drivekp;


    System.out.println("values are changing");
    if(auto){
    m_Swerve.drive(new Translation2d(driveDisance, 0).times(power), 
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