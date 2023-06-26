// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.HorizontalElevator;
import frc.robot.subsystems.VerticalElevator;
import frc.robot.subsystems.Wrist;


public class VerticalFirstHorizontalCommand extends CommandBase {


  private VerticalElevator verticalElevator;
  private HorizontalElevator horizontalElevator;
  private Wrist wrist;
  // setpoints
  private double verticalSetpoint;
  private double horizontalSetpoint;
  private double wristSetpoint;

  private boolean auto;

  private long time;
  // private long varTime = 2000;

  public VerticalFirstHorizontalCommand(VerticalElevator verticalElevator, HorizontalElevator horizontalElevator, Wrist wrist,
    double verticalSetpoint, double horizontalSetpoint, double wristSetpoint, boolean auto
  ) {
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
    // this.verticalElevator.setSetpoint(this.verticalSetpoint);

    if (auto) {
      time = System.currentTimeMillis() + 1500;
    }

    //this.horizontalElevator.setSetpoint(this.horizontalSetpoint);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.verticalElevator.setSetpoint(this.verticalSetpoint);
    if (verticalElevator.pid.atSetpoint()) {
      this.horizontalElevator.setSetpoint(this.horizontalSetpoint); 
      this.wrist.setSetpoint(this.wristSetpoint);

    // if(wrist.pid.atSetpoint()){
    //   this.horizontalElevator.setSetpoint(this.horizontalSetpoint);

    // }
  }

    //FOR AUTOS ONLY! Makes the command run the drive towards PID Commands effectively making it an all in one command.  NOTE: normally it would only be like this but for some reason it needs to run constantly
    if (auto) {
      this.verticalElevator.driveTowardsPid();
      this.horizontalElevator.driveTowardsPid();
      this.wrist.driveTowardsPid();
    }

    System.out.println("Command working");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("v working");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (auto) {
      return System.currentTimeMillis() > time;
    } else {
      return this.verticalElevator.pid.atSetpoint() && this.horizontalElevator.pid.atSetpoint() && this.wrist.pid.atSetpoint();
    }
  }
}
