package org.firstinspires.ftc.teamcode.powerplay;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "Autonomous (DO NOT USE)")
@Disabled
public class Autonomous2 extends LinearOpMode {
  private DcMotor CH0;
  private DcMotor CH1;
  private DcMotor CH2;
  private DcMotor CH3;
  private DcMotor armMotor;
  private Servo clawServo;
  private BNO055IMU imu;
  private ColorSensor colorSensor;
  private TouchSensor touchSensor;
  private final int ticks = 540;
  // private final int ticksPerRev = 2240;
  private final double speed = 0.3;
  private int zone = 0;
  private double startTime;
  
  @Override
  public void runOpMode() {
    BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
    
    CH0 = hardwareMap.get(DcMotor.class, "CH0");
    CH1 = hardwareMap.get(DcMotor.class, "CH1");
    CH2 = hardwareMap.get(DcMotor.class, "CH2");
    CH3 = hardwareMap.get(DcMotor.class, "CH3");
    touchSensor = hardwareMap.get(TouchSensor.class, "touchSensor");
    //armMotor = hardwareMap.get(DcMotor.class, "armMotor");
    //clawServo = hardwareMap.get(Servo.class, "clawServo");
    imu = hardwareMap.get(BNO055IMU.class, "imu");
    //colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");
    
    imu.initialize(parameters);

    CH1.setDirection(DcMotorSimple.Direction.REVERSE);
    CH3.setDirection(DcMotorSimple.Direction.REVERSE);
    
    CH0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    CH1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    CH2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    CH3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    
    //clawServo.setPosition(0);
    
    telemetry.addData("Status", "Initialized");
    telemetry.update();
    
    waitForStart();
    
    while (opModeIsActive()) {
      if (opModeIsActive()) {
        move(0.3, (int) Math.round(-ticks * 2.5));
        sleep(500);
        
        if (touchSensor.isPressed()) {
          zone = 2;
        } else {
          //move();
        }
      }
    }
  }
  
  private void move(double power, int tick1, int tick2, int tick3, int tick4) {
    CH0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    CH1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    CH2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    CH3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    
    CH0.setTargetPosition(tick1);
    CH1.setTargetPosition(tick2);
    CH2.setTargetPosition(tick3);
    CH3.setTargetPosition(tick4);
    
    CH0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    CH1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    CH2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    CH3.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    
    CH0.setPower(power);
    CH1.setPower(power);
    CH2.setPower(power);
    CH3.setPower(power);
    
    startTime = getRuntime();
    while (CH0.isBusy() && CH1.isBusy() && CH2.isBusy() && CH3.isBusy() && opModeIsActive()) {
      displayData();
    }
    
    stopMoving();
  }
  
  private void move(double power, int tick) {
    move(power, tick, tick, tick, tick);
  }
  
  private void stopMoving() {
    CH0.setPower(0);
    CH1.setPower(0);
    CH2.setPower(0);
    CH3.setPower(0);
  }

  private void displayData() {
    telemetry.addData("CH0 Power", CH0.getPower());
    telemetry.addData("CH1 Power", CH1.getPower());
    telemetry.addData("CH2 Power", CH2.getPower());
    telemetry.addData("CH3 Power", CH3.getPower());
    telemetry.addData("CH0 Current Position", CH0.getCurrentPosition());
    telemetry.addData("CH1 Current Position", CH1.getCurrentPosition());
    telemetry.addData("CH2 Current Position", CH2.getCurrentPosition());
    telemetry.addData("CH3 Current Position", CH3.getCurrentPosition());
    telemetry.addData("CH0 Target Position", CH0.getTargetPosition());
    telemetry.addData("CH1 Target Position", CH1.getTargetPosition());
    telemetry.addData("CH2 Target Position", CH2.getTargetPosition());
    telemetry.addData("CH3 Target Position", CH3.getTargetPosition());
    telemetry.update();
  }
}
