package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.autos.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    /* Controllers */
    public final XboxController driver = new XboxController(0);
    public final XboxController operator = new XboxController(1);

    /* Drive Controls */
    private final double translationAxis = XboxController.Axis.kLeftY.value;
    private final int strafeAxis = XboxController.Axis.kLeftX.value;
    private final int rotationAxis = XboxController.Axis.kRightX.value;
    //private final int = getPOV(0);

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kBack.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton aButton = new JoystickButton(driver, XboxController.Button.kA.value);
    private final JoystickButton bButton = new JoystickButton(driver, XboxController.Button.kB.value);
    private final JoystickButton opAButton = new JoystickButton(operator, XboxController.Button.kA.value);
    /* Subsystems */
    public final Swerve s_Swerve = new Swerve();
    public static VerticalElevator verticalElevator = new VerticalElevator();
    public static HorizontalElevator horizontalElevator = new HorizontalElevator();
    public static Wrist wrist = new Wrist();
    public static Intake intake = new Intake();
    public static CANDle candle = new CANDle();
    public static DigitalInput engage = new DigitalInput(0);
    public static Partner_Lift Engage = new Partner_Lift();


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        final int translate = (int) (((translationAxis<0)?-1:1)*Math.sqrt(Math.abs(translationAxis)));
        final int strafe = (int) (((strafeAxis<0)?-1:1)*Math.sqrt(Math.abs(strafeAxis)));
        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translate), 
                () -> -driver.getRawAxis(strafe), 
                () -> -driver.getRawAxis(rotationAxis), 
                () -> robotCentric.getAsBoolean()
            )
        );
        
        

        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        /* Driver Buttons */
        zeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));                
        aButton.onTrue(new VerticalFirstHorizontalCommand(verticalElevator, horizontalElevator, wrist, 1.07, 1, .8, false));
        aButton.onFalse(new HorizontalFirstVerticalCommand(verticalElevator, horizontalElevator, 0.05, 0.05));
        bButton.onTrue(new VerticalFirstHorizontalCommand(verticalElevator, horizontalElevator, wrist, 0.8, .5, .6, false));
        bButton.onFalse(new HorizontalFirstVerticalCommand(verticalElevator, horizontalElevator, 0.05, 0.05));
        Command aprilTagLineup = new AprilTagLineup(s_Swerve);
        opAButton.whileTrue(aprilTagLineup);
    }


    public void teleopPeriodic() {

        //Fix Gyro from Autos
        s_Swerve.setGyroOffset(0.0);

        // Vertical Elevator

        this.verticalElevator.driveTowardsPid();
        
        // Horizontal Elevator

         this.horizontalElevator.driveTowardsPid();

         this.wrist.driveTowardsPid();

        if (driver.getYButtonPressed()){
            intake.intake_on(.6);
        }
        if (driver.getYButtonReleased()){    
            intake.intake_on(0.0);
        }

        if (driver.getXButtonPressed()){
            intake.intake_on(-1);
        }
        if (driver.getXButtonReleased()){
            intake.intake_on(0.0);
        }

        if (driver.getRightBumperPressed()){
            wrist.setSetpoint(0.7);
        }
        if (driver.getLeftBumperPressed()){
            wrist.setSetpoint(1.1);
        }

        if (driver.getPOV() == 90){
            wrist.setSetpoint(0.8);
            verticalElevator.setSetpoint(-0.05);
            horizontalElevator.setSetpoint(.09);
        }
        if (driver.getPOV() == 270){
            wrist.setSetpoint(0.5);
            verticalElevator.setSetpoint(-0.07);
            horizontalElevator.setSetpoint(.1);
            if (wrist.pid.atSetpoint()){
                wrist.wrist_On(0.0);
            }
        }




        if (operator.getXButtonPressed()){
            candle.candleOn(242, 233, 61);
        }
        if (operator.getXButtonReleased()){
            candle.rainbowAnimation(0.3, 0.5, 60);
        }
        if (operator.getYButtonPressed()){
            candle.candleOn(20,59,87);;
        }

        if (!engage.get()){
            Engage.Partner_Lift_On(0);
        }
        if (operator.getYButtonReleased()){
            candle.rainbowAnimation(0.3, 0.5, 60);
        }
        if (operator.getRightBumperPressed()){
            Engage.Partner_Lift_On(.2);
        }
        if (operator.getRightBumperReleased()){
            Engage.Partner_Lift_On(0.0);
        }
        if (operator.getLeftBumperPressed()){
            Engage.Partner_Lift_On(-.2);
        }
        if (operator.getLeftBumperReleased()){
            Engage.Partner_Lift_On(0.0);
        }
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new exampleAuto(s_Swerve);
        

    }
}
