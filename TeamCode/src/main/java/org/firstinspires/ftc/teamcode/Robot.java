package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Nathan Huh on 2018-01-01.
 */

@TeleOp(name = "Robot", group = "OpMode")
public class Robot {
    DcMotor frontLeft;
    DcMotor backLeft;
    DcMotor frontRight;
    DcMotor backRight;
    DcMotor eatLeft;
    DcMotor eatRight;
    DcMotor liftEat;
    DcMotor liftEatBean;
    CRServo liftRelic;
    CRServo reLift;
    CRServo up;
    Servo lift;
    Servo grip;
    Servo jongChulPark;
    ColorSensor jewel;
    public double eatingPower;
    public double banzaiPower;
    public double berniePower;
    public double trumpPower;
    public Robot(HardwareMap hm) {
        frontLeft  = hm.get(DcMotor.class, "left");
        backLeft = hm.get(DcMotor.class, "backLeft");
        frontRight = hm.get(DcMotor.class, "right");
        backRight = hm.get(DcMotor.class, "backRight");
        eatLeft = hm.get(DcMotor.class, "eat_Left");
        eatRight = hm.get(DcMotor.class, "eat_Right");
        liftEat = hm.get(DcMotor.class, "lift_Eat");
        liftEatBean = hm.get(DcMotor.class, "lift_Eat_Bean");
        liftRelic = hm.get(CRServo.class, "lift_Relic");
        reLift = hm.get(CRServo.class, "reLift");
        up = hm.get(CRServo.class, "up");
        jewel = hm.get(ColorSensor.class,"jewel");
        lift = hm.get(Servo.class, "lift");
        grip = hm.get(Servo.class, "grip");
        jongChulPark = hm.get(Servo.class,"jewel_Servo");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        eatLeft.setDirection(DcMotor.Direction.REVERSE);
        eatRight.setDirection(DcMotor.Direction.REVERSE);
        liftEat.setDirection(DcMotor.Direction.FORWARD);
        liftEatBean.setDirection(DcMotor.Direction.REVERSE);
        up.setDirection(CRServo.Direction.FORWARD);

        eatingPower = 0.5;
        trumpPower = 0.4;
        banzaiPower = 0.4;
        berniePower = 0.4;
    }
    public void turnLeft() {
        frontRight.setPower(0.6);
        backRight.setPower(0.6);
        frontLeft.setPower(-0.6);
        backLeft.setPower(-0.6);
    }
    public void setTurnLeft(double power) {
        frontRight.setPower(power);
        backRight.setPower(power);
        frontLeft.setPower(-power);
        backLeft.setPower(-power);
    }
    public void turnRight() {
        frontRight.setPower(-0.6);
        backRight.setPower(-0.6);
        frontLeft.setPower(0.6);
        backLeft.setPower(0.6);
    }
    public void setTurnRight(double power) {
        frontRight.setPower(-power);
        backRight.setPower(-power);
        frontLeft.setPower(power);
        backLeft.setPower(power);
    }

    public void eat() {
        eatLeft.setPower(-eatingPower);
        eatRight.setPower(eatingPower);
    }
    public void full() {
        eatLeft.setPower(0);
        eatRight.setPower(0);
    }
    public void vomit() {
        eatLeft.setPower(eatingPower);
        eatRight.setPower(-eatingPower);
    }
    public void banzai() {
        frontRight.setPower(banzaiPower);
        backRight.setPower(banzaiPower);
        frontLeft.setPower(banzaiPower);
        backLeft.setPower(banzaiPower);
    }
    public void setBanzaiPower(double power) {
        frontRight.setPower(power);
        backRight.setPower(power);
        frontLeft.setPower(power);
        backLeft.setPower(power);
    }
    public void stop() {
        frontRight.setPower(0);
        backRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
    }
    public void aGene() {
        frontRight.setPower(-1);
        backRight.setPower(-1);
        frontLeft.setPower(-1);
        backLeft.setPower(-1);
    }
    public void setAGenePower(double power) {
        frontLeft.setPower(-power);
        backLeft.setPower(-power);
        frontRight.setPower(-power);
        backRight.setPower(-power);
    }
    public void setEatingPower(double power) {
        eatingPower = power;
    }
}
