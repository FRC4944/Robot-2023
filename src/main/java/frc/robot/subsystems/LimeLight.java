// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LimeLight extends SubsystemBase {
  /** Creates a new LimeLight. */
  NetworkTable lm3 = NetworkTableInstance.getDefault().getTable("limelight 3");
  NetworkTableEntry tx = lm3.getEntry("tx");
  NetworkTableEntry ty = lm3.getEntry("ty");
  NetworkTableEntry ta = lm3.getEntry("ta"); 
  
  public LimeLight() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    double x = tx.getDouble(0.0);
    double y = ty.getDouble(0.0);
    double area = ta.getDouble(0.0);
    
    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);
    
  }
}
