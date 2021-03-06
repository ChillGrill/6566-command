// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ShootSequence extends SequentialCommandGroup {
  /** Creates a new ShootCommand. */
  public ShootSequence(Shooter shooter, Intake intake, Command shootCommand) {
    final WaitForShooterDeadline waitForShooter = new WaitForShooterDeadline(intake, shootCommand);
    final IndexInnerToShooter innerToShooter = new IndexInnerToShooter(intake);
    final IndexOuterToInner outerToInner = new IndexOuterToInner(intake);
    
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        waitForShooter,
        innerToShooter,
        outerToInner,
        waitForShooter,
        innerToShooter);
  }
}
