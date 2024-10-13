package org.firstinspires.ftc.teamcode.centerstage;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous (name = "Far Autonomous (Blue)")
public class FarAutonomousBlue extends LinearOpMode {
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
                sleep(3000);

                int zone;
                if (checkRange(23, 37, jerome.getBackDistance())) { // middle
                    zone = 2;
                    jerome.addData("Zone", zone);
                    sleep(1000);
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
                    jerome.moveBackward(400);
                    jerome.clawHingeUp();
                    jerome.lowerArms(200);
                    sleep(TIME);
                    jerome.changeSpeed(TURN_SPEED);
                    jerome.turnRight(1050);
                    jerome.changeSpeed(SPEED);
                    sleep(TIME);
                    jerome.moveForward(4100);
                    sleep(TIME);
                    jerome.strafeLeft(1500);
                } else {
                    jerome.strafeLeft(400);
                    if (checkRange(20, 35, jerome.getBackDistance())) { // right
                        zone = 3;
                        jerome.addData("Zone", zone);
                        sleep(1000);
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
                        jerome.moveBackward(850);
                        jerome.lowerArms(200);
                        sleep(TIME);
                        jerome.changeSpeed(TURN_SPEED);
                        jerome.turnRight(1050);
                        jerome.changeSpeed(SPEED);
                        sleep(TIME);
                        jerome.moveForward(4600);
                        sleep(TIME);
                        jerome.strafeLeft(1150);
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
                        jerome.moveBackward(400);
                        jerome.lowerArms(200);
                        sleep(TIME);
                        jerome.strafeRight(1500);
                        sleep(TIME);
                        jerome.moveForward(4300);
                        sleep(TIME);
                        jerome.strafeLeft(2000);
                    }
                }

                sleep(1000);

                alignToAprilTag(jerome, zone);
                adjust(jerome);
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

    private void alignToAprilTag(Robot robot, int zone) {
        int size = robot.getAprilTagProcessor().getDetections().size();
        if (size > 0) {
            AprilTagDetection detection = null;
            for (AprilTagDetection d : robot.getAprilTagProcessor().getDetections()) {
                if (d.id == zone) {
                    detection = d;
                }
            }

            if (detection != null) {
                String name = detection.metadata.name;
                int id = detection.metadata.id;
                double x = detection.ftcPose.x;

                robot.addData("Detection name", name);
                robot.addData("Id", id);
                robot.addData("X", x);

                // 60 ticks = 1 inch (as per Austin)
                // 2.5 is the offset between the claw and camera
                robot.strafeRight((int) (x - 3) * 60);
            }

        }
    }

    private void adjust(Robot robot) {
        while ((!checkRange(8, 10, robot.getFrontLeftDistance())
                && !checkRange(8, 10, robot.getFrontRightDistance())) && opModeIsActive()) {
            if (robot.getFrontLeftDistance() > 11) robot.moveForward(50);
            else robot.moveBackward(10);
        }

        while (!checkRange(8, 10, robot.getFrontLeftDistance()) && opModeIsActive()) {
            if (robot.getFrontLeftDistance() > robot.getFrontRightDistance()) break;
            else robot.turnLeft(15);
        }

        while (!checkRange(8, 10, robot.getFrontRightDistance()) && opModeIsActive()) {
            if (robot.getFrontRightDistance() > robot.getFrontLeftDistance()) break;
            else robot.turnRight(15);
        }

        robot.displayData();
    }

    private void placeYellowPixel(@NonNull Robot robot) {
        robot.raiseArms(1300);
        robot.extendExtension(2.9);
        robot.openLeftClaw(); // Places yellow pixel
        sleep(TIME);
        robot.clawHingeDown();
        robot.retractExtension(3.1);
        robot.lowerArms(700);
        robot.clawHingeUp();
    }
}
