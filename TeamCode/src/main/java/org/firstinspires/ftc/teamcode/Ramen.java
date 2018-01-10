package org.firstinspires.ftc.teamcode;

import android.widget.Toast;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Nathan Huh on 2018-01-07.
 */

@Autonomous(name = "Ramen", group = "Kuk")
public class Ramen extends LinearOpMode {
    Robot robot;
    private ElapsedTime runtime = new ElapsedTime(ElapsedTime.Resolution.SECONDS);
    VuforiaLocalizer vuforia;
    public RelicRecoveryVuMark vuMark;
    public int vuMarkStatus;
    public VuforiaTrackables relicTrackables;
    public VuforiaTrackable relicTemplate;
    int isitRed;
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

        robot.gyro.calibrate();
        relicTrackables.activate();
        vuMarkStatus = 2000;
        isitRed = 0;
        runtime.reset();
        vuMark = RelicRecoveryVuMark.from(relicTemplate);
        runtime.startTime();
        robot.jongChulPark.setPosition(0.7);
        robot.jewel.enableLed(false);
        robot.line.enableLed(false);
        robot.secLine.enableLed(false);
        waitForStart();

        while (opModeIsActive() && runtime.time() < 1) {
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
            idle();
        }

        robot.jongChulPark.setPosition(Servo.MIN_POSITION);
        sleep(2000);

        if (robot.jewel.red() > 1) {
            robot.setAGenePower(0.3);
            sleep(100);
            robot.stop();
            robot.jongChulPark.setPosition(0.7);
            robot.setBanzaiPower(0.3);
            sleep(100);
            robot.stop();
        } else {
            robot.setBanzaiPower(0.3);
            sleep(100);
            robot.stop();
            robot.jongChulPark.setPosition(0.7);
            robot.setAGenePower(0.3);
            sleep(100);
            robot.stop();
        }

        robot.setBanzaiPower(0.2);
        sleep(1500);
        robot.stop();

        while (opModeIsActive()) {
            while (robot.gyro.getHeading() == 90) {
                String text = robot.gyro.getHeading() + ".";
                android.util.Log.i("으아아아아아아아아아아아아아아아아아아아아", text);
                robot.stop();
            }
        }

        robot.setBanzaiPower(0.2);
        sleep(1500);
        robot.stop();

        if (vuMarkStatus == 3) {
            robot.setBanzaiPower(0.1);
            while (robot.line.red() < 3) {
                idle();
            }
            robot.stop();
        } else if (vuMarkStatus == 2) {
            robot.setBanzaiPower(0.1);
            while (robot.secLine.red() < 3) {
                idle();
            }
            robot.stop();
        } else if (vuMarkStatus == 1) {
            robot.setBanzaiPower(0.1);
            while (robot.secLine.red() < 3) {
                idle();
            }
            sleep(500);
            while (robot.secLine.red() < 3) {
                idle();
            }
            robot.stop();
        } else {
            robot.setBanzaiPower(0.1);
            while (robot.line.red() < 3) {
                idle();
            }
            robot.stop();
        }

        sleep(500);

        robot.turnRight();
        while (opModeIsActive()) {
            while (robot.gyro.getHeading() == 90) {
                String text = robot.gyro.getHeading() + ".";
                android.util.Log.i("으아아아아아아아아아아아아아아아아아아아아", text);
                robot.stop();
            }
        }
    }
}
