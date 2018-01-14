package org.firstinspires.ftc.teamcode;

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

@Autonomous(name = "Ramen", group = "LinearOpMode")
public class Ramen extends LinearOpMode {
    Robot robot;
    VuforiaLocalizer vuforia;
    public RelicRecoveryVuMark vuMark;
    public int vuMarkStatus;
    public VuforiaTrackables relicTrackables;
    public VuforiaTrackable relicTemplate;
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "ARS7vfb/////AAAAGWKsj4OtdExwhKE/OTOJSzR9++AxPiyaq7P3W+iDO4C5Buh7Ojqj942pkRRFn9XIK6i" +
                "mGU1yv/SM+/dbkQSqfHNen+Kv/DZgRD4UN3BAHcrwULhfFOxyGrjgzzeF9As1aUsC0rREhsOPzc5lTpG2RHimo6JZF/o8YGgEA5JYKzt7AI5mzSdKDpng7p2u54gAEDjvKI/K7lJ71Q+2j2BXGHs" +
                "IBpYa1LozJUploM16NHtsx2zLwrjh3WLIEGYw22JWcsxic6l8XN0PNjf4KNo+nlhVuHhfgDo/aI7sLx7YZXak5v4b2GwPD7f7AxXLZEonYoI5Wpb7sI76d4jd/mqMjeB1h/Rc1jpGobDeAPWVDgqq";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        telemetry.addData(">", "Press Play to start");
        telemetry.update();

        robot.gyro.calibrate();
        vuMarkStatus = 2000;
        vuMark = RelicRecoveryVuMark.from(relicTemplate);
        robot.jongChulPark.setPosition(0.85);
        robot.jewel.enableLed(true);
        relicTrackables.activate();
        waitForStart();
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();
        while (opModeIsActive() && runtime.time() < 1) {
            String text12 = vuMark + ".";
            android.util.Log.i("Ssibal", text12);
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
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
        sleep(500);

        String text = robot.jewel.red() + ".";
        android.util.Log.i("k9", text);
        String text1 = robot.jewel.blue() + ".";
        android.util.Log.i("k2a1", text1);
        if (robot.jewel.red() > 1) {
            String text2 = robot.jewel.red() + ".";
            android.util.Log.i("kimchi", text2);
            String text3 = robot.jewel.blue() + ".";
            android.util.Log.i("danmuji", text3);
            robot.stop();
            robot.setAGenePower(0.3);
            sleep(290);
            robot.stop();
            robot.jongChulPark.setPosition(0.7);
            sleep(500);
            robot.setBanzaiPower(0.3);
            sleep(290);
            robot.stop();
        } else {
            String text4 = robot.jewel.red() + ".";
            android.util.Log.i("jjajangmyeon", text4);
            String text5 = robot.jewel.blue() + ".";
            android.util.Log.i("jjamppong", text5);
            robot.stop();
            robot.setBanzaiPower(0.3);
            sleep(290);
            robot.stop();
            robot.jongChulPark.setPosition(0.7);
            sleep(500);
            robot.setAGenePower(0.3);
            sleep(290);
            robot.stop();
        }

        runtime.reset();
        robot.jongChulPark.setPosition(0.3);

        while (opModeIsActive() && runtime.time() < 4) {
            while (robot.jewel.red() < 1) {
                robot.setBanzaiPower(0.15);
            }
        }
        robot.stop();
        robot.jongChulPark.setPosition(Servo.MIN_POSITION);
        sleep(1000);

        if (vuMarkStatus == 3) {
            String text2 = vuMarkStatus + ".";
            android.util.Log.i("low calorie", text2);

            sleep(500);

            robot.turnRight();
            sleep(700);

            robot.stop();
        } else if (vuMarkStatus == 2) {
            String text2 = vuMarkStatus + ".";
            android.util.Log.i("moisture zzang", text2);
            robot.setBanzaiPower(0.25);
            sleep(700);
            robot.stop();

            sleep(500);

            robot.turnRight();
            sleep(700);
            robot.stop();
        } else if (vuMarkStatus == 1) {
            String text2 = vuMarkStatus + ".";
            android.util.Log.i("toreta", text2);
            robot.setBanzaiPower(0.25);
            sleep(1700);
            robot.stop();

            sleep(500);

            robot.turnRight();
            sleep(700);
            robot.stop();
        } else {
            String text2 = vuMarkStatus + ".";
            android.util.Log.i("moisture", text2);

            sleep(500);

            robot.turnRight();
            sleep(700);

            robot.stop();
        }

        robot.setBanzaiPower(0.2);
        sleep(500);
        robot.stop();

        robot.vomit();
        robot.setAGenePower(0.1);
        sleep(1000);
        robot.stop();

        robot.setBanzaiPower(0.1);
        sleep(1000);
        robot.stop();

        robot.setAGenePower(0.1);
        sleep(500);
        robot.full();
        robot.stop();
    }
}
