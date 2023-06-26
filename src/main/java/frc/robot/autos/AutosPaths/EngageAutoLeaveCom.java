package frc.robot.autos.AutosPaths;


import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.autos.AutoEngage1;
import frc.robot.autos.AutoEngage2;
import frc.robot.autos.WaitUntil;
import frc.robot.autos.intakeOff;
import frc.robot.autos.intakeOn;
import frc.robot.autos.setGyro;
import frc.robot.commands.Drive_Back_Command;
import frc.robot.commands.Drive_Zero;
import frc.robot.commands.HorizontalFirstVerticalCommand;
import frc.robot.commands.VerticalFirstHorizontalCommand;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class EngageAutoLeaveCom extends SequentialCommandGroup {
    private final Swerve m_swerve;

    public double rotation = Math.PI / 6;

    public EngageAutoLeaveCom(Swerve swerve){
        this.m_swerve = swerve;
        // needs to change
        setGyro setGyro = new setGyro(78);

        addCommands(
            setGyro,
            new InstantCommand(() -> RobotContainer.wrist.setSetpoint(0.7)),
            new VerticalFirstHorizontalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 1.18, -1, .95, true), 
            new intakeOn(0.9),
            new WaitUntil(500),
            new intakeOff(), 
            new HorizontalFirstVerticalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 0.05, -0.05, 1, true),
            new Drive_Back_Command(swerve, .9, 0, Constants.Swerve.AutoMaxspeed, 1000, true),
            new HorizontalFirstVerticalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 0.05, -0.05, 1.3, true),
            new Drive_Back_Command(swerve, .95, 0, Constants.Swerve.AutoMaxspeed, 400, true),
            new Drive_Zero(swerve, 100, true),
            new WaitUntil(600),
            new Drive_Back_Command(swerve, -1.1, 0, Constants.Swerve.AutoMaxspeed, 1200, true),
            // new Drive_Back_Command(swerve, .4, 0, true),
            new AutoEngage1(swerve, true)
        );
    }
}