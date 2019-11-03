package com.amhsrobotics.motorsim;

import com.amhsrobotics.motorsim.motors.NEOMotor;
import com.amhsrobotics.motorsim.simulator.MotorSimulator;

public class Main {

	double velocity;
	public static void main(String[] args) {
		new MotorSimulator(new NEOMotor(), 2,30,1,4);
	}
	
}
