// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.AutoCommand;
import frc.robot.commands.EjectIndexerCommand;
import frc.robot.commands.IntakeSequence;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.ShootSequence;
import frc.robot.commands.StopIntakeCommand;
import frc.robot.commands.StopShooterCommand;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Lifter;
import frc.robot.subsystems.Shooter;

import static frc.robot.Constants.Controls.*;
import static frc.robot.Constants.Shooter.k_shooterLowSpeed;
import static frc.robot.Constants.Shooter.k_shooterHighSpeed;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  // Subsystems
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final Lifter m_lifter = new Lifter();
  private final Shooter m_shooter = new Shooter();

  // Joysticks
  private final Joystick m_driverJoystick = new Joystick(k_driverJoystickChannel);
  private final Joystick m_operatorJoystick = new Joystick(k_operatorJoystickChannel);

  // Commands
  private final AutoCommand m_autoCommand = new AutoCommand(m_drivetrain, m_shooter);
  private final IntakeSequence m_intakeCommand = new IntakeSequence(m_shooter);
  private final StopIntakeCommand m_stopIndexerCommand = new StopIntakeCommand(m_shooter);
  private final EjectIndexerCommand m_ejectIndexerCommand = new EjectIndexerCommand(m_shooter);
  private final ShootCommand m_shootLowCommand = new ShootCommand(m_shooter, k_shooterLowSpeed);
  private final ShootCommand m_shootHighCommand = new ShootCommand(m_shooter, k_shooterHighSpeed);
  private final StopShooterCommand m_stopShooterCommand = new StopShooterCommand(m_shooter);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Set the drivetrain's default drive command
    m_drivetrain.setDefaultCommand(
        new RunCommand(
            () -> m_drivetrain.drive(
                m_driverJoystick.getRawAxis(k_forwardDriveAxisChannel),
                m_driverJoystick.getRawAxis(k_turnDriveAxisChannel),
                m_driverJoystick.getRawButton(k_turnInPlaceButton)),
            m_drivetrain));

    m_lifter.setDefaultCommand(
      new RunCommand(m_lifter::lower, m_lifter)
    );

    m_shooter.setDefaultCommand(m_stopIndexerCommand);
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(m_driverJoystick, k_toggleCurveButton)
      .whenPressed(new InstantCommand(m_drivetrain::toggleCurve));

    new JoystickButton(m_driverJoystick, k_slowDriveButton)
      .whenPressed(new InstantCommand(m_drivetrain::setLowSpeed))
      .whenReleased(new InstantCommand(m_drivetrain::setHighSpeed));
    
    // Raise the lifter while the lift button is held and lower it when released.
    new JoystickButton(m_operatorJoystick, k_liftButton)
        .whenPressed(new InstantCommand(m_lifter::raise, m_lifter))
        .whenReleased(new InstantCommand(m_lifter::lower, m_lifter));

    // Begin intake when pressed. Stop intake when released.
    new JoystickButton(m_operatorJoystick, k_intakeButton)
        .whenPressed(m_intakeCommand)
        .whenReleased(m_stopIndexerCommand);

    // Begin the shooting process. Stop when released.
    new JoystickButton(m_operatorJoystick, k_shootLowButton)
        .whenPressed(new ShootSequence(m_shooter, m_shootLowCommand))
        .whenReleased(m_stopShooterCommand);
    new JoystickButton(m_operatorJoystick, k_shootHighButton)
        .whenPressed(new ShootSequence(m_shooter, m_shootHighCommand))
        .whenReleased(m_stopShooterCommand);

    // Eject from the robot when pressed. Stop when released.
    new JoystickButton(m_operatorJoystick, k_ejectButton)
        .whenPressed(m_ejectIndexerCommand)
        .whenReleased(m_stopIndexerCommand);
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
