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
    TalonSRX talonMotor;
    VictorSPX victorMotor;
    TalonFX falconMotor;
    Properties motorProp = new Properties();
    int MCFileNum = new File("WIPLib 2022 Template\\src\\main\\java\\frc\\robot\\subsystems\\motorConfigs").listFiles().length;
    private TalonSRX loadTalon(String filename) throws IOException{
        FileInputStream motorFiles = new FileInputStream(filename);
        motorProp.load(motorFiles);
        TalonSRX talonMotor = new TalonSRX(Integer.parseInt(motorProp.getProperty("motorID")));
        return talonMotor;
    }
    //
    private TalonFX loadFalcon(String filename) throws IOException{
        FileInputStream motorFiles = new FileInputStream(filename);
        motorProp.load(motorFiles);
        TalonFX falconMotor = new TalonFX(Integer.parseInt(motorProp.getProperty("motorID")));
        return falconMotor;
    }
    private VictorSPX loadVictor(String filename) throws IOException{
        FileInputStream motorFiles = new FileInputStream(filename);
        motorProp.load(motorFiles);
        VictorSPX victorMotor = new VictorSPX(Integer.valueOf(motorProp.getProperty("MotorPort")));
        return victorMotor;
    }
    public void driveMotor(String motorName, Double motorSpeed) throws IOException{
        String motorFile = getMotorFilename(motorName);
        FileInputStream motorFiles = new FileInputStream(motorFile);
        motorProp.load(motorFiles);
        String motorType = motorProp.getProperty("motorType");
        if(motorType.equals("TalonSRX")){
            talonMotor = loadTalon(motorFile);
            talonMotor.set(ControlMode.PercentOutput, motorSpeed);
        }
        else if(motorType.equals("VictorSPX")){
            victorMotor = loadVictor(motorFile);
            victorMotor.set(ControlMode.PercentOutput, motorSpeed);
        } 
        else if(motorType.equals("Falcon")){
            falconMotor = loadFalcon(motorFile);
            falconMotor.set(ControlMode.PercentOutput, motorSpeed);
        } 
        else if(motorType.equals("TalonFX")){
            falconMotor = loadFalcon(motorFile);
            falconMotor.set(ControlMode.PercentOutput, motorSpeed);
        } 
        else {
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


}
