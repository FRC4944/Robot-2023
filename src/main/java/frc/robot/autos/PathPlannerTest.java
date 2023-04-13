package frc.robot.autos;

import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.commands.HorizontalFirstVerticalCommand;
import frc.robot.commands.VerticalFirstHorizontalCommand;
import frc.robot.subsystems.HorizontalElevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Swerve;

import java.util.HashMap;

import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.FollowPathWithEvents;
import com.pathplanner.lib.commands.PPSwerveControllerCommand;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;


public class PathPlannerTest extends SequentialCommandGroup {
    public PathPlannerTest(Swerve s_Swerve){
        HashMap<String, Command> eventMap = new HashMap<>();
        Field2d m_field = new Field2d();
        // SequentialCommandGroup Score = new SequentialCommandGroup(new InstantCommand(() -> 
        // new VerticalFirstHorizontalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 1.15, -1, 1, true)));
        // Score.addCommands(new WaitCommand(.5));


        

        // wait command placeholder
        //eventMap.put("IntakeOn", new intakeOn(0.9));
        //eventMap.put("IntakeOff", new intakeOff());
        //eventMap.put("Zero", new HorizontalFirstVerticalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 0.05, -0.03, .94, true));
        eventMap.put("Wait", new WaitUntil(500));
        // eventMap.put("Wait", new PrintCommand(s_Swerve.gyro.getYaw()));
        // eventMap.put("Down", new HorizontalFirstVerticalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 0.05, -0.05, 1, true));
        // eventMap.put("grab cube", grabCube);
        // eventMap.put("drop cube", new InstantCommand(() -> armSubsystem.openClamper()));

        PathPlannerTrajectory path = PathPlanner.loadPath("Score Pick up cube Engage", new PathConstraints(1.5, 1), false);
        // var thetaController =
        //     new ProfiledPIDController(
        //         Constants.AutoConstants.kPThetaController, 0, 0, Constants.AutoConstants.kThetaControllerConstraints);
        // thetaController.enableContinuousInput(-Math.PI, Math.PI);
        m_field.getObject("Score Pick up cube Engage").setTrajectory(path);

        PPSwerveControllerCommand swerveControllerCommand = 
                 new PPSwerveControllerCommand(
                    path, 
                    s_Swerve::getPose, // Pose supplier
                    Constants.Swerve.swerveKinematics, // SwerveDriveKinematics
                    new PIDController(Constants.AutoConstants.kPXController, 0.05, 0), // X controller. Tune these values for your robot. Leaving them 0 will only use feedforwards.
                    new PIDController(Constants.AutoConstants.kPYController, 0.05, 0), // Y controller (usually the same values as X controller)
                    new PIDController(Constants.AutoConstants.kPThetaController, 0.05, 0), // Rotation controller. Tune these values for your robot. Leaving them 0 will only use feedforwards.
                    s_Swerve::setModuleStates, // Module states consumer
                    true, // Should the path be automatically mirrored depending on alliance color. Optional, defaults to true
                    s_Swerve // Requires this drive subsystem
                );
            FollowPathWithEvents command = new FollowPathWithEvents(swerveControllerCommand,path.getMarkers(),eventMap);
            addCommands(
                //new HorizontalFirstVerticalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 0.05, -0.03, .94, true),
                // new VerticalFirstHorizontalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 1.15, -1, 1, true), 
                // new intakeOn(0.9),
                // new WaitUntil(2500),
                // new intakeOff(), 
                // new HorizontalFirstVerticalCommand(RobotContainer.verticalElevator, RobotContainer.horizontalElevator, RobotContainer.wrist, 0.05, -0.05, 1, true),
                new InstantCommand(() -> s_Swerve.resetOdometry(path.getInitialPose())),
                command
            );
    
    }

}