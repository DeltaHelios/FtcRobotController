package org.firstinspires.ftc.teamcode.drive;


import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.MathUtil;

@TeleOp(name = "Robot Teleop | Single Driver | NEW BUTTONS", group = "Linear OpMode")
public class OneDriverNewButtons extends LinearOpMode {

    // always remember Pisicaaaa
    double intakeRotateServoPosition = 70;
    double outakeArmServoPosition = 0;
    double outakeSampleServoPosition = 10;
    double intakeTargetPos = 0;
    double outakeTargetPos =0;
    long startingTimer;
    long startingTimer2;
    boolean wasActive = false;
    boolean wasActive2 = false;
    double outakeRotateServoPosition = 48;
    ControlMotor intakeControlMotor;
    ControlMotor outakeControlMotor;
    SparkFunOTOS myOtos;
    final float[] hsvValues = new float[3];
    NormalizedColorSensor colorSensor;
    double intakeServoPower = 0;
    boolean isActivey = false;
    boolean isActiveb = false;
    boolean isActivea = false;
    boolean wasActiveOutake = false;
    boolean wasActiveOutake2 =false;

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

        //colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensorColor");

        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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


            // Slowy downy
            double slowydowny = 2.5;

            /// reversing values
            pivot = -pivot;

            //Automatizari

            if (Toggle.toggleButton(gamepad1.a) == 1){
                intakeRotateServoPosition = 245;
                intakeTargetPos = 270;
                intakeServoPower =1;
                outakeTargetPos = 0;
                wasActive = true;
                wasActive2 = true;
                isActivey = false;
                isActiveb = false;
                wasActiveOutake2 = false;
            }
            else if(!isActiveb && !isActivey){
                isActivea = true;
                intakeRotateServoPosition = 70;
                intakeTargetPos = 0;
                intakeServoPower = 0;
                wasActive = false;
                if(intakeMotor.getCurrentPosition()<5){
                    telemetry.addData("true", true);
                    intakeServoPower =-1;
                    if(wasActive2){
                        startingTimer2 = System.currentTimeMillis();
                        wasActive2 = false;
                    }
                    if(System.currentTimeMillis() > startingTimer2 + 400) {
                        outakeSampleServoPosition = 0;
                        outakeArmServoPosition = 30;
                        outakeRotateServoPosition = 0 + 48;
                        intakeServoPower = 0;
                    }
                }
            }




            //cod pus in cos
            if(gamepad1.y){
                isActivey = true;
                outakeSampleServoPosition=0;
                outakeArmServoPosition = 155;
                outakeRotateServoPosition=120+48;
                intakeServoPower =0;
                outakeTargetPos = -1020;
                wasActiveOutake = true;
                wasActiveOutake2 = true;
            }
            else if(wasActiveOutake){
                startingTimer = System.currentTimeMillis();
                wasActiveOutake = false;
            } else if(System.currentTimeMillis() > startingTimer + 1000 && wasActiveOutake2) {
                    outakeSampleServoPosition = 64;
                if (System.currentTimeMillis() > startingTimer + 1600) {
                    outakeArmServoPosition = 0;
                    telemetry.addData("true2", true);
                    outakeRotateServoPosition = 0 + 48;
                    if (intakeServoPower != 1)
                        intakeServoPower = 0;
                }
            }

            // cod specimen
            if(gamepad1.b){
                isActiveb = true;

                outakeArmServoPosition = 155;
                outakeRotateServoPosition=120+48;
                intakeServoPower =0;
                outakeTargetPos = -1020;
                wasActiveOutake = true;
                wasActiveOutake2 = true;
            }
            else if(wasActiveOutake){
                startingTimer = System.currentTimeMillis();
                wasActiveOutake = false;
            } else if(System.currentTimeMillis() > startingTimer + 1000 && wasActiveOutake2) {
                    outakeSampleServoPosition = 64 ;
                if (System.currentTimeMillis() > startingTimer + 1600) {
                    outakeArmServoPosition = 0;
                    telemetry.addData("true3", true);
                    outakeRotateServoPosition = 0 + 48;
                    if (intakeServoPower != 1)
                        intakeServoPower = 0;
                }
            }
            // cod pus specimen


            /*else if(wasActive){
                startingTimer = System.currentTimeMillis();
                wasActive = false;
            } else {
                outakeSampleServoPosition = 64;
                wasActive2 = true;
                if (System.currentTimeMillis() > startingTimer + 600) {
                    outakeArmServoPosition = 0;
                    outakeRotateServoPosition = 0+48;
                    if (intakeServoPower != 1)
                        intakeServoPower = 0;
                }
            }//*/


            //Intake target position
            if (intakeinput < 0 && intakeTargetPos <= 270)
                intakeTargetPos += 15;
            if (intakeinput > 0 && intakeTargetPos >= 0)
                intakeTargetPos -= 110;

            //telemetry.addData("test", intakeTargetPos);
            MathUtil.clamp(0, 270, intakeTargetPos);

            outakeTargetPos = Toggle.toggleButton2(gamepad1.x)!=0 ? -1020 : 0;

            double intakeMotorPower = 0;
            //Calculating via PID
            intakeMotorPower = intakeControlMotor.PIDControl(intakeTargetPos, intakeMotor.getCurrentPosition());
            //int remembernegativeintake=0;
            //telemetry.addData("intakemotorpower", intakeMotorpower);
            //telemetry.addData("intakemotorpos", intakeMotor.getCurrentPosition());

            //setting limits
            intakeMotorPower = MathUtil.clamp(-0.7, 0.7, intakeMotorPower);
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
            /*NormalizedRGBA colors = colorSensor.getNormalizedColors();
            Color.colorToHSV(colors.toColor(), hsvValues);

            telemetry.addLine()
                    .addData("Red", colors.red)
                    .addData("Green", colors.green)
                    .addData("Blue", colors.blue);
            telemetry.addLine()
                    .addData("Hue", hsvValues[0])
                    .addData("Saturation", hsvValues[1])
                    .addData("Value", hsvValues[2]);
            telemetry.addData("Alpha", colors.alpha);
            //*/

            /*telemetry.addData("outake Error", outakeControlMotor.getLastError());
            telemetry.addData("curent Pos RIGHT", outakeRightMotor.getCurrentPosition());
            telemetry.addData("target Pos", outakeTargetPos);
            telemetry.addData("powah", outakeMotorPower);//*/
            telemetry.addData("intakeCurentPOs", intakeMotor.getCurrentPosition());
            telemetry.addData("intakeTargetPos", intakeTargetPos);
            telemetry.addData("intakepower", intakeMotorPower);//*/
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

            intakeMotor.setPower(intakeMotorPower);
            outakeRightMotor.setPower(outakeMotorPower);
            outakeLeftMotor.setPower(-outakeMotorPower);
            //outakeRightMotor.setPower(0);
            //outakeLeftMotor.setPower(0);

            servo1.setPower(-intakeServoPower);
            servo2.setPower(intakeServoPower);
            //tester.setPosition(0);

            intakeRotateServo.setPosition(intakeRotateServoPosition / 360);
            outakeArmServo.setPosition(outakeArmServoPosition / 360);
            outakeSampleServo.setPosition(outakeSampleServoPosition / 360);
            outakeRotateServo.setPosition(outakeRotateServoPosition/360);
            updateTelemetry(telemetry);
        }
    }
}