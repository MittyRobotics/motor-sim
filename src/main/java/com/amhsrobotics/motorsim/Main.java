package com.amhsrobotics.motorsim;

import com.amhsrobotics.motorsim.math.Conversions;
import com.amhsrobotics.motorsim.motors.*;
import com.amhsrobotics.motorsim.simulator.MotorSimulator;

public class Main {
	
	public static void main(String[] args) {
		new MotorSimulator(new CIMMotor(), 4,125*Conversions.LBS_TO_KG,(42.0/11.0) * (50.0/24.0),4 * Conversions.IN_TO_M, 20, 12, "CIM motor");
		new MotorSimulator(new NEOMotor(), 4,125*Conversions.LBS_TO_KG,(42.0/11.0) * (50.0/24.0),4 * Conversions.IN_TO_M, 20, 12, "NEO motor");
		new MotorSimulator(new MiniCIMMotor(), 4,125*Conversions.LBS_TO_KG,(42.0/11.0) * (50.0/24.0),4 * Conversions.IN_TO_M, 20, 12, "Mini CIM motor");
		new MotorSimulator(new Pro775Motor(), 4,125*Conversions.LBS_TO_KG,(42.0/11.0) * (50.0/24.0),4 * Conversions.IN_TO_M, 20, 12, "775pro motor");
	}
}
