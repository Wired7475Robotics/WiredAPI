package frc.robot.commands.FRC_Templates;

import java.lang.reflect.Method;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.I2C.Port;

public class controller {
    static String filePath = "";
    XboxController controller = null;
    public controller(int port) {
        controller = new XboxController(port);
    }
    public static void setControllerMappingPath(String path) {
        filePath = path;
    }

    class button {
        String b = "";
        public button(String button){
            b = button;
        }
        public void bind(Method method){
            if (filePath.equals("")) {
                System.out.println("Please set the controller mapping path first!");
            } else {
                try {
                    
                } catch (Exception e) {
                    System.out.println("Error binding method: " + e.getMessage());
                }
            }
        }

        public void bind(motor motor, double speed){
            if (filePath.equals("")) {
                System.out.println("Please set the controller mapping path first!");
            } else {
                TimedRobot.addPeriodic(buttonLoop(motor, speed), 0.005);
            }
        }
        private void buttonLoop(motor motor, double speed){
            if (b == "x"){
                if(controller.getXButton()){
                    motor.run(speed);
                }
            }
        }
        private void buttonLoop(Method method){

        }
    }
    class joystick{
        public void bind(){

        }
    }
}
