// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FollowerType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXInvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.Drivetrain.*;

public class Drivetrain extends SubsystemBase {
  // Motors
  private final WPI_TalonFX m_frontLeftMotor = new WPI_TalonFX(k_frontLeftDriveID);
  private final WPI_TalonFX m_backLeftMotor = new WPI_TalonFX(k_backLeftDriveID);
  private final WPI_TalonFX m_frontRightMotor = new WPI_TalonFX(k_frontRightDriveID);
  private final WPI_TalonFX m_backRightMotor = new WPI_TalonFX(k_backRightDriveID);

  private final DifferentialDrive m_drive = new DifferentialDrive(m_frontLeftMotor, m_frontRightMotor);

  // Sensors
  private final Pigeon2 m_pigeon = new Pigeon2(k_pigeonID);

  // Instance variables
  private boolean m_isCurveDrive = false;
  private double m_speedMod = k_highSpeed;

  /** Creates a new Drivetrain. */
  public Drivetrain() {
    m_frontLeftMotor.configFactoryDefault();
    m_backLeftMotor.configFactoryDefault();
    m_frontRightMotor.configFactoryDefault();
    m_backRightMotor.configFactoryDefault();

    m_frontLeftMotor.setNeutralMode(NeutralMode.Brake);
    m_backLeftMotor.setNeutralMode(NeutralMode.Brake);
    m_frontRightMotor.setNeutralMode(NeutralMode.Brake);
    m_backRightMotor.setNeutralMode(NeutralMode.Brake);
    
    m_backLeftMotor.follow(m_frontLeftMotor, FollowerType.PercentOutput);
    m_backRightMotor.follow(m_frontRightMotor, FollowerType.PercentOutput);

    m_frontLeftMotor.setInverted(TalonFXInvertType.CounterClockwise);
    m_backLeftMotor.setInverted(TalonFXInvertType.FollowMaster);
    m_frontRightMotor.setInverted(TalonFXInvertType.Clockwise);
    m_backRightMotor.setInverted(TalonFXInvertType.FollowMaster);

    TalonFXConfiguration ultimateMasterConfig = new TalonFXConfiguration();
    ultimateMasterConfig.slot0.kF = k_forwardF;
    ultimateMasterConfig.slot0.kP = k_forwardP;
    ultimateMasterConfig.slot0.kI = k_forwardI;
    ultimateMasterConfig.slot0.kD = k_forwardD;
    ultimateMasterConfig.slot0.integralZone = k_forwardIntegralZone;
    
    ultimateMasterConfig.slot1.kF = k_turnF;
    ultimateMasterConfig.slot1.kP = k_turnP;
    ultimateMasterConfig.slot1.kI = k_turnI;
    ultimateMasterConfig.slot1.kD = k_turnD;
    ultimateMasterConfig.slot1.integralZone = k_turnIntegralZone;

    m_frontLeftMotor.configAllSettings(ultimateMasterConfig);
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

  private void arcadeDrive(double forwardSpeed, double turnSpeed) {
    m_drive.arcadeDrive(forwardSpeed, turnSpeed);
  }

  private void curveDrive(double forwardSpeed, double turnSpeed, boolean allowTurnInPlace) {
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
    
    m_backLeftMotor.follow(m_frontLeftMotor, FollowerType.PercentOutput);
    m_frontRightMotor.follow(m_frontLeftMotor, FollowerType.AuxOutput1);
    m_backRightMotor.follow(m_frontLeftMotor, FollowerType.AuxOutput1);

    m_frontLeftMotor.set(ControlMode.MotionMagic, distance);
  }
}
