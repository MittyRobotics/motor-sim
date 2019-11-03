package com.amhsrobotics.motorsim.simulator;

import com.amhsrobotics.motorsim.graph.Graph;
import com.amhsrobotics.motorsim.motors.Motor;

/**
 * Simulates a mechanism given data on the motors that drive it.
 * <p>
 * References:
 * The voltage to acceleration calculations are from 971's series about control system modeling:
 * https://www.youtube.com/watch?v=uGtT8ojgSzg
 * https://www.youtube.com/watch?v=RLrZzSpHP4E
 */

public class MotorSimulator {
	
	private Motor motor;
	private double numMotors;
	private double mass; //kg
	private double gearRatio;
	private double wheelRadius; //m
	
	private double Kv; //velocity constant
	private double Kt; //torque constant
	private double resistance;
	
	double velocity = 0;
	double position = 0;
	
	public MotorSimulator(Motor motor, double numMotors, double mass, double gearRatio, double simulationTime, double voltage, double wheelRadius) {
		this(motor, numMotors, mass, gearRatio, wheelRadius, simulationTime, voltage, "Motor Sim");
	}
	
	public MotorSimulator(Motor motor, double numMotors, double mass, double gearRatio, double wheelRadius, double simulationTime, double voltage, String name) {
		this.motor = motor;
		this.numMotors = numMotors;
		this.mass = mass;
		this.gearRatio = gearRatio;
		this.wheelRadius = wheelRadius;
		
		double stallTorque = motor.getStallTorque();
		double stallCurrent = motor.getStallCurrent();
		double freeSpeed = motor.getFreeSpeed();
		double freeCurrent = motor.getFreeCurrent();
		this.resistance = 12 / stallCurrent;
		this.Kv = ((freeSpeed / 60 * 2 * Math.PI) / (12 - resistance * freeCurrent));
		this.Kt = (numMotors * stallTorque) / stallCurrent;
		
		Graph graph = new Graph(name);
		
		double iterationTime = 0.01;
		
		for (double i = 0; i < simulationTime; i += iterationTime) {
			double acceleration = getAcceleration(voltage);
			position += velocity * iterationTime;
			velocity += acceleration * iterationTime;
			graph.addPosition(position, i);
			graph.addVelocity(velocity, i);
		}
	}
	
	double getAcceleration(double voltage) {
		return -Kt * gearRatio * gearRatio / (Kv * resistance * wheelRadius * wheelRadius * mass) * velocity + gearRatio * Kt / (resistance * wheelRadius * mass) * voltage;
	}
}
