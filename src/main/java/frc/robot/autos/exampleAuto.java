package frc.robot.autos;

import frc.robot.RobotContainer;
import frc.robot.commands.VisionLineup;
import frc.robot.commands.HorizontalFirstVerticalCommand;
import frc.robot.commands.VerticalFirstHorizontalCommand;
import frc.robot.subsystems.CANDle;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class exampleAuto extends SequentialCommandGroup {
    private final Swerve m_swerve;
    public exampleAuto(Swerve swerve){
        this.m_swerve = swerve;
    


        addCommands(
            new InstantCommand(() -> RobotContainer.wrist.setSetpoint(0.7)),
            new VerticalFirstHorizontalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 1.08, .55, 0.587, true), 
            new intakeOn(),
            new WaitUntil(3000),
            new intakeOff(), 
            new HorizontalFirstVerticalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, 0.05, 0.05, true),
            //new DriveAutoLineCommandGroup(swerve, 0, 5, 0, 2000)
            //new Drive_Back(swerve)
            new VisionLineup(swerve, RobotContainer.candle, 1)
        );
    }
}


//Results from testing:
//34.5 in
//31.75 in
//35 in
//35 in
//34 in 

// 1 unit is about 34 inches