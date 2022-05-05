package frc.robot.commands;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;             
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


/**
 * <h1>The class For running and loading motors.</h1>
 * 
 * declare variable by initiating like this:
 * {@code motor Motor = new motor(motorName, filePath);}
 * 
 * use {@code motor.run(speed) ;} to move motor
 * <h2>Notes</h2>
 * <ul>
 *  <li>The motor name is the name of the motor in the config file.</li>
 *  <li>The file path is the relative path to the folder CONTAINING config file.</li>
 *  <li>The speed is a double value. ex.{@code motor.run(0.5);} will run the motor at %50 power.</li>
 *  <li>!!DO NOT SET THE SPEED ABOVE 1.0 UNLESS YOU ARE ABSOLUTLY SURE OF WHAT YOU ARE DOING!!</li>
 * </ul>
 */
public class motor {
    // Declare motor variables
    private String motorType;
    TalonSRX talonMotor;
    VictorSPX victorMotor;
    TalonFX falconMotor;
    

    /**
     * <h1>The constructor for motor class.</h1>
     * 
     * @param motorName The name of the motor
     * @param filePath The path to the file
     */
    public motor (String motorName, String filePath) {
        loadMotor(motorName, filePath);
    }
    /**
    * Returns a Talon SRX motor object with the given properties
    * @param motorProp The motor properties file to read
    * @return Returns the new motor object
    * @
    */
    private TalonSRX loadTalon(Properties motorProp) {
        return new TalonSRX(Integer.parseInt(motorProp.getProperty("MotorPort")));
    }
   /**
    * Returns a Falcon FX motor object with the given properties
    * @param motorProp The motor properties file to read
    * @return Returns the new motor object
    */
    private TalonFX loadFalcon(Properties motorProp) {
        return new TalonFX(Integer.parseInt(motorProp.getProperty("MotorPort")));
    }
    /**
    * Returns a Victor (Falcon FX) motor object with the given properties
    * @param motorProp The motor properties file to read
    * @return Returns the new motor object
    */
    private VictorSPX loadVictor(Properties motorProp) {
        return new VictorSPX(Integer.valueOf(motorProp.getProperty("MotorPort")));
    }
    /**
     * Loads and sets the correct CAN controller type
     * @param motorName The name of the motor
     * @param filePath The path to the file
     */
    private void loadMotor(String motorName, String filePath) {
        Properties motorProp = new Properties();
        String motorFile = getMotorFilename(motorName, filePath);
        FileReader motorFiles;
        try {
            motorFiles = new FileReader(motorFile);
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
    /**
     * Searches for the motor config file in the folder given by checking each file for the matching name property
     * @param motorName The name of the motor
     * @param filePath The path to the folder
     * @return Returns the path to the motor config file as a string
     */
    private String getMotorFilename(String MotorName, String filePath){
        Properties motorProp = new Properties();
        int MCFileNum = new File(filePath).listFiles().length;
        File[] MCfile = new File(filePath).listFiles();
        String filename = "";
        
        for(int i = 0; i <= MCFileNum; i++){
            FileReader motorFiles;
            try {
                motorFiles = new FileReader(MCfile[i]);
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
    /**
     * Runs the motor at the given speed
     * @param speed The speed to run the motor at
     * <h2>NOTES:</h2>
     * <ul>
     * <li>The speed is a double value. ex.{@code motor.run(0.5);} will run the motor at %50 power.</li>
     * <li>!!DO NOT SET THE SPEED ABOVE 1.0 UNLESS YOU ARE ABSOLUTLY SURE OF WHAT YOU ARE DOING!!</li>
     */
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

    public void runForTime(double speed,int time){
        run(speed);
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        run(0);
    }
    /**
     * Stops the motor
     */
    public void stop(){
        run(0);
    }
}

