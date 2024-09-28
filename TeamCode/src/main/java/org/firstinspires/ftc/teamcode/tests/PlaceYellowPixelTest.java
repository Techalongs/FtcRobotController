package org.firstinspires.ftc.teamcode.tests;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robot;

@Autonomous (name = "Place Yellow Pixel Test")
@Disabled
public class PlaceYellowPixelTest extends LinearOpMode {
    final double SPEED = 0.7;
    final int TIME = 100;

    @Override
    public void runOpMode() {
        Robot jerome = new Robot(hardwareMap, telemetry, this, SPEED);
        jerome.init(0.45);
        jerome.clawHingeUp();
        jerome.closeClaws();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            if (opModeIsActive()) {
                placeYellowPixel(jerome);
                jerome.displayData();

                sleep(10000);
                break;
            }
        }
    }

    private void placeYellowPixel(@NonNull Robot robot) {
        robot.raiseArms(1500);
        robot.extendExtension(3.25);
        robot.openLeftClaw(); // Places yellow pixel
        sleep(TIME);
        robot.clawHingeDown();
        robot.retractExtension(3.25);
        robot.lowerArms(700);
        robot.clawHingeUp();
    }
}
