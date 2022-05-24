package frc.robot.commands;

import java.lang.reflect.Method;

import edu.wpi.first.wpilibj.XboxController;

public class controller {
    static String filePath = "";
    public controller(){

    }
    public static void setControllerMappingPath(String path) {
        filePath = path;
    }



    public void bind(Method method){

    }
}
