package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.GyroSensor;
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

@Autonomous(name = "Soup", group = "LinearOpMode")
public class Soup extends LinearOpMode {
    // Robot class that I defined to initialize all the motors, sensors, etc.
    Robot robot;
    VuforiaLocalizer vuforia;
    public RelicRecoveryVuMark vuMark;
    public int vuMarkStatus;
    public VuforiaTrackables relicTrackables;
    public VuforiaTrackable relicTemplate;
    GyroSensor gyro;
    ModernRoboticsI2cGyro gyrosensor;
    boolean isRed;
    boolean isEasy;
    boolean isChallenge;
    double basestart = 0.35;
    double basepower= 0.4;
    int ninety;
    public void runOpMode() {
        robot = new Robot(hardwareMap);
        isRed = true;
        isEasy = true;
        isChallenge = false;
        ninety = 650;

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

        vuMarkStatus = 2000;
        vuMark = RelicRecoveryVuMark.from(relicTemplate);
        robot.jongChulPark.setPosition(Servo.MIN_POSITION);
        robot.liftEatBean.setPower(-1);
        robot.liftEat.setPower(-1);
        sleep(200);
        robot.liftEatBean.setPower(0);
        robot.liftEat.setPower(0);
        robot.jewel.enableLed(true);
        relicTrackables.activate();

        gyro = hardwareMap.gyroSensor.get("gyro");
        gyrosensor = (ModernRoboticsI2cGyro) gyro;
        gyro.calibrate();
        int heading = gyrosensor.getHeading();

        telemetry.addData("Vulture", "Alright! Bring it on!");
        telemetry.update();
        waitForStart();
        while (gyrosensor.isCalibrating()) {
            String text123 = gyrosensor.getHeading() + ".";
            android.util.Log.i("Lee", text123);
        }
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();
        while (opModeIsActive()) {
            while (opModeIsActive() && runtime.time() < 1) {
                String text1 = vuMark + ".";
                android.util.Log.i("Ssibal", text1);
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

            robot.jongChulPark.setPosition(0.666);
            sleep(1500);

            String text = robot.jewel.red() + ".";
            android.util.Log.i("k9", text);
            String text1 = robot.jewel.blue() + ".";
            android.util.Log.i("k2a1", text1);
            if (isRed && isEasy) {
                if (robot.jewel.red() > robot.jewel.blue()) {
                    robot.setAGenePower(0.2);
                    sleep(300);
                    robot.stop();
                    robot.jongChulPark.setPosition(Servo.MIN_POSITION);
                    sleep(500);
                    robot.setBanzaiPower(0.5);
                    sleep(825);
                    robot.stop();
                    sleep(500);
                    robot.setAGenePower(0.3);
                    sleep(525);
                    robot.stop();
                } else if (robot.jewel.blue() > robot.jewel.red()) {
                    sleep(500);
                    robot.setBanzaiPower(0.3);
                    sleep(700);
                    robot.stop();
                    robot.jongChulPark.setPosition(Servo.MIN_POSITION);
                    sleep(500);
                    robot.setAGenePower(0.3);
                    sleep(450);
                    robot.stop();
                } else {
                    robot.jongChulPark.setPosition(Servo.MIN_POSITION);
                    robot.setBanzaiPower(0.3);
                    sleep(700);
                    robot.stop();
                    sleep(500);
                    robot.setAGenePower(0.3);
                    sleep(450);
                    robot.stop();
                }
            } else if (!isRed && isEasy) {
                if (robot.jewel.blue() > robot.jewel.red()) {
                    robot.setAGenePower(0.3);
                    sleep(700);
                    robot.stop();
                    robot.jongChulPark.setPosition(Servo.MIN_POSITION);
                    sleep(500);
                    robot.setBanzaiPower(0.5);
                    sleep(450);
                    robot.stop();
                } else if (robot.jewel.red() > robot.jewel.blue()) {
                    robot.setBanzaiPower(0.3);
                    sleep(350);
                    robot.stop();
                    robot.jongChulPark.setPosition(Servo.MIN_POSITION);
                    sleep(500);
                    robot.setAGenePower(0.5);
                    sleep(1600);
                    robot.stop();
                    robot.setBanzaiPower(0.3);
                    sleep(475);
                    robot.stop();
                } else {
                    robot.jongChulPark.setPosition(Servo.MIN_POSITION);
                    robot.setAGenePower(0.3);
                    sleep(700);
                    robot.stop();
                    sleep(500);
                    robot.setBanzaiPower(0.5);
                    sleep(450);
                    robot.stop();
                }
            } else if (isRed && !isEasy) {
                if (robot.jewel.red() > robot.jewel.blue()) {
                    robot.setAGenePower(0.2);
                    sleep(300);
                    robot.stop();
                    robot.jongChulPark.setPosition(Servo.MIN_POSITION);
                    sleep(500);
                    robot.setBanzaiPower(0.5);
                    sleep(825);
                    robot.stop();
                    sleep(500);
                    robot.setAGenePower(0.3);
                    sleep(525);
                    robot.stop();
                } else if (robot.jewel.blue() > robot.jewel.red()) {
                    sleep(500);
                    robot.setBanzaiPower(0.3);
                    sleep(700);
                    robot.stop();
                    robot.jongChulPark.setPosition(Servo.MIN_POSITION);
                    sleep(500);
                    robot.setAGenePower(0.3);
                    sleep(450);
                    robot.stop();
                } else {
                    robot.jongChulPark.setPosition(Servo.MIN_POSITION);
                    robot.setBanzaiPower(0.3);
                    sleep(700);
                    robot.stop();
                    sleep(500);
                    robot.setAGenePower(0.3);
                    sleep(450);
                    robot.stop();
                }
            } else {
                if (robot.jewel.blue() > robot.jewel.red()) {
                    robot.setAGenePower(0.3);
                    sleep(1000);
                    robot.stop();
                    robot.jongChulPark.setPosition(Servo.MIN_POSITION);
                    sleep(500);
                    robot.setBanzaiPower(0.5);
                    sleep(450);
                    robot.stop();
                } else if (robot.jewel.red() > robot.jewel.blue()) {
                    robot.setBanzaiPower(0.3);
                    sleep(350);
                    robot.stop();
                    robot.jongChulPark.setPosition(Servo.MIN_POSITION);
                    sleep(500);
                    robot.setAGenePower(0.5);
                    sleep(1600);
                    robot.stop();
                    robot.setBanzaiPower(0.3);
                    sleep(475);
                    robot.stop();
                } else {
                    robot.jongChulPark.setPosition(Servo.MIN_POSITION);
                    robot.setAGenePower(0.3);
                    sleep(1000);
                    robot.stop();
                    sleep(500);
                    robot.setBanzaiPower(0.5);
                    sleep(450);
                    robot.stop();
                }
            }

            sleep(1200);

            if (isEasy && isRed) {
                robot.setBanzaiPower(0.2);
                sleep(650);
                robot.stop();
            } else if (isEasy && !isRed) {
                robot.setAGenePower(0.2);
                sleep(650);
                robot.stop();
            } else if (!isEasy && isRed) {
                robot.setBanzaiPower(0.2);
                sleep(500);
                robot.stop();

                robot.turnLeft();
                sleep(ninety);
                robot.stop();

                robot.setBanzaiPower(0.2);
                sleep(300);
                robot.stop();
            } else {
                robot.setAGenePower(0.2);
                sleep(500);
                robot.stop();

                robot.turnLeft();
                sleep(ninety);
                robot.stop();

                robot.setBanzaiPower(0.2);
                sleep(300);
                robot.stop();
            }

            if (vuMarkStatus == 1 && isRed) {
                String text2 = vuMarkStatus + ".";
                android.util.Log.i("Firebat", text2);
                robot.setBanzaiPower(0.2);
                sleep(900);
                robot.stop();

                sleep(500);

                robot.turnRight();
                sleep(ninety);
                robot.stop();
            } else if (vuMarkStatus == 2 && isRed) {
                String text2 = vuMarkStatus + ".";
                android.util.Log.i("Medic", text2);
                robot.setBanzaiPower(0.2);
                sleep(450);
                robot.stop();

                sleep(500);

                robot.turnRight();
                sleep(ninety);
                robot.stop();
            } else if (vuMarkStatus == 3 && isRed) {
                String text2 = vuMarkStatus + ".";
                android.util.Log.i("Barrack", text2);

                sleep(500);

                robot.turnRight();
                sleep(ninety);
                robot.stop();

                String text1234 = gyrosensor.getHeading() + ".";
                android.util.Log.i("Weon", text1234);
                robot.stop();
            } else if (vuMarkStatus == 1 && !isRed) {
                String text2 = vuMarkStatus + ".";
                android.util.Log.i("Firebat", text2);

                sleep(500);
            } else if (vuMarkStatus == 2 && !isRed) {
                String text2 = vuMarkStatus + ".";
                android.util.Log.i("Medic", text2);
                robot.setBanzaiPower(0.2);
                sleep(450);
                robot.stop();

                sleep(500);
            } else if (vuMarkStatus == 3 && !isRed) {
                String text2 = vuMarkStatus + ".";
                android.util.Log.i("Barrack", text2);
                robot.setBanzaiPower(0.2);
                sleep(900);
                robot.stop();

                sleep(500);

                String text1234 = gyrosensor.getHeading() + ".";
                android.util.Log.i("Weon", text1234);
                robot.stop();
            } else if (!isRed) {
                robot.setBanzaiPower(0.2);
                sleep(900);
                robot.stop();

                sleep(500);
            } else {
                sleep(500);

                robot.turnRight();
                sleep(ninety);
                robot.stop();
            }
            if (!isRed) {
                if (isEasy) {
                    robot.turnRight();
                    sleep(ninety);
                    robot.stop();
                } else {
                    robot.turnLeft();
                    sleep(ninety);
                    robot.stop();
                }
            }

            robot.setBanzaiPower(0.2);
            sleep(950);
            robot.stop();

            AutoVomit();

            robot.liftEatBean.setPower(1);
            robot.liftEat.setPower(1);
            sleep(200);
            robot.liftEatBean.setPower(0);
            robot.liftEat.setPower(0);

            if (isChallenge && isEasy) {
                robot.setAGenePower(0.2);
                sleep(200);
                robot.stop();
                //gyrosensor.calibrate();
                //gyrosensor.resetZAxisIntegrator();
                /*
                while (heading > 81 || heading < 79) {
                    if (heading < 90 && heading > 70) {
                        robot.setTurnLeft(0.1);
                    }
                    else {
                        robot.setTurnLeft(basestart);
                    }
                    heading = gyrosensor.getHeading();
                }
                */
                robot.turnRight();
                sleep(1100);
                robot.stop();
                //move foward
                robot.setBanzaiPower(0.2);
                sleep(1550);
                robot.stop();
                //start eating
                AutoEat();
                //turn back to 90 degrees
                //gyrosensor.calibrate();
                //gyrosensor.resetZAxisIntegrator();
                /*
                while (heading > 291 || heading < 289) {
                    if (heading < 300 && heading > 280) {
                        robot.setTurnRight(0.1);
                    }
                    else {
                        robot.setTurnRight(basestart);
                    }
                    heading = gyrosensor.getHeading();
                }
                */
                robot.turnRight();
                sleep(2 * ninety);
                robot.stop();

                robot.setAGenePower(0.2);
                sleep(400);
                robot.stop();

                robot.turnLeft();
                sleep(ninety);
                robot.stop();

                robot.setBanzaiPower(0.2);
                sleep(450);
                robot.stop();

                robot.turnLeft();
                sleep(ninety);
                robot.stop();

                robot.setBanzaiPower(0.2);
                sleep(1250);
                robot.stop();

                //spit out two
                AutoVomit();
            }
            break;
        }
    }
    public void AutoVomit() {
        robot.vomit();
        robot.setAGenePower(0.1);
        sleep(1000);
        robot.stop();

        robot.setBanzaiPower(0.2);
        sleep(1000);
        robot.stop();

        robot.setAGenePower(0.1);
        sleep(500);
        robot.full();
        robot.stop();
    }
    public void AutoEat() {
        robot.eat();
        robot.setBanzaiPower(0.1);
        sleep(1000);
        robot.stop();

        robot.setAGenePower(0.2);
        sleep(750);
        robot.stop();

        robot.setBanzaiPower(0.1);
        sleep(1000);
        robot.full();
        robot.stop();
    }
}
