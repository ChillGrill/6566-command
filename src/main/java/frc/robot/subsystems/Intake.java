// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.Intake.*;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  // Motors
  private final WPI_VictorSPX m_intakeMotor = new WPI_VictorSPX(k_intakeMotorID);
  private final WPI_VictorSPX m_outerIndexerMotor = new WPI_VictorSPX(k_outerIndexerMotorID);
  private final WPI_VictorSPX m_innerIndexerMotor = new WPI_VictorSPX(k_innerIndexerMotorID);

  // Sensors
  private final DigitalInput m_innerIndexLimitSwitch = new DigitalInput(k_innerIndexLimitSwitchID);

  /** Creates a new Intake. */
  public Intake() {
    m_outerIndexerMotor.setNeutralMode(NeutralMode.Brake);
    m_innerIndexerMotor.setNeutralMode(NeutralMode.Brake);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
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

  public boolean isBallAtInnerIndex() {
    return m_innerIndexLimitSwitch.get();
  }
}
