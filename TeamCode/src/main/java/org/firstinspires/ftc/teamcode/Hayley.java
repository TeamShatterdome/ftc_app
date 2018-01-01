package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by Nathan Huh on 2018-01-01.
 */

public class Hayley extends OpMode {
    Robot robot;
    @Override
    public void init() {
        robot = new Robot(hardwareMap);
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
        robot.go(vD, thetaD, vTheta);
    }

    @Override
    public void stop() {
        super.stop();
    }
}
