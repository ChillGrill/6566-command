// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public final class Controls {
        public static final int k_leftDriveJoystickChannel = 0;
        public static final int k_rightDriveJoystickChannel = 1;
    }
    
    public final class DrivetrainConstants {
        public static final int k_frontLeftDriveID = 1;
        public static final int k_backLeftDriveID = 2;
        public static final int k_frontRightDriveID = 3;
        public static final int k_backRightDriveID = 4;

        public static final int k_leftDriveEncoderID = 10;
        public static final int k_rightDriveEncoderID = 11;
        public static final int k_pigeonID = 9;
    }

    public final class LifterConstants {
        public static final int k_lifterSolenoidID = 2;

        public static final boolean k_lifterRaiseValue = true;
    }

    public final class ShooterConstants {
        public static final int k_intakeWheelsID = 5;
        public static final int k_lowerConveyorID = 6;
        public static final int k_upperConveyorID = 7;
        public static final int k_shooterWheelsID = 8;

        public static final int k_intakeSolenoidID = 1;

        public static final int k_index1LimitSwitchID = 1;
        public static final int k_index2LimitSwitchID = 2;

        public static final double k_shooterWheelSpeed = 1.0;
        public static final double k_conveyorSpeed = 1.0;
        public static final double k_intakeSpeed = 1.0;
        public static final boolean k_intakeRaiseValue = true;
    }
}
