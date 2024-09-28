package org.firstinspires.ftc.teamcode.powerplay;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "Signal Autonomous")
@Disabled
public class SignalAutonomous extends LinearOpMode {
  private DcMotor CH0;
  private DcMotor CH1;
  private DcMotor CH2;
  private DcMotor CH3;
  private DcMotor armMotor;
  private Servo clawServo;
  private ColorSensor colorSensor;
  private final int ticks = 540;
  private final double speed = 0.3;
  private int zone = 0;
  private double startTime;
  
  @Override
  public void runOpMode() {
    CH0 = hardwareMap.get(DcMotor.class, "CH0");
    CH1 = hardwareMap.get(DcMotor.class, "CH1");
    CH2 = hardwareMap.get(DcMotor.class, "CH2");
    CH3 = hardwareMap.get(DcMotor.class, "CH3");
    armMotor = hardwareMap.get(DcMotor.class, "armMotor");
    clawServo = hardwareMap.get(Servo.class, "clawServo"); 
    colorSensor = hardwareMap.get(ColorSensor.class, "colorSensor");

    CH0.setDirection(DcMotorSimple.Direction.REVERSE);
    CH2.setDirection(DcMotorSimple.Direction.REVERSE);
    
    CH0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    CH1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    CH2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    CH3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    
    telemetry.addData("Status", "Initialized");
    telemetry.update();
    
    waitForStart();
    
    while (opModeIsActive()) {
      if (opModeIsActive()) {
        raiseArm(ticks + 100);
        move(0.3, ticks * 3 - 100);
        sleep(500);
        
        boolean red = checkColor("red");
        boolean green = checkColor("green");
        boolean blue = checkColor("blue");
        
        if (red) {
          zone = 2;
        } else if (green) {
          zone = 1;
        } else if (blue) {
          zone = 3;
        }
        
        if (zone == 1) {
          move(speed, ticks * 3, ticks * 3, -ticks * 3, -ticks * 3);
        } else if (zone == 3) {
          move(speed, -ticks * 3 - 150, -ticks * 3 - 150, ticks * 3 - 150, ticks * 3 - 150);
        }
        
        sleep(10000);
        
        break;
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
    CH2.setPower(power - 0.6);
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
  
  private void raiseArm(int tick) {
    armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    armMotor.setTargetPosition(tick);
    armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    
    armMotor.setPower(0.5);
    
    startTime = getRuntime();
    while (armMotor.isBusy() && opModeIsActive()) {
      displayData();
    }
    
    armMotor.setPower(0);
    
  }
  
  private boolean checkRange(int num1, int num2, double value) {
    return value > num1 && value < num2;
  }
  
  private boolean checkColor(String color) {
    boolean red;
    boolean green;
    boolean blue;
    
    switch (color) {
      case "red":
        red = checkRange(1500, 4000, colorSensor.red());
        green = checkRange(1000, 2500, colorSensor.green());
        blue = checkRange(100, 2500, colorSensor.blue());
        break;
      case "green":
        red = checkRange(0, 1500, colorSensor.red());
        green = checkRange(1500, 6000, colorSensor.green());
        blue = checkRange(500, 3500, colorSensor.blue());
        break;
      case "blue":
        red = checkRange(0, 1500, colorSensor.red());
        green = checkRange(1500, 3000, colorSensor.green());
        blue = checkRange(4000, 7500, colorSensor.blue());
        break;
      default:
        red = false;
        green = false;
        blue = false;
        break;
    }
    
    return red && blue && green;
  }

  private void displayData() {
    telemetry.addData("red", colorSensor.red());
    telemetry.addData("green", colorSensor.green());
    telemetry.addData("blue", colorSensor.blue());
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
    telemetry.addData("Zone", zone);
    telemetry.update();
  }
}
  
