package org.firstinspires.ftc.teamcode.util;

public class MathUtil {
    static public double clamp (double min, double max, double n){
        return Math.min(Math.max(min, n), max);
    }
}
