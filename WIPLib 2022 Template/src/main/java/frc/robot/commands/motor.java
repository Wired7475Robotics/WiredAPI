package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class motor {
    // Declare motor variables
    TalonSRX talonMotor;
    VictorSPX victorMotor;

    Properties motorProp = new Properties();
    int MCFileNum = new File("WIPLib 2022 Template\\src\\main\\java\\frc\\robot\\subsystems\\motorConfigs").listFiles().length;
    private TalonSRX loadTalon(String filename) throws IOException{
        FileInputStream motorFiles = new FileInputStream(filename);
        motorProp.load(motorFiles);
        TalonSRX talonMotor = new TalonSRX(Integer.parseInt(motorProp.getProperty("motorID")));
        motorProp.setProperty("loaded","true");
        return talonMotor;
    }
    private VictorSPX loadVictor(String filename) throws IOException{
        FileInputStream motorFiles = new FileInputStream(filename);
        motorProp.load(motorFiles);
        VictorSPX victorMotor = new VictorSPX(Integer.valueOf(motorProp.getProperty("MotorPort")));
        motorProp.setProperty("loaded","true");
        return victorMotor;
    }
    public void driveMotor(String motorName, Double motorSpeed) throws IOException{
        String motorFile = getMotorFilename(motorName);
        FileInputStream motorFiles = new FileInputStream(motorFile);
        motorProp.load(motorFiles);
        String motorType = motorProp.getProperty("motorType");
        if(motorType.equals("TalonSRX")){
            if(motorProp.getProperty("loaded").equals("false")){
            talonMotor = loadTalon(motorFile);
            }
            talonMotor.set(ControlMode.PercentOutput, motorSpeed);
        }
        else if(motorType.equals("VictorSPX")){
            if (motorProp.getProperty("loaded").equals("false")){
                victorMotor = loadVictor(motorFile);
            }    
            victorMotor.set(ControlMode.PercentOutput, motorSpeed);
        } else {
            System.out.println("Motor type not found");
        }
        

    }
    private String getMotorFilename(String MotorName) throws IOException{
        int MCFileNum = new File("WIPLib 2022 Template\\src\\main\\java\\frc\\robot\\subsystems\\motorConfigs").listFiles().length;
        File[] MCfile = new File("WIPLib 2022 Template\\src\\main\\java\\frc\\robot\\subsystems\\motorConfigs").listFiles();
        String filename = "";
        for(int i = 0; i <= MCFileNum; i++){
            FileInputStream motorFiles = new FileInputStream(MCfile[i]);
            motorProp.load(motorFiles);
            if(motorProp.getProperty("MotorName").equals(MotorName)){
                filename = MCfile[i].getPath();
                break;
            }
        }
        return filename;
    }
    public void exitMotors() throws IOException{
        int MCFileNum = new File("WIPLib 2022 Template\\src\\main\\java\\frc\\robot\\subsystems\\motorConfigs").listFiles().length;
        File[] MCfile = new File("WIPLib 2022 Template\\src\\main\\java\\frc\\robot\\subsystems\\motorConfigs").listFiles();
        for(int i = 0; i <= MCFileNum; i++){
            FileInputStream motorFiles = new FileInputStream(MCfile[i]);
            motorProp.load(motorFiles);
            motorProp.setProperty("loaded","false");
            
        }
    }

}