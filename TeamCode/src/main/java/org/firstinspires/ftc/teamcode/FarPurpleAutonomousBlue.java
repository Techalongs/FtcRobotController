package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous (name = "Far Purple Autonomous (Blue)")
public class FarPurpleAutonomousBlue extends LinearOpMode {
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
                if (checkRange(23, 37, jerome.getBackDistance())) { // middle
                    zone = 2;
                    jerome.addData("Zone", zone);
                    jerome.strafeRight(200);
                    sleep(TIME);
                    jerome.moveBackward(2100);
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
                    jerome.strafeLeft(400);
                    if (checkRange(30, 40, jerome.getBackDistance())) { // right
                        zone = 3;
                        jerome.addData("Zone", zone);
                        jerome.strafeLeft(250);
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
                    } else { // left - 25-31
                        zone = 1;
                        jerome.addData("Zone", zone);
                        jerome.strafeRight(10);
                        sleep(TIME);
                        jerome.moveBackward(1200);
                        sleep(TIME);
                        jerome.changeSpeed(TURN_SPEED);
                        jerome.turnRight(1050);
                        jerome.changeSpeed(SPEED);
                        sleep(TIME);
                        jerome.moveForward(800);
                        jerome.moveBackward(150);
                        sleep(TIME);
                        jerome.clawHingeDown();
                        jerome.lowerArms(500);
                        jerome.openRightClaw(); // Places purple pixel
                        sleep(TIME);
                        jerome.raiseArms(200);
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
