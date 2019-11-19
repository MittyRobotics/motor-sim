package com.amhsrobotics.motorsim.simulator;

import com.amhsrobotics.motorsim.math.Conversions;
import com.amhsrobotics.motorsim.motors.CIMMotor;
import com.amhsrobotics.motorsim.motors.Motor;

public class SimTalon implements Runnable {
    private SystemModel model;
    private double voltage;
    private SimTalon master;
    boolean follower = false;

    public SimTalon(SystemModel systemModel){
        this.model = systemModel;
    }


    public void set(double percent){
        this.voltage = Math.min(Math.max(percent * 12,-12), 12);
    }

    public void setFollower(SimTalon master){
        this.master = master;
        follower = true;
    }

    /**
     * Run method, updates the motor's position with the voltage
     *
     * This is called every 10 ms
     */
    @Override
    public void run() {
        while(true){
            if(follower){
                model.updateModel(master.getVoltage());
            }
            else{
                model.updateModel(voltage);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public double getPosition(){
        return model.getPosition() * Conversions.M_TO_IN;
    }

    public double getVelocity(){
        return model.getVelocity() * Conversions.M_TO_IN;
    }


    public SystemModel getModel() {
        return model;
    }

    public void setModel(SystemModel model) {
        this.model = model;
    }

    public double getVoltage() {
        return voltage;
    }
}
