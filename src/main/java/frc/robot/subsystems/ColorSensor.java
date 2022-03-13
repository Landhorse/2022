// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class ColorSensor extends SubsystemBase {

  private final ColorSensorV3 m_colorSensor;
  private final ColorMatch m_colorMatch;

  /** Creates a new Color_Sensor. */
  public ColorSensor() {
    m_colorSensor = new ColorSensorV3(I2C.Port.kOnboard);
    m_colorMatch = new ColorMatch();

    m_colorMatch.addColorMatch(Constants.Colors.BLUE_TARGET);
    m_colorMatch.addColorMatch(Constants.Colors.RED_TARGET);
  }

  public ColorMatchResult getColorMatch() {
    return m_colorMatch.matchClosestColor(m_colorSensor.getColor());
  }



  public boolean isAllianceColor() {
    ColorMatchResult CMR = getColorMatch();
    return (Constants.Colors.IS_BLUE == (CMR.color == Constants.Colors.BLUE_TARGET) && CMR.confidence > Constants.Colors.CONF_THRESHOLD);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // SmartDashboard.putBoolean("color", c == Constants.Colors.kBlueTarget);
    SmartDashboard.putBoolean("Color", !(isAllianceColor() ^ Constants.Colors.IS_BLUE));

    //       true false
    // true   t     f
    // false  f     t
  }
}
