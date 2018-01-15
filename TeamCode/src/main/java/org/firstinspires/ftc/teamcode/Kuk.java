package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


/**
 * Created by Nathan Huh on 2017-12-14.
 */
@Autonomous(name = "Kuk", group = "Kuk")
public class Kuk extends LinearOpMode {
    Robot robot;
    private ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
    VuforiaLocalizer vuforia;
    public RelicRecoveryVuMark vuMark;
    public int vuMarkStatus;
    public VuforiaTrackables relicTrackables;
    public VuforiaTrackable relicTemplate;
    int counter;

    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "ARS7vfb/////AAAAGWKsj4OtdExwhKE/OTOJSzR9++AxPiyaq7P3W+iDO4C5Buh7Ojqj942pkRRFn9XIK6i" +
                "mGU1yv/SM+/dbkQSqfHNen+Kv/DZgRD4UN3BAHcrwULhfFOxyGrjgzzeF9As1aUsC0rREhsOPzc5lTpG2RHimo6JZF/o8YGgEA5JYKzt7AI5mzSdKDpng7p2u54gAEDjvKI/K7lJ71Q+2j2BXGHs" +
                "IBpYa1LozJUploM16NHtsx2zLwrjh3WLIEGYw22JWcsxic6l8XN0PNjf4KNo+nlhVuHhfgDo/aI7sLx7YZXak5v4b2GwPD7f7AxXLZEonYoI5Wpb7sI76d4jd/mqMjeB1h/Rc1jpGobDeAPWVDgqq";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
        telemetry.addData(">", "Press Play to start");
        telemetry.update();

        //robot.gyro.calibrate();
        relicTrackables.activate();
        vuMarkStatus = 2000;
        runtime.reset();
        vuMark = RelicRecoveryVuMark.from(relicTemplate);
        runtime.startTime();
        waitForStart();

        while (opModeIsActive()) {
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                telemetry.addData("VuMark", "%s visible", vuMark);
                switch (vuMark) {
                    case LEFT:
                        vuMarkStatus = 1;
                        break;
                    case CENTER:
                        vuMarkStatus = 2;
                        break;
                    case RIGHT:
                        vuMarkStatus = 3;
                        break;
                }
            } else {
                telemetry.addData("VuMark", "not visible");
            }
            telemetry.update();
        }

        robot.jongChulPark.setPosition(0.53);
        sleep(1000);

        while (robot.jewel.argb() < 346 && robot.jewel.argb() > 20) {
            idle();
        }

        robot.banzai();
        sleep(400);
        robot.stop();

        robot.jongChulPark.setPosition(robot.jongChulPark.MIN_POSITION);

        sleep(1000);

        robot.banzai();
        sleep(1000);
        robot.stop();

        robot.turnRight();
        sleep(720);
        robot.stop();

        //robot.vomit();
        //sleep(1000);
        //robot.full();

        sleep(500);

        robot.setAGenePower(0.15);
        //robot.eat();
        sleep(2000);
        //robot.full();
        robot.stop();

        sleep(500);

        robot.banzai();
        sleep(1500);
        robot.stop();

        //robot.vomit();
        //sleep(1000);
        //robot.full();
    }
}