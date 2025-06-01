package ControlPanel.Threads;

public class Task {
    private final int choice;
    private final int x;
    private final int y;

    public Task(int choice) {
        this(choice, -1, -1);
    }

    public Task(int choice, int x, int y) {
        this.choice = choice;
        this.x = x;
        this.y = y;
    }

    public int getChoice() {
        return choice;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}