// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.Shooter.*;

public class Shooter extends SubsystemBase {
  // Motors
  private final WPI_VictorSPX m_intakeMotor = new WPI_VictorSPX(k_intakeMotorID);
  private final WPI_VictorSPX m_outerIndexerMotor = new WPI_VictorSPX(k_outerIndexerMotorID);
  private final WPI_VictorSPX m_innerIndexerMotor = new WPI_VictorSPX(k_innerIndexerMotorID);
  private final WPI_TalonFX m_shooterMotor = new WPI_TalonFX(k_shooterMotorID);

  // Sensors
  private final DigitalInput m_outerIndexLimitSwitch = new DigitalInput(k_outerIndexLimitSwitchID);
  private final DigitalInput m_innerIndexLimitSwitch = new DigitalInput(k_innerIndexLimitSwitchID);

  // Limelight
  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  private NetworkTableEntry tA = table.getEntry("tA");

  // Instance variables
  private double m_targetShooterVelocity = 0;
  private ShooterMode m_shooterMode = ShooterMode.Stop;

  /** Creates a new Intake. */
  public Shooter() {
    m_shooterMotor.setNeutralMode(NeutralMode.Coast);
    m_outerIndexerMotor.setNeutralMode(NeutralMode.Brake);
    m_innerIndexerMotor.setNeutralMode(NeutralMode.Brake);
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

  public void inputIntake() {
    m_intakeMotor.set(k_intakeSpeed);
  }

  public void outputIntake() {
    m_intakeMotor.set(-k_intakeSpeed);
  }

  public void stopIntake() {
    m_intakeMotor.stopMotor();
  }

  public void inputOuterIndexer() {
    m_outerIndexerMotor.set(k_indexerSpeed);
  }

  public void outputOuterIndexer() {
    m_outerIndexerMotor.set(-k_indexerSpeed);
  }

  public void stopOuterIndexer() {
    m_outerIndexerMotor.stopMotor();
  }

  public void inputInnerIndexer() {
    m_innerIndexerMotor.set(k_indexerSpeed);
  }

  public void outputInnerIndexer() {
    m_innerIndexerMotor.set(-k_indexerSpeed);
  }

  public void stopInnerIndexer() {
    m_innerIndexerMotor.stopMotor();
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

  public boolean isBallAtOuterIndex() {
    return m_outerIndexLimitSwitch.get();
  }

  public boolean isBallAtInnerIndex() {
    return m_innerIndexLimitSwitch.get();
  }

  public boolean isFullIndex() {
    return isBallAtOuterIndex() && isBallAtInnerIndex();
  }

  public int indexCount() {
    return (isBallAtOuterIndex() ? 1 : 0) + (isBallAtInnerIndex() ? 1 : 0);
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
