package frc.robot.commands;


import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Swerve;

public class Engage_Auto extends CommandBase {
    private Swerve swerve;
    private double pitch;

    public Engage_Auto(Swerve drivetrainSubsystem) {
        swerve = drivetrainSubsystem;

        addRequirements(swerve);
    }

    @Override
    public void execute() {

        
        swerve.drive(
        new Translation2d(pitch, 0).times(Constants.Swerve.EngageSpeed), 
         0 * Constants.Swerve.AutoAngleSpeed, 
        true, 
        true
    );
        
    }
  

    @Override
    public void end(boolean interrupted) {
      swerve.drive(
        new Translation2d(0, 0).times(Constants.Swerve.AutoMaxspeed), 
         0 * Constants.Swerve.AutoAngleSpeed, 
        true, 
        true
    );
}
}
