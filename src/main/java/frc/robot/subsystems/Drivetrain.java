// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.DrivetrainConstants.*;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drivetrain extends SubsystemBase {
  public double angle;
  
  private final WPI_VictorSPX m_frontLeftDriveMotor;
  private final WPI_VictorSPX m_backLeftDriveMotor;
  private final WPI_VictorSPX m_frontRightDriveMotor;
  private final WPI_VictorSPX m_backRightDriveMotor;
  private final MotorControllerGroup m_leftDriveMotors;
  private final MotorControllerGroup m_rightDriveMotors;
  private final DifferentialDrive m_drive;

  private final CANCoder m_leftDriveEncoder;
  private final CANCoder m_rightDriveEncoder;

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    // TODO: configure encoders to drive motors.
    m_leftDriveEncoder = new CANCoder(k_leftDriveEncoderID);
    m_rightDriveEncoder = new CANCoder(k_rightDriveEncoderID);
    
    m_frontLeftDriveMotor = new WPI_VictorSPX(k_frontLeftDriveID);
    m_backLeftDriveMotor = new WPI_VictorSPX(k_backLeftDriveID);
    m_leftDriveMotors = new MotorControllerGroup(m_frontLeftDriveMotor, m_backLeftDriveMotor);
    
    m_frontRightDriveMotor = new WPI_VictorSPX(k_frontRightDriveID);
    m_backRightDriveMotor = new WPI_VictorSPX(k_backRightDriveID);
    m_rightDriveMotors = new MotorControllerGroup(m_frontRightDriveMotor, m_backRightDriveMotor);

    m_drive = new DifferentialDrive(m_leftDriveMotors, m_rightDriveMotors);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    m_drive.tankDrive(leftSpeed, rightSpeed, false);
  }

  public void stopDrive() {
    m_drive.stopMotor();
  }
}
