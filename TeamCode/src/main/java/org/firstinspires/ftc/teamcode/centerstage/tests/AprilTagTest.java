package org.firstinspires.ftc.teamcode.centerstage.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.centerstage.Robot;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous (name = "April Tag Test")
@Disabled
public class AprilTagTest extends LinearOpMode {


  @Override
  public void runOpMode() {
    /* SIMPLE:
    myWebcam = hardwareMap.get(WebcamName.class, "Webcam 1");
    myAprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();
    myVisionPortal = VisionPortal.easyCreateWithDefaults(myWebcam, myAprilTagProcessor);
    */
    Robot jerome = new Robot(hardwareMap, telemetry, this, 0.7);
    jerome.init(0.5);
    
    telemetry.addData("Status", "Initialized");
    telemetry.update();
    waitForStart();
    
    while (opModeIsActive()) {
      if (opModeIsActive()) {
        int size = jerome.getAprilTagProcessor().getDetections().size();
        if (size > 0) {
          AprilTagDetection detection = jerome.getAprilTagProcessor().getDetections().get(0);

          String name = detection.metadata.name;
          int id = detection.metadata.id;
          double x = detection.ftcPose.x;

          jerome.addData("detection name", name);
          jerome.addData("id", id);
          jerome.addData("x", x);

          // 60 ticks = 1 inch (as per Austin)
          // 2.5 is the offset between the claw and camera
          jerome.strafeRight((int) (x - 2.5) * 60);
        }
        
        jerome.displayData();
        sleep(10000);
        
        break;
      }
    }
  }
}
