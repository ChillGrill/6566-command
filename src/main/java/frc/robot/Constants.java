// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  
  public final class Controls {
    // Drive controls
    public static final int k_driverJoystickChannel = 0;
    public static final int k_forwardDriveAxisChannel = 1;
    public static final int k_turnDriveAxisChannel = 1;
    public static final int k_slowDriveButton = 1;
    public static final int k_toggleCurveButton = 3;
    public static final int k_turnInPlaceButton = 4;
    
    // Operator controls
    public static final int k_operatorJoystickChannel = 1;
    public static final int k_liftButton = 3;
    public static final int k_intakeButton = 1;
    public static final int k_shootLowButton = 5;
    public static final int k_shootHighButton = 6;
    public static final int k_shootAutoButton = 7;
    public static final int k_ejectButton = 2;
  }
  
  public final class Drivetrain {
    // Motors
    public static final int k_frontLeftDriveID = 1;
    public static final int k_backLeftDriveID = 2;
    public static final int k_frontRightDriveID = 3;
    public static final int k_backRightDriveID = 4;
    
    // Sensors
    public static final int k_pigeonID = 0;

    // Values
    public static final double k_lowSpeed = 0.5;
    public static final double k_highSpeed = 1.0;

    // PID gains
    public static final double k_forwardF = 0.0;
    public static final double k_forwardP = 0.0;
    public static final double k_forwardI = 0.0;
    public static final double k_forwardD = -0.0;
    
    public static final double k_turnF = 0.0;
    public static final double k_turnP = 0.0;
    public static final double k_turnI = 0.0;
    public static final double k_turnD = -0.0;
  }

  public final class Intake {
    // Motors
    public static final int k_outerIndexerMotorID = 6;
    public static final int k_innerIndexerMotorID = 7;
    public static final int k_intakeMotorID = 8;

    // Sensors
    public static final int k_innerIndexLimitSwitchID = 0;

    // Values
    public static final double k_indexerSpeed = 0.35;
    public static final double k_intakeSpeed = 0.4;
  }

  public final class Lifter {
    // Pneumatics
    public static final int k_lifterSolenoidID = 0;
  }

  public final class Shooter {
    // Motors
    public static final int k_shooterMotorID = 5;

    // Speeds
    public static final double k_shooterLowSpeed = 7000;
    public static final double k_shooterHighSpeed = 13000;

    // Error range
    public static final double k_shooterAllowedError = 20;
    public static final int k_minimumCyclesAtSpeed = 10;

    // Camera-driven shooting
    public static final double k_baseVelocity = 16000;
    public static final double k_baseArea = 0.05;
    public static final double k_velocityChangePerPercentArea = 1500; // Smaller areas use higher speeds.
    public static final double k_minimumArea = 0.08;
    public static final double k_maximumArea = 0.04;

    // PID gains
    public static final double k_f = 0.465;
    public static final double k_p = 0.15;
    public static final double k_i = 0.000;
    public static final double k_d = 0.0;
    public static final double k_integralZone = 0.0;
  }
}
