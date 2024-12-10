package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(group = "drive")
public class AutonomieTest extends LinearOpMode {
    final static boolean SLOW = true;
    final float MEEP_MOD = 0.002727f;
    SparkFunOTOS otos;
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
    double outakeRotateServoPosition = 161;
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
    long timer;
    long timer1;
    double outakeMotorPower = 0;



    @Override
    public void runOpMode() throws InterruptedException {


        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("frontleft");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("backleft");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("frontright");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("backright");
        DcMotor intakeMotor = hardwareMap.dcMotor.get("intakemotor");
        DcMotor outakeLeftMotor = hardwareMap.dcMotor.get("outakeleftmotor");
        DcMotor outakeRightMotor = hardwareMap.dcMotor.get("outakerightmotor");

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

         outakeArmServo=hardwareMap.get(Servo.class, "outakeArmServo");
         otos = hardwareMap.get(SparkFunOTOS.class, "SparkFunSensor");
         Config.configureOtos(telemetry, otos);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        waitForStart();
        if (isStopRequested()) return;

        otos.begin();
        double otosh =  otos.getPosition().h;
        if(otosh<0) otosh = otosh+360;
        drive.setPoseEstimate(new Pose2d(otos.getPosition().x, otos.getPosition().y, otosh));

        Trajectory traj = drive.trajectoryBuilder(new Pose2d())
                //.splineTo(new Vector2d(40 * MEEP_MOD,  -45 * MEEP_MOD), 0, (v, pose2d, pose2d1, pose2d2) -> SLOW ? 0.05 : 1, (v, pose2d, pose2d1, pose2d2) -> SLOW ? 0.05 : 1)
               //.forward(50 * MEEP_MEEP_MODIFIER, (v, pose2d, pose2d1, pose2d2) -> SLOW ? 0.05 : 1, (v, pose2d, pose2d1, pose2d2) -> SLOW ? 0.05 : 1)
                .forward(35 * MEEP_MOD, (v, pose2d, pose2d1, pose2d2) -> SLOW ? 0.05 : 1, (v, pose2d, pose2d1, pose2d2) -> SLOW ? 0.05 : 1)
                .build();

        Trajectory traj2 = drive.trajectoryBuilder(new Pose2d())
            //.strafeTo(new Vector2d(50,-66), (v, pose2d, pose2d1, pose2d2) -> SLOW ? 0.05 : 1, (v, pose2d, pose2d1, pose2d2) -> SLOW ? 0.05 : 1)
                .back(20 * MEEP_MOD , (v, pose2d, pose2d1, pose2d2) -> SLOW ? 0.05 : 1, (v, pose2d, pose2d1, pose2d2) -> SLOW ? 0.05 : 1)
                .build();


        // Execution


        timer = System.currentTimeMillis();
        while(timer + 800 > System.currentTimeMillis()){
            OuttakeStateSpecimen();
            outakeMotorPower = outakeControlMotor.PIDControlUppy(outakeTargetPos, outakeRightMotor.getCurrentPosition());
            outakeMotorPower *= PIDincrement;
            outakeRightMotor.setPower(outakeMotorPower);
            outakeLeftMotor.setPower(-outakeMotorPower);
            outakeArmServo.setPosition(outakeArmServoPosition / 360);
            outakeSampleServo.setPosition(outakeSampleServoPosition / 360);
            outakeRotateServo.setPosition(outakeRotateServoPosition/360);
        }

        drive.followTrajectory(traj);


        timer1 = System.currentTimeMillis();
        while(timer1 + 1200 > System.currentTimeMillis()) {
            //stuff de facut
            if(timer1 + 1000 < System.currentTimeMillis())
                OuttakeStateSamplePickUp();
            if(timer1+ 650 < System.currentTimeMillis())
                outakeSampleServoPosition = servoextended;
            if(timer1+ 600  > System.currentTimeMillis()) {
                PIDincrement =5;
                outakeTargetPos = -560 - outakeTargetPosAdder - 170;
            }

            //putere
            outakeMotorPower = outakeControlMotor.PIDControlUppy(outakeTargetPos, outakeRightMotor.getCurrentPosition());
            outakeMotorPower *= PIDincrement;
            outakeRightMotor.setPower(outakeMotorPower);
            outakeLeftMotor.setPower(-outakeMotorPower);
            outakeArmServo.setPosition(outakeArmServoPosition / 360);
            outakeSampleServo.setPosition(outakeSampleServoPosition / 360);
            outakeRotateServo.setPosition(outakeRotateServoPosition/360);
        }//*/


        drive.followTrajectory(traj2);

        sleep(2000);

        /* drive.followTrajectory(
                drive.trajectoryBuilder(traj.end(), true)
                        .splineTo(new Vector2d(0, 0), Math.toRadians(180))
                        .build()
        );*/
    }


    public void OuttakeStateSpecimen(){
        telemetry.addData("OuttakeStateSpecimen",true);
        isOuttakeStateSpecimen = true;
        wasOuttakeStateSpecimen= true;
        outakeSampleServoPosition=0+3;
        outakeArmServoPosition = 230;
        outakeRotateServoPosition=100+90;
        if (intakeServoPower != 1)
            intakeServoPower =0;
        outakeTargetPos = -20-outakeTargetPosAdder-340;
    }
    public void OuttakeStateSamplePickUp(){ // ONe. . ..OuttakeStateSpecimen
        telemetry.addData("OuttakeStateSamplePickUp",true);
        isOuttakeStateSamplePickUp = true;
        outakeArmServoPosition = 0;
        outakeRotateServoPosition = 60 + 90;
        outakeTargetPos =0;
        outakeSampleServoPosition = servoextended;
        if (intakeServoPower != 1)
            intakeServoPower = 0;
    }
}
