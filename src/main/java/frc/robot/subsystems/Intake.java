// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  TalonFX intake;
  public Intake() {
    intake = new TalonFX(Constants.IntakeMotorID);
    this.intake.setNeutralMode(NeutralMode.Brake);
  }

  public void intake_on(double power){
    intake.set(ControlMode.PercentOutput, power);
  }

  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
