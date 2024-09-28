package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "Sensors Test")
public class SensorsTest extends LinearOpMode {
  
  @Override
  public void runOpMode() {
    Robot jerome = new Robot(hardwareMap, telemetry, this);
    jerome.init(0.5);
    waitForStart();
    
    while (opModeIsActive()) {
      if (opModeIsActive()) {
        telemetry.addData("Front Left Distance", jerome.getFrontLeftDistance());
        telemetry.addData("Front Right Distance", jerome.getFrontRightDistance());
        telemetry.addData("Back Distance", jerome.getBackDistance());
        telemetry.addData("Front Extension Magnetic Limit Switch", jerome.isFrontExtensionLimitPressed());
        telemetry.addData("Back Extension Magnetic Limit Switch", jerome.isBackExtensionLimitPressed());
        telemetry.addData("Up Arm Limit", jerome.isUpArmLimitPressed());
        telemetry.addData("Down Arm Limit", jerome.isDownArmLimitPressed());
        telemetry.update();
      }
    }
  }
}
