package frc.robot.autos;


import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.Drive_Back_Command;
import frc.robot.commands.HorizontalFirstVerticalCommand;
import frc.robot.commands.VerticalFirstHorizontalCommand;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class Auto5 extends SequentialCommandGroup {
    private final Swerve m_swerve;

    public double rotation = Math.PI / 3.33;

    public Auto5(Swerve swerve){
        this.m_swerve = swerve;
        // needs to change
        setGyro setGyro = new setGyro(258);

        addCommands(
            setGyro,
            new InstantCommand(() -> RobotContainer.wrist.setSetpoint(0.7)),
            new VerticalFirstHorizontalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 1.15, -1, 1, true), 
            new VerticalFirstHorizontalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 1.15, -1, 1, true), 
            new intakeOn(),
            new WaitUntil(2500),
            new intakeOff(), 
            new HorizontalFirstVerticalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 0.05, -0.05, 1.1, true),
            new Drive_Back_Command(swerve, 1.6, 0, Constants.Swerve.AutoMaxspeed,1000, true),
            new WaitUntil(400),
            new Drive_Back_Command(swerve, 0, -rotation, Constants.Swerve.AutoMaxspeed, 1000, true),
            new HorizontalFirstVerticalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 0.05, 0.05, .88, true),
            new Drive_Intake(swerve, RobotContainer.intake, .3, 0, Constants.Swerve.AutoMaxspeed, true),
            new HorizontalFirstVerticalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 0.05, 0.05, .88, true),
            new intakeOff(),
            new intakeOn(),
            new WaitUntil(1000),
            new intakeOff()
        );  
    }


}