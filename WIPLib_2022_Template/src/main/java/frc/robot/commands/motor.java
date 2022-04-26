package frc.robot.commands;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class motor {
    // Declare motor variables
    private String motorType;
    TalonSRX talonMotor;
    VictorSPX victorMotor;
    TalonFX falconMotor;
    

  
    public motor (String motorName, String filePath) {
        loadMotor(motorName, filePath);
    }
    private TalonSRX loadTalon(Properties motorProp) {
        return new TalonSRX(Integer.parseInt(motorProp.getProperty("MotorPort")));
    }
   /**
    * Returns a Falcon FX motor object with the given properties
    * @param motorProp The motor properties file to read
    * @return Returns the new motor object
    * @
    */
    private TalonFX loadFalcon(Properties motorProp) {
        return new TalonFX(Integer.parseInt(motorProp.getProperty("MotorPort")));
    }
    private VictorSPX loadVictor(Properties motorProp) {
        return new VictorSPX(Integer.valueOf(motorProp.getProperty("MotorPort")));
    }
    public void loadMotor(String motorName, String filePath) {
        Properties motorProp = new Properties();
        String motorFile = getMotorFilename(motorName, filePath);
        FileInputStream motorFiles;
        try {
            motorFiles = new FileInputStream(motorFile);
            motorProp.load(motorFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }

        motorType = motorProp.getProperty("motorType");
        if(motorType.equals("TalonSRX")){
            talonMotor = loadTalon(motorProp);
        }
        else if(motorType.equals("VictorSPX")){
            victorMotor = loadVictor(motorProp);
        } 
        else if(motorType.equals("Falcon")){
            falconMotor = loadFalcon(motorProp);
        } 
        else if(motorType.equals("TalonFX")){
            falconMotor = loadFalcon(motorProp);
        } 
        else {
            System.out.println("Motor type not found");
        }
    }

    private String getMotorFilename(String MotorName, String filePath){
        Properties motorProp = new Properties();
        int MCFileNum = new File(filePath).listFiles().length;
        File[] MCfile = new File(filePath).listFiles();
        String filename = "";
        
        for(int i = 0; i <= MCFileNum; i++){
            FileInputStream motorFiles;
            try {
                motorFiles = new FileInputStream(MCfile[i]);
                motorProp.load(motorFiles);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(motorProp.getProperty("MotorName").equals(MotorName)){
                filename = MCfile[i].getPath();
                break;
            }
        }
        return filename;
    }

    public void run(double speed){
        switch(motorType){
        case "TalonSRX":
        {
            talonMotor.set(ControlMode.PercentOutput, speed);
            break;
        }

        case "VictorSPX":
        {
            victorMotor.set(ControlMode.PercentOutput, speed);
            break;
        }

        case "Falcon": case "TalonFX":
        {
            falconMotor.set(ControlMode.PercentOutput, speed);
            break;
        }
        }
    }

    public void runSame(double speed, motor ... motors)
    {
        this.run(speed);
        int length = motors.length;
        for(int i = 0; i < length; i++)
        {
            motors[i].run(speed);
        }
    }

    public void runOpposite(double speed, motor ... motors)
    {
        this.run(speed);
        int length = motors.length;
        for(int i = 0; i < length; i++)
        {
            motors[i].run(-speed);
        }
    }

    public static void runOpposite(double speed, motor matchMotors[], motor oppositeMotors[])
    {
        int length = matchMotors.length;
        for(int i = 0; i < length; i++)
        {
            matchMotors[i].run(speed);
        }
    
        length = matchMotors.length;
        for(int i = 0; i < length; i++)
        {
            oppositeMotors[i].run(speed);
        }
    }

}

