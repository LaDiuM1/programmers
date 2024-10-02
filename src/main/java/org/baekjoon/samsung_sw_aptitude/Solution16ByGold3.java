package org.baekjoon.samsung_sw_aptitude;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution16ByGold3 {

    /**
     [https://www.acmicpc.net/problem/15685]
     삼성 SW 역량 테스트 기출 문제
     드래곤 커브

     이 문제는 선을 그리며 선을 그려나갈 때마다 전체 모양을 회전시켜
     끝 점에 이어나간다는 새로운 유형의 문제였다.

     -- 구상
     맨 처음 문제를 접하고 드는 생각은 그릴때마다 배열을 회전시켜
     연결해야하나 하는 생각이지만 단순한 문제를 굉장히 복잡하게
     만드는 느낌을 받았다, 그래서 회전한다는 의미를 분해해서 생각
     해보았는데, 회전이라는건 모양을 이루고 있는 모든 선의 진행
     방향을 회전하려는 방향으로 바꾸는 것이고, 끝 점에 이어서
     모두를 회전시킨다는 것은 선을 그려 나갔던 순서를 역순으로
     방향을 회전시키며 다시 그리면 된다는 것으로 문제를 작은 문제
     로 분해하여 해석하였다. 그렇다면 해결은 간단해지며 스택의 개념
     으로 세대마다 재귀하여 역순으로 다시 그리면 될 것이다.

     -- 구현
     문제를 작은 문제로 해석하니 구현은 단순했다. 드로잉할 배열을 선언하고
     명령어를 순서대로 처리하는 반복문을 선언 후 각 명령어 안에서 변수를 선
     언하여 처음 방향으로 한번 그린 다음 리스트에 그린 방향을 담고 리스트를
     매개로 받아 depth가 세대(gen)에 도달 시까지 반복하는 재귀 함수를 선언
     후 함수에서는 리스트에 담긴 그려진 방향들을 역순으로 순회하여 각 방향을
     반시계 방향으로 변경하여 그려준 후 그렸던 방향을 다시 리스트에 추가하는
     방법으로 재귀 코드를 작성하여 비교적 간단하게 문제를 해결하였다.

     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int numOfCmd = Integer.parseInt(br.readLine());
        int[][] drawingPaper = new int[101][101];
        int[][] vector = {{0, 1}, {-1, 0}, {0, -1}, {1, 0}}; // 우상좌하
        // 명령어 순차적으로 처리
        for(int i = 0; i < numOfCmd; i++) {
            int[] command = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int x = command[0];
            int y = command[1];
            int dir = command[2];
            int gen = command[3];

            drawingPaper[y][x] = 1;
            x += vector[dir][1];
            y += vector[dir][0];
            drawingPaper[y][x] = 1;

            List<Integer> drawingOrder = new ArrayList<>();
            drawingOrder.add(dir);
            // 역순으로 depth 만큼 다시 그리는 재귀 함수
            workDraw(0, x, y, drawingOrder, gen, drawingPaper, vector);
        }

        int result = 0;

        // 정답 확인
        for(int i = 0; i < drawingPaper.length -1; i++) {
            for(int j = 0; j < drawingPaper[0].length -1; j++) {
                if(drawingPaper[i][j] == 1
                        && drawingPaper[i][j + 1] == 1
                        && drawingPaper[i + 1][j] == 1
                        && drawingPaper[i + 1][j + 1] == 1) {
                    result++;
                }
            }
        }

        System.out.print(result);
    }

    private static void workDraw(int depth, int x, int y, List<Integer> drawingOrder, int goal, int[][] drawingPaper, int[][] vector) {
        if(depth == goal) return;

        for(int i = drawingOrder.size() - 1; i >= 0; i--) {
            int dir = (drawingOrder.get(i) + 1) % 4;
            x += vector[dir][1];
            y += vector[dir][0];
            drawingPaper[y][x] = 1;
            drawingOrder.add(dir);
        }

        workDraw(depth + 1, x, y, drawingOrder, goal, drawingPaper, vector);
    }
}