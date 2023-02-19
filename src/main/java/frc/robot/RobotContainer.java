package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.simulation.XboxControllerSim;
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
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kBack.value);

    private final JoystickButton dr_ZeroGyro = new JoystickButton(operator, XboxController.Button.kStart.value);
    private final JoystickButton dr_RightTrigger = new JoystickButton(driver, XboxController.Axis.kRightTrigger.value);
    private final JoystickButton dr_LeftTrigger = new JoystickButton(driver, XboxController.Axis.kLeftTrigger.value);
    private final JoystickButton dr_RightBumper = new JoystickButton(driver, XboxController.Button.kRightBumper.value);
    private final JoystickButton dr_LeftBumper = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);
    private final JoystickButton dr_aButton = new JoystickButton(driver, XboxController.Button.kA.value);
    private final JoystickButton dr_bButton = new JoystickButton(driver, XboxController.Button.kB.value);
    private final JoystickButton dr_xButton = new JoystickButton(driver, XboxController.Button.kX.value);
    private final JoystickButton dr_yButton = new JoystickButton(driver, XboxController.Button.kY.value);

    /* Operator Buttons */
    private final JoystickButton op_Back = new JoystickButton(operator, XboxController.Button.kBack.value);
    private final JoystickButton op_Start = new JoystickButton(operator, XboxController.Button.kStart.value);
    private final JoystickButton op_RightTrigger = new JoystickButton(operator, XboxController.Axis.kRightTrigger.value);
    private final JoystickButton op_LeftTrigger = new JoystickButton(operator, XboxController.Axis.kLeftTrigger.value);
    private final JoystickButton op_RightBumper = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
    private final JoystickButton op_LeftBumper = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
    private final JoystickButton op_aButton = new JoystickButton(operator, XboxController.Button.kA.value);
    private final JoystickButton op_bButton = new JoystickButton(operator, XboxController.Button.kB.value);
    private final JoystickButton op_xButton = new JoystickButton(operator, XboxController.Button.kX.value);
    private final JoystickButton op_yButton = new JoystickButton(operator, XboxController.Button.kY.value);

    //Drive practice to see if we want this
    // private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);

    /* Subsystems */
    private final Swerve s_Swerve = new Swerve();
    public VerticalElevator verticalElevator = new VerticalElevator();
    public HorizontalElevator horizontalElevator = new HorizontalElevator();
    public Wrist wrist = new Wrist();
    public Intake intake = new Intake();


    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        final int translate = (int) (((translationAxis<0)?-1:1)*Math.sqrt(Math.abs(translationAxis)));
        final int strafe = (int) (((strafeAxis<0)?-1:1)*Math.sqrt(Math.abs(strafeAxis)));
        final int steer = (int) (((rotationAxis<0)?-1:1)*Math.sqrt(Math.abs(rotationAxis)));
        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -driver.getRawAxis(translationAxis), 
                () -> -driver.getRawAxis(strafeAxis), 
                () -> driver.getRawAxis(rotationAxis),
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
        // dr_Back.onTrue();
        dr_ZeroGyro.onTrue(new InstantCommand(() -> s_Swerve.zeroGyro()));
        dr_aButton.onTrue(new VerticalFirstHorizontalCommand(verticalElevator, horizontalElevator, 0.95, 0.9));
        dr_aButton.onFalse(new HorizontalFirstVerticalCommand(verticalElevator, horizontalElevator, 0.05, 0.05));
        dr_bButton.onTrue(new VerticalFirstHorizontalCommand(verticalElevator, horizontalElevator, 0.55, 0.5));
        dr_bButton.onFalse(new HorizontalFirstVerticalCommand(verticalElevator, horizontalElevator, 0.05, 0.05));
        dr_xButton.onTrue(new HumanPlayerCubeIntake(verticalElevator, horizontalElevator, intake, 0.8, 0.8, 0.7));
        dr_xButton.onFalse(new HumanPlayerCubeIntake(verticalElevator, horizontalElevator, intake, 0.05, 0.05, 0.05));
        dr_yButton.onTrue(new HumanPlayerConeIntake(verticalElevator, horizontalElevator, intake, 0.8, 0.8, -0.8));
        dr_yButton.onFalse(new HumanPlayerConeIntake(verticalElevator, horizontalElevator, intake, 0.05, 0.05, 0.05));    
 
        /* Operator Buttons */
        op_Back.onTrue(new BringElevatorToZero(verticalElevator, horizontalElevator, intake, translationAxis, strafeAxis, rotationAxis));
        op_Start.onTrue(new ZeroAllEncoders(verticalElevator, horizontalElevator, intake, translationAxis, strafeAxis, rotationAxis));
        op_aButton.onTrue(new FoldInIntake(verticalElevator, horizontalElevator, intake, translationAxis, strafeAxis, rotationAxis));
        op_aButton.onFalse(new FoldInIntake(verticalElevator, horizontalElevator, intake, translationAxis, strafeAxis, rotationAxis));
        op_bButton.onTrue(new LineRobotWithAprilTag(verticalElevator, horizontalElevator, intake, translationAxis, strafeAxis, rotationAxis));
        op_bButton.onFalse(new LineRobotWithAprilTag(verticalElevator, horizontalElevator, intake, translationAxis, strafeAxis, rotationAxis));
        op_RightBumper.onTrue(new DropFork(verticalElevator, horizontalElevator, intake, translationAxis, strafeAxis, rotationAxis));
        op_RightBumper.onTrue(new DropFork(verticalElevator, horizontalElevator, intake, translationAxis, strafeAxis, rotationAxis));
        op_LeftBumper.onTrue(new PidOnEngage(verticalElevator, horizontalElevator, intake, translationAxis, strafeAxis, rotationAxis));
        op_LeftBumper.onTrue(new PidOnEngage(verticalElevator, horizontalElevator, intake, translationAxis, strafeAxis, rotationAxis));

    }

    public void teleopPeriodic() {
        System.out.println("RobotContainer teleopPeriodic");

        // Vertical Elevator

        this.verticalElevator.driveTowardsPid();
        
        // Horizontal Elevator

         this.horizontalElevator.driveTowardsPid();

         this.wrist.driveTowardsPid();

// Vertical Elevator PID
    //this.verticalElevator.driveTowardsPid();
// Horizontal Elevator PID
    //this.horizontalElevator.driveTowardsPid();
     // Ground Intake Cube
     if (driver.getRightBumperPressed()){
        intake.intake_on(0.7);
     }
     if (driver.getRightBumperReleased()){
        intake.intake_on(0.0);
     }
     // Ground Intake Cone
     if (driver.getLeftBumperPressed()){
        intake.intake_on(-0.8);
     }
     if (driver.getLeftBumperReleased()){
        intake.intake_on(0.0);
     }
    // Human Player intake Cube
     if (driver.getXButtonPressed()){
        intake.intake_on(0.7);
     }
     if (driver.getXButtonReleased()){
        intake.intake_on(0.0);
     }
     // Human Player intake Cone
     if (driver.getYButtonPressed()){
        intake.intake_on(-0.8);
     }
     if (driver.getYButtonReleased()){
        intake.intake_on(0.0);
     }
     if (driver.getPOV() == 90){
        wrist.setSetpoint(0.8);
        verticalElevator.setSetpoint(-3.95);
        //intake.intake_on(0.7);
     
     if (driver.getPOV() == 270){
        wrist.setSetpoint(0.8);
        verticalElevator.setSetpoint(-3.95);
     }

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return new TestAuto(s_Swerve);
        

    }
}

