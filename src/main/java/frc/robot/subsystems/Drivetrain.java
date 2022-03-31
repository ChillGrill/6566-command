// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.Drivetrain.*;

public class Drivetrain extends SubsystemBase {
  // Motors
  private final WPI_TalonFX m_frontLeftDriveMotor = new WPI_TalonFX(k_frontLeftDriveID);
  private final WPI_TalonFX m_backLeftDriveMotor = new WPI_TalonFX(k_backLeftDriveID);
  private final WPI_TalonFX m_frontRightDriveMotor = new WPI_TalonFX(k_frontRightDriveID);
  private final WPI_TalonFX m_backRightDriveMotor = new WPI_TalonFX(k_backRightDriveID);

  private final MotorControllerGroup m_leftDriveMotors = new MotorControllerGroup(
    m_frontLeftDriveMotor, m_backLeftDriveMotor);
  private final MotorControllerGroup m_rightDriveMotors = new MotorControllerGroup(
    m_frontRightDriveMotor, m_backRightDriveMotor);
  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftDriveMotors, m_rightDriveMotors);

  // Sensors
  // private final Pigeon2 m_pigeon = new Pigeon2(k_pigeonID);

  // Instance variables
  private boolean m_isCurveDrive = false;
  private double m_speedMod = k_highSpeed;

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    m_frontLeftDriveMotor.configFactoryDefault();
    m_backLeftDriveMotor.configFactoryDefault();
    m_frontRightDriveMotor.configFactoryDefault();
    m_backRightDriveMotor.configFactoryDefault();

    // TODO: configure drive PID
    // TalonFXConfiguration a = new TalonFXConfiguration();
    // a.slot0.kF = k_forwardF;
    // a.slot0.kP = k_forwardP;
    // a.slot0.kI = k_forwardI;
    // a.slot0.kD = k_forwardD;
    
    // a.slot1.kF = k_turnF;
    // a.slot1.kP = k_turnP;
    // a.slot1.kI = k_turnI;
    // a.slot1.kD = k_turnD;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void drive(double forwardSpeed, double turnSpeed) {
    drive(forwardSpeed, turnSpeed, false);
  }

  public void drive(double forwardSpeed, double turnSpeed, boolean allowTurnInPlace) {
    forwardSpeed *= m_speedMod;
    if (m_isCurveDrive) {
      curveDrive(forwardSpeed, turnSpeed, allowTurnInPlace);
    } else {
      arcadeDrive(forwardSpeed, turnSpeed);
    }
  }

  public void arcadeDrive(double forwardSpeed, double turnSpeed) {
    m_drive.arcadeDrive(forwardSpeed, turnSpeed);
  }

  public void curveDrive(double forwardSpeed, double turnSpeed, boolean allowTurnInPlace) {
    m_drive.curvatureDrive(forwardSpeed, turnSpeed, allowTurnInPlace);
  }

  public void stopDrive() {
    m_drive.stopMotor();
  }

  public void toggleCurve() {
    m_isCurveDrive = !m_isCurveDrive;
  }

  public void setLowSpeed() {
    m_speedMod = k_lowSpeed;
  }
  
  public void setHighSpeed() {
    m_speedMod = k_highSpeed;
  }

  public void driveDistance(double distance) {
    // TODO: setup motion magic move
    
    m_backLeftDriveMotor.follow(m_frontLeftDriveMotor, FollowerType.PercentOutput);
    m_frontRightDriveMotor.follow(m_frontLeftDriveMotor, FollowerType.AuxOutput1);
    m_backRightDriveMotor.follow(m_frontLeftDriveMotor, FollowerType.AuxOutput1);

    // m_frontLeftDriveMotor.set(ControlMode.MotionMagic, distance);
  }
}
