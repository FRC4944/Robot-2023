package frc.robot.autos;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

import java.util.function.DoubleSupplier;

public class Turn extends CommandBase {
  private final Swerve m_Swerve;
  private final PIDController rotSpeedController = new PIDController(0.01, 0, 0.001);

  public Turn(
      Swerve driveSubsystem,
      double angleDegrees) {
    this.m_Swerve = driveSubsystem;
    rotSpeedController.setSetpoint(angleDegrees);
    rotSpeedController.enableContinuousInput(-180, 180);
    addRequirements(m_Swerve);
  }

  @Override
  public void execute() {
    double rot = rotSpeedController.calculate(m_Swerve.gyro.getYaw());
    
  }

  @Override
  public boolean isFinished() {
    return Math.abs(rotSpeedController.getSetpoint() 
    - m_Swerve.gyro.getYaw()) < 0.5;
  }
}