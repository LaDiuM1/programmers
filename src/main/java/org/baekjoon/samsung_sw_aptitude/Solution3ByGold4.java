package org.baekjoon.samsung_sw_aptitude;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Solution3ByGold4 {

    /**
     [https://www.acmicpc.net/problem/3190]
     삼성 SW 역량 테스트 기출 문제
     뱀

     이 문제는 일반적으로 잘 알려진 지렁이 게임으로 기존에 알고 있었던 게임이라 상대적으로 간단하게 풀 수 있었다.

     제한사항은 아래와 같으며 읽었을 때 지렁이게임과 동일한 흐름으로 진행하는 것으로 보여 지렁이 게임을 구현하듯
     프로그램을 작성하였다.

     1.먼저 뱀은 몸길이를 늘려 머리를 다음칸에 위치시킨다.
     2.만약 벽이나 자기자신의 몸과 부딪히면 게임이 끝난다.
     3.만약 이동한 칸에 사과가 있다면, 그 칸에 있던 사과가 없어지고 꼬리는 움직이지 않는다.
     4.만약 이동한 칸에 사과가 없다면, 몸길이를 줄여서 꼬리가 위치한 칸을 비워준다. 즉, 몸길이는 변하지 않는다.

     구상
     2차원 정수 Deque를 사용하여 first를 head, last를 tail 개념으로 사용,
     이동 명령은 Map에 담아 명령어 인식을 containsKey로 판별하하는 것으로 구상하였다.

     구현
     테스트 입력을 받아 배열을 선언하고 배열에 사과 위치를 2로 표시.
     HashMap을 선언하여 키에 명령어 입력 시간, 값이 명령어를 삽입.
     초기 0,0 위치를 1로 설정하고 (지렁이 영역) deque에 해당 위치(머리) 삽입.
     방향 변수는 1,2,3,4 로 1부터 3시 방향부터 시계방향으로 기준 설정.
     지렁이 이동을 구현하는  while문을 선언, 이동 로직은 처음에 이동
     횟수를 증가시킨 후 deque의 첫번째 요소(머리)를 반환받아 X, Y좌표를
     변수로 설정 후 방향에 따라 좌표를 변경하는 case 로직 작성
     이후 종료 조건 확인 메서드를 작성하여 이동할 좌표가 게임 종료 조건에
     해당하는지 확인하여 참일 시 break로 탈출 후 결과 반환,
     종료 조건에 해당이 안된다면 이동할 좌표값을 deque 첫번째 요소(머리)로
     삽입 후 머리 위치한 곳이 사과인지 확인하는 변수 선언 후 현재 머리 위치를 1로 변경
     만약 머리 위치가 사과(2)라면 deque에서 마지막 요소(꼬리)를 꺼내어 꼬리 위치를
     0으로 설정, 이후 이동 횟수가 map에 있는지 확인 후 있다면 명령어에 따라
     vector 변수 값 변경 진행.
     이동 과정을 반복하여 최종 이동 횟수 결과 반환

     */

    public static void main(String[] args) throws IOException {
        String input = """
                6
                3
                3 4
                2 5
                5 3
                3
                3 D
                15 L
                17 D
                """;

        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        int[][] board = new int[n][n]; // 1 : 뱀, 2 : 사과
        int appleQuantity = Integer.parseInt(br.readLine());

        for (int i = 0; i < appleQuantity; i++) {
            String[] applePosition = br.readLine().split(" ");
            int positionY = Integer.parseInt(applePosition[0]) - 1;
            int positionX = Integer.parseInt(applePosition[1]) - 1;
            board[positionY][positionX] = 2;
        }

        int numOfCommend = Integer.parseInt(br.readLine());
        Map<Integer, String> commendMap = new HashMap<>();

        for (int i = 0; i < numOfCommend; i++) {
            String[] commend = br.readLine().split(" ");
            int moveSecond = Integer.parseInt(commend[0]);
            String moveType = commend[1];
            commendMap.put(moveSecond, moveType);
        }

        int moveCount = 0;
        int vector = 1;

        Deque<int[]> snake = new LinkedList<>();
        snake.add(new int[]{0, 0});
        board[0][0] = 1;

        while(!snake.isEmpty()) {
            int[] head = snake.peekFirst();
            int headY = head[0];
            int headX = head[1];
            moveCount++;

            switch (vector) {
                // 1부터 3시 기준 시계방향
                case 1 : {
                    headX++;
                    break;
                }
                case 2 : {
                    headY++;
                    break;
                }
                case 3 : {
                    headX--;
                    break;
                }
                case 4 : {
                    headY--;
                }
            }
            if(isGameEnd(headX, headY, board)) break;

            boolean isApple = board[headY][headX] == 2;
            snake.addFirst(new int[]{headY, headX});

            if(!snake.isEmpty()) {
                head = snake.peekFirst();
                board[head[0]][head[1]] = 1;
                if(!isApple) {
                    int[] tail = snake.pollLast();
                    board[tail[0]][tail[1]] = 0;
                }
            }

            if(commendMap.containsKey(moveCount)) {
                switch (commendMap.get(moveCount)) {
                    case "L" : {
                        vector--;
                        if(vector == 0) vector = 4;
                        break;
                    }
                    case "D" : {
                        vector++;
                        if(vector == 5) vector = 1;
                        break;
                    }
                }
            }
        }

        System.out.print(moveCount);
    }

    private static boolean isGameEnd(int x, int y, int[][] board) {
        return x < 0 || x >= board.length || y < 0 || y >= board.length || board[y][x] == 1;
    }

}