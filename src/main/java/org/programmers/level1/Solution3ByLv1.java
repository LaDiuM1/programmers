package org.programmers.level1;

import org.programmers.Application;

import java.util.*;

public class Solution3ByLv1 {
    static int YEAR_UNIT = 28 * 12;
    static int MONTH_UNIT = 28;

    public int[] solution(String today, String[] terms, String[] privacies) {
        int formattedToday = parseStrToInt(today);

        Map<String, Integer> dateStandard = new HashMap<>();
        for(String termInfo : terms) {
            dateStandard.put(termInfo.split(" ")[0]
                    , Integer.parseInt(termInfo.split(" ")[1]) * MONTH_UNIT
            );
        }

        List<Integer> resultList = new ArrayList<>();

        for(int i = 0; i < privacies.length; i++) {
            String[] splitedPrivace = privacies[i].split(" ");

            int formattedDate = parseStrToInt(splitedPrivace[0]);
            int extDate = dateStandard.get(splitedPrivace[1]);

            if(formattedDate + extDate <= formattedToday) resultList.add(i + 1);
        }


        return resultList.stream().mapToInt(Integer::intValue).toArray();
    }

    private int parseStrToInt(String str) {
        String[] splitedStr = str.split("\\.");

        return Integer.parseInt(splitedStr[0]) * YEAR_UNIT
                + Integer.parseInt(splitedStr[1]) * MONTH_UNIT
                + Integer.parseInt(splitedStr[2]);
    }


    public static void main(String[] args) {
        Solution3ByLv1 application = new Solution3ByLv1();
        String today = "2022.05.19";
        String[] terms = {"A 6", "B 12", "C 3"};
        String[] privacies = {"2021.05.02 A", "2021.07.01 B", "2022.02.19 C", "2022.02.20 C"};

        int[] result = application.solution(today, terms, privacies);

        System.out.println("Arrays.toString(result) = " + Arrays.toString(result));
    }
}
