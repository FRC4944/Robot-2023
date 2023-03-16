package frc.robot.autos;


import frc.robot.RobotContainer;
import frc.robot.commands.Drive_Back_Command;
import frc.robot.commands.Engage_Auto;
import frc.robot.commands.HorizontalFirstVerticalCommand;
import frc.robot.commands.VerticalFirstHorizontalCommand;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class Auto2 extends SequentialCommandGroup {
    private final Swerve m_swerve;

    public double rotation = Math.PI / 4;

    public Auto2(Swerve swerve){
        this.m_swerve = swerve;
        // needs to change
        setGyro setGyro = new setGyro(180);

        addCommands(
            setGyro,
            new InstantCommand(() -> RobotContainer.wrist.setSetpoint(0.7)),
            new VerticalFirstHorizontalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 1.08, -.55, 0.587, true), 
            new intakeOn(),
            new WaitUntil(3000),
            new intakeOff(), 
            //test
            new HorizontalFirstVerticalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 0.05, -0.05, 0.6, true),
            new Drive_Back_Command(swerve, .68, 0, true),
            // new Drive_Back_Command(swerve, .4, 0, true),
            new AutoEngageCommand(swerve, true),
            new InstantCommand(() -> swerve.zeroGyro())
        );
    }
}
