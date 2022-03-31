// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class StopShooterCommand extends InstantCommand {
  private final Shooter m_shooter;
  
  public StopShooterCommand(Shooter shooter) {
    m_shooter = shooter;

    // Use addRequirements() here to declare subsystem dependencies.
    // Doesn't need to interrupt the shooter.
    addRequirements(m_shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_shooter.stopIntake();
    m_shooter.stopOuterIndexer();
    m_shooter.stopInnerIndexer();
    m_shooter.stopShooter();
  }
}
