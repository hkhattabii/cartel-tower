package hkhattabi.models;

public class Trajectory {
    private double m;
    private double p;
    private int direction;

    public Trajectory(double m, double p, int direction) {
        this.m = m;
        this.p = p;
        this.direction = direction;
    }


    public double getM() {
        return m;
    }

    public double getP() {
        return p;
    }

    public int getDirection() {
        return direction;
    }
}
