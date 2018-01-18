package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by ASUS-UX330UA on 1/9/2018.
 */
@Autonomous(name="auto_timed", group="Linear Opmode")
public class auto_timed extends LinearOpMode {
    DcMotor leftfront;
    DcMotor rightfront;
    DcMotor leftback;
    DcMotor rightback;
    DcMotor righteating;
    DcMotor lefteating;
    DcMotor rightlift;
    DcMotor leftlift;
    GyroSensor gyro;
    ModernRoboticsI2cGyro gyrosensor;
    double basestart = 0.2;
    double basepower=0.4;
    public ElapsedTime time = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        leftfront = hardwareMap.dcMotor.get("leftfront");
        leftfront.setDirection(DcMotor.Direction.REVERSE);
        rightfront = hardwareMap.dcMotor.get("rightfront");
        rightfront.setDirection(DcMotor.Direction.FORWARD);
        leftback = hardwareMap.dcMotor.get("leftback");
        leftback.setDirection(DcMotor.Direction.REVERSE);
        rightback = hardwareMap.dcMotor.get("rightback");
        rightback.setDirection(DcMotor.Direction.FORWARD);
        righteating = hardwareMap.dcMotor.get("righteating");
        righteating.setDirection(DcMotor.Direction.FORWARD);
        lefteating = hardwareMap.dcMotor.get("lefteating");
        lefteating.setDirection((DcMotor.Direction.REVERSE));
        rightlift = hardwareMap.dcMotor.get("rightlift");
        rightlift.setDirection(DcMotor.Direction.FORWARD);
        leftlift = hardwareMap.dcMotor.get("leftlift");
        leftlift.setDirection(DcMotor.Direction.REVERSE);

        gyro = hardwareMap.gyroSensor.get("gyro");
        gyrosensor = (ModernRoboticsI2cGyro) gyro;
        gyro.calibrate();

        waitForStart();

        while (gyro.isCalibrating()) {
        }

        while (opModeIsActive()) {
            time.reset();
            time.startTime();
            telemetry.addData("time", time.time());
            telemetry.addData("gyro", gyrosensor.getHeading());
            telemetry.update();

            leftlift.setPower(0.4);
            rightlift.setPower(0.4);
            sleep(250);
            leftlift.setPower(0);
            rightlift.setPower(0);
            sleep(200);
            //move foward

            leftback.setPower(basepower);
            leftfront.setPower(basepower);
            rightback.setPower(basepower);
            rightfront.setPower(basepower);
            sleep(1600);
            //turn 90 degrees right

            while (gyrosensor.getHeading() > 91 || gyrosensor.getHeading() < 89) {
                leftback.setPower(basestart);
                leftfront.setPower(basestart);
                rightback.setPower(-basestart);
                rightfront.setPower(-basestart);
            }
            leftback.setPower(0);
            leftfront.setPower(0);
            rightback.setPower(0);
            rightfront.setPower(0);
            sleep(1000);
            //spit out one block

            righteating.setPower(-1);
            lefteating.setPower(-1);
            sleep(2000);

            righteating.setPower(-1);
            lefteating.setPower(-1);
            leftback.setPower(0.6);
            leftfront.setPower(0.6);
            rightback.setPower(0.6);
            rightfront.setPower(0.6);
            sleep(500);
            righteating.setPower(0);
            lefteating.setPower(0);
            leftfront.setPower(-0.2);
            leftback.setPower(-0.2);
            rightfront.setPower(-0.2);
            rightback.setPower(-0.2);
            sleep(1000);
            leftback.setPower(0);
            leftfront.setPower(0);
            rightback.setPower(0);
            rightfront.setPower(0);
            sleep(200);
            //turn 180 degrees

            while (gyrosensor.getHeading() > 271 || gyrosensor.getHeading() < 269) {
                leftback.setPower(-basestart);
                leftfront.setPower(-basestart);
                rightback.setPower(basestart);
                rightfront.setPower(basestart);

            }
            //move foward
            leftback.setPower(basepower);
            leftfront.setPower(basepower);
            rightback.setPower(basepower);
            rightfront.setPower(basepower);
            sleep(1200);
            //eat two blocks
            leftback.setPower(0);
            leftfront.setPower(0);
            rightback.setPower(0);
            rightfront.setPower(0);
            leftlift.setPower(-0.4);
            rightlift.setPower(-0.4);
            sleep(250);
            leftlift.setPower(0);
            rightlift.setPower(0);
            //start eating
            righteating.setPower(0.7);
            lefteating.setPower(0.7);
            sleep(3000);

            leftlift.setPower(0.4);
            rightlift.setPower(0.4);
            righteating.setPower(0);
            lefteating.setPower(0);
            sleep(250);

            leftlift.setPower(0);
            rightlift.setPower(0);

            leftback.setPower(-basestart);
            leftfront.setPower(-basestart);
            rightback.setPower(-basestart);
            rightfront.setPower(-basestart);
            sleep(800);
            leftback.setPower(0);
            leftfront.setPower(0);
            rightback.setPower(0);
            rightfront.setPower(0);
            sleep(200);
            //turn back to 90 degrees
            while(gyrosensor.getHeading() > 81 || gyrosensor.getHeading() < 79){
                leftback.setPower(basestart);
                leftfront.setPower(basestart);
                rightback.setPower(-basestart);
                rightfront.setPower(-basestart);
            }
            leftback.setPower(basepower);
            leftfront.setPower(basepower);
            rightback.setPower(basepower);
            rightfront.setPower(basepower);
            sleep(1400);
            leftback.setPower(0);
            leftfront.setPower(0);
            rightback.setPower(0);
            rightfront.setPower(0);
            sleep(200);
            //spit out two
            righteating.setPower(-0.7);
            lefteating.setPower(-0.7);
            sleep(1000);

            righteating.setPower(-0.7);
            lefteating.setPower(-0.7);
            rightback.setPower(0.5);
            rightfront.setPower(0.5);
            leftback.setPower(0.5);
            leftfront.setPower(0.5);
            sleep(1000);

            righteating.setPower(0);
            lefteating.setPower(0);
            rightback.setPower(0);
            rightfront.setPower(0);
            leftback.setPower(0);
            leftfront.setPower(0);
            sleep(100);

            break;
        }
    }
}