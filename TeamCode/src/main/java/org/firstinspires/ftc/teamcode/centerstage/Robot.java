package org.firstinspires.ftc.teamcode.centerstage;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.HashMap;

public class Robot implements MecanumDrivetrain {
    private final DcMotor frontLeft;
    private final DcMotor frontRight;
    private final DcMotor backLeft;
    private final DcMotor backRight;
    private final DcMotor leftArm;
    private final DcMotor rightArm;
    private final CRServo leftExtension;
    private final CRServo rightExtension;
    private final Servo leftClaw;
    private final Servo rightClaw;
    private final Servo clawHinge;
    private final Servo dronePosition;
    private final Servo droneLaunch;
    private final TouchSensor frontExtensionLimit;
    private final TouchSensor backExtensionLimit;
    private final TouchSensor upArmLimit;
    private final TouchSensor downArmLimit;
    private final DistanceSensor frontLeftDistance;
    private final DistanceSensor frontRightDistance;
    private final DistanceSensor backDistance;
    private final WebcamName webcam;
    private final AprilTagProcessor aprilTagProcessor;
    private final VisionPortal visionPortal;
    private final Telemetry telemetry;
    private final LinearOpMode opMode;
    private final HashMap<String, String> extraData = new HashMap<>();
    private double speed;

    public Robot(HardwareMap hardwareMap, Telemetry telemetry, LinearOpMode opMode) {
        frontLeft = hardwareMap.get(DcMotor.class, "CH1");
        frontRight = hardwareMap.get(DcMotor.class, "CH0");
        backLeft = hardwareMap.get(DcMotor.class, "CH3");
        backRight = hardwareMap.get(DcMotor.class, "CH2");
        leftArm = hardwareMap.get(DcMotor.class, "armMotor2");
        rightArm = hardwareMap.get(DcMotor.class, "armMotor1");
        leftExtension = hardwareMap.get(CRServo.class, "leftExtension");
        rightExtension = hardwareMap.get(CRServo.class, "rightExtension");
        leftClaw = hardwareMap.get(Servo.class, "leftClaw");
        rightClaw = hardwareMap.get(Servo.class, "rightClaw");
        clawHinge = hardwareMap.get(Servo.class, "clawHinge");
        dronePosition = hardwareMap.get(Servo.class, "dronePosition");
        droneLaunch = hardwareMap.get(Servo.class, "droneLaunch");
        frontExtensionLimit = hardwareMap.get(TouchSensor.class, "frontExtensionLimit");
        backExtensionLimit = hardwareMap.get(TouchSensor.class, "backExtensionLimit");
        upArmLimit = hardwareMap.get(TouchSensor.class, "upArmLimit");
        downArmLimit = hardwareMap.get(TouchSensor.class, "downArmLimit");
        frontLeftDistance = hardwareMap.get(DistanceSensor.class, "frontLeftDistance");
        frontRightDistance = hardwareMap.get(DistanceSensor.class, "frontRightDistance");
        backDistance = hardwareMap.get(DistanceSensor.class, "backDistance");
        webcam = hardwareMap.get(WebcamName.class, "Webcam 1");

        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setDrawTagID(true)
                .setDrawTagOutline(true)
                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setTagLibrary(AprilTagGameDatabase.getCenterStageTagLibrary())
                .setLensIntrinsics(1460.37, 146037, 630.659, 363.582)
                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(webcam)
                .addProcessor(aprilTagProcessor)
                .setCameraResolution(new Size(1280, 720))
                .enableLiveView(true)
                .setAutoStopLiveView(true)
                .build();

        this.telemetry = telemetry;
        this.opMode = opMode;
        this.speed = 0.5;
    }

    public Robot(HardwareMap hardwareMap, Telemetry telemetry, LinearOpMode opMode, double speed) {
        this(hardwareMap, telemetry, opMode);
        this.speed = speed;
    }

    public void init(double positionAngle) {
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        rightArm.setDirection(DcMotorSimple.Direction.REVERSE);
        leftExtension.setDirection(CRServo.Direction.REVERSE);
        rightClaw.setDirection(Servo.Direction.REVERSE);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //dronePosition.setPosition(0.35);
        dronePosition.setPosition(positionAngle);
        droneLaunch.setPosition(0);
    }

