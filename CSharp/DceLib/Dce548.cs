namespace DceLib;

public static class Dce548
{
    /// <summary>
    /// Given a clock time in hours and minutes, get the angle between the
    /// hour hand and the minute hand, to the nearest degree
    /// </summary>
    /// <param name="time">the time, in hh:mm format</param>
    /// <returns>the angle between the hour hand and the minute hand, to the nearest degree</returns>
    public static int TimeToAngle(string time)
    {
        //each hour on the clock is 30 degrees
        int hour2deg = 30;
        
        //each minute is 6 degrees
        int min2deg = 6;

        //ensure time is 5 digits long
        if (time.Length != 5) throw new ArgumentException("argument too long");

        //split time into hours and minutes
        string[] times = time.Split(':');

        //ensure there is only 2 segments 
        if (times.Length != 2) throw new ArgumentException("argument must be split by a single ':'");

        //ensure that hours and minutes are only 2 digits
        if (times[0].Length != 2) throw new ArgumentException("hours and minutes should be 2 digits each");

        //try and parse hours into an int
        if (!int.TryParse(times[0], out int hours)) throw new ArgumentException("hours must be an integer");

        //ensure hours are valid
        if (hours > 23 || hours < 0) throw new ArgumentException("hours must be an integer from 00 to 23");
        if (hours > 12) hours -= 12;

        //try and parse minutes into an int
        if (!int.TryParse(times[1], out int minutes)) throw new ArgumentException("minutes must be an integer");

        //ensure minutes are valid
        if (minutes > 59 || minutes < 0) throw new ArgumentException("minutes must be an integer from 00 to 59");

        float thetaH = hours * hour2deg + minutes * hour2deg / 60.0f;
        float thetaM = minutes * min2deg;

        //convert hours and minutes to degrees, and then find the difference in the angles
        float result = MathF.Abs(thetaH - thetaM);

        //return the result
        return (int)MathF.Round(result);
    }
}
