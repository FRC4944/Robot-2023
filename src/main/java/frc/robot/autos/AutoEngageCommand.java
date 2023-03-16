// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autos;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Swerve;

public class AutoEngageCommand extends CommandBase {
  /** Creates a new AutoEngageCommand. */
  private double balanaceEffort; // The effort the robot should use to balance
  private double turningEffort; // The effort the robot should use to turn
  Swerve m_Swerve;

  private final boolean auto;
  private long time;
  public AutoEngageCommand(Swerve swerve, boolean auto) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.m_Swerve = swerve;
    this.auto = auto;

    addRequirements(swerve);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (auto) {
      time = System.currentTimeMillis() + 2000;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
  if (auto) {
    balanaceEffort =
    (0 - m_Swerve.gyro.getPitch()) 
            * 0.006;

  turningEffort =
    (0 - m_Swerve.gyro.getYaw())
            * 0.007;

    m_Swerve.drive(new Translation2d(-balanaceEffort, 0).times(Constants.Swerve.AutoMaxspeed), 
    turningEffort * Constants.Swerve.AutoAngleSpeed, 
    true, 
    true
    );

    System.out.println("working auto-engage");

    if (m_Swerve.gyro.getPitch() > 1){
      RobotContainer.candle.candleOn(0, 250, 0);
    }
}
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {

  m_Swerve.drive(new Translation2d(0, 0).times(Constants.Swerve.AutoMaxspeed), 
  0 * Constants.Swerve.AutoAngleSpeed, 
  true, 
  true
  );

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    if (auto) {
      return System.currentTimeMillis() > time;
    }else {
      return Math.abs(m_Swerve.gyro.getPitch()) < 2;
      //test
      //test
    }
  }
}
