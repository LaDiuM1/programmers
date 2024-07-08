package org.programmers.level2;

import java.time.LocalTime;

public class Solution3ByLv2 {

    public int solution(int h1, int m1, int s1, int h2, int m2, int s2) {
        LocalTime startTime = LocalTime.of(h1, m1, s1);
        LocalTime endTime = LocalTime.of(h2, m2, s2);

        int count = 0;

        for (int i = startTime.toSecondOfDay(); i <= endTime.toSecondOfDay(); i++) {
            double secondDegree = getSecondDegree(i);
            double minuteDegree = getMinuteDegree(i);
            double hourDegree = getHourDegree(i);

            double differenceMinute = getDegreeDifference(secondDegree, minuteDegree);
            double differenceHour = getDegreeDifference(secondDegree, hourDegree);

            boolean checkMinuteAlarm = false;
            boolean checkHourAlarm = false;

            if (differenceMinute < 6 || differenceHour < 6) {
                for(double j = 0; j < 1000; j++) {
                    double millisecond =  i + j / 1000;

                    secondDegree = getSecondDegree(millisecond);
                    minuteDegree = getMinuteDegree(millisecond);
                    hourDegree = getHourDegree(millisecond);

                    differenceMinute = getDegreeDifference(secondDegree, minuteDegree);
                    differenceHour = getDegreeDifference(secondDegree, hourDegree);

                    if(secondDegree == 0 && minuteDegree == 0 && hourDegree == 0) {
                        count++;
                        break;
                    }

                    if (secondDegree >= minuteDegree && differenceMinute < 0.006 && !checkMinuteAlarm) {
                        count++;
                        checkMinuteAlarm = true;
                    }

                    if (secondDegree >= hourDegree && differenceHour < 0.006 && !checkHourAlarm) {
                        count++;
                        checkHourAlarm = true;
                    }

                    if (i == endTime.toSecondOfDay() || (checkMinuteAlarm && checkHourAlarm)) break;
                }
            }
        }

        return count;
    }

    static public double getDegreeDifference(double degree1, double degree2) {
        double difference = Math.abs(degree1 - degree2);
        return Math.min(difference, 360 - difference);
    }

    static public double getSecondDegree(double second) {
        return 360 * ((second % 60) / 60);
    }
    static public double getMinuteDegree(double secondOfMinute) {
        return 360 * ((secondOfMinute % 3600) / 3600);
    }
    static public double getHourDegree(double secondOfHour) {
        return 360.0 * ((secondOfHour % 43200) / 43200);
    }

}