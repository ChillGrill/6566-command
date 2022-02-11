// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.AutoCommand;
import frc.robot.commands.EjectCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.Shooter;

import java.util.function.DoubleSupplier;

import static frc.robot.Constants.Controls.*;
import static frc.robot.Constants.k_pigeonID;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final Lifter m_lifter = new Lifter();
  private final Shooter m_shooter = new Shooter();
  private final Pigeon2 m_pigeon = new Pigeon2(k_pigeonID);
  private final DoubleSupplier m_heading = m_pigeon::getYaw;

  private final Joystick m_leftDriveJoystick = new Joystick(k_leftDriveJoystickChannel);
  private final Joystick m_rightDriveJoystick = new Joystick(k_rightDriveJoystickChannel);
  private final Joystick m_operatorJoystick = new Joystick(k_operatorJoystickChannel);
  
  private final AutoCommand m_autoCommand = new AutoCommand(m_drivetrain, m_shooter);
  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Set the drivetrain's default drive command
    m_drivetrain.setDefaultCommand(
      new RunCommand(
        () ->
          m_drivetrain.tankDrive(
            m_leftDriveJoystick.getRawAxis(k_leftDriveAxisChannel),
            m_rightDriveJoystick.getRawAxis(k_rightDriveAxisChannel)
          ),
        m_drivetrain
    ));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Raise the lifter while the lift button is held and lower it when released.
    new ConditionalCommand(
      new InstantCommand(m_lifter::raise, m_lifter),
      new InstantCommand(m_lifter::lower, m_lifter),
      new JoystickButton(m_operatorJoystick, k_liftButton));

    new JoystickButton(m_operatorJoystick, k_intakeButton)
      .whenPressed(new IntakeCommand(m_shooter));

    new JoystickButton(m_operatorJoystick, k_ejectButton)
      .whenPressed(new EjectCommand(m_shooter));
    
    new JoystickButton(m_operatorJoystick, k_shootButton)
      .whenPressed(new ShootCommand(m_shooter));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
