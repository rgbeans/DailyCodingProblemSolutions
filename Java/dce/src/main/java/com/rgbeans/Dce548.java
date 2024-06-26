package com.rgbeans;

public class Dce548 {

    private Dce548() {}
    
    public static int timeToAngle(String time) {
        int hour2deg = 30;

        int min2deg = 6;

        if (time.length() != 5) throw new IllegalArgumentException("argument wrong size");

        String[] times = time.split(":");

        if (times.length != 2) throw new IllegalArgumentException("argument must be split by a single ':'");

        if (times[0].length() != 2) throw new IllegalArgumentException("hours and minutes should be 2 digits each");

        int hours;
        try {
            hours = Integer.parseInt(times[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("hours must be an integer");
        }

        if (hours > 23 || hours < 0) throw new IllegalArgumentException("hours must be an integer from 00 to 23");
        if (hours > 12) hours -= 12;

        int minutes;
        try {
            minutes = Integer.parseInt(times[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("minutes must be an integer");
        }

        if (minutes > 59 || minutes < 0) throw new IllegalArgumentException("minutes must be an integer from 00 to 59");

        float thetaH = hours * hour2deg + (minutes * hour2deg) / 60.0f;
        float thetaM = minutes * (float)min2deg;

        float result = Math.abs(thetaH - thetaM);

        return Math.round(result);
    }
}
