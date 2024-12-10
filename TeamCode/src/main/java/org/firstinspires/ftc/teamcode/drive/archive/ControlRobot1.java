package org.firstinspires.ftc.teamcode.drive.archive;


import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.Config;
import org.firstinspires.ftc.teamcode.drive.ControlMotor;
import org.firstinspires.ftc.teamcode.drive.Toggle;

@TeleOp(name = "Method1 v3.18 dashboard", group = "Linear OpMode")
public class ControlRobot1 extends LinearOpMode {

    // always remember Pisicaaaa
    double intakeRotateServoPosition = 0;
    double outakeArmServoPosition = 0;
    double outakeSampleServoPosition = 10;
    double intakeTargetPos = 0;
    double outakeTargetPos =0;
    long startingTimer;
    boolean wasActive = false;
    double outakeRotateServoPosition =0;
    ControlMotor intakeControlMotor;
    ControlMotor outakeControlMotor;
    SparkFunOTOS myOtos;
    double intakeServoPower = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontleft");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backleft");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontright");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backright");
        DcMotor intakeMotor = hardwareMap.dcMotor.get("intakemotor");
        DcMotor outakeLeftMotor = hardwareMap.dcMotor.get("outakeleftmotor");
        DcMotor outakeRightMotor = hardwareMap.dcMotor.get("outakerightmotor");
        myOtos = hardwareMap.get(SparkFunOTOS.class, "SparkFunSensor");
        //declare servos
        CRServo servo1 = hardwareMap.get(CRServo.class, "rightservo");
        CRServo servo2 = hardwareMap.get(CRServo.class, "leftservo");
        Servo intakeRotateServo = hardwareMap.get(Servo.class, "intakeRotateServo");
        Servo outakeArmServo = hardwareMap.get(Servo.class, "outakeArmServo");
        Servo outakeSampleServo = hardwareMap.get(Servo.class, "outakeSampleServo");
        Servo outakeRotateServo = hardwareMap.get(Servo.class, "outakeRotateServo");
        //Servo tester = hardwareMap.get(Servo.class, "tester");
        intakeControlMotor = new ControlMotor();
        outakeControlMotor = new ControlMotor();

        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Config.configureOtos(telemetry, myOtos);

        Toggle.toggled = false;
        Toggle.toggle_var = false;
        Toggle.toggledButton = false;
        Toggle.toggledVarButton = false;
        Toggle.toggledVarButton2 = false;
        Toggle.toggledButton2 = false;
        Toggle.toggledVarButton3 = false;
        Toggle.toggledButton3 = false;

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            ///gamepad1
            double vertical = gamepad1.left_stick_y;
            double horizontal = gamepad1.left_stick_x;
            double pivot = gamepad1.right_stick_x;
            boolean slowdown = gamepad1.left_bumper;

            ///gamepad2
            double intakeinput = gamepad2.left_stick_y;
            SparkFunOTOS.Pose2D pos = myOtos.getPosition();
            //if (gamepad1.y)
            //    myOtos.resetTracking();

            // Re-calibrate the IMU if the user requests it
            //if (gamepad1.x)
            //    myOtos.calibrateImu();

            //declaring slow down variable(amount)
            //1 ar insemna deloc, 2 ar insemna de 2 ori mai incet

            double slowydowny = 2.5;

            /// reversing values
            pivot = -pivot;
            //horizontal = -horizontal;

            //Automatizari

            if (Toggle.toggleButton(gamepad1.a) == 1){
                intakeRotateServoPosition = 190;
                intakeServoPower =1;
            }
            else{
                intakeRotateServoPosition =0;
                intakeServoPower = 0;
            }
            //if (intakeMotor.getCurrentPosition() > 90)
            //    intakeRotateServoPosition = 190;
            //if (intakeMotor.getCurrentPosition() < 80)
            //    intakeRotateServoPosition = 0;

            //cod transfer
            if(Toggle.toggleButton3(gamepad1.y)!=0){
                outakeSampleServoPosition=0;
                outakeArmServoPosition = 155;
                outakeRotateServoPosition=60;
                intakeServoPower =-1;
                wasActive = true;
            } else if(wasActive){
                startingTimer = System.currentTimeMillis();
                wasActive = false;
            } else {
                outakeSampleServoPosition = 64;
                if (System.currentTimeMillis() > startingTimer + 400) {
                    outakeArmServoPosition = 0;
                    outakeRotateServoPosition = 0;
                    if (intakeServoPower != 1) intakeServoPower = 0;
                }
            }




            //Intake target position
            if (intakeinput < 0 && intakeTargetPos <= 110)
                intakeTargetPos += 15;
            if (intakeinput > 0 && intakeTargetPos >= 0)
                intakeTargetPos -= 110;

            //telemetry.addData("test", intakeTargetPos);
            if (intakeTargetPos > 110)
                intakeTargetPos = 110;
            if (intakeTargetPos < 0)
                intakeTargetPos = 0;


            if(Toggle.toggleButton2(gamepad1.x)!=0) outakeTargetPos = -900;
            else outakeTargetPos =0;


            double intakeMotorpower = 0;
            //Calculating via PID
            intakeMotorpower = intakeControlMotor.PIDControl(intakeTargetPos, intakeMotor.getCurrentPosition());
            //int remembernegativeintake=0;
            //telemetry.addData("intakemotorpower", intakeMotorpower);
            //telemetry.addData("intakemotorpos", intakeMotor.getCurrentPosition());

            //setting limits
            if (intakeMotorpower < -0.7)
                intakeMotorpower = -0.7;
            if (intakeMotorpower > 0.7)
                intakeMotorpower = 0.7;

            double outakeMotorPower;

            outakeMotorPower = outakeControlMotor.PIDControlUppy(outakeTargetPos, outakeRightMotor.getCurrentPosition());
            //setting limits
            /*if (outakeMotorPower < -0.7)
                outakeMotorPower = -0.7;
            if (outakeMotorPower > 0.7)
                outakeMotorPower = 0.7;//*/


            //calculating nedded power by method 1
            double frontRightPower = (pivot - vertical - horizontal);
            double backRightPower = (pivot - vertical + horizontal);
            double frontLeftPower = (pivot + vertical - horizontal);
            double backLeftPower = (pivot + vertical + horizontal);

            if (Toggle.outputtoggle(gamepad1.b) != 0)
                intakeServoPower = Toggle.outputtoggle(gamepad1.b);




            //telemetry.addData("intakerotatey",intakeRotateServo.getPosition());
            //telemetry.addData("intakerotateygoalposition",intakeRotateServoPower);

            //telemetry, again
            telemetry.addData("outake Error", outakeControlMotor.getLastError());
            telemetry.addData("curent Pos RIGHT", outakeRightMotor.getCurrentPosition());
            telemetry.addData("curent Pos LEFT", outakeLeftMotor.getCurrentPosition());
            telemetry.addData("target Pos", outakeTargetPos);
            telemetry.addData("powah", outakeMotorPower);
            //telemetry.addData("servo POs", tester.getPosition());
            //telemetry.addData("y", pos.y);
            //telemetry.addData("x", pos.x);
            //telemetry.addData("rotation/orientation", pos.h);
            //telemetry.addData("outakeArmServoCurrentPosition",outakeArmServo.getPosition());
            //telemetry.addData("outakeArmServo",outakeArmServoPosition);
            //telemetry.addData("outakeSampleServoCurrentPosition",outakeSampleServo.getPosition());
            //telemetry.addData("outakeSampleServo",outakeSampleServoPosition);

            if (slowdown) {
                frontLeftPower /= slowydowny;
                backRightPower /= slowydowny;
                frontRightPower /= slowydowny;
                backLeftPower /= slowydowny;
            }


            // set motor power
            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);
            intakeMotor.setPower(intakeMotorpower);
            outakeRightMotor.setPower(outakeMotorPower);
            outakeLeftMotor.setPower(-outakeMotorPower);
            //outakeRightMotor.setPower(0);
            //outakeLeftMotor.setPower(0);
            servo1.setPower(intakeServoPower);
            servo2.setPower(-intakeServoPower);
            //tester.setPosition(0);
            intakeRotateServo.setPosition(intakeRotateServoPosition / 360);
            outakeArmServo.setPosition(outakeArmServoPosition / 360);
            outakeSampleServo.setPosition(outakeSampleServoPosition / 360);
            outakeRotateServo.setPosition(outakeRotateServoPosition/360);
            updateTelemetry(telemetry);
        }
    }
}