package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous (name = "Zone Test")
@Disabled
public class ZoneTest extends LinearOpMode {
  private DcMotor CH0;
  private DcMotor CH1;
  private DcMotor CH2;
  private DcMotor CH3;
  private DcMotor armMotor1;
  private DcMotor armMotor2;
  private CRServo leftExtension;
  private CRServo rightExtension;
  private Servo leftClaw;
  private Servo rightClaw;
  private Servo clawHinge;
  private TouchSensor extensionLimit;
  private DistanceSensor distanceSensor;
  private int zone;
  
  @Override
  public void runOpMode() {
    CH0 = hardwareMap.get(DcMotor.class, "CH0");
    CH1 = hardwareMap.get(DcMotor.class, "CH1");
    CH2 = hardwareMap.get(DcMotor.class, "CH2");
    CH3 = hardwareMap.get(DcMotor.class, "CH3");
    armMotor1 = hardwareMap.get(DcMotor.class, "armMotor1");
    armMotor2 = hardwareMap.get(DcMotor.class, "armMotor2");
    leftExtension =  hardwareMap.get(CRServo.class, "leftExtension");
    rightExtension = hardwareMap.get(CRServo.class, "rightExtension");
    leftClaw = hardwareMap.get(Servo.class, "leftClaw");
    rightClaw = hardwareMap.get(Servo.class, "rightClaw");
    clawHinge = hardwareMap.get(Servo.class, "clawHinge");
    extensionLimit = hardwareMap.get(TouchSensor.class, "extensionLimit");
    distanceSensor = hardwareMap.get(DistanceSensor.class, "distanceSensor");
    
    CH1.setDirection(DcMotorSimple.Direction.REVERSE);
    CH3.setDirection(DcMotorSimple.Direction.REVERSE);
    armMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
    leftExtension.setDirection(CRServo.Direction.REVERSE);
    rightClaw.setDirection(Servo.Direction.REVERSE);
    
    CH0.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    CH1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    CH2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    CH3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    armMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    armMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    
    clawHinge.setPosition(0.4);
    leftClaw.setPosition(0);
    rightClaw.setPosition(0);
    
    telemetry.addData("Status", "Initialized");
    telemetry.update();
    waitForStart();
    
    while (opModeIsActive()) {
      if (opModeIsActive()) {
        backward(0.3, 200);
        clawHinge.setPosition(0.9);
        
        if (checkRange(35, 50, distanceSensor.getDistance(DistanceUnit.CM))) { // middle
          zone = 2;
        } else {
          strafeLeft(0.3, 700);
          if (checkRange(35, 45, distanceSensor.getDistance(DistanceUnit.CM))) { // right
            zone = 3;
          } else { // left
            zone = 1;
          }
        }
        
        displayData();
        
        sleep(30000);
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
    CH2.setPower(power);
    CH3.setPower(power);
    
    while (CH0.isBusy() && CH1.isBusy() && CH2.isBusy() && CH3.isBusy() && opModeIsActive()) {
      displayData();
    }
    
    stopMoving();
  }
  
  private void forward(double power, int tick) {
    move(power, tick, tick, tick, tick);
  }
  
  private void backward(double power, int tick) {
    forward(power, -tick);
  }
  
  private void turnRight(double power, int tick) {
    move(power, -tick, tick, -tick, tick);
  }
  
  private void turnLeft(double power, int tick) {
    turnRight(power, -tick);
  }
  
  private void strafeRight(double power, int tick) {
    move(power, -tick, -tick, tick, tick);
  }
  
  private void strafeLeft(double power, int tick) {
    strafeRight(power, -tick);
  }
  
  private void stopMoving() {
    CH0.setPower(0);
    CH1.setPower(0);
    CH2.setPower(0);
    CH3.setPower(0);
  }
  
  private void raiseArms(int tick) {
    armMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    armMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    
    armMotor1.setTargetPosition(tick);
    armMotor2.setTargetPosition(tick);
    
    armMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    armMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    
    armMotor1.setPower(0.5);
    armMotor2.setPower(0.5);
    
    while (armMotor1.isBusy() && armMotor2.isBusy() && opModeIsActive()) {
      displayData();
    }
    
    armMotor1.setPower(0);
    armMotor2.setPower(0);
  }
  
  private void lowerArms(int tick) {
    raiseArms(-tick);
  }
  
  private boolean checkRange(int num1, int num2, double value) {
    return value > num1 && value < num2; // num1 < value < num2
  }
  
  private void displayData() {
    telemetry.addData("Status", "Running");
    telemetry.addData("CH0", CH0.getPower());
    telemetry.addData("CH1", CH1.getPower());
    telemetry.addData("CH2", CH2.getPower());
    telemetry.addData("CH3", CH3.getPower());
    telemetry.addData("Arm Motor 1", armMotor1.getPower());
    telemetry.addData("Arm Motor 2", armMotor2.getPower());
    telemetry.addData("Arm Motor 1 ticks", armMotor1.getCurrentPosition());
    telemetry.addData("Arm Motor 2 ticks", armMotor2.getCurrentPosition());
    telemetry.addData("Left Extension", leftExtension.getPower());
    telemetry.addData("Right Extension", rightExtension.getPower());
    telemetry.addData("Left Claw", leftClaw.getPosition());
    telemetry.addData("Right Claw", rightClaw.getPosition());
    telemetry.addData("Claw Hinge", clawHinge.getPosition());
    telemetry.addData("Distance Sensor", distanceSensor.getDistance(DistanceUnit.CM));
    telemetry.addData("Extension Limit Switch", extensionLimit.isPressed());
    telemetry.addData("Zone", zone);
    telemetry.update();
  }
}
