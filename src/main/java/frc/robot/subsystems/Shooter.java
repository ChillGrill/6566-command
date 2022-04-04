// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.Shooter.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  // Motors
  private final WPI_TalonFX m_shooterMotor = new WPI_TalonFX(k_shooterMotorID);

  // Limelight
  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  private NetworkTableEntry tA = table.getEntry("tA");

  // Instance variables
  private double m_targetShooterVelocity = 0;
  private ShooterMode m_shooterMode = ShooterMode.Stop;

  /** Creates a new Intake. */
  public Shooter() {
    m_shooterMotor.setNeutralMode(NeutralMode.Coast);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    switch (m_shooterMode) {
      case Auto:
        // TODO: Auto shooting speed based on camera.
        break;
      case Manual:
        m_shooterMotor.set(ControlMode.Velocity, m_targetShooterVelocity);
        break;
      case Stop:
        m_shooterMotor.stopMotor();
        break;
    }
  }

  public void setTargetShooterVelocity(double targetVelocity) {
    m_shooterMode = ShooterMode.Manual;
    m_targetShooterVelocity = targetVelocity;
  }

  public void shootLowGoal() {
    setTargetShooterVelocity(k_shooterLowSpeed);
  }

  public void shootHighGoal() {
    setTargetShooterVelocity(k_shooterHighSpeed);
  }

  public void autoShooterSpeed() {
    m_shooterMode = ShooterMode.Auto;
  }

  public void stopShooter() {
    m_shooterMode = ShooterMode.Stop;
  }

  public boolean isShooterAtSpeed() {
    return Math.abs(m_shooterMotor.getClosedLoopError()) < k_shooterAllowedError;
  }

  private enum ShooterMode {
    Stop,
    Manual,
    Auto
  }
}
