// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class WaitUntil extends CommandBase {
  /** Creates a new wait. */
  double endTime;
  double duration;
  public WaitUntil(double endTime) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.duration = endTime;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.endTime = System.currentTimeMillis() + this.duration;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (System.currentTimeMillis() > endTime) {
      return true;
    } else {
      return false;
    }
    
  }
}
