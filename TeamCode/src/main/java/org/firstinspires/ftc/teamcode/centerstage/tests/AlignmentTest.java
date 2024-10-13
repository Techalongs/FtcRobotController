package org.firstinspires.ftc.teamcode.centerstage.tests;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.centerstage.Robot;

@Autonomous (name = "Alignment Test")
@Disabled
public class AlignmentTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        Robot jerome = new Robot(hardwareMap, telemetry, this);
        jerome.init(0.5);
        waitForStart();

        if (opModeIsActive()) {
            while ((!checkRange(8, 10, jerome.getFrontLeftDistance()) // TODO: Shrink range
                    && !checkRange(8, 10, jerome.getFrontRightDistance())) && opModeIsActive()) {
                if (jerome.getFrontLeftDistance() > 11) jerome.moveForward(50);
                else jerome.moveBackward(50);
            }

            while (!checkRange(8, 10, jerome.getFrontLeftDistance()) && opModeIsActive()) {
                if (jerome.getFrontLeftDistance() > jerome.getFrontRightDistance()) break;
                else jerome.turnLeft(50);
            }

            while (!checkRange(8, 10, jerome.getFrontRightDistance()) && opModeIsActive()) {
                if (jerome.getFrontRightDistance() > jerome.getFrontLeftDistance()) break;
                else jerome.turnRight(50);
            }

            placeYellowPixel(jerome);
            jerome.displayData();
        }
    }

    private boolean checkRange(int num1, int num2, double value) {
        return value > num1 && value < num2; // num1 < value < num2
    }

    private void placeYellowPixel(@NonNull Robot robot) {
        robot.raiseArms(1500);
        robot.extendExtension(3.25);
        robot.openLeftClaw(); // Places yellow pixel
        sleep(200);
        robot.clawHingeDown();
        robot.retractExtension(3.25);
        robot.lowerArms(700);
        robot.clawHingeUp();
    }
}
