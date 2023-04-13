package frc.robot;


import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.PathPlannerTrajectory.PathPlannerState;

import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.autos.*;
import frc.robot.autos.Test;
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

    /* Driver Buttons */
    private final JoystickButton zeroGyro = new JoystickButton(driver, XboxController.Button.kBack.value);
    private final JoystickButton robotCentric = new JoystickButton(driver, XboxController.Button.kLeftBumper.value);

    /*Operator Buttons */
    private final JoystickButton RB = new JoystickButton(operator, XboxController.Button.kRightBumper.value);
    private final JoystickButton LB = new JoystickButton(operator, XboxController.Button.kLeftBumper.value);
    //private final opBButton = new JoystickButton(operator, XboxController.Button.kB.value);

    /* Subsystems */
    public static Swerve s_Swerve = new Swerve();
    public static VerticalElevator verticalElevator = new VerticalElevator();
    public static HorizontalElevator horizontalElevator = new HorizontalElevator();
    public static Wrist wrist = new Wrist();
    public static Intake intake = new Intake();
    public static CANDle candle = new CANDle();
    public static DigitalInput engage = new DigitalInput(0);
    public static Partner_Lift Engage = new Partner_Lift();

    private static double verticalelevatorsp;
    private static double horizontalelevatorsp;
    private static double wristsp;

    private static double level;
    private static double gp;

  // A chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        gp = 2;
        level = 1;
        final int translate = (int) (((translationAxis<0)?-1:1)*Math.sqrt(Math.abs(translationAxis)));
        final int strafe = (int) (((strafeAxis<0)?-1:1)*Math.sqrt(Math.abs(strafeAxis)));        
        

        s_Swerve.setDefaultCommand(
            new TeleopSwerve(
                s_Swerve, 
                () -> -(driver.getRawAxis(translate) * getDriveMultiplier()), 
                () -> -driver.getRawAxis(strafe) * getDriveMultiplier(), 
                () -> -driver.getRawAxis(rotationAxis) * getDriveMultiplier(), 
                () -> robotCentric.getAsBoolean()
            )
        );

        // Configure the button bindings
        configureButtonBindings();
        // Bring up autonomous mode selector
        autonomousOptions();
    }

    public double getDriveMultiplier() {
        return driver.getRightBumper() ? 0.33 : 1;
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

        /* Operator Buttons */
        //Robot aligns with apriltags while operator presses A and reflective tape while operator presses B
        //Command aprilTagLineup = new VisionLineup(s_Swerve, candle, 1);
        //opAButton.whileTrue(aprilTagLineup);
        //Command reflectiveTapeLineup = new VisionLineup(s_Swerve, candle, 2);
        //opBButton.whileTrue(reflectiveTapeLineup);
        RB.whileTrue(new VerticalFirstHorizontalCommand(verticalElevator, horizontalElevator, wrist, -0.05, -0.05, 1.218, false));
        RB.whileFalse(new VerticalFirstHorizontalCommand(verticalElevator, horizontalElevator, wrist, -0.05, -0.05, 1.218, false));

        LB.whileTrue(new VerticalFirstHorizontalCommand(verticalElevator, horizontalElevator, wrist, 0.45, -0.05, 1.358, false));
        LB.whileFalse(new VerticalFirstHorizontalCommand(verticalElevator, horizontalElevator, wrist, 0.45, -0.05, 1.358, false));
    }

    public void teleopPeriodic() {
        //Fix Gyro from Autos
        s_Swerve.setGyroOffset(0.0);
        //Linear filter for intake amp measurement
        LinearFilter filter = LinearFilter.singlePoleIIR(0.1, 0.02);

        // Has the pid running the whole time 
        this.verticalElevator.driveTowardsPid();
        this.horizontalElevator.driveTowardsPid();
        this.wrist.driveTowardsPid();

         // Intake 
        if (driver.getYButtonPressed()){
            intake.intake_on(.6);
        }

        if (driver.getYButtonReleased()){    
            intake.intake_on(0.0);
        }
        
        // Outtake
        if (driver.getXButtonPressed()){
            intake.intake_on(-1);
        }
        // Stops outtake motor
        if (driver.getXButtonReleased()){
            intake.intake_on(0.0);
        }

        // Moves the wrist using the pid 
        // if (driver.getRightBumperPressed()){
        //     wrist.setSetpoint(0.6);
        // }
        // if (driver.getLeftBumperPressed()){
        //     wrist.setSetpoint(1.1);
        // }
        
        // Sets the Elevators to zero and lift wrist for cubes 
        if (driver.getBButtonPressed()){
            wrist.setSetpoint(0.95);
            verticalElevator.setSetpoint(-0.05);
            horizontalElevator.setSetpoint(-.33);
            intake.intake_on(0.9);
        }
        // Sets the wrist back to default
        if (driver.getBButtonReleased()){
            wrist.setSetpoint(1.3);
            verticalElevator.setSetpoint(-0.05);
            horizontalElevator.setSetpoint(-0.02);
            intake.intake_on(0);
        }

        // Sets the wrist to the ground to pick up cones
        if (driver.getAButtonPressed()){
            wrist.setSetpoint(0.42);
            verticalElevator.setSetpoint(-0.05);
            horizontalElevator.setSetpoint(-0.33);
            intake.intake_on(-.9);
        }
        // Sets the wrist back to default
        if (driver.getAButtonReleased()){
            wrist.setSetpoint(1.3);
            verticalElevator.setSetpoint(-0.05);
            horizontalElevator.setSetpoint(-0.02);
            intake.intake_on(0);
        }

        /* Operator Controller buttons subsystems. */
        
        //Operator game piece choice: Cube
        if (operator.getXButtonPressed()){
            gp = 1;
        }
        // Operator game piece choice: Cone
        if (operator.getYButtonPressed()){    
            gp = 2;
        }

        if(gp == 2){
            //Controller rumble and white candle when intake amps spike on cones. Linear filter used to reduce noise
            if (intake.intake.getMotorOutputVoltage() > 13){
                driver.setRumble(RumbleType.kBothRumble, 1);
                candle.candleOn(255, 255, 255);
            } else {
                driver.setRumble(RumbleType.kBothRumble, 0);
                candle.candleOn(240, 240, 0);
            }
        }

        if(gp == 1){
            //Controller rumble and white candle when intake amps spike on cubes. Linear filter is used to reduce noise
            if (intake.intake.getMotorOutputVoltage() > 20){
                driver.setRumble(RumbleType.kBothRumble, 1);
                candle.candleOn(255, 255, 255);
            } else {
                driver.setRumble(RumbleType.kBothRumble, 0);
                candle.candleOn(102, 0, 102);
            }
        }

        //Operator vertical level choice: High
        if (operator.getPOV() == 0){
            level = 1;
        }
        if (level == 1){
            if (gp == 1){
                verticalelevatorsp = 1.07;
                horizontalelevatorsp = -1;
                wristsp = 1.2;
                System.out.print("working high cube");
                System.out.print(verticalelevatorsp);
                System.out.print(horizontalelevatorsp);
                System.out.print(wristsp);
                
            }
            if (gp == 2){
                verticalelevatorsp = 1.13;
                horizontalelevatorsp = -0.98;
                wristsp = 0.9;
                System.out.print("working high cone");
                System.out.print(verticalelevatorsp);
                System.out.print(horizontalelevatorsp);
                System.out.print(wristsp);

                
// high
// 1.17  v
// -.98  h
// .747  w

// mid
//1.17
//-0.54
//0.48


                // verticalelevatorsp = 1.10;
                // horizontalelevatorsp = -1;
                // wristsp = 0.95;
            }
        }

        //Operator vertical level choice: Middle
        if (operator.getPOV() == 90 || operator.getPOV() == 270){
            level = 2;
        }
        if (level == 2){
            if (gp == 1){
                verticalelevatorsp = .6;
                horizontalelevatorsp = -.6;
                wristsp = 1.2;
                
            }   
            if (gp == 2){
                verticalelevatorsp = 1.06;
                horizontalelevatorsp = -.55;
                wristsp = 0.66;
            }
        }

        //Operator vertical level choice: Low
        if (operator.getPOV() == 180){
            level = 3;
        }
        if (level == 3){
            if (gp == 1){
                verticalelevatorsp = 0.05;
                horizontalelevatorsp = -0.05;
                wristsp = 1;
            }

            if (gp == 2){
                verticalelevatorsp = .5;
                horizontalelevatorsp = 0;
                wristsp = .2;
            }
        }

        Command scoreCommand = new VerticalFirstHorizontalCommand(verticalElevator, horizontalElevator, wrist, verticalelevatorsp, horizontalelevatorsp, wristsp, false);

        if (driver.getPOV() == 90){
            scoreCommand.execute();
        }

        if (driver.getPOV() == 270){
            horizontalElevator.setSetpoint(-0.05);
            if(horizontalElevator.pid.atSetpoint()){
            verticalElevator.setSetpoint(-0.06);
            wrist.setSetpoint(1.1);

            // -0.06 v
            //-0.02  h
            //0.8    w
            }
            // if(verticalElevator.pid.atSetpoint()){
            // wrist.setSetpoint(1.1);
            // }
            }

            if (operator.getRightBumperPressed()){
                intake.intake_on(-0.9);
            }
            if (operator.getRightBumperReleased()){
                intake.intake_on(0.0);
            }

            if (operator.getLeftBumperPressed()){
                intake.intake_on(0.9);
            }
            if (operator.getLeftBumperReleased()){
                intake.intake_on(0.0);
            }

            if (operator.getBackButtonPressed()){
                verticalElevator.vZero();
            }

        
        // //Limit switch to turn off forky 
        // if (!engage.get()){
        //     Engage.Partner_Lift_On(0);
        // }

        // // Engage/forky 
        // if (operator.getRightBumperPressed()){
        //     Engage.Partner_Lift_On(.25);
        // }
        // if (operator.getRightBumperReleased()){
        //     Engage.Partner_Lift_On(0.0);
        // }
        // if (operator.getLeftBumperPressed()){
        //     Engage.Partner_Lift_On(-.25);
        // }
        // if (operator.getLeftBumperReleased()){
        //     Engage.Partner_Lift_On(0.0);
        // }
        // //Operator D-Pad UP
        // if (operator.getPOV() == 0){
        //     wrist.setSetpoint(1.5);
        // }
        // //Operator D-Pad Down
        // if (operator.getPOV() == 180){
        //     wrist.setSetpoint(0.5);
        // }

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
    // Get the selected Auto in smartDashboard

    //return m_chooser.getSelected();
    
    return new Auto1(s_Swerve);
    //Uncomment this ^ if auto selector is not working
    }
    private void autonomousOptions() {
       m_chooser.setDefaultOption("engage", new Auto2(s_Swerve));
       m_chooser.addOption("score and intake", new Auto5(s_Swerve));
     //   m_chooser.addOption("Auto 3", new Auto3(s_Swerve));
     //   m_chooser.addOption("Auto 4", new Auto4(s_Swerve));
     //   m_chooser.addOption("Auto 5", new Auto5(s_Swerve));
     //   m_chooser.addOption("Auto 6", new Auto6(s_Swerve));
       SmartDashboard.putData("Auto mode", m_chooser);
    }
}


// high
// 1.17  v
// -.98  h
// .747  w

// low
//1.17
//-0.54
//0.48