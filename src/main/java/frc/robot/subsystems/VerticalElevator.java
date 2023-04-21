// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXSensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.simulation.DIOSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;


public class VerticalElevator extends SubsystemBase {
  /** Creates a new VerticalElevator. */
  private final TalonFX vertical_elevator_motor1 = new TalonFX(Constants.verticalElevatorMotor1ID);
  private final TalonFX vertical_elevator_motor2 = new TalonFX(Constants.verticalElevatorMotor2ID);
  private final TalonFXSensorCollection encoder   = new TalonFXSensorCollection(vertical_elevator_motor1);
  public final PIDController pid = new PIDController(0.735, 1.0/8.0, 0.0001);
  private double setPoint = 0;
  DIOSim BrakeButton = new DIOSim(0);

  // Constants
  private static final int ENCODER_BUFFER = 500;
  private static final int BOTTOM_ENCODER_VALUE = -30000 + ENCODER_BUFFER;
  private static final int TOP_ENCODER_VALUE = 46000 - ENCODER_BUFFER;
  private static final double MAX_POWER = 0.9;

  public VerticalElevator(){
    this.pid.setTolerance(0.15, 0.05/20);

    this.vertical_elevator_motor1.setNeutralMode(NeutralMode.Brake);
    this.vertical_elevator_motor1.setInverted(false);
    this.vertical_elevator_motor2.setNeutralMode(NeutralMode.Brake);
    this.vertical_elevator_motor2.setInverted(false);
    this.setSetpoint(this.getEncoderValue());
 }
  
  public void Vertical_Elevator_On(Double power) {
    vertical_elevator_motor1.set(ControlMode.PercentOutput, power);
    vertical_elevator_motor2.set(ControlMode.PercentOutput, power);
    vertical_elevator_motor1.setNeutralMode(NeutralMode.Brake);
    vertical_elevator_motor2.setNeutralMode(NeutralMode.Brake);
    
  }

  public double getEncoderValue() {
    return (this.encoder.getIntegratedSensorPosition() - BOTTOM_ENCODER_VALUE) / (TOP_ENCODER_VALUE - BOTTOM_ENCODER_VALUE);
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
  // setpoints
  public void setSetpoint(double setpoint) {
    this.setPoint = setpoint;
    this.pid.setSetpoint(setpoint);
  }

  public double vZero(){
    return (this.getEncoderValue() * 0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
   // double Vdegrees = Vcoder.getPosition();
    SmartDashboard.putNumber("Vertical Encoders", encoder.getIntegratedSensorPosition());
    SmartDashboard.putNumber("Current Setpoint", this.setPoint);
    SmartDashboard.putNumber("Motor Power", this.vertical_elevator_motor1.getMotorOutputPercent());
    SmartDashboard.putNumber("Transform Encoder Value vertical", getEncoderValue());

    if (BrakeButton.getIsInput() == true){
      this.vertical_elevator_motor1.setNeutralMode(NeutralMode.Coast);
      this.vertical_elevator_motor2.setNeutralMode(NeutralMode.Coast);
    }

    if (BrakeButton.getIsInput() == false){
      this.vertical_elevator_motor1.setNeutralMode(NeutralMode.Brake);
      this.vertical_elevator_motor2.setNeutralMode(NeutralMode.Brake);
    }
  }

  public void driveTowardsPid() {
    double power = this.getPidPower();
    this.Vertical_Elevator_On(power);
  }
}
