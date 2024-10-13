package org.firstinspires.ftc.teamcode.centerstage;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous (name = "Close Autonomous (Blue)")
public class CloseAutonomousBlue extends LinearOpMode {
  final double SPEED = 1;
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
        if (checkRange(20, 37, jerome.getBackDistance())) { // middle
          zone = 2;
          jerome.addData("Zone", zone);
          jerome.strafeLeft(200);
          sleep(TIME);
          jerome.turnRight(2100);
          sleep(TIME);
          jerome.moveForward(1800);
          jerome.moveBackward(400);
          sleep(TIME);
          jerome.clawHingeDown();
          jerome.lowerArms(300);
          sleep(TIME);
          jerome.openRightClaw(); // Places purple pixel
          sleep(TIME);
          jerome.raiseArms(150);
          jerome.moveBackward(500);
          jerome.clawHingeUp();
          jerome.lowerArms(200);
          sleep(TIME);
          jerome.turnLeft(1050);
          sleep(TIME);
          jerome.moveForward(1000);
          sleep(TIME);
          jerome.strafeRight(500);
        } else {
          jerome.strafeRight(500);
          if (checkRange(30, 40, jerome.getBackDistance())) { // left
            zone = 1;
            jerome.addData("Zone", zone);
            jerome.strafeRight(350);
            sleep(TIME);
            jerome.turnRight(2100);
            jerome.moveForward(2000);
            jerome.moveBackward(900);
            sleep(TIME);
            jerome.clawHingeDown();
            jerome.lowerArms(300);
            sleep(TIME);
            jerome.openRightClaw(); // Places purple pixel
            sleep(TIME);
            jerome.raiseArms(150);
            jerome.clawHingeUp();
            jerome.moveBackward(300);
            jerome.lowerArms(200);
            sleep(TIME);
            jerome.turnLeft(1050);
          } else { // right
            zone = 3;
            jerome.addData("Zone", zone);
            jerome.moveBackward(1200);
            sleep(TIME);
            jerome.turnLeft(1050);
            sleep(TIME);
            jerome.moveForward(1000);
            jerome.moveBackward(300);
            sleep(TIME);
            jerome.clawHingeDown();
            jerome.lowerArms(500);
            jerome.openRightClaw(); // Places purple pixel
            sleep(TIME);
            jerome.raiseArms(200);
            jerome.clawHingeUp();
            jerome.moveBackward(700);
            jerome.lowerArms(200);
            sleep(TIME);
            jerome.turnRight(2100);
            sleep(TIME);
            jerome.strafeRight(300);
          }
        }

        sleep(1000);

        alignToAprilTag(jerome, zone);
        jerome.moveForward(850);
        adjust(jerome);
        placeYellowPixel(jerome);
        jerome.moveBackward(100);

        if (zone == 2) jerome.strafeLeft(1400);
        else if (zone == 1) jerome.strafeLeft(1300);
        else jerome.strafeLeft(1600);

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
    while ((!checkRange(7, 11, robot.getFrontLeftDistance())
            && !checkRange(9, 11, robot.getFrontRightDistance())) && opModeIsActive()) {
      if (robot.getFrontLeftDistance() > 11) robot.moveForward(50);
      else robot.moveBackward(10);
    }

    while (!checkRange(7, 11, robot.getFrontLeftDistance()) && opModeIsActive()) {
      if (robot.getFrontLeftDistance() > robot.getFrontRightDistance()) break;
      else robot.turnLeft(15);
    }

    while (!checkRange(8, 12, robot.getFrontRightDistance()) && opModeIsActive()) {
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
