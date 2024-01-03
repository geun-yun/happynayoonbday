package src.main.java;

public class GlobalState {
    private static GlobalState instance = new GlobalState();
    private double[] totalPoints = {0,0,0}; // <--- 포인트 조작하는 곳

    private GlobalState() {}

    public static GlobalState getInstance() {
        return instance;
    }

    public double getTotalPoints(int index) {
        return totalPoints[index];
    }

    public void addPoints(int index, double points) {
        totalPoints[index] += points;
    }

    public void resetPoints() {
        totalPoints[0] = 0;
        totalPoints[1] = 0;
        totalPoints[2] = 0;
    }
}

