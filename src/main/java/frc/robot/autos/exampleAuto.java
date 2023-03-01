package frc.robot.autos;

import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.HorizontalFirstVerticalCommand;
import frc.robot.commands.StartingPost;
import frc.robot.commands.VerticalFirstHorizontalCommand;
import frc.robot.subsystems.Swerve;
import frc.robot.subsystems.Wrist;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;

public class exampleAuto extends SequentialCommandGroup {
    public exampleAuto(Swerve s_Swerve){
        TrajectoryConfig config =
            new TrajectoryConfig(
                    Constants.AutoConstants.kMaxSpeedMetersPerSecond,
                    Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
                .setKinematics(Constants.Swerve.swerveKinematics);

        TrajectoryConfig config2 =  new TrajectoryConfig(
            Constants.AutoConstants.kMaxSpeedMetersPerSecond,
            Constants.AutoConstants.kMaxAccelerationMetersPerSecondSquared)
        .setKinematics(Constants.Swerve.swerveKinematics);

        config2.setReversed(true);  //TODO Figure out how to make negative cordinates to work.

        Trajectory exampleTrajectory =
            TrajectoryGenerator.generateTrajectory(
                // Start at the origin facing the +X direction
                new Pose2d(0, 0, new Rotation2d(0)),
                // Pass through these two interior waypoints.
                List.of(new Translation2d(1, 0), new Translation2d(1, 0)),
                
                new Pose2d(1, 0, new Rotation2d(0)),
                config);

            Trajectory trajectory1 =
                    TrajectoryGenerator.generateTrajectory(
                        // Start at the origin facing the +X direction
                        new Pose2d(1, 0, new Rotation2d(0)),
                        // Pass through these two interior waypoints.
                        List.of(new Translation2d(1, 0), new Translation2d(2, 0)),
                        
                        new Pose2d(2.5, 0, new Rotation2d(0)),
                        config2);

        var thetaController =
            new ProfiledPIDController(
                Constants.AutoConstants.kPThetaController, 0, 0, Constants.AutoConstants.kThetaControllerConstraints);
        thetaController.enableContinuousInput(-Math.PI, Math.PI);

        SwerveControllerCommand swerveControllerCommand =
            new SwerveControllerCommand(
                exampleTrajectory,
                s_Swerve::getPose,
                Constants.Swerve.swerveKinematics,
                new PIDController(Constants.AutoConstants.kPXController, 0, 0),
                new PIDController(Constants.AutoConstants.kPYController, 0, 0),
                thetaController,
                s_Swerve::setModuleStates,
                s_Swerve);

        SwerveControllerCommand swerveControllerCommand2 =
            new SwerveControllerCommand(
                trajectory1,
                s_Swerve::getPose,
                Constants.Swerve.swerveKinematics,
                new PIDController(Constants.AutoConstants.kPXController, 0, 0),
                new PIDController(Constants.AutoConstants.kPYController, 0, 0),
                thetaController,
                s_Swerve::setModuleStates,
                s_Swerve);

        setGyro setGyro = new setGyro(90);


        addCommands(
            setGyro,
            new InstantCommand(() -> RobotContainer.wrist.setSetpoint(0.7)),
            new VerticalFirstHorizontalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 1.07, 1, .89, true), 
            new intakeOn(),
            new WaitUntil(3000),
            new intakeOff(), 
            new HorizontalFirstVerticalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, 0.05, 0.05),
            new StartingPost(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 0.05, 0.05, 0.7, true),
            new InstantCommand(() -> s_Swerve.resetOdometry(exampleTrajectory.getInitialPose())),
            swerveControllerCommand, swerveControllerCommand2
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