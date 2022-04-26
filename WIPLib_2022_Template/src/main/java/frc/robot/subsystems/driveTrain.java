import frc.robot.commands.motor;

public class driveTrain {
    motor driveLeft1;
    motor driveLeft2;
    motor driveRight1;
    motor driveRight2;
    public void drivestraight(){
        driveLeft1.runSame(0.5,driveLeft2);
        driveLeft1.runOpposite(0.5, driveRight1,driveRight2);

        motor leftDrives[] = {driveLeft1, driveLeft2};
        motor rightDrives[] = {driveRight1, driveRight2};

        motor.runOpposite(0.5, leftDrives, rightDrives);

        driveLeft1.runSame(0.5, rightDrives);
    }
}