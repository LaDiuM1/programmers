package org.programmers.other;

import java.util.ArrayList;
import java.util.Arrays;

public class Solution2 {

    /**
     * ### 문제:
     *
     * 팩토리얼을 계산하는 프로그램을 작성하여 100!을 정수 형태로 출력하다.
     * (단, 기본 자료형만을 사용할 것. BigInteger 등은 사용할 수 없음)
     *
     */

    StringBuilder sb = new StringBuilder();

    public void solution() {
        double factorial =  getFactorial(100);
        parseIntegerToString(factorial);
        System.out.println("sb.toString() = " + sb.toString());
    }

    private double getFactorial(int n) {
        if(n == 1) {
            return 1.0;
        }

        return getFactorial(n - 1) * n;
    }

    private double parseIntegerToString(double value) {
        if(value == 0) {
            return 0;
        }
        sb.append(value % 10);
        return parseIntegerToString(value / 10);
    }

    public static void main(String[] args) {
        Solution2 application = new Solution2();

        application.solution();
//        System.out.println("result = " + result);
    }
}