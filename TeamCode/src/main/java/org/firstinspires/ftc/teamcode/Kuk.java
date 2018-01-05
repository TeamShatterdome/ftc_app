package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;


/**
 * Created by Nathan Huh on 2017-12-14.
 */
@Autonomous(name = "Kuk", group = "Kuk")
public class Kuk extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor eatLeft;
    private DcMotor eatRight;
    public double eatingPower;
    private RelicRecoveryVuMark vuMark;
    private int vuMarkStatus;
    public void runOpMode() throws InterruptedException {
        frontLeft  = hardwareMap.get(DcMotor.class, "front_Left");
        frontRight = hardwareMap.get(DcMotor.class, "front_Right");
        backLeft = hardwareMap.get(DcMotor.class, "back_Left");
        backRight = hardwareMap.get(DcMotor.class, "back_Right");
        eatLeft = hardwareMap.get(DcMotor.class, "eat_Left");
        eatRight = hardwareMap.get(DcMotor.class, "eat_Right");

        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        eatLeft.setDirection(DcMotor.Direction.REVERSE);
        eatRight.setDirection(DcMotor.Direction.REVERSE);

        eatingPower = 0.5;

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "ARS7vfb/////AAAAGWKsj4OtdExwhKE/OTOJSzR9++AxPiyaq7P3W+iDO4C5Buh7Ojqj942pkRRFn9XIK6i" +
                "mGU1yv/SM+/dbkQSqfHNen+Kv/DZgRD4UN3BAHcrwULhfFOxyGrjgzzeF9As1aUsC0rREhsOPzc5lTpG2RHimo6JZF/o8YGgEA5JYKzt7AI5mzSdKDpng7p2u54gAEDjvKI/K7lJ71Q+2j2BXGHs" +
                "IBpYa1LozJUploM16NHtsx2zLwrjh3WLIEGYw22JWcsxic6l8XN0PNjf4KNo+nlhVuHhfgDo/aI7sLx7YZXak5v4b2GwPD7f7AxXLZEonYoI5Wpb7sI76d4jd/mqMjeB1h/Rc1jpGobDeAPWVDgqq";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");
        telemetry.addData(">", "Press Play to start");
        telemetry.update();

        waitForStart();
        relicTrackables.activate();
        runtime.reset();
        while (opModeIsActive() && runtime.seconds() < 1) {
            vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                telemetry.addData("VuMark", "%s visible", vuMark);
                switch (vuMark) {
                    case UNKNOWN:
                        vuMarkStatus = 0;
                        break;
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
            }
            else {
                telemetry.addData("VuMark", "not visible");
            }
            telemetry.update();
            idle();
        }
        runtime.reset();
        while (opModeIsActive() && runtime.seconds() < 3) {
            if (vuMarkStatus == 0) {
                go(1, 0, 0);
            } else if (vuMarkStatus == 1) {
                go(1, Math.PI/2, 0);
            } else if (vuMarkStatus == 2) {
                go(1, Math.PI, 0);
            } else if (vuMarkStatus == 3) {
                go(1, 3 * (Math.PI)/2, 0 );
            }
            idle();
        }
        runtime.reset();
        while (opModeIsActive() && runtime.seconds() < 3) {
            eat();
            idle();
        }
    }

    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
    public double oscarMike(double vD, double thetaD, double vTheta, int motorType) {
        double motorPower = 0;
        /*
         1 = frontLeft
         2 = frontRight
         3 = backLeft
         4 = backRight
        */
        if (motorType == 1) {
            motorPower = vD * Math.sin(thetaD + (Math.PI / 4)) + vTheta;
        } else if (motorType == 2) {
            motorPower  = vD * Math.cos(thetaD + (Math.PI / 4)) - vTheta;
        } else if (motorType == 3) {
            motorPower = vD * Math.cos(thetaD + (Math.PI / 4)) + vTheta;
        } else if (motorType == 4) {
            motorPower = vD * Math.sin(thetaD + (Math.PI / 4)) - vTheta;
        }
        motorPower = motorPower / 2;
        return  motorPower;
    }
    public void go(double velocity, double angle, double turnVelocity) {
        double[] power = new double[4];
        power[0] = oscarMike(velocity, angle, turnVelocity, 1);
        power[1] = oscarMike(velocity, angle, turnVelocity, 2);
        power[2] = oscarMike(velocity, angle, turnVelocity, 3);
        power[3] = oscarMike(velocity, angle, turnVelocity, 4);
        power = clampPower(power);
        frontLeft.setPower(power[0]);
        frontRight.setPower(power[1]);
        backLeft.setPower(power[2]);
        backRight.setPower(power[3]);
    }
    public void eat() {
        eatLeft.setPower(eatingPower);
        eatRight.setPower(-eatingPower);
    }
    public void setEatingPower(double power) {
        eatingPower = power;
    }
    public double[] clampPower(double[] power) {
        double maximum = power[0];
        double minimum = power[0];
        double maxMag = 0;
        for (int i = 0; i < power.length; i++) {
            if (power[i] > maximum) {
                maximum = power[i];
            }
            if (power[i] < minimum) {
                minimum = power[i];
            }
        }
        maxMag = Math.max(Math.abs(minimum), Math.abs(maximum));
        if (maxMag > 1.0) {
            for (int i = 0; i < power.length; i++) {
                power[i] = power[i] / maxMag;
            }
        }
        return power;
    }
}


