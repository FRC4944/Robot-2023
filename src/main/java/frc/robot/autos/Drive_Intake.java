
package frc.robot.autos;


import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.SwerveModule;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Swerve;

public class Drive_Intake extends CommandBase {
  /** Creates a new Drive_Back_Command. */

  private long time;

  private final boolean auto;
  public double xdistance;
  public double xrotation;


  public double kp = Constants.AutoConstants.kPThetaController;
  public double power;

  
  //private static final double MAX_POWER = 1;
  private final Swerve m_swerve;

  public SwerveModule m_swervemodule;
  public Intake intake;

  public Drive_Intake(Swerve swerve, Intake intake, double xDistance, double xRotation, double power, boolean auto) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.m_swerve = swerve;
    this.auto = auto;
    this.intake = intake;
    this.xdistance = xDistance;
    this.xrotation = xRotation;
    this.power = power;


  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (auto) {
      time = System.currentTimeMillis() + 1500;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
        
    if (auto) {
      m_swerve.drive(
        new Translation2d(xdistance, 0).times(power), 
         xrotation * Constants.Swerve.AutoAngleSpeed, 
        true, 
        true
    );
  }

  intake.intake_on(0.9);
  if (intake.intake.getOutputCurrent() > 12){
    intake.intake_on(0.0);
    }
  
  
} 

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    System.out.println("Swere is done driving");
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (auto) {
      return System.currentTimeMillis() > time;
    }else {
      return false;

    }
    }
  }