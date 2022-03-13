// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {

  TalonSRX LFTalon, LBTalon, RFTalon, RBTalon;

  Ultrasonic LUltrasonic, RUltrasonic;

  AnalogGyro gyro;

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    LFTalon = new TalonSRX(Constants.Talons.IDs.LF_TALON_ID);
    LBTalon = new TalonSRX(Constants.Talons.IDs.LB_TALON_ID);
    RFTalon = new TalonSRX(Constants.Talons.IDs.RF_TALON_ID);
    RBTalon = new TalonSRX(Constants.Talons.IDs.RB_TALON_ID);

    LFTalon.setInverted(Constants.Talons.Inversions.LF_TALON_INVERT);
    LBTalon.setInverted(Constants.Talons.Inversions.LB_TALON_INVERT);
    RFTalon.setInverted(Constants.Talons.Inversions.RF_TALON_INVERT);
    RBTalon.setInverted(Constants.Talons.Inversions.RB_TALON_INVERT);

    LBTalon.follow(LFTalon);
    RBTalon.follow(RFTalon);

    LUltrasonic = new Ultrasonic(Constants.Sensors.Ultrasonics.DIOs.LEFT_PORT[0], Constants.Sensors.Ultrasonics.DIOs.LEFT_PORT[1]);
    RUltrasonic = new Ultrasonic(Constants.Sensors.Ultrasonics.DIOs.RIGHT_PORT[0], Constants.Sensors.Ultrasonics.DIOs.RIGHT_PORT[1]);

    gyro = new AnalogGyro(Constants.Sensors.Gyros.Port.port);
  }

  public void set(double leftPower, double rightPower) {
    LFTalon.set(ControlMode.PercentOutput, leftPower * Constants.Talons.Speeds.LF_TALON_SPEED);
    RFTalon.set(ControlMode.PercentOutput, rightPower * Constants.Talons.Speeds.RF_TALON_SPEED);
  }

  public void arcadeDrive(double forward, double rotation, double throttle) {
    set((forward + rotation) * throttle, (forward - rotation) * throttle);
  }

  public double getLeftDistance() {
    return LUltrasonic.getRangeInches();
  }

  public double getRightDistance() {
    return RUltrasonic.getRangeInches();
  }

  public double getDistance() {
    return Math.min(getLeftDistance(), getRightDistance());
  }

  public double getRotation() {
    return gyro.getAngle();
  }




  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Ultransonics", getDistance());
    SmartDashboard.putNumber("Gyro", getRotation());
  }
}
