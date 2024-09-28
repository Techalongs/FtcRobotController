package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous (name = "Old Far Autonomous (Red)")
@Disabled
public class OldFarAutonomousRed extends LinearOpMode {
  final double SPEED = 0.7;
  final int TIME = 100;

  @Override
  public void runOpMode() {
    Robot jerome = new Robot(hardwareMap, telemetry, this, SPEED);
    jerome.init(0.5);
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
        if (checkRange(23, 40, jerome.getBackDistance())) { // middle
          zone = 2;
          jerome.addData("Zone", zone);
          jerome.strafeLeft(200);
          sleep(TIME);
          jerome.turnRight(2100);
          sleep(TIME);
          jerome.moveForward(2000);
          sleep(TIME);
          jerome.moveBackward(500);
          sleep(TIME);
          jerome.clawHingeDown();
          jerome.lowerArms(300);
          sleep(TIME);
          jerome.openRightClaw(); // Place purple pixel
          sleep(TIME);
          jerome.raiseArms(150);
          jerome.moveBackward(250);
          jerome.clawHingeUp();
          jerome.lowerArms(200);
          sleep(TIME);
          jerome.strafeRight(1300);
          sleep(TIME);
          jerome.moveForward(1300);
          sleep(TIME);
          jerome.turnRight(1000);
          sleep(TIME);
          jerome.moveForward(3200);
          sleep(TIME);
          jerome.strafeRight(1200);
          sleep(TIME);
          jerome.moveForward(75);
        } else {
          jerome.strafeRight(400);
          if (checkRange(15, 25, jerome.getBackDistance())) { // right
            zone = 3;
            jerome.addData("Zone", zone);
            jerome.strafeLeft(10);
            sleep(TIME);
            jerome.turnRight(2100);
            sleep(TIME);
            jerome.moveForward(1200);
            sleep(TIME);
            jerome.turnLeft(1050);
            sleep(TIME);
            jerome.lowerArms(500);
            sleep(TIME);
            jerome.moveBackward(2350);
            jerome.moveForward(460);
            sleep(TIME);
            jerome.strafeRight(220);
            sleep(TIME);
            jerome.moveForward(100);
            jerome.raiseArms(300);
            jerome.clawHingeDown();
            jerome.extendExtension(2.3);
            jerome.lowerArms(350);
            jerome.openRightClaw(); // Places purple pixel
            sleep(TIME);
            jerome.raiseArms(200);
            jerome.retractExtension(2.5);
            jerome.clawHingeUp();
            jerome.lowerArms(200);
            sleep(TIME);
            jerome.strafeLeft(220);
            jerome.moveBackward(10);
            sleep(TIME);
            jerome.turnRight(1050);
            sleep(TIME);
            jerome.moveForward(1300);
            sleep(TIME);
            jerome.turnRight(1050);
            jerome.moveForward(3170);
            sleep(TIME);
            jerome.strafeRight(1750);
            jerome.moveForward(120);
          } else { // left
            zone = 1;
            jerome.addData("Zone", zone);
            jerome.turnRight(2100);
            sleep(TIME);
            jerome.moveForward(1800);
            sleep(TIME);
            jerome.moveBackward(750);
            sleep(TIME);
            jerome.turnLeft(1050);
            jerome.clawHingeDown();
            sleep(TIME);
            jerome.moveBackward(150);
            sleep(TIME);
            jerome.strafeRight(520);
            sleep(TIME);
            jerome.moveForward(75);
            sleep(TIME);
            jerome.lowerArms(300);
            sleep(TIME);
            jerome.openRightClaw(); // Places purple pixel
            sleep(TIME);
            jerome.raiseArms(200);
            jerome.clawHingeUp();
            jerome.moveBackward(250);
            jerome.lowerArms(200);
            sleep(TIME);
            jerome.strafeLeft(300);
            sleep(TIME);
            jerome.moveBackward(1140);
            sleep(TIME);
            jerome.turnRight(1050);
            sleep(TIME);
            jerome.moveForward(1300);
            sleep(TIME);
            jerome.turnRight(1050);
            sleep(TIME);
            jerome.moveForward(3200);
            sleep(TIME);
            jerome.strafeRight(1420);
            sleep(TIME);
            jerome.moveForward(60);
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
    sleep(TIME);
    robot.clawHingeDown();
    robot.retractExtension(3.25);
    robot.lowerArms(700);
    robot.clawHingeUp();
  }
}
