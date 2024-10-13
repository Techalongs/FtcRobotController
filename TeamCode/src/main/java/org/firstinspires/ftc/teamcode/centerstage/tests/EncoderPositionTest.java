package org.firstinspires.ftc.teamcode.centerstage.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.centerstage.Robot;

@TeleOp(name = "Encoder Position Test")
@Disabled
public class EncoderPositionTest extends LinearOpMode {
  
  @Override
  public void runOpMode() {
    Robot jerome = new Robot(hardwareMap, telemetry, this);
    jerome.init(0.5);
    
    telemetry.addData("Status", "Initialized");
    telemetry.update();
    waitForStart();
    
    while (opModeIsActive()) {
      if (opModeIsActive()) {
        // jerome.drive(0.8, gamepad1);

        double armPower = -gamepad2.left_stick_y * 0.8;
        jerome.raiseArms(armPower);

        telemetry.addData("Front Left Power", jerome.getFLMotorPower());
        telemetry.addData("Front Right Power", jerome.getFRMotorPower());
        telemetry.addData("Back Left Power", jerome.getBLMotorPower());
        telemetry.addData("Back Right Power", jerome.getBRMotorPower());
        telemetry.addData("Front Left Position", jerome.getFLMotorPosition());
        telemetry.addData("Front Right Position", jerome.getFRMotorPosition());
        telemetry.addData("Back Left Position", jerome.getBLMotorPower());
        telemetry.addData("Back Right Position", jerome.getBRMotorPower());
        telemetry.update();
      }
    }
  }
}
