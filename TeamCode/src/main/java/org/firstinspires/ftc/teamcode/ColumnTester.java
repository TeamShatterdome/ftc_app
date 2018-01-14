package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by Nathan Huh on 2018-01-06.
 */

@Autonomous(name = "CT", group = "LinearOpMode")
public class ColumnTester extends LinearOpMode {
    ColorSensor CS;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("앙 기모띠", "Press Play to start");
        telemetry.update();

        CS = hardwareMap.get(ColorSensor.class, "CS");
        CS.enableLed(false);
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("R: %d", CS.red());
            telemetry.addData("G: %d", CS.green());
            telemetry.addData("B: %d", CS.blue());
            telemetry.update();
        }
    }
}
