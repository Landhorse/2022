// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class DriveForward extends CommandBase {

  private final Drivetrain m_drivetrain;
  /** Creates a new DriveForward. */
  public DriveForward(Drivetrain drivetrain) {
    m_drivetrain = drivetrain;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (m_drivetrain.getDistance() < Constants.Sensors.Ultrasonics.Distances.SLOW_DISTANCE) {
      double speed = m_drivetrain.getDistance() * Constants.Sensors.Ultrasonics.DISTANCE_MULT;
      m_drivetrain.set(speed, speed);
    } else {
      m_drivetrain.set(Constants.Sensors.AUTO_DRIVE_SPEED, Constants.Sensors.AUTO_DRIVE_SPEED);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.set(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_drivetrain.getDistance() < Constants.Sensors.Ultrasonics.Distances.MIN_DISTANCE;
  }
}
