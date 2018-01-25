package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="Coffee", group="OpMode")
public class Coffee extends OpMode {
    Robot robot;
    GyroSensor gyro;
    ModernRoboticsI2cGyro gyrosensor;

    public ElapsedTime time= new ElapsedTime();

    @Override
    public void init(){
        robot = new Robot(hardwareMap);
        gyro = hardwareMap.get(ModernRoboticsI2cGyro.class, "gyro");

        robot.jongChulPark.setPosition(Servo.MIN_POSITION);
        gyrosensor = ((ModernRoboticsI2cGyro) gyro);

        gyro.calibrate();
        //robot.grip.setPosition(1);
        robot.lift.setPosition(0);
    }
    public void mechanum(){
        if (gamepad1.right_bumper) {
            robot.frontLeft.setPower((-0.4) * gamepad1.left_stick_y);
            robot.backLeft.setPower((-0.4) * gamepad1.left_stick_y);
            robot.frontRight.setPower((-0.4) * gamepad1.right_stick_y);
            robot.backRight.setPower((-0.4) * gamepad1.right_stick_y);
        } else if(gamepad1.left_bumper){
            robot.frontLeft.setPower((-0.2) * gamepad1.left_stick_y);
            robot.backLeft.setPower((-0.2) * gamepad1.left_stick_y);
            robot.frontRight.setPower((-0.2) * gamepad1.right_stick_y);
            robot.backRight.setPower((-0.2) * gamepad1.right_stick_y);
        } else {
            robot.frontLeft.setPower((-1) * gamepad1.left_stick_y);
            robot.backLeft.setPower((-1) * gamepad1.left_stick_y);
            robot.frontRight.setPower((-1) * gamepad1.right_stick_y);
            robot.backRight.setPower((-1) * gamepad1.right_stick_y);

            if(gamepad1.a) {
                robot.frontLeft.setPower((-1) * gamepad1.left_stick_y);
                robot.backLeft.setPower((-1) * gamepad1.left_stick_y);
                robot.frontRight.setPower((-1) * gamepad1.right_stick_y);
                robot.backRight.setPower((-1) * gamepad1.right_stick_y);
            }
            else if(gamepad1.b){
                robot.frontLeft.setPower(gamepad1.left_stick_y);
                robot.backLeft.setPower(gamepad1.left_stick_y);
                robot.frontRight.setPower(gamepad1.right_stick_y);
                robot.backRight.setPower(gamepad1.right_stick_y);
            }
        }
        while(gamepad1.right_trigger >= 0.9){
            gyrosensor.resetZAxisIntegrator();
            while(gyrosensor.getHeading() != 270){
                robot.turnRight();
                break;
            }
            break;
        }
        while(gamepad2.left_trigger >= 0.9){
            gyrosensor.resetZAxisIntegrator();
            while(gyrosensor.getHeading() != 90){
                robot.turnLeft();
                break;
            }
            break;
        }
    }

    public void eating() {
        if (gamepad2.y) {
            robot.eatLeft.setPower(0.2);
            robot.eatRight.setPower(-0.2);
        }
        else if (gamepad2.x) {
            robot.eatLeft.setPower(-1);
            robot.eatRight.setPower(1);
        }
        else if (gamepad2.right_trigger > 0) {
            robot.eatLeft.setPower(0.0);
            robot.eatRight.setPower(0.0);
        }
        if (gamepad1.a) {
            robot.jongChulPark.setPosition(0.666);
        }
        else if(gamepad1.b) {
            robot.jongChulPark.setPosition(Servo.MIN_POSITION);
        }
    }
    public void lifting(){
        if (gamepad2.left_stick_y >= 0.8) {
            robot.liftRelic.setPower(1);
        }
        else if (gamepad2.left_stick_y <= -0.8) {
            robot.liftRelic.setPower(-1);
        }
        else {
            robot.liftRelic.setPower(0.01);
        }
        if (gamepad2.right_stick_y >= 0.8 || gamepad2.right_stick_y <= -0.8){
            robot.liftEat.setPower(gamepad2.right_stick_y);
            robot.liftEatBean.setPower(gamepad2.right_stick_y);
        } else{
            robot.liftEat.setPower(0);
            robot.liftEatBean.setPower(0);
        }
    }

    public void relic() {
        if (gamepad2.start){
            robot.lift.setPosition(0.4);
        }
        if (gamepad2.left_bumper) {
            robot.grip.setPosition(0.3);
            robot.lift.setPosition(0.4);
        }
        else if (gamepad2.right_bumper) {
            robot.grip.setPosition(0.3);
            robot.lift.setPosition(1);
        }
        else if(gamepad2.left_trigger >= 0.9){
            robot.lift.setPosition(0);
        }
        else {
            robot.grip.setPosition(1);
        }

        while (gamepad2.b){
            time.reset();
            time.startTime();

            while(time.time() < 1.1){
                robot.grip.setPosition(0.3);
                robot.lift.setPosition(0.5);
            }
            while(time.time() > 1 && time.time() < 1.3){
                robot.grip.setPosition(1);
                robot.lift.setPosition(0.5);
                robot.setAGenePower(0.1);
            }
            while(time.time() > 1.2 && time.time() < 1.7){
                robot.setBanzaiPower(0.1);
            }
            while(time.time() > 1.6 && time.time() < 2){
                robot.grip.setPosition(1);
                robot.lift.setPosition(0);
            }
            break;
        }
    }

    public void loop(){
        mechanum();
        eating();
        lifting();
        relic();
    }
    public void stop(){
    }
}