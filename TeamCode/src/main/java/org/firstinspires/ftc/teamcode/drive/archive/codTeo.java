package org.firstinspires.ftc.teamcode.drive.archive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "robot (Blocks to Java)")
public class codTeo extends LinearOpMode {

    private DcMotor brat;
    private DcMotor left_back;
    private DcMotor left_front;
    private DcMotor right_back;
    private DcMotor right_front;
    private Servo test;

    /**
     * Describe this function...
     */

    /**
     * This function is executed when this OpMode is selected from the Driver Station.
     */
    @Override
    public void runOpMode() {
        int test2;
        float vertical;
        float horizontal;
        float pivot;

        brat = hardwareMap.get(DcMotor.class, "bratAsDcMotor");
        left_back = hardwareMap.get(DcMotor.class, "left_backAsDcMotor");
        left_front = hardwareMap.get(DcMotor.class, "left_frontAsDcMotor");
        right_back = hardwareMap.get(DcMotor.class, "right_backAsDcMotor");
        right_front = hardwareMap.get(DcMotor.class, "right_frontAsDcMotor");
        test = hardwareMap.get(Servo.class, "test");

        // Put initialization blocks here.
        brat.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        brat.setTargetPosition(1);
        brat.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left_back.setDirection(DcMotor.Direction.REVERSE);
        left_front.setDirection(DcMotor.Direction.REVERSE);
        left_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_back.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right_front.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        test.setPosition(0.4);
        brat.setPower(1);
        test2 = 0;
        waitForStart();
        if (opModeIsActive()) {
            // Put run blocks here.
            while (opModeIsActive()) {
                vertical = gamepad1.left_stick_y;
                horizontal = -gamepad1.left_stick_x;
                pivot = gamepad1.right_stick_x;
                right_front.setPower(-pivot + (vertical - horizontal));
                right_back.setPower(-pivot + vertical + horizontal);
                left_front.setPower(pivot + vertical + horizontal);
                left_back.setPower(pivot + (vertical - horizontal));
                test.setPosition(test2);
                if (gamepad2.left_stick_y > 0) {
                    if (gamepad2.left_trigger > 0.2) {
                        brat.setTargetPosition(brat.getTargetPosition() - 2);
                    } else {
                        brat.setTargetPosition(brat.getTargetPosition() - 8);
                    }
                } else {
                    if (gamepad2.left_stick_y < 0) {
                        if (gamepad2.left_trigger > 0.2) {
                            brat.setTargetPosition(brat.getTargetPosition() + 2);
                        } else {
                            brat.setTargetPosition(brat.getTargetPosition() + 8);
                        }
                    } else {
                    }
                }
                if (gamepad2.y) {
                    brat.setTargetPosition(brat.getCurrentPosition());
                }

                telemetry.addData("current pos", brat.getCurrentPosition());
                telemetry.addData("target pos", brat.getTargetPosition());
                telemetry.addData("LT", gamepad2.left_trigger);
                telemetry.addData("SERVO POS", test.getPosition());
                telemetry.update();
            }
        }
    }

    /**
     * Describe this function...
     */
}
    /**
     * Describe this function...
     */
