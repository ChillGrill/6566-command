// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.components;

import edu.wpi.first.wpilibj.motorcontrol.MotorController;

/** Add your docs here. */
public class TernaryMotorController {
  private final MotorController m_motorController;
  private final double m_speed;

  public TernaryMotorController(MotorController motorController, double speed) {
    m_motorController = motorController;
    m_speed = speed;
  }

  public void forward() {
    m_motorController.set(m_speed);
  }
  
  public void reverse() {
    m_motorController.set(-m_speed);
  }
  
  public void stop() {
    m_motorController.set(0);
  }
}
