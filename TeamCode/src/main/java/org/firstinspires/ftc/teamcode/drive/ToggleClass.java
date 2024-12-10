package org.firstinspires.ftc.teamcode.drive;

public class ToggleClass {
    boolean toggled;

    boolean state;
    public ToggleClass(){
        toggled = false;
        state = false;
    }

        public void toggle(boolean input) {
            if (input) {
                if(!toggled) {
                    toggled = true;
                    state = !state;
                }
            }else toggled = false;
        }
        public boolean v(){return  state;}


}
