package src.main.java;

public class GlobalState {
    private static GlobalState instance = new GlobalState();
    private double totalPoints = 0;

    private GlobalState() {}

    public static GlobalState getInstance() {
        return instance;
    }

    public double getTotalPoints() {
        return totalPoints;
    }

    public void addPoints(double points) {
        totalPoints += points;
    }

    public void resetPoints() {totalPoints = 0;}
}

