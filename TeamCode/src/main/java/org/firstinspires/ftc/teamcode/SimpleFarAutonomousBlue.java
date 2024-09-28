package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous (name = "Simple Far Autonomous (Blue)")
@Disabled
public class SimpleFarAutonomousBlue extends LinearOpMode {
    final double SPEED = 0.5;
    final int TIME = 300;

    @Override
    public void runOpMode() {
        Robot jerome = new Robot(hardwareMap, telemetry, this, SPEED);
        jerome.init(0.45);
        jerome.clawHingeDown();
        jerome.closeClaws();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            if (opModeIsActive()) {
                jerome.moveBackward(200);
                jerome.clawHingeUp();

                int zone;
                // Places yellow pixel
                if (checkRange(30, 40, jerome.getBackDistance())) { // middle
                    zone = 2;
                    jerome.addData("Zone", zone);
                    jerome.turnRight(2100);
                    sleep(TIME);
                    jerome.moveForward(2000);
                    sleep(TIME);
                    jerome.moveBackward(580);
                    sleep(TIME);
                    jerome.clawHingeDown();
                    jerome.lowerArms(300);
                    sleep(TIME);
                    jerome.openRightClaw(); // Places purple pixel
                    sleep(TIME);
                    jerome.raiseArms(150);
                    jerome.moveBackward(275);
                    jerome.clawHingeUp();
                    jerome.lowerArms(200);
                    sleep(TIME);
                    jerome.strafeLeft(1530);
                    sleep(TIME);
                    jerome.turnLeft(1050);
                    sleep(TIME);
                    jerome.moveForward(3210);
                } else {
                    jerome.strafeLeft(700);
                    // Places yellow pixel
                    if (checkRange(35, 40, jerome.getBackDistance())) { // right
                        zone = 3;
                        jerome.addData("Zone", zone);
                        jerome.turnRight(2100);
                        sleep(TIME);
                        jerome.moveForward(1800);
                        sleep(TIME);
                        jerome.moveBackward(750);
                        sleep(TIME);
                        jerome.turnRight(1050);
                        jerome.clawHingeDown();
                        sleep(TIME);
                        jerome.moveBackward(600);
                        sleep(TIME);
                        jerome.strafeLeft(520);
                        sleep(TIME);
                        jerome.lowerArms(300);
                        sleep(TIME);
                        jerome.openRightClaw(); // Places purple pixel
                        sleep(TIME);
                        jerome.raiseArms(150);
                        jerome.clawHingeUp();
                        jerome.moveBackward(340);
                        jerome.lowerArms(200);
                        sleep(TIME);
                        jerome.strafeRight(300);
                        sleep(TIME);
                        jerome.moveBackward(1110);
                        jerome.turnRight(2100);
                        sleep(TIME);
                        jerome.moveForward(3210);
                        sleep(TIME);
                        jerome.strafeRight(100);
                        sleep(TIME);
                    } else { // left - 25-31
                        zone = 1;
                        jerome.addData("Zone", zone);
                        jerome.turnRight(2100);
                        sleep(TIME);
                        jerome.moveForward(1250);
                        sleep(TIME);
                        jerome.turnRight(1050);
                        sleep(TIME);
                        jerome.lowerArms(500);
                        sleep(TIME);
                        jerome.moveBackward(2950); // 2090
                        sleep(TIME);
                        jerome.moveForward(860);
                        sleep(TIME);
                        jerome.strafeLeft(220);
                        sleep(TIME);
                        jerome.raiseArms(300);
                        jerome.clawHingeDown();
                        jerome.extendExtension(3.3);
                        jerome.lowerArms(350);
                        sleep(TIME);
                        jerome.openRightClaw(); // Places purple pixel
                        jerome.raiseArms(200);
                        jerome.retractExtension(3.3);
                        jerome.clawHingeUp();
                        jerome.lowerArms(200);
                        sleep(TIME);
                        jerome.strafeRight(220);
                        sleep(TIME);
                        jerome.turnRight(2100);
                        sleep(TIME);
                        jerome.moveForward(3210);
                        sleep(TIME);
                        jerome.strafeLeft(100);
                    }
                }

                sleep(TIME);

                placeYellowPixel(jerome);
                jerome.displayData();

                sleep(10000);
                break;
            }
        }
    }

    private boolean checkRange(int num1, int num2, double value) {
        return value > num1 && value < num2; // num1 < value < num2
    }

    private void placeYellowPixel(@NonNull Robot robot) {
        robot.raiseArms(1500);
        robot.extendExtension(3.25);
        robot.openLeftClaw(); // Places yellow pixel
        sleep(100);
        robot.clawHingeDown();
        robot.retractExtension(3.25);
        robot.lowerArms(700);
        robot.clawHingeUp();
    }
}
