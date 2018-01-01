package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Nathan Huh on 2018-01-01.
 */

public class Robot {
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor eatLeft;
    private DcMotor eatRight;
    public double eatingPower;
    public Robot(HardwareMap hm) {
        frontLeft  = hm.get(DcMotor.class, "front_Left");
        frontRight = hm.get(DcMotor.class, "front_Right");
        backLeft = hm.get(DcMotor.class, "back_Left");
        backRight = hm.get(DcMotor.class, "back_Right");
        eatLeft = hm.get(DcMotor.class, "eat_Left");
        eatRight = hm.get(DcMotor.class, "eat_Right");

        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        eatLeft.setDirection(DcMotor.Direction.FORWARD);
        eatRight.setDirection(DcMotor.Direction.FORWARD);

        eatingPower = 1;
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
    public void eat() {
        eatLeft.setPower(eatingPower);
        eatRight.setPower(eatingPower);
    }
    public void setEatingPower(double power) {
        eatingPower = power;
    }
}
