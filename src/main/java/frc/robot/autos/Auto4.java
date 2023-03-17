package frc.robot.autos;


import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.Drive_Back_Command;
import frc.robot.commands.HorizontalFirstVerticalCommand;
import frc.robot.commands.VerticalFirstHorizontalCommand;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class Auto4 extends SequentialCommandGroup {
    private final Swerve m_swerve;

    public double rotation = Math.PI / 4;

    public Auto4(Swerve swerve){
        this.m_swerve = swerve;
        // needs to change
        setGyro setGyro = new setGyro(78);

        addCommands(
            setGyro,
            new InstantCommand(() -> RobotContainer.wrist.setSetpoint(0.7)),
            new VerticalFirstHorizontalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 1.08, .55, 0.587, true), 
            new intakeOn(),
            new WaitUntil(3000),
            new intakeOff(), 
            new HorizontalFirstVerticalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 0.05, 0.05, 0.6, true),
            new Drive_Back_Command(swerve, .7, 0, Constants.Swerve.AutoMaxspeed, true),
            new WaitUntil(200),
            new Drive_Back_Command(swerve, .4, 0, Constants.Swerve.AutoMaxspeed, true),
            new Drive_Back_Command(swerve, -.6, 0, Constants.Swerve.AutoMaxspeed1, true)
            // new Drive_Back_Command(swerve, .4, 0, true),
            //new AutoEngageCommand(swerve, true)
        );
    }
}