package org.programmers.level2;

import java.time.LocalTime;

public class Solution3ByLv2 {

    public int solution(int h1, int m1, int s1, int h2, int m2, int s2) {
        LocalTime startTime = LocalTime.of(h1, m1, s1);
        LocalTime endTime = LocalTime.of(h2, m2, s2);

        int count = 0;

        boolean beforeMinuteStatus = true;
        boolean beforeHourStatus = true;

        for (int i = startTime.toSecondOfDay(); i <= endTime.toSecondOfDay(); i++) {
            int second = i % 60;
            int secondOfMinute = i % 3600;
            int secondOfHour = i % 43200;

            double secondsDegree = 360.0 / (60.0 / second);
            double minutesDegree = 360.0 / (3600.0 / secondOfMinute);
            double hoursDegree = 360.0 / (43200.0 / secondOfHour);

            boolean checkNegativeMinutes = secondsDegree - minutesDegree < 0;
            boolean checkNegativeHours = secondsDegree - hoursDegree < 0;

            if ((beforeMinuteStatus && !checkNegativeMinutes) || (beforeHourStatus && !checkNegativeHours)) {
                count++;
            }

            beforeMinuteStatus = checkNegativeMinutes;
            beforeHourStatus = checkNegativeHours;
        }

        return count;
    }
}