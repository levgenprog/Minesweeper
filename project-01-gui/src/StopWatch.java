import processing.core.PApplet;

public class StopWatch extends PApplet {
    private int startTime, stopTime;
    private boolean isRunning;

    StopWatch() {
        startTime = 0;
        stopTime = 0;
        isRunning = false;
    }

    public void start() {
        startTime = millis();
        isRunning = true;
    }

    public void stop() {
        stopTime = getTime();
        isRunning = false;
    }

    public int getTime() {
        int time;
        if (isRunning) {
            int current = millis();
            time = current - startTime;
        } else {
            time = stopTime;
        }
        return time;
    }

    public int getSeconds() {
        return (getTime() / 1000) % 60;
    }
}
