package org.baekjoon.samsung_sw_aptitude;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class Solution5ByGold4 {

    /**
     [https://www.acmicpc.net/problem/14499]
     삼성 SW 역량 테스트 기출 문제
     주사위 굴리기

     이 문제는 주사위를 굴렸을 때 지도의 값과 주사위의 특정 위치(아랫면)의 값을 비교하여
     지도의 상태를 변경하며 특정 순간마다 주사위의 특정 위치(윗면)의 값을 이동 시마다
     출력하는 문제이다.

     주사위의 개념에 대한 문제는 처음 접해봐서 생소했으므로 주사위에 대한 정의부터 들어갔다.
     주사위는 3차원 입체의 정육면체 도형이며 이는 축이 3개, 축마다의 요소는 2개로 정의할 수 있다.
     그렇다면 주사위 평면 기준 좌우를 x축, 평면 기준 상하를 y축, 높이 기준 상하를 z축으로 표현한다면
     이는 2차원 배열에서의 행이 3개, 열이 2개라고 프로그램에서는 표현할 수 있다.
     주사위에 대한 정의에 들어갔으니 주사위를 굴렸을 때 축마다의 좌표 변화값을 알아낼 차례이다.
     문제에서는 주사위를 상, 하, 좌, 우로 이동할 수 있다고 하였으며 이동은 1칸을 기준으로 한다.

     그러면 상, 하, 좌, 우에 대한 개별적인 변화를 산출해보자면
     위로 이동 : x축의 변화량은 없으며 z와 y축이 교환된다. 교환 후 z축의 요소가 역순으로 정렬된다.
     아래로 이동 : x축의 변화량은 없으며 z와 y축이 교환된다. 교환 후 y축의 요소가 역순으로 정렬된다.
     왼쪽으로 이동 : y축의 변화량은 없으며 z와 x축이 교환된다. 교환 후 z축의 요소가 역순으로 정렬된다.
     오른쪽 이동 : y축의 변화량은 없으며 z와 x축이 교환된다. 교환 후 y축의 요소가 역순으로 정렬된다.
     주사위의 정의와 이동 규칙을 파악했으니, 이를 기반으로 문제를 구현하면 된다.

     구현
     테스트 케이스 분할 후 지도를 작성, 이후 명령어를 큐에 입력, 주사위의 초기 좌표를 설정 후
     큐의 모든 요소를 빼가며 순회하는 방식으로 이동을 구현.
     큐에서 명령어를 꺼낸 후 명령어 종류에 따라 switch문으로 로직 작성,
     1, 2, 3, 4(방향)별로 주사위 배열 변화와 주사위 좌표값을 변화시키고
     switch 종료 후 변화된 주사위 배열에서 윗면의 값과 아랫면의 값을 추출하여
     이동된 주사위 위치의 지도의 값과 비교하여 조건에 따라 주사위의 값 또는 지도의 값을 변경 후
     주사위 윗면의 값을 출력하여 정답을 순차적으로 출력 진행.
     지문에서 주사위 이동 위치가 지도를 벗어나면 명령을 무시하라 하였으니
     명령 무시 여부 확인용 메서드를 선언하여 switch문의 각 case마다 변화 예정인 좌표를 전달하여
     무시할 명령이면 바로 다음 이동으로 continue하는 메서드를 작성하여 명령 무시 구현하여 문제 해결.

     상기 구현을 완료 후 백준에 제출하였으나 초기에 바로 실패가 나와서 원인 분석 진행.
     구현된 프로세스와 지문의 흐름 및 조건이 일치하는지 확인하여 이상 여부 없음을 확인하고
     각 테스트 케이스를 분석 진행. 테스트 케이스 확인 시 주사위의 초기 위치값이 모두 동일한
     것을 확인하여 혹시 지문에서의 x, y값이 구현 기준의 x, y값과 다른 건지 확인하기 위해
     테스트 케이스에서 x, y 좌표값의 추출 순서를 변경하여 백준에 제출하니 테스트 케이스 모두
     통과되어 문제 원인은 파악하였으나 의문점이 생겨 웹에 검색을 시도하였다.

     내가 기존에 알고 있던 직교좌표계는 x는 가로축, y는 세로축으로 기준점이 왼쪽 하단 모서리이며
     값의 증가는 오른쪽과 위쪽으로 표시하는 형태를 많이 접하였으나, 프로그래밍적 배열에서는 기준이
     왼쪽 상단 모서리로 값이 증가하는 형태가 하단과 우측이다. 이를 동일한 좌표에서 x, y의 증가분으로
     표현한다면 프로그래밍에서 좌표계는 직교좌표계를 90도 시계방향으로 회전한 형태이며 이렇게 되면
     축이 서로 교환되므로 2차원 배열에서 표현은 [x][y]인 것으로 이해하였다. 배열 출력 시 직관적으로
     보이는 것에 기존 지식과의 모순이 생겨 당분간 헷갈릴 것으로 생각되며 적응이 필요해 보인다.

     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] line1 = br.readLine().split(" ");
        int n = Integer.parseInt(line1[0]);
        int m = Integer.parseInt(line1[1]);
        int diceY = Integer.parseInt(line1[2]);
        int diceX = Integer.parseInt(line1[3]);

        int[][] board = new int[n][m];
        for(int i = 0; i < n; i++) {
            int[] rowNumbers = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            board[i] = rowNumbers;
        }

        Queue<Integer> cmd = Arrays.stream(br.readLine().split(" ")).map(Integer::parseInt).collect(Collectors.toCollection(LinkedList::new));

        int[][] dice = {
                {0, 0}, // 주사위의 x축 왼쪽, 오른쪽
                {0, 0}, // 주사위의 y축 위, 아래
                {0, 0}  // 주사위의 z축 위, 아래
        };

        while(!cmd.isEmpty()) { // 주사위 이동
            int commend = cmd.poll();
            switch (commend) {
                case 1 : {  // 오른쪽으로 굴림
                    if(isIgnoreCommend(diceX + 1, diceY, n, m)) continue;
                    int[] temp1 = dice[2];
                    dice[2] = dice[0];
                    dice[0] = temp1;

                    int temp2 = dice[0][1];
                    dice[0][1] = dice[0][0];
                    dice[0][0] = temp2;
                    diceX++;
                    break;
                }
                case 2 : { // 왼쪽으로 굴림
                    if(isIgnoreCommend(diceX - 1, diceY, n, m)) continue;
                    int[] temp1 = dice[2];
                    dice[2] = dice[0];
                    dice[0] = temp1;

                    int temp2 = dice[2][1];
                    dice[2][1] = dice[2][0];
                    dice[2][0] = temp2;
                    diceX--;
                    break;
                }
                case 3 : { // 위로 굴림
                    if(isIgnoreCommend(diceX, diceY - 1, n, m)) continue;
                    int[] temp1 = dice[2];
                    dice[2] = dice[1];
                    dice[1] = temp1;

                    int temp2 = dice[2][1];
                    dice[2][1] = dice[2][0];
                    dice[2][0] = temp2;
                    diceY--;
                    break;
                }
                case 4 : { // 아래로 굴림
                    if(isIgnoreCommend(diceX, diceY + 1, n, m)) continue;
                    int[] temp1 = dice[2];
                    dice[2] = dice[1];
                    dice[1] = temp1;

                    int temp2 = dice[1][1];
                    dice[1][1] = dice[1][0];
                    dice[1][0] = temp2;
                    diceY++;
                }
            }

            int numberOfTop = dice[2][0];
            int numberOfBottom = dice[2][1];

            int nowPositionNumber = board[diceY][diceX];
            if(nowPositionNumber == 0) {
                board[diceY][diceX] = numberOfBottom;
            } else {
                dice[2][1] = board[diceY][diceX];
                board[diceY][diceX] = 0;
            }
            System.out.println(numberOfTop);
        }
    }

    private static boolean isIgnoreCommend(int x, int y, int n, int m) {
        return x < 0 || x >= m || y < 0 || y >= n;
    }

}