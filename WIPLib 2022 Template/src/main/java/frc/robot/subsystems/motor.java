package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class motor {
    // Declare motor variables
    TalonSRX talonMotor;
    VictorSPX victorMotor;
    int MCFiles = new File("WIPLib 2022 Template\\src\\main\\java\\frc\\robot\\subsystems\\motorConfigs").listFiles().length
    private TalonSRX loadTalon(String filename){
        Properties motorProp = new Properties()
        FileInputStream motorFiles = new FileInputStream(filename);
        motorProp.load(motorFiles);
        talonMotor.set(ControlMode.PercentOutput, Double.parseDouble(motorProp.getProperty("MotorPort")));
        return talonMotor;
    }
    private VictorSPX loadVictor(){

    }
}