// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;

public class HorizontalElevator extends SubsystemBase {
  /** Creates a new HorizontalElevator. */
  private final TalonFX horizontal_elevator_motor = new TalonFX(Constants.horizontalElevatorMotorID);
  private final TalonFXSensorCollection hencoder   = new TalonFXSensorCollection(horizontal_elevator_motor);
  public final PIDController pid = new PIDController(.43, 1/20.0, 0.0008);

  //Constants
  private static final int ENCODER_BUFFER = 500;
  private static final int BOTTOM_ENCODER_VALUE = 0 + ENCODER_BUFFER;
  private static final int TOP_ENCODER_VALUE = 25000 - ENCODER_BUFFER;
  private static final double MAX_POWER = 0.7;

  public HorizontalElevator() {
    this.pid.setTolerance(0.15, 0.05/20);
    this.horizontal_elevator_motor.setNeutralMode(NeutralMode.Brake);

    this.setSetpoint(this.getEncoderValue());
  }

  public void Horizontal_Elevator_On(Double power){
    horizontal_elevator_motor.set(ControlMode.PercentOutput, power);
  }
 

  public void driveTowardsPid() {
    double power = this.getPidPower();
    this.Horizontal_Elevator_On(power);
  }


  private double getEncoderValue() {
    return ((this.hencoder.getIntegratedSensorPosition() - BOTTOM_ENCODER_VALUE) / (TOP_ENCODER_VALUE - BOTTOM_ENCODER_VALUE));
  }


  private double getPidPower() {
    double power = this.pid.calculate(this.getEncoderValue());
    return Math.min(MAX_POWER, Math.max(-MAX_POWER, power));
  }
  // setpoints
  public void setSetpoint(double setpoint) {
    this.pid.setSetpoint(setpoint);
  }

  public double hZero(){
    return (this.getEncoderValue() * 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Horizontal Encoder", hencoder.getIntegratedSensorPosition());
    SmartDashboard.putNumber("Horizontal Encoder", getEncoderValue());
    
  }
}

