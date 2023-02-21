// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  public CANSparkMax intake;
  public Intake() {
    intake = new CANSparkMax(Constants.IntakeMotorID, MotorType.kBrushless);
    
  }

  public void intake_on(double power){
    intake.set(power);
  }

  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
