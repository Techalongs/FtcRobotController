package org.firstinspires.ftc.teamcode.centerstage.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "Slow Down Test")
@Disabled
public class SlowDownTest extends LinearOpMode {
    DcMotor CH0;
    DcMotor CH1;
    DcMotor CH2;
    DcMotor CH3;

    @Override
    public void runOpMode() {

        CH0 = hardwareMap.get(DcMotor.class, "CH0");
        CH1 = hardwareMap.get(DcMotor.class, "CH1");
        CH2 = hardwareMap.get(DcMotor.class, "CH2");
        CH3 = hardwareMap.get(DcMotor.class, "CH3");

        CH0.setDirection(DcMotorSimple.Direction.REVERSE);
        CH2.setDirection(DcMotorSimple.Direction.REVERSE);
        CH3.setDirection(DcMotorSimple.Direction.REVERSE);

        CH0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        CH1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        CH2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        CH3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        CH0.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        CH1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        CH2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        CH3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            if (opModeIsActive()) {
                /*
                jerome.moveForward(1500);
                jerome.changeSpeed(0.5);
                jerome.moveForward(1300);
                */

                CH0.setPower(0.8);
                CH1.setPower(0.8);
                CH2.setPower(0.8);
                CH3.setPower(0.8);

                double startTime = getRuntime();
                while (getRuntime() - startTime < 2 && opModeIsActive()) {
                }

                CH0.setPower(0.3);
                CH1.setPower(0.3);
                CH2.setPower(0.3);
                CH3.setPower(0.3);

                startTime = getRuntime();
                while (getRuntime() - startTime < 2 && opModeIsActive()) {
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
