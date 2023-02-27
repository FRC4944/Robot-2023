// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.subsystems.HorizontalElevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.VerticalElevator;

public class GroundIntake extends CommandBase {
  /** Creates a new HighScore. */

  private VerticalElevator verticalElevator;
  private HorizontalElevator horizontalElevator;
  private Intake intake;
  
  private double verticalSetpoint;
  private double horizontalSetpoint;
  private double power;

  public GroundIntake(VerticalElevator verticalElevator, HorizontalElevator horizontalElevator, Intake intake,
    double verticalSetpoint, double horizontalSetpoint, double power
  ) {
    this.horizontalElevator = horizontalElevator;
    this.verticalElevator = verticalElevator;
    this.intake = intake;

    this.verticalSetpoint = verticalSetpoint;
    this.horizontalSetpoint = horizontalSetpoint;
    this.power = power;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.verticalElevator.setSetpoint(this.verticalSetpoint);

    // this.horizontalElevator.setSetpoint(this.horizontalSetpoint);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // if (verticalElevator.pid.atSetpoint()) {
    if (verticalElevator.pid.getPositionError() < 0.3) {
      this.horizontalElevator.setSetpoint(this.horizontalSetpoint);
      this.intake.intake_on(power);
    }
    System.out.println("Command working");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return this.verticalElevator.pid.atSetpoint() && this.horizontalElevator.pid.atSetpoint();
  }
}