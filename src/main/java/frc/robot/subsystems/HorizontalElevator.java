// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class HorizontalElevator extends SubsystemBase {
  /** Creates a new HorizontalElevator. */
  TalonFX horizontal_elevator_motor = new TalonFX(Constants.Swerve.horizontalElevatorMotorID);

  public HorizontalElevator() {
  }

  public void Horizontal_Elevator_On(Double power){
    horizontal_elevator_motor.set(ControlMode.PercentOutput, power);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
