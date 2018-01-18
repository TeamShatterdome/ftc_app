package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Coffee", group="OpMode")
public class Coffee extends OpMode {
    Robot robot;

    @Override
    public void init(){
        robot = new Robot(hardwareMap);

        robot.jongChulPark.setPosition(Servo.MIN_POSITION);
        robot.grip.setPosition(1);
        robot.lift.setPosition(0.45);
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
        }
    }

    public void eating() {
        if (gamepad2.y) {
            robot.eatLeft.setPower(1);
            robot.eatRight.setPower(-1);
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
        if(gamepad2.right_stick_button){
            robot.up.setPower(1);
        }
        else if(gamepad2.left_stick_button){
            robot.up.setPower(-1);
        }
        else{
            robot.up.setPower(0);
        }
    }
    public void lifting(){
        if (gamepad2.left_stick_y >= 0.8) {
            robot.liftRelic.setPower(-1);
            robot.reLift.setPower(0.2);
        }
        else if (gamepad2.left_stick_y <= -0.8) {
            robot.liftRelic.setPower(1);
            robot.reLift.setPower(-0.2);
        }
        else {
            robot.liftRelic.setPower(0);
            robot.reLift.setPower(0);
        }
        if (gamepad2.right_stick_y >= 0.8 || gamepad2.right_stick_y <= -0.8){
            robot.liftEat.setPower(gamepad2.right_stick_y);
            robot.liftEatBean.setPower(gamepad2.right_stick_y);
        } else{
            robot.liftEat.setPower(0);
            robot.liftEatBean.setPower(0);
        }
    }

    public void relic(){
        if (gamepad2.left_bumper) {
            robot.grip.setPosition(0.3);
            robot.lift.setPosition(0.45);
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
            robot.lift.setPosition(0.45);
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