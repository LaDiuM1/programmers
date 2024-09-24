package org.baekjoon.samsung_sw_aptitude;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution12ByGold3 {
    /**
     [https://www.acmicpc.net/problem/14890]
     삼성 SW 역량 테스트 기출 문제
     경사로

     이 문제는 2차원 배열에서 각 행 또는 열의 전체가 하나의 길이며
     높이가 같아야 건널 수 있고, 건널 수 없다면 높이 차이가 1 이하일
     때 주어진 길이에 맞춰 경사로를 겹치지 않게 설치해야 건널 수 있다는
     흥미로운 문제이다.

     현재 문제에서는 조건과 요구가 명확하기 때문에 각 배열의 행과 열을
     조건에 맞춰 순회하는 로직을 작성하였다.

     -- 구상
     2차원 배열을 행과, 열을 각각 순회하는 메서드를 선언하고 메서드에서는
     두번째 인덱스부터 시작하여 이전 값과 비교, 같을 시에는 블럭 하나를 건
     너고(continue) 다를 시 높이 차이가 1 초과 시 길 건너기 취소 후 다
     음 길 탐색, 높이 차이가 1 이하라면 현재 요소의 크기가 더 크다면 내
     려가는 경사로 설치, 작다면 올라가는 경사로를 설치하고 경사로는 경사
     로 설치 여부 boolean 배열과 각 증감, 차감하는 L 길이만큼의 반복문
     을 사용하여 설치 및 조건 확인하여 정상적 설치가 완료되면 길 건너기
     진행, 길을 모두 건넜다면 결과에 증분하여 메서드 값 반환하고 각 행 기
     준 탐색, 열 기준 탐색으로 메서드를 두번 실행하여 받은 결과값을 더하여
     정답 반환 진행

     */


    public static void main(String[] args) throws IOException {
        String input = """
                6 1
                3 2 1 1 2 3
                3 2 2 1 2 3
                3 2 2 2 3 3
                3 3 3 3 3 3
                3 3 3 3 2 2
                3 3 3 3 2 2
                """;

        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] nowLine = br.readLine().split(" ");
        final int N = Integer.parseInt(nowLine[0]);
        final int L = Integer.parseInt(nowLine[1]);
        int[][] map = new int[N][N];

        for(int i = 0; i < N; i++) {
            map[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        int result = 0;
        result += working(map, N, L, true);
        result += working(map, N, L, false);


        System.out.print(result);
    }

    private static int working(int[][] map, int N, int L, boolean isHorizon) {
        int result = 0;

        skipRow :
        for(int i = 0; i < N; i++) {
            boolean[] setup = new boolean[N];
            for(int j = 1; j < N; j++) {
                int before = isHorizon ? map[i][j - 1] : map[j - 1][i];
                int now = isHorizon ? map[i][j] : map[j][i];

                if(before == now) continue;

                if(Math.abs(before - now) > 1) continue skipRow;
                if(before < now) {
                    for(int k = j - 1; k >= j - L; k--) {
                        if(k == -1 || setup[k] || (isHorizon ? map[i][k] : map[k][i]) != before) continue skipRow;
                        setup[k] = true;
                    }
                } else {
                    for(int k = j; k < j + L; k++) {
                        if(k == N || setup[k] || (isHorizon ? map[i][k] : map[k][i]) != now) continue skipRow;
                        setup[k] = true;
                    }
                    j += L - 1;
                }
            }
            result++;
        }

        return result;
    }

}