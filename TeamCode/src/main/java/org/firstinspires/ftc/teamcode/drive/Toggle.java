package org.firstinspires.ftc.teamcode.drive;

public class Toggle {
    public static boolean toggled;
    public static boolean toggle_var;
    public static long  startingTime;
    public static long  startingTime2;
    public static boolean toggledButton;
    public static boolean toggledVarButton;
    public static boolean toggledButton2;
    public static boolean toggledVarButton2;
    public static boolean toggledButton3;
    public static boolean toggledVarButton3;


    public static int toggle(boolean input) {
        int output;

        if (input) {
            if (!toggled) {
                toggle_var = !toggle_var;
                toggled = true;
            }
            output = toggle_var ? 1 : 0;
        } else {
            toggled = false;
            output = toggle_var ? 1 : 0;
        }

        return output;
    }

    public static double outputtoggle(boolean starter){
        if(starter){
              startingTime = System.currentTimeMillis();
        }
        if(System.currentTimeMillis() < startingTime + 300){
            return -0.7;
        }else{
            return 0;
        }
    }
    public static double outputtoggle2(boolean starter){
        if(starter){
            startingTime2 = System.currentTimeMillis();
        }
        if(System.currentTimeMillis() < startingTime2 + 1000){
            return -0.7;
        }else{
            return 0;
        }
    }

    public static int toggleButton(boolean input) {
        int output;

        if (input) {
            if (!toggledButton) {
                toggledVarButton = !toggledVarButton;
                toggledButton = true;
            }
            output = toggledVarButton ? 1 : 0;
        } else {
            toggledButton = false;
            output = toggledVarButton ? 1 : 0;
        }

        return output;
    }

    public static int toggleButton2(boolean input) {
        int output;
        if (input) {
            if (!toggledButton2) {
                toggledVarButton2 = !toggledVarButton2;
                toggledButton2 = true;
            }
            output = toggledVarButton2 ? 1 : 0;
        } else {
            toggledButton2 = false;
            output = toggledVarButton2 ? 1 : 0;
        }
        return output;
    }
    public static int toggleButton3(boolean input) {
        int output;
        if (input) {
            if (!toggledButton3) {
                toggledVarButton3 = !toggledVarButton3;
                toggledButton3 = true;
            }
            output = toggledVarButton3 ? 1 : 0;
        } else {
            toggledButton3 = false;
            output = toggledVarButton3 ? 1 : 0;
        }
        return output;
    }
}
