package com.amhsrobotics.motorsim;

import com.amhsrobotics.motorsim.graph.RobotGraph;
import com.amhsrobotics.motorsim.motors.CIMMotor;
import com.amhsrobotics.motorsim.motors.Falcon500Motor;
import com.amhsrobotics.motorsim.motors.Motor;
import com.amhsrobotics.motorsim.simulator.*;

public class SimMain {
    public static void main(String[] args) {
        SimSampleRobot robot = new SimSampleRobot();
        RobotSimManager.getInstance().setupRobotSimManager(robot,SimSampleDrivetrain.getInstance(), 125,7,2,20,30,0.02);
        SimOI.getInstance();
    }

}

