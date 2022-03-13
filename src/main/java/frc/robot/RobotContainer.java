// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_drivetrain = new Drivetrain();

  private final Shooter m_shooter = new Shooter();

  private final Arm m_arm = new Arm();

  private final Elevator m_elevator = new Elevator();

  private final ColorSensor m_colorSensor = new ColorSensor();

  private final Joystick m_driverStick = new Joystick(Constants.Controls.JoystickIDs.DRIVER_ID);

  private final Joystick m_operateStick = new Joystick(Constants.Controls.JoystickIDs.OP_ID);

  private JoystickButton intakeButton, shootButton, elevatorUpButton, elevatorDownButton, armIntakeButton, armShootButton, armUpButton, armMoveButton;
  

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    m_drivetrain.setDefaultCommand(new Drive(m_drivetrain, 
                                             () -> m_driverStick.getRawAxis(Constants.Controls.AxisIDs.FORWARD_AXIS_ID),
                                             () -> m_driverStick.getRawAxis(Constants.Controls.AxisIDs.ROTATION_AXIS_ID),
                                             () -> m_driverStick.getRawAxis(Constants.Controls.AxisIDs.THROTTLE_AXIS_ID)));
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
    intakeButton = new JoystickButton(m_operateStick, Constants.Controls.ButtonIDs.INTAKE_BUTTON_ID);
    intakeButton.whenHeld(new Intake(m_shooter, 
                                      () -> m_operateStick.getRawAxis(Constants.Controls.AxisIDs.THROTTLE_AXIS_ID)));
    
    shootButton = new JoystickButton(m_operateStick, Constants.Controls.ButtonIDs.SHOOT_BUTTON_ID);
    shootButton.whenHeld(new Shoot(m_shooter,
                                    () -> m_operateStick.getRawAxis(Constants.Controls.AxisIDs.THROTTLE_AXIS_ID)));

    armIntakeButton = new JoystickButton(m_operateStick, Constants.Controls.ButtonIDs.ARM_INTAKE_BUTTON_ID);
    armIntakeButton.toggleWhenPressed(new ArmToIntake(m_arm));

    armShootButton = new JoystickButton(m_operateStick, Constants.Controls.ButtonIDs.ARM_SHOOT_BUTTON_ID);
    armShootButton.whenPressed(new ArmToShoot(m_arm));

    armUpButton = new JoystickButton(m_operateStick, Constants.Controls.ButtonIDs.ARM_UP_BUTTON_ID);
    armUpButton.toggleWhenPressed(new ArmToUp(m_arm));

    armMoveButton = new JoystickButton(m_operateStick, Constants.Controls.ButtonIDs.ARM_MOVE_BUTTON_ID);
    armMoveButton.toggleWhenPressed(new ArmMove(m_arm,
                                        () -> m_operateStick.getRawAxis(Constants.Controls.AxisIDs.FORWARD_AXIS_ID)));

    elevatorUpButton = new JoystickButton(m_operateStick, Constants.Controls.ButtonIDs.ELV_UP_BUTTON_ID);
    elevatorUpButton.whenHeld(new ElevateUp(m_elevator));

    elevatorDownButton = new JoystickButton(m_operateStick, Constants.Controls.ButtonIDs.ELV_DO_BUTTON_ID);
    elevatorDownButton.whenHeld(new ElevateDown(m_elevator));
    

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    /**
     * Lower arm - done
     * 
     * Drive forward
     * Intake ball- donish
     * 
     * Raise Arm -done
     * Rotate 180
     * 
     * Drive forward
     * 
     * Shoot -done
     */
    SequentialCommandGroup auto = new SequentialCommandGroup(
      new ArmToIntake(m_arm),
      new ParallelRaceGroup(
        new DriveForward(m_drivetrain),
        new AutoIntake(m_shooter, m_colorSensor)
      ),
      new DriveRotate(m_drivetrain),
      new ParallelCommandGroup(
        new ArmToShoot(m_arm),
        new DriveForward(m_drivetrain)
      ),
      new AutoShoot(m_shooter)
    );
    return auto;
  }
}
