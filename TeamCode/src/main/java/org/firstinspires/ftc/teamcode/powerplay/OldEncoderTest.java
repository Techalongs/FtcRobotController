package org.firstinspires.ftc.teamcode.powerplay;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name = "Old Encoder Test")
@Disabled
public class OldEncoderTest extends LinearOpMode {
  private DcMotor CH0;
  private DcMotor CH1;
  private DcMotor CH2;
  private DcMotor CH3;
  private double startTime;
  
  @Override
  public void runOpMode() {
    CH0 = hardwareMap.get(DcMotor.class, "CH0");
    CH1 = hardwareMap.get(DcMotor.class, "CH1");
    CH2 = hardwareMap.get(DcMotor.class, "CH2");
    CH3 = hardwareMap.get(DcMotor.class, "CH3");
    
    CH0.setDirection(DcMotorSimple.Direction.REVERSE);
    CH2.setDirection(DcMotorSimple.Direction.REVERSE);
    
    CH0.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
    CH1.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
    CH2.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
    CH3.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
    
    CH0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    CH1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    CH2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    CH3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    
    telemetry.addData("Status", "Initialized");
    telemetry.update();
    
    waitForStart();
    
    while (opModeIsActive()) {
      if (opModeIsActive()) {
        CH0.setPower(0.3);
        CH1.setPower(0.3);
        CH2.setPower(0.3);
        CH3.setPower(0.3);
        
        startTime = getRuntime();
        while (opModeIsActive() && getRuntime() - startTime < 5) {
          telemetry.update();
        }
        
        CH0.setPower(0);
        CH1.setPower(0);
        CH2.setPower(0);
        CH3.setPower(0);
        
        break;
      }
    }
  }
}
