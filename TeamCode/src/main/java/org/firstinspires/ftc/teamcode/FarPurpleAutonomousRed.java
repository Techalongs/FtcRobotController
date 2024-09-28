package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous (name = "Far Purple Autonomous (Red)")
public class FarPurpleAutonomousRed extends LinearOpMode {
    final double SPEED = 1;
    final double TURN_SPEED = 0.5;
    final int TIME = 100;

    @Override
    public void runOpMode() {
        Robot jerome = new Robot(hardwareMap, telemetry, this, SPEED);
        jerome.init(0.6);
        jerome.clawHingeDown();
        jerome.closeClaws();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            if (opModeIsActive()) {
                jerome.moveBackward(200);
                jerome.clawHingeUp();
                // sleep(6500);

                int zone;
                if (checkRange(18, 25, jerome.getBackDistance())) { // middle
                    zone = 5;
                    jerome.addData("Zone", zone);
                    jerome.strafeLeft(250);
                    sleep(TIME);
                    jerome.moveBackward(2150);
                    sleep(TIME);
                    jerome.clawHingeDown();
                    jerome.lowerArms(300);
                    sleep(TIME);
                    jerome.openRightClaw(); // Places purple pixel
                    sleep(TIME);
                    jerome.raiseArms(150);
                    jerome.moveBackward(200);
                    jerome.clawHingeUp();
                    jerome.lowerArms(200);
                } else {
                    jerome.strafeRight(400);
                    if (!checkRange(30, 40, jerome.getBackDistance())) { // right
                        zone = 6;
                        jerome.addData("Zone", zone);
                        jerome.strafeLeft(10);
                        sleep(TIME);
                        jerome.moveBackward(1200);
                        sleep(TIME);
                        jerome.changeSpeed(TURN_SPEED);
                        jerome.turnLeft(1050);
                        jerome.changeSpeed(SPEED);
                        sleep(TIME);
                        jerome.moveForward(1000);
                        jerome.moveBackward(400);
                        sleep(TIME);
                        jerome.clawHingeDown();
                        jerome.lowerArms(500);
                        jerome.openRightClaw(); // Places purple pixel
                        sleep(TIME);
                        jerome.raiseArms(200);
                        jerome.clawHingeUp();
                        jerome.moveBackward(200);
                        jerome.lowerArms(200);
                    } else { // left - 25-31
                        zone = 4;
                        jerome.addData("Zone", zone);
                        jerome.strafeRight(25);
                        jerome.moveBackward(1750);
                        sleep(TIME);
                        jerome.clawHingeDown();
                        jerome.lowerArms(300);
                        sleep(TIME);
                        jerome.openRightClaw(); // Places purple pixel
                        sleep(TIME);
                        jerome.raiseArms(150);
                        jerome.clawHingeUp();
                        jerome.moveBackward(200);
                        jerome.lowerArms(200);
                    }
                }

                sleep(TIME);
                jerome.displayData();
                sleep(10000);
                break;
            }
        }
    }

    private boolean checkRange(int num1, int num2, double value) {
        return value > num1 && value < num2; // num1 < value < num2
    }
}