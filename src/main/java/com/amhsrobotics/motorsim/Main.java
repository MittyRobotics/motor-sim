package com.amhsrobotics.motorsim;

public class Main {

	double velocity;
	public static void main(String[] args) {
		new MotorSimulator(new NEOMotor(), 2,30,1,4);
	}
	
}
