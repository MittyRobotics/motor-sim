package com.amhsrobotics.motorsim.simulator;

import com.amhsrobotics.motorsim.graph.RobotGraph;

import javax.swing.*;

public class SimRobot implements Runnable{
    private final SimDrivetrain drivetrain;
    private final SimMechanism[] mechanisms;
    private final double periodTime;

    public SimRobot(SimDrivetrain drivetrain, SimMechanism[] mechanisms, double periodTime){
        this.drivetrain = drivetrain;
        drivetrain.initDrivetrain(periodTime);
        this.mechanisms = mechanisms;
        this.periodTime = periodTime;
        robotInit();
    }

    public SimDrivetrain getDrivetrain() {
        return drivetrain;
    }

    public SimMechanism[] getMechanisms() {
        return mechanisms;
    }

    public double getPeriodTime() {
        return periodTime;
    }

    @Override
    public void run() {
        while(true){
            robotPeriodic();
            try {
                Thread.sleep((long) (periodTime * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void robotInit(){

    }
    private void robotPeriodic(){
        arcadeDrive();
        drivetrain.updateDrivetrain();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RobotGraph.getInstance().graphRobot(drivetrain.getRobotX(), drivetrain.getRobotY(), drivetrain.getHeading(),20,40);
            }
        });
        System.out.println(drivetrain.getLeftVelociy());
        //System.out.println((double)Math.round(drivetrain.getRobotX()*100)/100 + "  " + (double)Math.round(drivetrain.getRobotY()*100)/100 +  " " + (double)Math.round(drivetrain.getHeading()*100)/100);
    }

    private void arcadeDrive(){
        double drive = 0;
        double turn = 0;

        if(SimOI.getInstance().isLeftKey()){
            turn = -10;
        }
        else if(SimOI.getInstance().isRightKey()){
            turn  = 10;
        }
        else{
            turn = 0;
        }
        if(SimOI.getInstance().isUpKey()){
            drive = 40;
        }
        else if(SimOI.getInstance().isDownKey()){
            drive = -40;
        }
        else{
            drive = 0;
        }

        getDrivetrain().setVelocities(drive + turn,drive - turn);

    }
}
