// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.Engagelib;
import frc.robot.subsystems.Swerve;

public class Auto_Engage extends CommandBase {
  /** Creates a new Auto_Engage. */
  private final Swerve m_swerve;
  public Auto_Engage(Swerve swerve) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.m_swerve = swerve;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
