package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Nathan Huh on 2018-01-05.
 */

@Autonomous(name = "Flore", group = "Kuk")
public class Flore extends OpMode {
    Robot robot;
    private ElapsedTime runtime = new ElapsedTime();
    VuforiaLocalizer vuforia;
    public RelicRecoveryVuMark vuMark;
    public int vuMarkStatus;
    public VuforiaTrackables relicTrackables;
    public VuforiaTrackable relicTemplate;
    State state;
    int counter;
    @Override
    public void init() {
        robot = new Robot(hardwareMap);

        counter = 0;

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "ARS7vfb/////AAAAGWKsj4OtdExwhKE/OTOJSzR9++AxPiyaq7P3W+iDO4C5Buh7Ojqj942pkRRFn9XIK6i" +
                "mGU1yv/SM+/dbkQSqfHNen+Kv/DZgRD4UN3BAHcrwULhfFOxyGrjgzzeF9As1aUsC0rREhsOPzc5lTpG2RHimo6JZF/o8YGgEA5JYKzt7AI5mzSdKDpng7p2u54gAEDjvKI/K7lJ71Q+2j2BXGHs" +
                "IBpYa1LozJUploM16NHtsx2zLwrjh3WLIEGYw22JWcsxic6l8XN0PNjf4KNo+nlhVuHhfgDo/aI7sLx7YZXak5v4b2GwPD7f7AxXLZEonYoI5Wpb7sI76d4jd/mqMjeB1h/Rc1jpGobDeAPWVDgqq";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
    }

    @Override
    public void init_loop() {
        super.init_loop();
    }

    @Override
    public void start() {
        super.start();
        relicTrackables.activate();
        runtime.reset();
        state = State.STATE_INITIALIZATION;
    }

    @Override
    public void loop() {
        switch (state) {
            case STATE_INITIALIZATION:
                state = State.STATE_VUMARK;
                break;
            case STATE_VUMARK:
                vuMark = RelicRecoveryVuMark.from(relicTemplate);
                runtime.startTime();
                runtime.reset();
                while (runtime.time() < 3000) {
                    if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                        telemetry.addData("VuMark", "%s visible", vuMark);
                        switch (vuMark) {
                            case UNKNOWN:
                                vuMarkStatus = 0;
                                break;
                            case LEFT:
                                vuMarkStatus = 1;
                                break;
                            case CENTER:
                                vuMarkStatus = 2;
                                break;
                            case RIGHT:
                                vuMarkStatus = 3;
                                break;
                        }
                    } else {
                        telemetry.addData("VuMark", "not visible");
                    }
                }
                state = State.STATE_JEWEL;
                break;
            case STATE_JEWEL:
                runtime.reset();
                while (runtime.time() < 3000) {
                    robot.setBanzaiPower(0.6);
                    robot.banzai();
                }
                robot.jewel.enableLed(true);
                if (robot.jewel.red() > robot.jewel.blue()) {
                    runtime.reset();
                    while (runtime.time() < 3000) {
                        robot.bernie();
                    }
                } else if (robot.jewel.blue() > robot.jewel.red()) {
                    runtime.reset();
                    while (runtime.time() < 3000) {
                        robot.trump();
                    }
                }
                robot.stop();
                state = State.STATE_FIRST;
                break;
            case STATE_FIRST:
                runtime.reset();
                while (runtime.time() < 4000) {
                    robot.trump();
                }
                robot.stop();
                runtime.reset();
                while (runtime.time() < 4000) {
                    if (counter != vuMarkStatus) {
                        robot.columnCounter(counter);
                    }
                    if (counter == vuMarkStatus) {
                        robot.stop();
                        runtime.reset();
                        while (runtime.time() < 3000) {
                            robot.ford();
                        }
                        robot.strike();
                        state = State.STATE_SECOND_EAT;
                    }
                }
                break;
            case STATE_SECOND_EAT:
                runtime.reset();
                while (runtime.time() < 5000) {
                    robot.analGene();
                    robot.eat();
                }
                state = State.STATE_SECOND_PUT;
                break;
            case STATE_SECOND_PUT:
                stop();
                break;
        }
    }

    @Override
    public void stop() {
        super.stop();
    }
    public enum State {STATE_INITIALIZATION, STATE_JEWEL, STATE_VUMARK, STATE_FIRST, STATE_SECOND_EAT, STATE_SECOND_PUT}
}