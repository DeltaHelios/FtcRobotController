package org.firstinspires.ftc.teamcode.drive;


import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "ServoTesting", group = "Linear OpMode")
public class ServoTesting extends LinearOpMode {

    double intakeRotateServoPosition = 0;
    double outakeArmServoPosition = 0;
    double outakeSampleServoPosition = 10;
    double outakeRotateServoPosition =161; // new 0
    double intakeServoPower = 0;
    SparkFunOTOS myOtos;

    @Override
    public void runOpMode() throws InterruptedException {

        CRServo servo1 = hardwareMap.get(CRServo.class, "leftservo");
        CRServo servo2 = hardwareMap.get(CRServo.class, "rightservo");
        Servo intakeRotateServo = hardwareMap.get(Servo.class, "intakeRotateServo");
        Servo outakeArmServo = hardwareMap.get(Servo.class, "outakeArmServo");
        Servo outakeSampleServo = hardwareMap.get(Servo.class, "outakeSampleServo");
        Servo outakeRotateServo = hardwareMap.get(Servo.class, "outakeRotateServo");
        DcMotor outakeLeftMotor = hardwareMap.dcMotor.get("outakeleftmotor");
        DcMotor outakeRightMotor = hardwareMap.dcMotor.get("outakerightmotor");
        //Servo tester = hardwareMap.get(Servo.class, "tester");
        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if (gamepad1.right_bumper)
                outakeArmServoPosition += 0.2;
            if (gamepad1.left_bumper)
                outakeArmServoPosition -= 0.2;
            if(gamepad1.a)
                outakeSampleServoPosition = 64;
            if(gamepad1.b)
                outakeSampleServoPosition = 0+6;
            if(gamepad1.x)
                outakeRotateServoPosition+=0.2;
            if(gamepad1.y)
                outakeRotateServoPosition-=0.2;
// 64 deschis 0 inchis la sample
// 0 la rotate 60 pozitie baschet



            if (Toggle.outputtoggle(gamepad1.right_trigger > 0) != 0)
                intakeServoPower = Toggle.outputtoggle(gamepad1.right_trigger > 0);

            //telemetry
            telemetry.addData("outakeArmServoPOS GO TO", outakeRotateServoPosition);
            telemetry.addData("outakeArmServoPOS", outakeArmServo.getPosition());
            telemetry.addData("outakeSamplePOS", outakeSampleServo.getPosition());
            telemetry.addData("outakeSamplePOS GO TO ", outakeSampleServoPosition);
            telemetry.addData("outakeRotateServo", outakeArmServo.getPosition());
            telemetry.addData("outakeROTATEPOS GO TO ", outakeRotateServoPosition);
            telemetry.addData("intakeRotateServoPos(TBS)", intakeRotateServo.getPosition());
            telemetry.addData("outake motor pos ", outakeRightMotor.getCurrentPosition());
            telemetry.update();



            //tester.setPosition(0);
            intakeRotateServo.setPosition(intakeRotateServoPosition / 360);
            outakeArmServo.setPosition(outakeArmServoPosition / 360);
            outakeSampleServo.setPosition(outakeSampleServoPosition / 360);
            outakeRotateServo.setPosition(outakeRotateServoPosition/360);
            servo1.setPower(intakeServoPower);
            servo2.setPower(-intakeServoPower);

        }

    }
}