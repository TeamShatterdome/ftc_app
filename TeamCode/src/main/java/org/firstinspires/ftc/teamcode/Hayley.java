package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Nathan Huh on 2018-01-01.
 */
@TeleOp(name = "Hayley", group = "Kuk")
public class Hayley extends OpMode {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor eatLeft;
    private DcMotor eatRight;
    public double eatingPower;
    @Override
    public void init() {
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
        eatLeft.setDirection(DcMotor.Direction.FORWARD);
        eatRight.setDirection(DcMotor.Direction.FORWARD);

        eatingPower = 0.7;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        double vD = Math.sqrt(Math.pow(gamepad1.left_stick_x, 2) +
                Math.pow(gamepad1.left_stick_y, 2));
        double thetaD = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x);
        double vTheta = gamepad1.right_stick_x;
        go(vD, thetaD, vTheta);

        eating();
    }

    @Override
    public void stop() {
        super.stop();
    }

    public void eating() {
        if (gamepad1.a) {
            eat();
        }
    }
    public void eat() {
        eatLeft.setPower(eatingPower);
        eatRight.setPower(-eatingPower);
    }
    public void setEatingPower(double power) {
        eatingPower = power;
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
            motorPower = vD * Math.sin(thetaD + Math.PI / 4) + vTheta;
        } else if (motorType == 2) {
            motorPower  = vD * Math.cos(thetaD + Math.PI / 4) - vTheta;
        } else if (motorType == 3) {
            motorPower = vD * Math.cos(thetaD + Math.PI / 4) + vTheta;
        } else if (motorType == 4) {
            motorPower = vD * Math.sin(thetaD + Math.PI / 4) - vTheta;
        }
        return  motorPower;
    }
    public void go(double velocity, double angle, double turnVelocity) {
        double frontLeftPower = oscarMike(velocity, angle, turnVelocity, 1);
        double frontRightPower = oscarMike(velocity, angle, turnVelocity, 2);
        double backLeftPower = oscarMike(velocity, angle, turnVelocity, 3);
        double backRightPower = oscarMike(velocity, angle, turnVelocity, 4);
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);
    }
}