    @Override
    public void drive(double limiter, Gamepad gamepad, double y) {
        float FLPower = (-gamepad.left_stick_y + gamepad.right_stick_x) + gamepad.left_stick_x;
        float FRPower = (-gamepad.left_stick_y - gamepad.right_stick_x) - gamepad.left_stick_x;
        float BLPower = (-gamepad.left_stick_y + gamepad.right_stick_x) - gamepad.left_stick_x;
        float BRPower = (-gamepad.left_stick_y - gamepad.right_stick_x) + gamepad.left_stick_x;

        /*
        if (y != 0 && y < 20 && -gamepad.left_stick_y > 0) {
            frontLeft.setPower(0);
            frontRight.setPower(0);
            backLeft.setPower(0);
            backRight.setPower(0);
        }
         */

        frontLeft.setPower(FLPower * limiter);
        frontRight.setPower(FRPower * limiter);
        backLeft.setPower(BLPower * limiter);
        backRight.setPower(BRPower * limiter);
    }

    private void move(int tick1, int tick2, int tick3, int tick4) {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setTargetPosition(tick1);
        frontRight.setTargetPosition(tick2);
        backLeft.setTargetPosition(tick3);
        backRight.setTargetPosition(tick4);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(speed);
        frontRight.setPower(speed);
        backLeft.setPower(speed);
        backRight.setPower(speed);

        while (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy() && opMode.opModeIsActive()) {
            displayData();
        }

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    @Override
    public void moveForward(int ticks) {
        move(ticks, ticks, ticks, ticks);
    }

    @Override
    public void moveBackward(int ticks) {
        moveForward(-ticks);
    }

    @Override
    public void turnLeft(int ticks) {
        move(-ticks, ticks, -ticks, ticks);
    }

    @Override
    public void turnRight(int ticks) {
        turnLeft(-ticks);
    }

    @Override
    public void strafeLeft(int ticks) {
        move(-ticks, ticks, ticks, -ticks);
    }

    @Override
    public void strafeRight(int ticks) {
        strafeLeft(-ticks);
    }

    public void raiseArms(int ticks) {
        leftArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftArm.setTargetPosition(ticks);
        rightArm.setTargetPosition(ticks);

        leftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        raiseArms(speed);

        while (leftArm.isBusy() && rightArm.isBusy() && opMode.opModeIsActive() && !isUpArmLimitPressed()) {
            displayData();
        }

        stopArms();
    }

    public void lowerArms(int ticks) {
        leftArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftArm.setTargetPosition(-ticks);
        rightArm.setTargetPosition(-ticks);

        leftArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        lowerArms(speed);

        while (leftArm.isBusy() && rightArm.isBusy() && opMode.opModeIsActive() && !isDownArmLimitPressed()) {
            displayData();
        }

        stopArms();
    }

    public void stayArms(int stayPosition) {
        if (getLeftArmPosition() < stayPosition) raiseArms(0.1);
        else lowerArms(0.1);
    }

    public void raiseArms(double power) {
        if (!isUpArmLimitPressed()) {
            leftArm.setPower(power);
            rightArm.setPower(power);
        }
    }

    public void lowerArms(double power) {
        if (!isDownArmLimitPressed()) {
            leftArm.setPower(-power);
            rightArm.setPower(-power);
        }
    }

    public void stopArms() {
        raiseArms(0.0);
    }

    public int getLeftArmPosition() {
        return leftArm.getCurrentPosition();
    }

    public int getRightArmPosition() {
        return rightArm.getCurrentPosition();
    }

    public void extendExtension(double time) {
        leftExtension.setPower(speed);
        rightExtension.setPower(speed);

        double startTime = opMode.getRuntime();
        while (opMode.opModeIsActive() && opMode.getRuntime() - startTime < time && !backExtensionLimit.isPressed()) {
            telemetry.update();
        }

        stopExtension();
    }

    public void retractExtension(double time) {
        leftExtension.setPower(-speed);
        rightExtension.setPower(-speed);

        double startTime = opMode.getRuntime();
        while (opMode.opModeIsActive() && opMode.getRuntime() - startTime < time && !frontExtensionLimit.isPressed()) {
            telemetry.update();
        }

        stopExtension();
    }

    public void extendExtension() {
        leftExtension.setPower(1);
        rightExtension.setPower(1);
    }

    public void retractExtension() {
        leftExtension.setPower(-1);
        rightExtension.setPower(-1);
    }

    public void stopExtension() {
        leftExtension.setPower(0);
        rightExtension.setPower(0);
    }

    public void clawHingeDown() {
        clawHinge.setPosition(0.38);
    } // 0.25 - 0.35

    public void clawHingeUp() {
        clawHinge.setPosition(0.05);
    }

    public void closeClaws() {
        leftClaw.setPosition(0);
        rightClaw.setPosition(0);
    }

    public void openLeftClaw() {
        leftClaw.setPosition(0.55);
    }

    public void openRightClaw() {
        rightClaw.setPosition(0.4);
    }

    public void openClaws() {
        openLeftClaw();
        openRightClaw();
    }

    public void launchDrone(double positionAngle) {
        dronePosition.setPosition(positionAngle);
        droneLaunch.setPosition(1);
    }

    public double getFLMotorPower() {
        return frontLeft.getPower();
    }

    public double getFRMotorPower() {
        return frontRight.getPower();
    }

    public double getBLMotorPower() {
        return backLeft.getPower();
    }

    public double getBRMotorPower() {
        return backRight.getPower();
    }

    public double getFLMotorPosition() {
        return frontLeft.getCurrentPosition();
    }

    public double getFRMotorPosition() {
        return frontRight.getCurrentPosition();
    }

    public double getBLMotorPosition() {
        return backLeft.getCurrentPosition();
    }

    public double getBRMotorPosition() {
        return backRight.getCurrentPosition();
    }

    public boolean isFrontExtensionLimitPressed() {
        return frontExtensionLimit.isPressed();
    }

    public boolean isBackExtensionLimitPressed() {
        return backExtensionLimit.isPressed();
    }

    public boolean isUpArmLimitPressed() {
        return upArmLimit.isPressed();
    }

    public boolean isDownArmLimitPressed() {
        return downArmLimit.isPressed();
    }

    public double getFrontLeftDistance() {
        return frontLeftDistance.getDistance(DistanceUnit.CM);
    }

    public double getFrontRightDistance() {
        return frontRightDistance.getDistance(DistanceUnit.CM);
    }

    public double getBackDistance() {
        return backDistance.getDistance(DistanceUnit.CM);
    }

    public AprilTagProcessor getAprilTagProcessor() {
        return aprilTagProcessor;
    }

    public void addData(String caption, double value) {
        addData(caption, String.valueOf(value));
    }

    public void changeSpeed(double speed) {
        this.speed = speed;
    }

    public void addData(String caption, String value) {
        extraData.put(caption, value);
    }

    public void displayData() {
        telemetry.addData("Status", "Running");
        telemetry.addData("Front Left Power", getFLMotorPower());
        telemetry.addData("Front Right Power", getFRMotorPower());
        telemetry.addData("Back Left Power", getBLMotorPower());
        telemetry.addData("Back Right Power", getBRMotorPower());
        telemetry.addData("Front Left Position", getFLMotorPosition());
        telemetry.addData("Front Right Position", getFRMotorPosition());
        telemetry.addData("Back Left Position", getBLMotorPosition());
        telemetry.addData("Back Right Position", getBRMotorPosition());
        telemetry.addData("Left Arm", leftArm.getPower());
        telemetry.addData("Right Arm", rightArm.getPower());
        telemetry.addData("Left Arm ticks", getLeftArmPosition());
        telemetry.addData("Right Arm ticks", getRightArmPosition());
        telemetry.addData("Left Extension", leftExtension.getPower());
        telemetry.addData("Right Extension", rightExtension.getPower());
        telemetry.addData("Left Claw", leftClaw.getPosition());
        telemetry.addData("Right Claw", rightClaw.getPosition());
        telemetry.addData("Claw Hinge", clawHinge.getPosition());
        telemetry.addData("Up Arm Limit", isUpArmLimitPressed());
        telemetry.addData("Down Arm Limit", isDownArmLimitPressed());
        telemetry.addData("Front Extension Limit", isFrontExtensionLimitPressed());
        telemetry.addData("Back Extension Limit", isBackExtensionLimitPressed());
        telemetry.addData("Front Left Distance Sensor", getFrontLeftDistance());
        telemetry.addData("Front Right Distance Sensor", getFrontRightDistance());
        telemetry.addData("Back Distance Sensor", getBackDistance());

        for (String caption : extraData.keySet()) {
            telemetry.addData(caption, extraData.get(caption));
        }

        telemetry.update();
    }
}
