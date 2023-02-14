// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Wrist extends SubsystemBase {
  /** Creates a new Wrist. */
  private final TalonFX Wrist = new TalonFX(Constants.WristMotorID);
  private final TalonFXSensorCollection Wencoder = new TalonFXSensorCollection(Wrist);

  public Wrist() {

    this.Wrist.setNeutralMode(NeutralMode.Brake);

  }

  public void Wrist_On(Double Speed){
    Wrist.set(ControlMode.PercentOutput, Speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Wrist Encoder", Wencoder.getIntegratedSensorPosition());
  }
}
