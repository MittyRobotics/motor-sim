package com.amhsrobotics.motorsim.simulator;

import com.amhsrobotics.motorsim.math.Conversions;

import java.util.Set;

public class ControlLoop {
	
	private double lastError = 0;
	private double lastMeasured = 0;
	private double totalError = 0;
	
	private ControlLoopType type;
	private double iterationTime;
	private double maxVoltage;
	
	private double Kv;
	private double Ka;
	private double Kp;
	private double Ki;
	private double Kd;
	private double Kf;
	
	public ControlLoop(ControlLoopType type, double maxVoltage, double iterationTime){
		this.type = type;
		this.maxVoltage = maxVoltage;
		this.iterationTime = iterationTime;
	}
	
	public void setupVelocityController(double Kv, double Ka, double Kp){
		this.Kv = Kv;
		this.Ka = Ka;
		this.Kp = Kp;
	}
	
	
	public void setupPIDFController(double Kp, double Ki, double Kd, double Kf){
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;
		this.Kf = Kf;
	}
	
	public double update(double target, double measured){
		target = target * Conversions.M_TO_IN;
		measured = measured * Conversions.M_TO_IN;
		switch(type){
			case PIDF:
				return PIDFControl(target,measured);
			case VELOCITY:
				return velocityControl(target,measured);
		}
		return 0;
	}
	
	private double velocityControl(double target, double measured){
		double voltage = 0;
		
		double FF = Kv * target + Ka * ((measured - lastMeasured)/iterationTime);
		
		double error = target - measured;
		
		double FB = Kp * error;

		voltage = FF + FB;
		
		voltage = Math.max(-maxVoltage, Math.min(maxVoltage,voltage));
		
		lastMeasured = measured;
		lastError = error;
		
		return voltage;
	}
	
	double integral = 0;
	
	private double PIDFControl(double target, double measured){
		double voltage = 0;
		
		double error = target - measured;
		
		integral = integral + error * iterationTime;
		double derivative = (error-lastError) / iterationTime;
		
		voltage = Kp * error + Ki * integral + Kd * derivative + Kf * target;
		
		voltage = Math.max(-maxVoltage, Math.min(maxVoltage,voltage));
		
		lastMeasured = measured;
		lastError = error;
		
		return voltage;
	}
}
