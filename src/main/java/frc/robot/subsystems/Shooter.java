// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.Shooter.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
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
  private boolean m_doRunShooter = false;
  private int m_cyclesAtSpeed = 0;

  /** Creates a new Intake. */
  public Shooter() {
    TalonFXConfiguration shooterConfig = new TalonFXConfiguration();
    shooterConfig.slot0.kF = k_f;
    shooterConfig.slot0.kP = k_p;
    shooterConfig.slot0.kI = k_i;
    shooterConfig.slot0.kD = k_d;
    shooterConfig.slot0.integralZone = k_integralZone;
    m_shooterMotor.configAllSettings(shooterConfig);

    m_shooterMotor.setNeutralMode(NeutralMode.Coast);
    m_shooterMotor.setInverted(TalonFXInvertType.CounterClockwise);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    updateShooter();
  }

  public void setTargetShooterVelocity(double targetVelocity) {
    m_targetShooterVelocity = targetVelocity;
    m_cyclesAtSpeed = 0;
    m_doRunShooter = true;
  }

  public void shootLowGoal() {
    setTargetShooterVelocity(k_shooterLowSpeed);
  }

  public void shootHighGoal() {
    setTargetShooterVelocity(k_shooterHighSpeed);
  }

  public void shootAuto() {
    double area = tA.getDouble(0.0);
    if (area < k_minimumArea || k_maximumArea < area) {
      stopShooter();
      return;
    }
    double targetVelocity = k_baseVelocity + ((k_baseArea - area) * k_velocityChangePerPercentArea);
    setTargetShooterVelocity(targetVelocity);
  }

  public void stopShooter() {
    m_doRunShooter = false;
    m_cyclesAtSpeed = 0;
  }

  private void updateShooter() {
    if (m_doRunShooter) {
      m_shooterMotor.set(ControlMode.Velocity, m_targetShooterVelocity);
      checkShooterSpeed();
    } else {
      m_shooterMotor.stopMotor();
    }
  }

  private void checkShooterSpeed() {
    if (Math.abs(m_shooterMotor.getClosedLoopError()) < k_shooterAllowedError) {
      m_cyclesAtSpeed++;
    } else {
      m_cyclesAtSpeed = 0;
    }
  }

  public boolean isAtSpeed() {
    return m_cyclesAtSpeed >= k_minimumCyclesAtSpeed;
  }
}
