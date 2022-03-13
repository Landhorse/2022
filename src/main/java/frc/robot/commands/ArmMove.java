// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Arm;

public class ArmMove extends CommandBase {

  private final Arm m_arm;
  private final DoubleSupplier m_forwardSupplier;
  /** Creates a new ArmMove. */
  public ArmMove(Arm arm, DoubleSupplier forwardSupplier) {
    m_arm = arm;
    m_forwardSupplier = forwardSupplier;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_arm);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double forward = m_forwardSupplier.getAsDouble();
    if ((forward > 0 && m_arm.isInThreshold(Constants.Sensors.Encoders.Distances.ARM_UP_DISTANCE)) ||
        (forward < 0 && m_arm.isInThreshold(Constants.Sensors.Encoders.Distances.ARM_INTAKE_DISTANCE))) {
      m_arm.set(0);
    } else {
      m_arm.set(forward);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_arm.set(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
