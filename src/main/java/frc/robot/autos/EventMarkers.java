// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autos;

import java.util.Map;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Command.InterruptionBehavior;
import frc.robot.RobotContainer;
import frc.robot.commands.VerticalFirstHorizontalCommand;
import frc.robot.subsystems.HorizontalElevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.VerticalElevator;
import frc.robot.subsystems.Wrist;

/** Add your docs here. */
public class EventMarkers {

    public static Map<String, Command> getMap(Intake intake, VerticalElevator verticalelevator, HorizontalElevator horizontalelevator, Wrist wrist) {
        return Map.of(
            "Intake", new VerticalFirstHorizontalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, -0.02, -0.1, 1, true),
            "Score Cone", new VerticalFirstHorizontalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 1.17, -1, 1, true),
            "Score Cube", new VerticalFirstHorizontalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 1.15, -1, 1.2, true)

            );
    }
}
