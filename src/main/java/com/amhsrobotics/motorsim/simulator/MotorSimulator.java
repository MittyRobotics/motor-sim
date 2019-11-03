package com.amhsrobotics.motorsim.simulator;

import com.amhsrobotics.motorsim.graph.Graph;
import com.amhsrobotics.motorsim.math.Conversions;
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
	
	private double maxVoltage;
	
	private double velocity = 0;
	private double position = 0;
	
	public MotorSimulator(Motor motor, double numMotors, double mass, double gearRatio, double simulationTime, double maxVoltage, double wheelRadius) {
		this(motor, numMotors, mass, gearRatio, wheelRadius, simulationTime, maxVoltage, "Motor Sim");
	}
	
	public MotorSimulator(Motor motor, double numMotors, double mass, double gearRatio, double wheelRadius, double simulationTime, double maxVoltage, String name) {
		this.motor = motor;
		this.numMotors = numMotors;
		this.mass = mass;
		this.gearRatio = gearRatio;
		this.wheelRadius = wheelRadius;
		this.maxVoltage = maxVoltage;
		
		double stallTorque = motor.getStallTorque();
		double stallCurrent = motor.getStallCurrent();
		double freeSpeed = motor.getFreeSpeed();
		double freeCurrent = motor.getFreeCurrent();
		this.resistance = 12 / stallCurrent;
		this.Kv = ((freeSpeed / 60 * 2 * Math.PI) / (12 - resistance * freeCurrent));
		this.Kt = (numMotors * stallTorque) / stallCurrent;
		
		Graph graph = new Graph(name);
		
		double iterationTime = 0.01;
		
		double setpoint = 10 * Conversions.IN_TO_M;
		
		double threshold = 0.1;
		
		double t = 0.0;
		
		while(t < simulationTime) {
			double voltage = controlLoop(setpoint,position,iterationTime);
			double acceleration = getAcceleration(voltage);
			position += velocity * iterationTime;
			velocity += acceleration * iterationTime;
			graph.addPosition(position,t);
			graph.addVelocity(velocity, t);
			graph.addVoltage(voltage,t);
			t += iterationTime;
		}
	}
	
	double lastError = 0;
	
	private double controlLoop(double setpoint, double position, double iterationTime){
		double voltage = 0;
		
		double Kp = 2;
		
		double error = setpoint - position;
		lastError = error;
		
		voltage = Kp * error;
		
		voltage = Math.max(-maxVoltage, Math.min(maxVoltage,voltage));
		
		return voltage;
	}
	
	private double getAcceleration(double voltage) {
		return -Kt * gearRatio * gearRatio / (Kv * resistance * wheelRadius * wheelRadius * mass) * velocity + gearRatio * Kt / (resistance * wheelRadius * mass) * voltage;
	}
}
