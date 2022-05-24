package frc.robot.commands.FRC_Templates;

import java.lang.reflect.Method;


import edu.wpi.first.wpilibj.TimedRobot;


public class controllerLoop{
    String buttonName = "";
    String bindType;
    Method methodToRun;
    motor motorToRun;
    public controllerLoop(String button){
        buttonName = button;
        methodToRun = method;
        bindType = "method";
        TimedRobot.addPeriodic(() -> {
            methodToRun.invoke();
        },0.01);
    }
    public controllerLoop(String button, motor motor){

    }
    class methodObj{
        public void run(){

        }
    }
}
