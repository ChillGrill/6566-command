// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.components.TernaryMotorController;

import static frc.robot.Constants.ShooterConstants.*;

public class Shooter extends SubsystemBase {
  private final TernaryMotorController m_intakeWheels = new TernaryMotorController(new WPI_VictorSPX(k_intakeWheelsID), k_intakeSpeed);
  private final TernaryMotorController m_lowerFeeder = new TernaryMotorController(new WPI_VictorSPX(k_lowerFeederID), k_feederSpeed);
  private final TernaryMotorController m_upperFeeder = new TernaryMotorController(new WPI_VictorSPX(k_upperFeederID), k_feederSpeed);
  private final TernaryMotorController m_shooterWheels = new TernaryMotorController(new WPI_VictorSPX(k_shooterWheelsID), k_shooterWheelSpeed);

  private final Solenoid m_intakeSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, k_intakeSolenoidID);

  private final DigitalInput m_index1LimitSwitch = new DigitalInput(k_index1LimitSwitchID);
  private final DigitalInput m_index2LimitSwitch = new DigitalInput(k_index2LimitSwitchID);

  /** Creates a new Intake. */
  public Shooter() {
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void raiseIntake() {
    m_intakeSolenoid.set(k_intakeRaiseValue);
  }

  public void lowerIntake() {
    m_intakeSolenoid.set(!k_intakeRaiseValue);
  }

  public void inputIntake() {
    m_intakeWheels.forward();
  }

  public void outputIntake() {
    m_intakeWheels.reverse();
  }

  public void stopIntake() {
    m_intakeWheels.stop();
  }

  public void inputLowerFeeder() {
    m_lowerFeeder.forward();
  }

  public void outputLowerFeeder() {
    m_lowerFeeder.reverse();
  }

  public void stopLowerFeeder() {
    m_lowerFeeder.stop();
  }

  public void inputUpperFeeder() {
    m_upperFeeder.forward();
  }

  public void outputUpperFeeder() {
    m_upperFeeder.reverse();
  }

  public void stopUpperFeeder() {
    m_upperFeeder.stop();
  }

  public void outputShooterWheels() {
    m_shooterWheels.forward();
  }

  public void stopShooterWheels() {
    m_shooterWheels.stop();
  }

  public boolean isBallAtIndex1() {
    return m_index1LimitSwitch.get();
  }

  public boolean isBallAtIndex2() {
    return m_index2LimitSwitch.get();
  }

  public boolean isFullIndex() {
    return isBallAtIndex1() && isBallAtIndex2();
  }

  public int indexCount() {
    return (isBallAtIndex1() ? 1 : 0) + (isBallAtIndex2() ? 1 : 0);
  }
}
