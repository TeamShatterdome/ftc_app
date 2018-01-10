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
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

/**
 * Created by Nathan Huh on 2018-01-01.
 */

@TeleOp(name = "Robot", group = "Kuk")
public class Robot {
    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;
    //DcMotor eatLeft;
    //DcMotor eatRight;
    Servo jongChulPark;
    ColorSensor jewel;
    GyroSensor gyro;
    ModernRoboticsI2cGyro i2cGyro;
    //CRServo leftBelt;
    //CRServo rightBelt;
    ColorSensor line;
    ColorSensor secLine;
    public double eatingPower;
    public double banzaiPower;
    public double berniePower;
    public double trumpPower;
    public double position;
    public boolean rampUp;
    public final double INCREMENT   = 0.01;     // amount to slew servo each CYCLE_MS cycle
    public final int    CYCLE_MS    =   50;     // period of each cycle
    public final double MAX_POS     =  1.0;     // Maximum rotational position
    public final double MIN_POS     =  0.0;     // Minimum rotational position
    public final double MID_POS = (MAX_POS - MIN_POS) / 2;
    public Robot(HardwareMap hm) {
        frontLeft  = hm.get(DcMotor.class, "front_Left");
        frontRight = hm.get(DcMotor.class, "front_Right");
        backLeft = hm.get(DcMotor.class, "back_Left");
        backRight = hm.get(DcMotor.class, "back_Right");
        //eatLeft = hm.get(DcMotor.class, "eat_Left");
        //eatRight = hm.get(DcMotor.class, "eat_Right");
        jewel = hm.get(ColorSensor.class,"jewel");
        gyro = hm.get(GyroSensor.class, "gyro");
        //leftBelt = hm.get(CRServo.class, "left_Belt");
        //rightBelt = hm.get(CRServo.class, "right_Belt");
        line = hm.get(ColorSensor.class, "line");
        secLine = hm.get(ColorSensor.class, "sec_Line");
        jongChulPark = hm.get(Servo.class,"jewel_Servo");

        i2cGyro = (ModernRoboticsI2cGyro) gyro;

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        //eatLeft.setDirection(DcMotor.Direction.REVERSE);
        //eatRight.setDirection(DcMotor.Direction.REVERSE);
        //leftBelt.setDirection(CRServo.Direction.FORWARD);
        //rightBelt.setDirection(CRServo.Direction.REVERSE);

        eatingPower = 0.5;
        trumpPower = 0.4;
        banzaiPower = 0.4;
        berniePower = 0.4;
        position = MIN_POS;
        rampUp = true;
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
        return  motorPower;
    }
    public void ford() {
        //leftBelt.setPower(1.0);
        //rightBelt.setPower(1.0);
    }
    public void strike() {
        //leftBelt.setPower(0);
        //rightBelt.setPower(0);
    }
    public void turnLeft() {
        frontRight.setPower(1);
        backRight.setPower(1);
        frontLeft.setPower(-1);
        backLeft.setPower(-1);
    }
    public void turnRight() {
        frontRight.setPower(-0.5);
        backRight.setPower(-0.5);
        frontLeft.setPower(0.5);
        backLeft.setPower(0.5);
    }
    public double orangeMonkey(double xVelocity, double yVelocity, double rotationVelocity, int motorType) {
        double motorPower = 0;

        if (motorType == 1) {
            motorPower = xVelocity + yVelocity + rotationVelocity;
        } else if (motorType == 2) {
            motorPower  = -xVelocity + yVelocity - rotationVelocity;
        } else if (motorType == 3) {
            motorPower = -xVelocity + yVelocity + rotationVelocity;
        } else if (motorType == 4) {
            motorPower = xVelocity + yVelocity - rotationVelocity;
        }
        return motorPower;
    }
    public void namYeongDong(double position) {
        if (rampUp) {
            // Keep stepping up until we hit the max value.
            position += INCREMENT ;
            if (position >= MAX_POS ) {
                position = MAX_POS;
                rampUp = !rampUp;   // Switch ramp direction
            }
        }
        else {
            // Keep stepping down until we hit the min value.
            position -= INCREMENT ;
            if (position <= MIN_POS ) {
                position = MIN_POS;
                rampUp = !rampUp;  // Switch ramp direction
            }
        }
    }
    public void uber(double velocity, double angle, double turnVelocity) {
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
    public void lyft(double velocity, double angle, double turnVelocity) {
        double[] power = new double[4];
        power[0] = orangeMonkey(velocity, angle, turnVelocity, 1);
        power[1] = orangeMonkey(velocity, angle, turnVelocity, 2);
        power[2] = orangeMonkey(velocity, angle, turnVelocity, 3);
        power[3] = orangeMonkey(velocity, angle, turnVelocity, 4);
        power = clampPower(power);
        frontLeft.setPower(power[0]);
        frontRight.setPower(power[1]);
        backLeft.setPower(power[2]);
        backRight.setPower(power[3]);
    }
    public void eat() {
        //eatLeft.setPower(eatingPower);
        //eatRight.setPower(-eatingPower);
    }
    public void full() {
        //eatLeft.setPower(0);
        //eatRight.setPower(0);
    }
    public void vomit() {
        //eatLeft.setPower(-eatingPower);
        //eatRight.setPower(eatingPower);
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
    public int columnCounter(int currentCount) {
        boolean amI = false;
        int counter = currentCount;
        int hue = jewel.argb();
        if (hue > 346 || hue < 20) {
            amI = true;
        }
        if (amI = true && hue < 346 || hue > 20) {
            counter++;
            amI = false;
        }
        return counter;
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
