package org.programmers.other;

import java.util.ArrayList;
import java.util.Arrays;

public class Solution1 {

    /**
     * ### 문제:
     *
     * 아래의 구성에서 보듯이 A + B를 계산해서 C를 구하려고 한다.
     * 아래의 조건을 만족하는 A, B, C의 값을 구하여, 아래의 출력 요구 사항을 만족하라.
     *
     * 1. A는 3자리의 수, B는 3자리의 수, C는 4자리의 수이다.
     * 2. 0에서 9까지의 숫자를 이용하여 A, B, C를 구성할 수 있다.
     * 3. 모든 숫자는 A, B, C 전체에서 한 번씩만 사용할 수 있다.
     * 4. A는 B보다 항상 커야 한다.
     *
     * 예)
     *   6  5  7
     * + 3  5  2
     * ---------
     *   1  0  8  9
     *
     * ### 문제) C의 값이 1030~1090이 나오는 구성의 총 개수를 출력하고, 각 구성에서 사용된 A의 합을 출력하라.
     *
     */


    int count = 0;
    int sumValue = 0;

    public int[] solution() {
        dfs(new ArrayList<>(), new boolean[10]);

        return new int[]{count, sumValue};
    }

    private void dfs(ArrayList<Integer> now, boolean[] visited) {
        if(now.size() == 10) {
            int a = now.get(0) * 100 + now.get(1) * 10 + now.get(2);
            int b = now.get(3) * 100 + now.get(4) * 10 + now.get(5);
            int c1 = now.get(6);
            int c2 = now.get(7);
            int c3 = now.get(8);
            int c4 = now.get(9);
            int c = a + b;

            boolean validC = c1 == c / 1000 && c2 == c % 1000 / 100 && c3 == c % 100 / 10 && c4 == c % 10;

            if(validC && a > b && c >= 1030 && c <= 1090) {
                count++;
                sumValue += a;
            }
            return;
        }

        for(int i = 0; i < 10; i++) {
            if(!visited[i]) {
                visited[i] = true;
                now.add(i);
                dfs(now, visited);
                now.remove(now.size() -1);
                visited[i] = false;
            }
        }
    }

    public static void main(String[] args) {
        Solution1 application = new Solution1();
        int n = 5;

        int[] result = application.solution();
        System.out.println("Arrays.toString(result) = " + Arrays.toString(result));
    }
}