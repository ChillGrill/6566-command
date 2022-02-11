// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.ShooterConstants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  private final VictorSPX m_intakeWheels;
  private final VictorSPX m_lowerConveyor;
  private final VictorSPX m_upperConveyor;
  private final VictorSPX m_shooterWheels;

  private final Solenoid m_intakeSolenoid;

  private final DigitalInput m_index1LimitSwitch;
  private final DigitalInput m_index2LimitSwitch;

  /** Creates a new Intake. */
  public Shooter() {
    m_intakeWheels = new VictorSPX(k_intakeWheelsID);
    m_lowerConveyor = new VictorSPX(k_lowerConveyorID);
    m_upperConveyor = new VictorSPX(k_upperConveyorID);
    m_shooterWheels = new VictorSPX(k_shooterWheelsID);
    
    m_intakeSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, k_intakeSolenoidID);

    m_index1LimitSwitch = new DigitalInput(k_index1LimitSwitchID);
    m_index2LimitSwitch = new DigitalInput(k_index2LimitSwitchID);
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
    setIntakeWheels(k_intakeSpeed);
  }

  public void outputIntake() {
    setIntakeWheels(-k_intakeSpeed);
  }

  public void inputLowerConveyor() {
    setLowerConveyor(k_conveyorSpeed);
  }

  public void outputLowerConveyor() {
    setLowerConveyor(-k_conveyorSpeed);
  }

  public void inputUpperConveyor() {
    setUpperConveyor(k_conveyorSpeed);
  }

  public void outputUpperConveyor() {
    setUpperConveyor(-k_conveyorSpeed);
  }

  public void outputShooterWheels() {
    setShooterWheels(k_shooterWheelSpeed);
  }

  private void setIntakeWheels(double speed) {
    m_intakeWheels.set(ControlMode.PercentOutput, speed);
  }
  
  private void setLowerConveyor(double speed) {
    m_lowerConveyor.set(ControlMode.PercentOutput, speed);
  }
  
  private void setUpperConveyor(double speed) {
    m_upperConveyor.set(ControlMode.PercentOutput, speed);
  }
  
  private void setShooterWheels(double speed) {
    m_shooterWheels.set(ControlMode.PercentOutput, speed);
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
