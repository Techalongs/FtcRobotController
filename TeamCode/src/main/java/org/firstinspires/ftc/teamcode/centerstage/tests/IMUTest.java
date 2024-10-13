package org.firstinspires.ftc.teamcode.centerstage.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous (name = "IMU Test")
@Disabled
public class IMUTest extends LinearOpMode {
  private DcMotor CH0;
  private DcMotor CH1;
  private DcMotor CH2;
  private DcMotor CH3;
  private BNO055IMU imu;
  
  @Override
  public void runOpMode() {
    BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
    
    CH0 = hardwareMap.get(DcMotor.class, "CH0");
    CH1 = hardwareMap.get(DcMotor.class, "CH1");
    CH2 = hardwareMap.get(DcMotor.class, "CH2");
    CH3 = hardwareMap.get(DcMotor.class, "CH3");
    imu = hardwareMap.get(BNO055IMU.class, "imu");
    
    CH0.setDirection(DcMotorSimple.Direction.REVERSE);
    CH2.setDirection(DcMotorSimple.Direction.REVERSE);
    
    imu.initialize(parameters);
    
    waitForStart();
    
    while (opModeIsActive()) {
      if (opModeIsActive()) {
        Orientation robotOrientation;
        robotOrientation = imu.getAngularOrientation();
        
        double yaw = robotOrientation.firstAngle;
        
        while (opModeIsActive()) {
          CH0.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
          CH1.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
          CH2.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
          CH3.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
          
          CH0.setPower(0.3);
          CH1.setPower(-0.3);
          CH2.setPower(0.3);
          CH3.setPower(-0.3);
          
          robotOrientation = imu.getAngularOrientation();
          yaw = robotOrientation.firstAngle;
          
          if (yaw <= -0.01) {
            CH0.setPower(0);
            CH1.setPower(0);
            CH2.setPower(0);
            CH3.setPower(0);
            break;
          }
          
          telemetry.addData("heading", yaw);
          telemetry.addData("CH0", CH0.getPower());
          telemetry.addData("CH1", CH1.getPower());
          telemetry.addData("CH2", CH2.getPower());
          telemetry.addData("CH3", CH3.getPower());
          telemetry.update();
        }
        
        CH0.setPower(0);
        CH1.setPower(0);
        CH2.setPower(0);
        CH3.setPower(0);
        
        
      }
    }
  }
}
