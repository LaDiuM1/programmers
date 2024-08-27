package org.programmers.level3;

import java.util.HashMap;
import java.util.Map;

public class Solution15ByLv3 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/60059]
     * 2020 KAKAO BLIND RECRUITMENT
     * 자물쇠와 열쇠
     *
     * 이 문제는 자물쇠와 키에 대한 모든 가능한 경우를 확인해야 하는 문제로
     * 개인적으로 배열을 회전해야 하는 경우를 처음 경험하는 터라 효율적인 방법을 찾기 위해
     * 시간이 걸린 문제였다. 구상 후 결과적으로 배열 회전은 대각선 기준으로 행과 열 번호를 반전 후
     * 모든 행을 역순으로 정렬하는 방법으로 회전하였으며, 자물쇠 오픈 여부 확인은
     * 자물쇠 배열 길이만큼 오프셋을 줘서 좌상단부터 우하단까지 모든 위치를 검증하는
     * 방법으로 문제를 해결하였다. 이 문제는 다른 효율적인 알고리즘이 있을 것으로 느껴지는데
     * 동일한 유형을 풀어보며 감을 익혀나가야 할 것 같다.
     */

    int[] keyIndex;
    int[] lockIndex;
    int patternCount = 0;
    public boolean solution(int[][] key, int[][] lock) {
        keyIndex = new int[key.length];
        lockIndex = new int[lock.length];

        int j = lock.length - 1;
        for(int i = 0; i < key.length; i++) {
            keyIndex[i] = j;
            lockIndex[i] = i;
            j--;
        }

        for(int i = 0; i < lock.length; i++) {
            for(int k = 0; k < lock[0].length; k++) {
                if(lock[i][k] == 0) patternCount++;
            }
        }

        for(int i = 0; i < 4; i++) {
            if(checkPossibleOpen(key, lock)) return true;
            if(i < 3) rotate(key);
        }

        return false;
    }

    private boolean checkPossibleOpen(int[][] key, int[][] lock) {
        int keySize = key.length;
        int lockSize = lock.length;

        for (int offsetX = -keySize + 1; offsetX < lockSize; offsetX++) {
            for (int offsetY = -keySize + 1; offsetY < lockSize; offsetY++) {
                boolean isClosed = false;
                int count = 0;

                for (int i = 0; i < keySize; i++) {
                    for (int j = 0; j < keySize; j++) {
                        int lockX = offsetX + i;
                        int lockY = offsetY + j;

                        // 자물쇠와 열쇠가 겹치는 부분만 검사
                        if (lockX >= 0 && lockX < lockSize && lockY >= 0 && lockY < lockSize) {
                            if (key[i][j] == 1 && lock[lockX][lockY] == 1) {
                                isClosed = true; // 열쇠와 자물쇠의 돌기 부분이 겹치는 경우
                                break;
                            }
                            if (key[i][j] == 1 && lock[lockX][lockY] == 0) {
                                count++; // 열쇠의 돌기가 자물쇠의 홈을 채우는 경우
                            }
                        }
                    }
                    if (isClosed) break;
                }

                // 자물쇠의 모든 홈이 열쇠랑 맞물릴 경우
                if (!isClosed && count == patternCount) {
                    return true;
                }
            }
        }
        return false;
    }

    private void rotate(int[][] arr) {
        int n = arr.length;
        for(int i = 0; i < n; i++) {
            for(int j = i; j < n; j++) {
                int temp = arr[i][j];
                arr[i][j] = arr[j][i];
                arr[j][i] = temp;
            }
        }

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n / 2; j++) {
                int temp = arr[i][j];
                arr[i][j] = arr[i][n - 1 - j];
                arr[i][n - 1 - j] = temp;
            }
        }
    }

}