// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.subsystems.HorizontalElevator;
import frc.robot.subsystems.VerticalElevator;

public class HorizontalFirstVerticalCommand extends CommandBase {
  /** Creates a new HighScore. */

  private VerticalElevator verticalElevator;
  private HorizontalElevator horizontalElevator;
  
  private double verticalSetpoint;
  private double horizontalSetpoint;

  public HorizontalFirstVerticalCommand(VerticalElevator verticalElevator, HorizontalElevator horizontalElevator,
    double verticalSetpoint, double horizontalSetpoint
  ) {
    this.horizontalElevator = horizontalElevator;
    this.verticalElevator = verticalElevator;

    this.verticalSetpoint = verticalSetpoint;
    this.horizontalSetpoint = horizontalSetpoint;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.print("HorizontalFirstVerticalCommand started");

    this.horizontalElevator.setSetpoint(this.horizontalSetpoint);

    // this.horizontalElevator.setSetpoint(this.horizontalSetpoint);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (horizontalElevator.pid.atSetpoint()) {
      this.verticalElevator.setSetpoint(this.verticalSetpoint);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.print("HorizontalFirstVerticalCommand finished");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return this.verticalElevator.pid.atSetpoint() && this.horizontalElevator.pid.atSetpoint();
  }
}
