package org.firstinspires.ftc.teamcode.drive;
import com.qualcomm.robotcore.util.ElapsedTime;
public class ControlMotor {
    double integralSum =0;
    double kp=0.0070;
    double kd=0.0002;
    //double ki=0.02;
    //double kf=0;

    double kpUppy=0.0080;
    double kdUppy=0.0001;
    ElapsedTime timer=new ElapsedTime();

    public double getLastError() {
        return lastError;
    }

    private double lastError=0;

    public double PIDControl(double targetPosition, double curentPosition){
        double error= targetPosition - curentPosition;
        //integralSum +=error * timer.seconds();
        double derivative=(error-lastError) / timer.seconds();

        lastError = error;
        timer.reset();
        double pid = (error*kp+derivative*kd);
        if(pid<0 && curentPosition<40) pid = -0.3;
        if(pid<0 && curentPosition<10) pid = -0.25;
        if(pid<0 && curentPosition<=3) pid = 0;
        return pid;

        //return (error*kp+derivative*kd) * (((lastError < 0 && lastError > -50) || (lastError  < -415 && lastError > -450))  ? 2 : 1);
        //return ((error*kp)+(derivative*kd)+(integralSum *ki)+(targetPosition*kf));
    }

    public double PIDControlUppy(double targetPosition, double curentPosition){
        double error= targetPosition - curentPosition;
        //integralSum +=error * timer.seconds();
        double derivative=(error-lastError) / timer.seconds();

        lastError = error;
        timer.reset();
        double pid = error*kpUppy+derivative*kdUppy;
        /*TODO: SA IL FACI MAI FRUMOS, L-AM SCRIS ASA LA GRABA SI SA INVERSEZI SI TU
        NEGATIVUL CU POZITIVUL CA E CONTRA INTUITIV ACUM ~vlad*/
        if(pid>0.01 && curentPosition < -2)
            pid = curentPosition < -25 ? 0.003 : 0.3;
        if(curentPosition >-100 && targetPosition==0) pid = 0.6;
        if(curentPosition == targetPosition) pid = 0;
        if(curentPosition > -15 && targetPosition==0) pid = 0;
        return (pid);

    }
}


