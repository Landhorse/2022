// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

public class DriveRotate extends CommandBase {
  private final Drivetrain m_drivetrain;
  private double speed;
  /** Creates a new DriveRotate. */
  public DriveRotate(Drivetrain drivetrain) {
    m_drivetrain = drivetrain;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    speed = Constants.Sensors.AUTO_DRIVE_SPEED * m_drivetrain.getLeftDistance() > m_drivetrain.getRightDistance()? 1 : -1;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.set(-speed, speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.set(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(m_drivetrain.getRotation() - 180) < Constants.Sensors.Gyros.THRESHOLD;
  }
}
