package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Nathan Huh on 2017-12-14.
 */
@Autonomous(name = "GLK", group = "Kuk")
public class Kuk extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    public void runOpMode() throws InterruptedException {
        Robot robot = new Robot(hardwareMap);

        waitForStart();
        runtime.reset();
        while (opModeIsActive()) {

        }
    }
}

