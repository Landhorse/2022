// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Shooter;

public class Shoot extends CommandBase {

  private final Shooter m_shooter;
  private final DoubleSupplier m_throttleSupplier;

  /** Creates a new Intake. */
  public Shoot(Shooter shooter, DoubleSupplier throttle) {
    m_shooter = shooter;
    m_throttleSupplier = throttle;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double throttle =  m_throttleSupplier.getAsDouble()/-2 + 0.5;
    m_shooter.setFlyWheels(Constants.Talons.Speeds.SHOOT_TALON_SHOOT_SPEED * throttle);
    m_shooter.setConvey(Constants.Talons.Speeds.CONVEY_TALON_SHOOT_SPEED);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_shooter.setFlyWheels(0);
    m_shooter.setConvey(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
