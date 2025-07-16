import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimer {
    private int secondsElapsed = 0;
    private boolean started = false;
    private final Timer timer;
    private final JLabel display;

    public GameTimer(JLabel displayLabel) {
        this.display = displayLabel;
        this.timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsElapsed++;
                display.setText("   Time: " + secondsElapsed + "s");
            }
        });
    }

    public void start() {
        if (!started) {
            timer.start();
            started = true;
        }
    }

    public void stop() {
        timer.stop();
    }

    public void reset() {
        stop();
        secondsElapsed = 0;
        started = false;
        display.setText("   Time: 0s");
    }
}