package org.firstinspires.ftc.teamcode.drive;


import android.graphics.Color;

import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.MathUtil;

@TeleOp(name = "Robot Teleop | Single Driver | NEW BUTTONS STATES", group = "Linear OpMode")
public class OneDriverNewStates extends LinearOpMode {

    // always remember Pisicaaaa
    double intakeRotateServoPosition = 70;
    double outakeArmServoPosition = 0;
    double outakeSampleServoPosition = 10;
    double intakeTargetPos = 0;
    double outakeTargetPos =0;
    double outakeTargetPosAdder =0;
    long startingTimer;
    long startingTimer2;
    long colortimer;
    long outPutTimer;
    boolean wasActive = false;
    boolean wasActive2 = false;
    double outakeRotateServoPosition = 105;
    double outakeRotateServoPositionDefault = 105;
    ControlMotor intakeControlMotor;
    ControlMotor outakeControlMotor;
    SparkFunOTOS myOtos;
    final float[] hsvValues = new float[3];
    NormalizedColorSensor colorSensor;
    double intakeServoPower = 0;
    boolean isIntakeStateExtended = false;
    boolean isIntakeStateRectracted = false;
    boolean isOuttakeStateStandbyWithSample = false;
    boolean isOuttakeStateSamplePickUp = false;
    boolean isOuttakeStateBascket = false;
    boolean isOuttakeStateSpecimen = false;
    boolean wasIntakeStateExtended = false;
    boolean wasActiveintake =false;
    boolean wasOuttakeStateBascket = false;
    boolean wasOuttakeStateSpecimen = false;
    boolean wascolor = false;
    double PIDincrement =1;
    int servoextended = 90;

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

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "sensorColor");

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
        OuttakeStateStandbyWithSample();
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
            NormalizedRGBA colors = colorSensor.getNormalizedColors();
            Color.colorToHSV(colors.toColor(), hsvValues);


            // Slowy downy
            double slowydowny = 4;
            double slowyDownyManal = 2.5;

            // reversing values
            pivot = -pivot;

            //increment
            PIDincrement = 1;

            //cod intake



            if(colors.red>=0.007 || colors.blue>=0.007){
                if(wasIntakeStateExtended){
                    colortimer= System.currentTimeMillis();
                    wasIntakeStateExtended = false;
                }
                Toggle.toggledButton = false;
                Toggle.toggledVarButton = false;

            }


            if (Toggle.toggleButton(gamepad1.a)==1){
                IntakeStateExtended();
                OuttakeStateSamplePickUp();
            }else {
                IntakeStateRectracted();
                if(!isOuttakeStateBascket && !isOuttakeStateSpecimen) {
                    if (intakeMotor.getCurrentPosition() < 3){
                        if (isIntakeStateExtended) {
                            isIntakeStateExtended = false;
                            startingTimer = System.currentTimeMillis();
                        }
                        intakeServoPower = -1;
                        if (System.currentTimeMillis() > startingTimer + 250) {
                            OuttakeStateStandbyWithSample();
                        }
                    }
                }
            }//*/

            //cod pus in cos
            if(Toggle.toggleButton2(gamepad1.x) == 1){
                isOuttakeStateStandbyWithSample = false;
                OuttakeStateBascket();
            }
            else if(!isOuttakeStateStandbyWithSample && !isOuttakeStateSpecimen){
                if(wasOuttakeStateBascket){
                    wasOuttakeStateBascket = false;
                    startingTimer2 = System.currentTimeMillis();
                }
                outakeSampleServoPosition = servoextended;
                if(System.currentTimeMillis() > startingTimer2+600){
                    if(!gamepad1.x) {
                        OuttakeStateSamplePickUp();
                        isOuttakeStateBascket = false;
                    }
                }
            }


            // cod specimen
            if(Toggle.toggleButton3(gamepad1.b) == 1){
                isOuttakeStateStandbyWithSample = false;
                OuttakeStateSpecimen();
            }
            else if(!isOuttakeStateStandbyWithSample && !isOuttakeStateBascket){
                outakeTargetPos = -560-outakeTargetPosAdder-150;
                PIDincrement = 3;
                if(!gamepad1.b) {
                    if(wasOuttakeStateSpecimen){
                        wasOuttakeStateSpecimen = false;
                        startingTimer2 = System.currentTimeMillis();
                    }
                    outakeSampleServoPosition = servoextended;
                    if(System.currentTimeMillis() > startingTimer2+300) {
                        OuttakeStateSamplePickUp();
                        isOuttakeStateSpecimen = false;
                    }
                }

            }

            if(Toggle.outputtoggle2(gamepad1.y) != 0) {
                if(wasActiveintake){
                    wasActiveintake = false;
                    outPutTimer = System.currentTimeMillis();
                    telemetry.addData("timer", true);
                }
                outakeArmServoPosition = 200;
                outakeRotateServoPosition=140+outakeRotateServoPositionDefault;
                if(System.currentTimeMillis() > outPutTimer+700)
                    outakeSampleServoPosition=servoextended;
            }
            if(gamepad1.dpad_up)
                outakeTargetPosAdder += 2;
            if(gamepad1.dpad_down)
                outakeTargetPosAdder -= 2;
            if (Toggle.outputtoggle(gamepad1.right_bumper) != 0)
                intakeServoPower = -1;



            //Intake target position
            if (intakeinput < 0 && intakeTargetPos <= 270)
                intakeTargetPos += 15;
            if (intakeinput > 0 && intakeTargetPos >= 0)
                intakeTargetPos -= 110;

            //telemetry.addData("test", intakeTargetPos);
            MathUtil.clamp(0, 270, intakeTargetPos);

            //outakeTargetPos = Toggle.toggleButton2(gamepad1.x)!=0 ? -1020 : 0;

            double intakeMotorPower = 0;
            //Calculating via PID
            intakeMotorPower = intakeControlMotor.PIDControl(intakeTargetPos, intakeMotor.getCurrentPosition());


            //setting limits
            intakeMotorPower = MathUtil.clamp(-0.7, 0.7, intakeMotorPower);
            double outakeMotorPower;

            outakeMotorPower = outakeControlMotor.PIDControlUppy(outakeTargetPos, outakeRightMotor.getCurrentPosition());
            outakeMotorPower *= PIDincrement;



            //calculating nedded power by method 1
            double frontRightPower = (pivot - vertical - horizontal);
            double backRightPower = (pivot - vertical + horizontal);
            double frontLeftPower = (pivot + vertical - horizontal);
            double backLeftPower = (pivot + vertical + horizontal);



            //telemetry.addData("intakerotatey",intakeRotateServo.getPosition());
            //telemetry.addData("intakerotateygoalposition",intakeRotateServoPower);

            //telemetry, again
            /*

            telemetry.addLine()
                    .addData("Red", colors.red)
                    .addData("Green", colors.green)
                    .addData("Blue", colors.blue);
            /*telemetry.addLine()
                    .addData("Hue", hsvValues[0])
                    .addData("Saturation", hsvValues[1])
                    .addData("Value", hsvValues[2]);
            telemetry.addData("Alpha", colors.alpha);
            //*/

            telemetry.addData("outake Error", outakeControlMotor.getLastError());
            telemetry.addData("curent Pos RIGHT", outakeRightMotor.getCurrentPosition());
            telemetry.addData("target Pos", outakeTargetPos);
            telemetry.addData("powah", outakeMotorPower);
            telemetry.addData("OutakeAdder", outakeTargetPosAdder);//*/
            /*telemetry.addData("intakeCurentPOs", intakeMotor.getCurrentPosition());
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

            if (isOuttakeStateBascket || isOuttakeStateSpecimen || isIntakeStateExtended) {
                frontLeftPower /= slowydowny;
                backRightPower /= slowydowny;
                frontRightPower /= slowydowny;
                backLeftPower /= slowydowny;
            }
            if(slowdown){
                frontLeftPower /= slowyDownyManal;
                backRightPower /= slowyDownyManal;
                frontRightPower /= slowyDownyManal;
                backLeftPower /= slowyDownyManal;
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


    public void IntakeStateExtended(){
        telemetry.addData("IntakeStateExtended",true);
        isIntakeStateExtended = true;
        wasIntakeStateExtended = true;
        wasActiveintake = true;
        intakeRotateServoPosition = 250;
        intakeTargetPos = 270;
        intakeServoPower = 1;
    }
    public void IntakeStateRectracted(){
        telemetry.addData("IntakeStateRectracted",true);
        isIntakeStateRectracted = true;
        intakeRotateServoPosition = 70;
        intakeTargetPos = 0;
        intakeServoPower = 0;
    }
    public void OuttakeStateStandbyWithSample(){
        telemetry.addData("OuttakeStateStandbyWithSample",true);
        isOuttakeStateStandbyWithSample = true;
        outakeSampleServoPosition = 0+6;
        outakeArmServoPosition = 30;
        outakeRotateServoPosition = 0 + outakeRotateServoPositionDefault;
        outakeTargetPos =0;
        if (intakeServoPower != 1)
            intakeServoPower = 0;
    }
    public void OuttakeStateSamplePickUp(){ // ONe. . ..OuttakeStateSpecimen
        telemetry.addData("OuttakeStateSamplePickUp",true);
        isOuttakeStateSamplePickUp = true;
        outakeArmServoPosition = 0;
        outakeRotateServoPosition = 60 + outakeRotateServoPositionDefault;
        outakeTargetPos =0;
        outakeSampleServoPosition = servoextended;
        if (intakeServoPower != 1)
            intakeServoPower = 0;
    }
    public void OuttakeStateBascket(){
        telemetry.addData("OuttakeStateBascket",true);
        isOuttakeStateBascket = true;
        wasOuttakeStateBascket = true;
        outakeSampleServoPosition=0+6;
        outakeArmServoPosition = 185;
        outakeRotateServoPosition=120+outakeRotateServoPositionDefault; // new 0
        if (intakeServoPower != 1)
            intakeServoPower =0;
        outakeTargetPos = -1020-outakeTargetPosAdder;
    }

    public void OuttakeStateSpecimen(){
        telemetry.addData("OuttakeStateSpecimen",true);
        isOuttakeStateSpecimen = true;
        wasOuttakeStateSpecimen= true;
        outakeSampleServoPosition=0+3;
        outakeArmServoPosition = 230;
        outakeRotateServoPosition=100+outakeRotateServoPositionDefault;
        outakeTargetPos = -40-outakeTargetPosAdder-340;
    }
    //outake arm la 175, outakeROtate -22/0, glisiere -59 iar apoi -159
    // new 0 is 161
    // 374 outake adder

}