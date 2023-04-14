package frc.robot.autos.AutosPaths;


import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.autos.setGyro;
import frc.robot.commands.AutoDrive;
import frc.robot.commands.Drive_Back_Command;
import frc.robot.commands.HorizontalFirstVerticalCommand;
import frc.robot.commands.VerticalFirstHorizontalCommand;
import frc.robot.subsystems.Swerve;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;


public class TwoPieceAuto extends SequentialCommandGroup {
    private final Swerve m_swerve;

    public TwoPieceAuto(Swerve swerve){
        this.m_swerve = swerve;
        // needs to change
        setGyro setGyro = new setGyro(78);

        addCommands(
            new InstantCommand(() -> swerve.zeroGyro()),
            //new AutoDrive(swerve)
            new PathPlannerTest(swerve)
        );
    }
}