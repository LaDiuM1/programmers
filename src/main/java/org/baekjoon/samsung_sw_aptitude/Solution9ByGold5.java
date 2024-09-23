package org.baekjoon.samsung_sw_aptitude;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Solution9ByGold5 {
    /**
     [https://www.acmicpc.net/problem/14503]
     삼성 SW 역량 테스트 기출 문제
     로봇청소기

     이 문제는 배열에서의 이동 및 상태 변화를 관리하는 문제이며 이동 조건은 아래와 같다.

     1. 현재 칸이 아직 청소되지 않은 경우, 현재 칸을 청소한다.
     2. 현재 칸의 주변 4칸 중 청소되지 않은 빈 칸이 없는 경우,
        1. 바라보는 방향을 유지한 채로 한 칸 후진할 수 있다면 한 칸 후진하고 1번으로 돌아간다.
        2. 바라보는 방향의 뒤쪽 칸이 벽이라 후진할 수 없다면 작동을 멈춘다.
     3. 현재 칸의 주변 4칸 중 청소되지 않은 빈 칸이 있는 경우,
        1. 반시계 방향으로 90도 회전한다.
        2. 바라보는 방향을 기준으로 앞쪽 칸이 청소되지 않은 빈 칸인 경우 한 칸 전진한다.
        3. 1번으로 돌아간다.

     이동 조건을 정해주었기 때문에 별다른 알고리즘을 사용하지 않고 while문으로 이동 조건과
     동일한 로직을 구현하여 로직을 해결하였다, 로직 구현 중 후진 명령이 있는데 이는 비트 반전
     을 사용하여 후진을 구현해보았다.

     */

    public static void main(String[] args) throws IOException {
        int[][] vector = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // 북동남서

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] splitNowLine = br.readLine().split(" ");
        int n = Integer.parseInt(splitNowLine[0]);
        int m = Integer.parseInt(splitNowLine[1]);

        splitNowLine = br.readLine().split(" ");
        int x = Integer.parseInt(splitNowLine[0]);
        int y = Integer.parseInt(splitNowLine[1]);
        int direction = Integer.parseInt(splitNowLine[2]);

        int[][] room = new int[n][m];

        for(int i = 0; i < n; i++) {
            room[i] = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        }

        int countWork = 0;
        while(true) {
            if(room[x][y] == 0) {
                room[x][y] = 2;
                countWork++;
            }
            if(checkPossibleClean(room, vector, x, y)) {
                direction = (direction + 3) % 4;
                int neighborX = x + vector[direction][0];
                int neighborY = y + vector[direction][1];
                if(!isWall(room, neighborX, neighborY) && room[neighborX][neighborY] == 0) {
                    x = neighborX;
                    y = neighborY;
                }
                continue;
            }

            int backWorkX = x + (~vector[direction][0] + 1);
            int backWorkY = y + (~vector[direction][1] + 1);

            if(isWall(room, backWorkX, backWorkY)) break;

            x = backWorkX;
            y = backWorkY;
        }

        System.out.print(countWork);
    }

    private static boolean checkPossibleClean(int[][] room, int[][] vector, int x, int y) {
        for(int i = 0; i < vector.length; i++) {
            int neighborX = x + vector[i][0];
            int neighborY = y + vector[i][1];

            if(isWall(room, neighborX, neighborY)) continue;
            if(room[neighborX][neighborY] == 0) return true;
        }

        return false;
    }

    private static boolean isWall(int[][] room, int x, int y) {
        return x < 0 || x >= room.length || y < 0 || y >= room[0].length || room[x][y] == 1;
    }

}