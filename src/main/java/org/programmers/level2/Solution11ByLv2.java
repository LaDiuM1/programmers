package org.programmers.level2;

public class Solution11ByLv2 {

    /**
     * 2중 반복문을 이용할 경우 시간복잡도가 매우 안좋아 DP 사용을 고려하였으나
     * DP 알고리즘도 현재 케이스에서는 효율적이지 못하다고 판단하여 효율적인 알고리즘을 검색,
     * 슬라이딩 윈도우 학습하여 문제 해결 진행
     */

    public int[] solution(int[] sequence, int k) {
        int start = 0;
        int end = 0;
        long currentSumValue = 0;
        int currentLength = Integer.MAX_VALUE;
        int[] result = new int[2];

        while (end < sequence.length) {
            if(sequence[end] == k) return new int[]{end, end};
            currentSumValue += sequence[end];

            while(currentSumValue >= k) {
                int length = end - start;
                if(currentSumValue == k && length < currentLength) {
                    result = new int[]{start, end};
                    currentLength = length;
                }

                currentSumValue -= sequence[start];
                start++;
            }

            end++;
        }

        return result;
    }

}