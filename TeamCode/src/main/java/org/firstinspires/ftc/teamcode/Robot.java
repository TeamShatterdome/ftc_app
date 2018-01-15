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
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    DcMotor eatLeft;
    DcMotor eatRight;
    DcMotor liftEat;
    DcMotor liftEatBean;
    CRServo liftRelic;
    CRServo reLift;
    Servo lift;
    Servo grip;
    Servo jongChulPark;
    ColorSensor jewel;
    GyroSensor gyro;
    ModernRoboticsI2cGyro i2cGyro;
    public double eatingPower;
    public double banzaiPower;
    public double berniePower;
    public double trumpPower;
    public Robot(HardwareMap hm) {
        frontLeft  = hm.get(DcMotor.class, "front_Left");
        frontRight = hm.get(DcMotor.class, "front_Right");
        backLeft = hm.get(DcMotor.class, "back_Left");
        backRight = hm.get(DcMotor.class, "back_Right");
        eatLeft = hm.get(DcMotor.class, "eat_Left");
        eatRight = hm.get(DcMotor.class, "eat_Right");
        liftEat = hm.get(DcMotor.class, "lift_Eat");
        liftEatBean = hm.get(DcMotor.class, "lift_Eat_Bean");
        liftRelic = hm.get(CRServo.class, "lift_Relic");
        reLift = hm.get(CRServo.class, "reLift");
        jewel = hm.get(ColorSensor.class,"jewel");
        gyro = hm.get(GyroSensor.class, "gyro");
        lift = hm.get(Servo.class, "lift");
        grip = hm.get(Servo.class, "grip");
        jongChulPark = hm.get(Servo.class,"jewel_Servo");
        i2cGyro = (ModernRoboticsI2cGyro) gyro;

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        eatLeft.setDirection(DcMotor.Direction.REVERSE);
        eatRight.setDirection(DcMotor.Direction.REVERSE);
        liftEat.setDirection(DcMotor.Direction.FORWARD);
        liftEatBean.setDirection(DcMotor.Direction.REVERSE);

        eatingPower = 0.5;
        trumpPower = 0.4;
        banzaiPower = 0.4;
        berniePower = 0.4;
    }
    public void turnLeft() {
        frontRight.setPower(1);
        backRight.setPower(1);
        frontLeft.setPower(-1);
        backLeft.setPower(-1);
    }
    public void turnRight() {
        frontRight.setPower(-1);
        backRight.setPower(-1);
        frontLeft.setPower(1);
        backLeft.setPower(1);
    }

    public void eat() {
        eatLeft.setPower(eatingPower);
        eatRight.setPower(-eatingPower);
    }
    public void full() {
        eatLeft.setPower(0);
        eatRight.setPower(0);
    }
    public void vomit() {
        eatLeft.setPower(-eatingPower);
        eatRight.setPower(eatingPower);
    }
    public void banzai() {
        frontRight.setPower(banzaiPower);
        frontLeft.setPower(banzaiPower);
        backRight.setPower(banzaiPower);
        backLeft.setPower(banzaiPower);
    }
    public void setBanzaiPower(double power) {
        frontRight.setPower(power);
        frontLeft.setPower(power);
        backRight.setPower(power);
        backLeft.setPower(power);
    }
    public void trump() {
        frontRight.setPower(-trumpPower * 0.85 );
        backLeft.setPower(-trumpPower * 0.85);
        frontLeft.setPower(trumpPower * 0.8);
        backRight.setPower(trumpPower);
    }
    public void setTrumpPower(double power) {
        this.trumpPower = power;
    }
    public void bernie() {
        frontRight.setPower(berniePower);
        backLeft.setPower(berniePower);
        frontLeft.setPower(-berniePower);
        backRight.setPower(-berniePower);
    }
    public void setBerniePower(double power) {
        this.berniePower = power;
    }
    public void stop() {
        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
    }
    public void aGene() {
        frontRight.setPower(-1);
        frontLeft.setPower(-1);
        backRight.setPower(-1);
        backLeft.setPower(-1);
    }
    public void setAGenePower(double power) {
        frontLeft.setPower(-power);
        frontRight.setPower(-power);
        backRight.setPower(-power);
        backLeft.setPower(-power);
    }
    public void setEatingPower(double power) {
        eatingPower = power;
    }
}
