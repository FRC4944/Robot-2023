// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Partner_Lift extends SubsystemBase {
  /** Creates a new Partner_Lift. */
  public CANSparkMax partner_Lift = new CANSparkMax(19, MotorType.kBrushless);
  public Partner_Lift() {
    
    
  }

  public void Partner_Lift_On(double power){
    partner_Lift.set(power);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
