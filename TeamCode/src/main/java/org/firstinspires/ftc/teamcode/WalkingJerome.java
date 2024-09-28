package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@TeleOp(name = "Walking Jerome")
public class WalkingJerome extends LinearOpMode {
  private int stayPosition = 0;
  @Override
  public void runOpMode() {
    final double ARM_LIMITER = 0.8;
    final double POSITION_ANGLE = 0.6; // Increase = more angled (less steep)

    Robot jerome = new Robot(hardwareMap, telemetry, this, 0.9);
    jerome.init(POSITION_ANGLE);
    
    telemetry.addData("Status", "Initialized");
    telemetry.update();
    waitForStart();
    
    while (opModeIsActive()) {
      if (opModeIsActive()) {
        // Drivetrain controls
        double y = 0;
        /*
        // Descoring Prevention
        double y = 0;
        if (jerome.getAprilTagProcessor().getDetections().size() > 0) {
          AprilTagDetection detection = jerome.getAprilTagProcessor().getDetections().get(0);
          y = detection.ftcPose.y;
          jerome.addData("y", y);
        }
         */

        if (gamepad1.right_bumper) jerome.drive(1, gamepad1, y); // Standard - 0.84
        else if (gamepad1.left_bumper) jerome.drive(0.7, gamepad1, y); // Standard - 0.4
        else jerome.drive(1, gamepad1, y);
        // Set normal speed to 0.5 at beginning of next season - for practice

        // Drone controls
        if (gamepad1.dpad_up && gamepad1.left_trigger > 0.5) jerome.launchDrone(POSITION_ANGLE);

        // Arm controls
        if (gamepad2.left_stick_y == 0 && jerome.getLeftArmPosition() != stayPosition && !(jerome.getRightArmPosition() <= 700)) {
          if (stayPosition == 0) stayPosition = jerome.getLeftArmPosition();
          jerome.stayArms(stayPosition);
        } else if (gamepad2.left_stick_y == 0) {
          jerome.stopArms();
        } else {
          stayPosition = 0;

          // Right arm is naturally faster - slight tilt
          double armPower = -gamepad2.left_stick_y * ARM_LIMITER;
          if (armPower > 0) jerome.raiseArms(armPower);
          else jerome.lowerArms(-armPower);
        }

        // jerome.raiseArms(-gamepad2.left_stick_y * ARM_LIMITER);

        // Extension controls
        if (gamepad2.dpad_up && !jerome.isBackExtensionLimitPressed()) jerome.extendExtension();
        else if (gamepad2.dpad_down && !jerome.isFrontExtensionLimitPressed()) jerome.retractExtension();
        else jerome.stopExtension();

        // Claw Hinge controls
        if (gamepad2.left_bumper) jerome.clawHingeUp();
        else if (gamepad2.right_bumper) jerome.clawHingeDown();

        // Claw controls
        if (gamepad2.a) jerome.closeClaws();
        else if (gamepad2.x) jerome.openLeftClaw();
        else if (gamepad2.y) jerome.openRightClaw();
        else if (gamepad2.b) {
          jerome.openClaws();
          sleep(300);
          jerome.clawHingeDown();
        }

        // Driver automation - Pick up pixels
        if (gamepad2.right_trigger > 0.3) {
          if (jerome.getLeftArmPosition() < 200) jerome.raiseArms(200 - jerome.getLeftArmPosition());
          jerome.moveForward(0);
          jerome.retractExtension(1000);
          jerome.openClaws();
          jerome.clawHingeDown();
          sleep(200);
          jerome.lowerArms(800);
          jerome.closeClaws();
          sleep(800);
          jerome.raiseArms(300);
          jerome.init(POSITION_ANGLE);
        }

        jerome.displayData();
      }
    }
  }
}
