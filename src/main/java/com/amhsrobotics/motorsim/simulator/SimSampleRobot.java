package com.amhsrobotics.motorsim.simulator;

public class SimSampleRobot implements SimRobot {
	
	@Override
	public void robotInit() {
		SimSampleDrivetrain.getInstance().setSpeeds(1,1);

	}
	
	@Override
	public void robotPeriodic() {
	
	}
}
