package com.amhsrobotics.motorsim.motors;

public class Motor {
	
	private final double stallTorque;
	private final double stallCurrent;
	private final double freeSpeed;
	private final double freeCurrent;
	
	public Motor(double stallTorque, double stallCurrent, double freeSpeed, double freeCurrent){
		this.stallTorque = stallTorque;
		this.stallCurrent = stallCurrent;
		this.freeSpeed = freeSpeed;
		this.freeCurrent = freeCurrent;
	}
	
	public double getStallTorque() {
		return stallTorque;
	}
	
	public double getStallCurrent() {
		return stallCurrent;
	}
	
	public double getFreeSpeed() {
		return freeSpeed;
	}
	
	public double getFreeCurrent() {
		return freeCurrent;
	}
}
