package com.amhsrobotics.motorsim;
		
		import com.amhsrobotics.motorsim.graph.Graph;
		import com.amhsrobotics.motorsim.math.Conversions;
		import com.amhsrobotics.motorsim.motors.*;
		import com.amhsrobotics.motorsim.simulator.ControlLoop;
		import com.amhsrobotics.motorsim.simulator.ControlLoopType;
		import com.amhsrobotics.motorsim.simulator.ControlType;
		import com.amhsrobotics.motorsim.simulator.MotorSimulator;
		
		import java.io.FileWriter;
		import java.io.IOException;
		import java.util.ArrayList;
		import java.util.Arrays;
		import java.util.List;


public class Main {
	
	public static void main(String[] args) {
		double maxVoltage = 12;
		double iterationTime = 0.02;
		double setpoint = 48 * Conversions.IN_TO_M;
		ControlType controlType = ControlType.POSITION;
		
		ControlLoop controlLoop = new ControlLoop(ControlLoopType.PIDF, maxVoltage,0.06);
		controlLoop.setupPIDFController(4,0,0,0);
		
		double mass = 30.0 * Conversions.LBS_TO_KG; //Kg
		double gearRatio = 42.0/11.0 * 50.0/24.0;
		double wheelRadius = 2*Conversions.IN_TO_M; //Meters
		
			MotorSimulator motorSimulator = new MotorSimulator(new CIMMotor(), 4,mass,gearRatio,wheelRadius, controlLoop, controlType, "CIM motor");
		Graph graph = new Graph("CIM Motor");
		
		double t = 0.0;
		
		// Our example data
		List<List<String>> rows = new ArrayList<>();
		
		while(t < 5) {
			
			motorSimulator.update(setpoint, iterationTime);
			graph.addPosition(motorSimulator.getPosition(),t);
			graph.addVelocity(motorSimulator.getVelocity(), t);
			graph.addVoltage(motorSimulator.getVoltage(),t);
			graph.addSetpoint(setpoint,t);
			t += iterationTime;
			rows.add( Arrays.asList(t + "", setpoint+ "", motorSimulator.getPosition()+ ""));
		}
	}
	
	private static void writeToCSV(List<List<String>> rows) throws IOException {
		

		FileWriter csvWriter = new FileWriter("data.csv");
		csvWriter.append("Time");
		csvWriter.append(",");
		csvWriter.append("Input");
		csvWriter.append(",");
		csvWriter.append("Output");
		csvWriter.append("\n");
		
		for (List<String> rowData : rows) {
			csvWriter.append(String.join(",", rowData));
			csvWriter.append("\n");
		}
		
		csvWriter.flush();
		csvWriter.close();
	}
}
