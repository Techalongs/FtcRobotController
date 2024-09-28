package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Test")
public class Test extends LinearOpMode {
    private Servo servo;

    @Override
    public void runOpMode(){
        servo = hardwareMap.get(Servo.class, "servo");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            servo.setPosition(0);
            sleep(1000);
            servo.setPosition(1);
            sleep(1000);
        }
    }
}
