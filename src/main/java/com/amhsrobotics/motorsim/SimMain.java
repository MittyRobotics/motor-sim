package com.amhsrobotics.motorsim;

import com.amhsrobotics.motorsim.motors.CIMMotor;
import com.amhsrobotics.motorsim.motors.Falcon500Motor;
import com.amhsrobotics.motorsim.motors.Motor;
import com.amhsrobotics.motorsim.simulator.*;

public class SimMain {


    public static void main(String[] args) {
        Motor motor = new CIMMotor();
        SimTalon[] leftTalons = new SimTalon[]{new SimTalon(new SystemModel(motor)), new SimTalon(new SystemModel(motor))};
        SimTalon[] rightTalons = new SimTalon[]{new SimTalon(new SystemModel(motor)), new SimTalon(new SystemModel(motor))};
        SimDrivetrain drivetrain = new SimDrivetrain(leftTalons,rightTalons, 40,7,2, 20);

        SimRobot robot = new SimRobot(drivetrain,null,.02);
        new Thread(robot).start();
        SimOI.getInstance();
    }

}

