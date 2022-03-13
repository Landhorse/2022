// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;

public class ArmToShoot extends CommandBase {

  private final Arm m_arm;
  private double speed;
  /** Creates a new ArmToShoot. */
  public ArmToShoot(Arm arm) {
    m_arm = arm;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_arm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    speed = Math.copySign(Constants.Talons.Speeds.ARM_TALON_SPEED, Constants.Sensors.Encoders.Distances.ARM_SHOOT_DISTANCE - m_arm.getDistance());
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_arm.set(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_arm.set(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_arm.isInThreshold(Constants.Sensors.Encoders.Distances.ARM_SHOOT_DISTANCE);
  }
}
