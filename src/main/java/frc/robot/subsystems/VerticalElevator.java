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

public class VerticalElevator extends SubsystemBase {
  /** Creates a new VerticalElevator. */
  private final TalonFX vertical_elevator_motor = new TalonFX(Constants.verticalElevatorMotorID);
  private final TalonFXSensorCollection encoder   = new TalonFXSensorCollection(vertical_elevator_motor);
  public final PIDController pid = new PIDController(0.9, 1.0/7.0, 0);
  private double setPoint = 0;

  // Constants
  private static final int ENCODER_BUFFER = 500;
  private static final int BOTTOM_ENCODER_VALUE = -30000 + ENCODER_BUFFER;
  private static final int TOP_ENCODER_VALUE = 46000 - ENCODER_BUFFER;
  private static final double MAX_POWER = 0.7;

  public VerticalElevator(){
    this.pid.setTolerance(0.2, 0.05/20);

    this.vertical_elevator_motor.setNeutralMode(NeutralMode.Coast);
    this.vertical_elevator_motor.setInverted(true);
    this.setSetpoint(this.getEncoderValue());
 }
  
  public void Vertical_Elevator_On(Double power) {
    vertical_elevator_motor.set(ControlMode.PercentOutput, power);
    
  }

  private double getEncoderValue() {
    return (-this.encoder.getIntegratedSensorPosition() - BOTTOM_ENCODER_VALUE) / (TOP_ENCODER_VALUE - BOTTOM_ENCODER_VALUE);
  }

  private double getPidPower() {
    double power = this.pid.calculate(this.getEncoderValue());
    return Math.min(MAX_POWER, Math.max(-MAX_POWER, power));
  }

  /**
   * Set the current setpoint for where the elevator should move to
   * 
   * @param setpoint Value between 0 and 1
   */
  public void setSetpoint(double setpoint) {
    this.setPoint = setpoint;
    this.pid.setSetpoint(setpoint);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
   // double Vdegrees = Vcoder.getPosition();
    SmartDashboard.putNumber("Vertical Encoders", encoder.getIntegratedSensorPosition());
    SmartDashboard.putNumber("Current Setpoint", this.setPoint);
    SmartDashboard.putNumber("Motor Power", this.vertical_elevator_motor.getMotorOutputPercent());
    SmartDashboard.putNumber("Transform Encoder Value vertical", getEncoderValue());
  }

  public void driveTowardsPid() {
    double power = this.getPidPower();
    this.Vertical_Elevator_On(power);
  }
}
