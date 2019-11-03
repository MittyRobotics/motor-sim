package com.amhsrobotics.motorsim;

import com.amhsrobotics.motorsim.math.Conversions;
import com.amhsrobotics.motorsim.motors.CIMMotor;
import com.amhsrobotics.motorsim.motors.Falcon500Motor;
import com.amhsrobotics.motorsim.motors.NEOMotor;
import com.amhsrobotics.motorsim.motors.Pro775Motor;
import com.amhsrobotics.motorsim.simulator.MotorSimulator;

public class Main {

	double velocity;
	public static void main(String[] args) {
		new MotorSimulator(new Falcon500Motor(), 4,125 * Conversions.LBS_TO_KG,1.0/50,4 * Conversions.IN_TO_M, 5, 12, "Falcon 500");
		new MotorSimulator(new NEOMotor(), 4,125 * Conversions.LBS_TO_KG,1.0/50,4 * Conversions.IN_TO_M, 5, 12, "NEO");
		new MotorSimulator(new CIMMotor(), 4,125 * Conversions.LBS_TO_KG,1.0/50,4 * Conversions.IN_TO_M, 5, 12, "CIM");
		new MotorSimulator(new Pro775Motor(), 4,125 * Conversions.LBS_TO_KG,1.0/50,4 * Conversions.IN_TO_M, 5, 12, "775pro");
	}
}
