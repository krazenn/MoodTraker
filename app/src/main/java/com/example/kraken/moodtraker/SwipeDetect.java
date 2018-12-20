package com.example.kraken.moodtraker;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.kraken.moodtraker.controller.MainActivity;

public class SwipeDetect extends GestureDetector.SimpleOnGestureListener {

    // Minimal x and y axis swipe distance.
    private static int MIN_SWIPE_DISTANCE_X = 100;
    private static int MIN_SWIPE_DISTANCE_Y = 100;

    // Maximal x and y axis swipe distance.
    private static int MAX_SWIPE_DISTANCE_X = 1000;
    private static int MAX_SWIPE_DISTANCE_Y = 1000;

    // Source activity that display message in text view.
    private MainActivity activity = null;

    public MainActivity getActivity() {
        return activity;
    }

    public void setActivity(MainActivity activity) {
        this.activity = activity;
    }

    /* This method is invoked when a swipe gesture happened. */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        // Get swipe delta value in x axis.
        float deltaX = e1.getX() - e2.getX();

        // Get swipe delta value in y axis.
        float deltaY = e1.getY() - e2.getY();

        // Get absolute value.
        float deltaYAbs = Math.abs(deltaY);

        if ((deltaYAbs >= MIN_SWIPE_DISTANCE_Y) && (deltaYAbs <= MAX_SWIPE_DISTANCE_Y)) {
            if (deltaY > 0) {
                Toast.makeText(this.activity, "up", Toast.LENGTH_LONG).show();
            } else {

            }
            this.activity.nextMoodTheme();
        }
        return true;
    }
}
