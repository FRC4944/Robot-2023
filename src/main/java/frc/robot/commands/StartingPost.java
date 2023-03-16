// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HorizontalElevator;
import frc.robot.subsystems.VerticalElevator;
import frc.robot.subsystems.Wrist;

public class StartingPost extends CommandBase {

  private VerticalElevator verticalElevator;
  private HorizontalElevator horizontalElevator;
  private Wrist wrist;
  
  private double verticalSetpoint;
  private double horizontalSetpoint;
  private double wristSetpoint;

  private long time;

  private boolean auto;

  public StartingPost(VerticalElevator verticalElevator, HorizontalElevator horizontalElevator, Wrist wrist,
    double verticalSetpoint, double horizontalSetpoint, double wristSetpoint, boolean auto) {
    this.horizontalElevator = horizontalElevator;
    this.verticalElevator = verticalElevator;
    this.wrist = wrist;

    this.verticalSetpoint = verticalSetpoint;
    this.horizontalSetpoint = horizontalSetpoint;
    this.wristSetpoint = wristSetpoint;

    this.auto = auto;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.horizontalElevator.setSetpoint(this.horizontalSetpoint);
    if (auto) {
      time = System.currentTimeMillis() + 2000;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (horizontalElevator.pid.getPositionError() < 0.01) {
      this.verticalElevator.setSetpoint(this.verticalSetpoint);
      this.wrist.setSetpoint(this.wristSetpoint);
    }

    if(auto) {
      this.verticalElevator.driveTowardsPid();
      this.horizontalElevator.driveTowardsPid();
      this.wrist.driveTowardsPid();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(auto) {
      return System.currentTimeMillis() > time;
    } else {
      return this.verticalElevator.pid.atSetpoint() && this.horizontalElevator.pid.atSetpoint() 
      && this.wrist.pid.atSetpoint();
    }
  }
}