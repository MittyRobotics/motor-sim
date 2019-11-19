package com.amhsrobotics.motorsim.simulator;

import com.amhsrobotics.motorsim.math.Conversions;

public class SimDrivetrain {
    private final SimTalon[] leftTalons;
    private final SimTalon[] rightTalons;
    private final double mass;
    private final double gearRatio;
    private final double wheelRadius;
    private final double width;
    private double periodTime;

    public SimDrivetrain(SimTalon[] leftTalons, SimTalon[] rightTalons, double mass, double gearRatio, double wheelRadius, double width) {
        this.leftTalons = leftTalons;
        this.rightTalons = rightTalons;
        this.mass = mass * Conversions.LBS_TO_KG;
        this.gearRatio = gearRatio;
        this.wheelRadius = wheelRadius * Conversions.IN_TO_M;
        this.width = width;
    }

    public void initDrivetrain(double periodTime) {
        this.periodTime = periodTime;
        double massPerSide = mass / 2;
        double massPerLeftSide = massPerSide / leftTalons.length;
        double massPerRightSide = massPerSide / rightTalons.length;
        System.out.println(massPerLeftSide);
        for (int i = 0; i < leftTalons.length; i++) {

            new Thread(leftTalons[i]).start();

            if (i > 0) {
                leftTalons[i].setFollower(leftTalons[0]);
            }

            leftTalons[i].getModel().initSystemModel(massPerLeftSide, gearRatio, wheelRadius, 0.01);
        }
        for (int i = 0; i < rightTalons.length; i++) {

            new Thread(rightTalons[i]).start();
            if (i > 0) {
                rightTalons[i].setFollower(rightTalons[0]);
            }
            rightTalons[i].getModel().initSystemModel(massPerRightSide, gearRatio, wheelRadius, 0.01);
        }
    }

    public void setSpeeds(double left, double right) {
        leftTalons[0].set(left);
        rightTalons[0].set(right);
    }

    public void setVelocities(double left, double right) {
        leftTalons[0].set(getPercentOutputFromPIDF(left,leftTalons[0].getVelocity(), 1));
        rightTalons[0].set(getPercentOutputFromPIDF(right,rightTalons[0].getVelocity(), 1));
    }

    double integral;
    double lastError;

    private double getPercentOutputFromPIDF(double target, double measured, double maxPercent) {
        double Kp = .2;
        double Ki = 0.0;
        double Kd = 0.0;
        double Kf = 0.0;


        double percent = 0;

        double error = target - measured;

        integral = integral + error * periodTime;
        double derivative = (error - lastError) / periodTime;

        percent = Kp * error + Ki * integral + Kd * derivative + target * Kf;

        percent = Math.max(-maxPercent, Math.min(maxPercent, percent));

        lastError = error;

        return percent;
    }


    public void updateDrivetrain() {
        odometry();
    }

    double prevLeftPos = 0;
    double prevRightPos = 0;

    private double x;
    private double y;
    private double heading;

    public void odometry() {

        double deltaLeftPos = getLeftPosition() - prevLeftPos;
        double deltaRightPos = getRightPosition() - prevRightPos;

        double deltaPos = (deltaLeftPos + deltaRightPos) / 2;

        heading += Math.toDegrees(Math.atan2((deltaLeftPos - deltaRightPos), width));

        y += Math.cos(Math.toRadians(heading)) * deltaPos;
        x += Math.sin(Math.toRadians(heading)) * deltaPos;

        prevLeftPos = getLeftPosition();
        prevRightPos = getRightPosition();
    }

    public void setOdometry(double x, double y, double heading) {
        this.x = x;
        this.y = y;
        this.heading = heading;
    }


    public double getLeftPosition() {
        return leftTalons[0].getPosition();
    }

    public double getRightPosition() {
        return rightTalons[0].getPosition();
    }

    public double getLeftVelociy() {
        return leftTalons[0].getVelocity();
    }

    public double getRightVelocity() {
        return rightTalons[0].getVelocity();
    }

    public double getRobotX() {
        return x;
    }

    public double getRobotY() {
        return y;
    }

    public double getHeading() {
        return heading;
    }


    public SimTalon[] getLeftTalons() {
        return leftTalons;
    }

    public SimTalon[] getRightTalons() {
        return rightTalons;
    }

    public double getMass() {
        return mass;
    }

    public double getGearRatio() {
        return gearRatio;
    }

    public double getWheelRadius() {
        return wheelRadius;
    }
}
