package org.baekjoon.samsung_sw_aptitude;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Solution13ByGold5 {

    /**
     [https://www.acmicpc.net/problem/14891]
     삼성 SW 역량 테스트 기출 문제
     톱니바퀴

     이 문제는 톱니바퀴의 맞물림 여부를 N극과 S극으로 표현한 문제로
     두 톱니바퀴의 맞대어진 축이 서로 상극일 때 톱니바퀴가 회전하는
     개념의 문제이다. 초기의 톱니바퀴 구성 요소와 명령어들이 주어졌을
     때 최종적인 톱니바퀴 상태를 구해야 하는 문제로 처음 접하는 특이한
     문제였다.

     -- 구상
     톱니바퀴는 List에 담은 후 List의 배열 4개로 표현하는 것으로 정함.
     톱니바퀴의 회전은 Deque 개념으로 구현하여 시계방향 및 반시계 방향은
     각 처음과 끝 요소의 추가, 제거로 구현, 명령어는 Queue에 담은 후
     Queue가 빌 때까지 실행하는 반복문에서 명령어 처리 진행, 명령어
     처리에서는 기준 톱니바퀴에서 왼쪽 방향과 오른쪽 방향을 확인하여
     극 일치여부 확인 후 극이 상극일 시 회전시켜야 할 방향과 회전 톱
     니바퀴 번호를 List에 추가 후 마지막에 일괄 회전 처리 진행하여
     모든 명령어 실행 후 최종 상태 확인 진행하여 결과 반환으로 구상

     -- 최종평
     문제 자체는 그림도 많고 처음보는 유형이라 쉽지 않겠다는 느낌을 받았지만
     지문을 읽어갈 때마다 필요한 구상이 바로바로 떠오르는 것을 보면 생각
     만큼 어려운 문제는 아니었다는 생각이 든다, 구현 문제는 특정 알고리즘
     보다는 지문을 정확히 읽고 요구대로 로직만 잘 작성해도 쉽게 해결할 수
     있다는 느낌을 받은 문제였다.

     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        List<Integer>[] cogwheelArr = new List[4];
        for(int i = 0; i < 4; i++) {
            cogwheelArr[i] = createCogwheel(br);
        }

        Queue<int[]> commends = new LinkedList<>(); // 톱니바퀴 번호, 1 : 시계방향, -1 : 반시계방향

        final int N = Integer.parseInt(br.readLine());
        for(int i = 0; i < N; i++) {
            String[] cmd = br.readLine().split(" ");
            int cogNo = Integer.parseInt(cmd[0]);
            int dir = Integer.parseInt(cmd[1]);
            commends.add(new int[]{cogNo, dir});
        }

        while(!commends.isEmpty()) {
            int[] cmd = commends.poll();
            int cogNo = cmd[0] - 1;
            int dir = cmd[1];
            List<int[]> rotateCogwheel = new ArrayList<>();

            int nowDir = dir;
            // 기준에서 왼쪽 방향으로 체크
            for(int i = cogNo; i > 0; i--) {
                List<Integer> now = cogwheelArr[i];
                List<Integer> neighbor = cogwheelArr[i - 1];

                // 2번은 오른쪽 나사, 6번은 왼쪽 나사
                if(now.get(6) == neighbor.get(2)) break;
                rotateCogwheel.add(new int[]{i - 1, -nowDir});
                nowDir = -nowDir;
            }

            nowDir = dir;
            // 기준에서 오른쪽 방향으로 체크
            for(int i = cogNo; i < cogwheelArr.length - 1; i++) {
                List<Integer> now = cogwheelArr[i];
                List<Integer> neighbor = cogwheelArr[i + 1];

                // 2번은 오른쪽 나사, 6번은 왼쪽 나사
                if(now.get(2) == neighbor.get(6)) break;
                rotateCogwheel.add(new int[]{i + 1, -nowDir});
                nowDir = -nowDir;
            }

            rotateCogwheel.add(new int[]{cogNo, dir});

            // 일괄 회전
            for(int[] rotateCmd : rotateCogwheel) {
                rotate(cogwheelArr[rotateCmd[0]], rotateCmd[1]);
            }
        }

        int result = 0;
        int operator = 1;
        for(int i = 0; i < cogwheelArr.length; i++) {
            if(cogwheelArr[i].get(0) == 1) result += operator;
            operator *= 2;
        }

        System.out.print(result);
    }

    private static List<Integer> createCogwheel(BufferedReader br) throws IOException {
        List<Integer> cogwheel = new ArrayList<>();

        String nowLine = br.readLine();
        for (int j = 0; j < nowLine.length(); j++) {
            cogwheel.add(nowLine.charAt(j) - '0');
        }

        return cogwheel;
    }

    private static void rotate(List<Integer> cogwheel, int dir) {
        if(cogwheel.isEmpty()) return;

        // 1 : 시계방향, -1 : 반시계방향
        if(dir == 1) {
            int last = cogwheel.remove(cogwheel.size() - 1);
            cogwheel.add(0, last);
        } else {
            int first = cogwheel.remove(0);
            cogwheel.add(first);
        }
    }

}