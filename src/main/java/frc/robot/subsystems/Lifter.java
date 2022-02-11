// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.LifterConstants.*;

public class Lifter extends SubsystemBase {
  private final Solenoid m_lifterSolenoid;

  /** Creates a new Lifter. */
  public Lifter() {
    m_lifterSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, k_lifterSolenoidID);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void raise() {
    m_lifterSolenoid.set(k_lifterRaiseValue);
  }

  public void lower() {
    m_lifterSolenoid.set(!k_lifterRaiseValue);
  }
}
