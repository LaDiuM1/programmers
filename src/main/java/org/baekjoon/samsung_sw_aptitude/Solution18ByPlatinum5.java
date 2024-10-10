package org.baekjoon.samsung_sw_aptitude;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution18ByPlatinum5 {

    /**
     [https://www.acmicpc.net/problem/5373]
     삼성 SW 역량 테스트 기출 문제
     큐빙

     이 문제는 큐브의 위, 아래, 앞, 뒤, 왼쪽, 오른쪽을 시계방향 또는
     반시계방향으로 회전시키는 여러 명령어가 주어졌을 때 명령어에 따른
     최종 회전 결과를 찾는 문제이다.

     -- 구상
     문제를 보자마자 느낀 생각은 간단하게 풀 수 있는 방법이 떠오르지
     않는다는 것이었다, 어떻게 풀어도 3차원 배열의 상태에 따른 복잡한
     회전을 구현해야 하는 느낌이 들었고 이는 단순한 로직으로 풀 수 없
     음을 의미하였다, 하여 큐브의 상태 변경에 따른 해결방법으로 아래
     두 가지 방법이 처음으로 떠올랐다.
     방법 1. 큐브 자체를 회전시켜 회전을 처리할 때 큐브 상태를 일정하게
     유지하여 처리하는 방법
     방법 2. 큐브는 고정하고 회전면과 인접면을 매핑하여 각 상태 변경
     로직을 작성하여 처리하는 방법

     일단 첫번째 방법의 장점으로는 큐브를 돌리는 명령어를 잘 구현해 놓으면
     회전을 처리하는 메서드 하나로 일관되게 큐브를 돌릴 수 있고 명령어마다
     큐브를 같은 위치로 맞추는 직관적인 방법을 사용함으로써 구현이 상대적
     으로 단순하다는 장점이 있다.
     하지만 단점으로는 불필요한 메모리와 연산을 수행한다는 점이 단점이다.

     두번째 방법의 장점으로는 명령어 별로 변경해야 할 인접상태를 매핑하여
     처리하므로 메서드 하나에 명령어와 회전만 매개변수로 전달하면 되므로
     메인 로직에서 메서드 하나만 호출하여 메인부의 코드는 상대적으로 깔끔
     하고 메모리 효율성과 속도가 빠르다는 장점이 있다.
     단점으로는 각 명령어별로 변경 상태를 모두 다르게 처리해야 하므로
     코드가 복잡해지고 실수할 여지가 상대적으로 더 높을 수 있다는게 단점이다.

     두 가지 방법 중 제한 사항이 테스트 케이스 100 * 케이스별 최대 명령 횟수
     1000으로 최악의 경우에도 10만번의 명령을 처리하면 되니 구현 실수가 상대
     적으로 적고 직관적으로 해결 가능한 첫번째 방법으로 문제를 해결하였다.

     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<Queue<String>> cases = new ArrayList<>();

        int numOfCase = Integer.parseInt(br.readLine());
        for(int i = 0; i < numOfCase; i++) {
            br.readLine();
            Queue<String> queue = new LinkedList<>(Arrays.asList(br.readLine().split(" ")));
            cases.add(queue);
        }

        for (Queue<String> commands : cases) {
            char[][][] cube = createCube();

            while(!commands.isEmpty()) {
                String command = commands.poll();
                char commandType = command.charAt(0);
                boolean isClockwise = command.charAt(1) == '+';
                switch (commandType) {
                    case 'U' -> {
                        rotateHorizonByFirstRows(cube, isClockwise);
                    }
                    case 'D' -> {
                        rollUp(cube);
                        rollUp(cube);
                        rotateHorizonByFirstRows(cube, isClockwise);
                        rollDown(cube);
                        rollDown(cube);
                    }
                    case 'F' -> {
                        rollUp(cube);
                        rotateHorizonByFirstRows(cube, isClockwise);
                        rollDown(cube);
                    }
                    case 'B' -> {
                        rollDown(cube);
                        rotateHorizonByFirstRows(cube, isClockwise);
                        rollUp(cube);
                    }
                    case 'L' -> {
                        rollRight(cube);
                        rotateHorizonByFirstRows(cube, isClockwise);
                        rollLeft(cube);

                    }
                    case 'R' -> {
                        rollLeft(cube);
                        rotateHorizonByFirstRows(cube, isClockwise);
                        rollRight(cube);
                    }
                }
            }
            printAnswer(cube);
        }
    }

    private static void rotateHorizonByFirstRows(char[][][] cube, boolean isClockwise) {
        if(isClockwise) {
            changeFirstRowByFace(cube, 1, 3);
            changeFirstRowByFace(cube, 4, 2);
            changeFirstRowByFace(cube, 2, 3);
            rotateClockwise90ByFace(cube, 0);
        } else {
            changeFirstRowByFace(cube, 3, 4);
            changeFirstRowByFace(cube, 2, 3);
            changeFirstRowByFace(cube, 1, 3);
            rotateCounterclockwise90ByFace(cube, 0);
        }
    }

    private static void changeFirstRowByFace(char[][][] cube, int faceIndex1, int faceIndex2) {
        char[] temp = cube[faceIndex1][0];
        cube[faceIndex1][0] = cube[faceIndex2][0];
        cube[faceIndex2][0] = temp;
    }

    private static void rollUp(char[][][] cube) {
        changeFace(cube, 0, 1);
        changeFace(cube, 4, 5);
        changeFace(cube, 1, 4);
        rotateClockwise90ByFace(cube, 4);
        rotateClockwise90ByFace(cube, 4);
        rotateClockwise90ByFace(cube, 5);
        rotateClockwise90ByFace(cube, 5);
        rotateClockwise90ByFace(cube, 3);
        rotateCounterclockwise90ByFace(cube, 2);
    }

    private static void rollDown(char[][][] cube) {
        changeFace(cube, 0, 1);
        changeFace(cube, 4, 5);
        changeFace(cube, 0, 5);
        rotateClockwise90ByFace(cube, 4);
        rotateClockwise90ByFace(cube, 4);
        rotateClockwise90ByFace(cube, 0);
        rotateClockwise90ByFace(cube, 0);
        rotateClockwise90ByFace(cube, 2);
        rotateCounterclockwise90ByFace(cube, 3);
    }

    private static void rollLeft(char[][][] cube) {
        changeFace(cube, 0, 3);
        changeFace(cube, 2, 5);
        changeFace(cube, 2, 3);
        rotateCounterclockwise90ByFace(cube, 0);
        rotateCounterclockwise90ByFace(cube, 2);
        rotateCounterclockwise90ByFace(cube, 1);
        rotateCounterclockwise90ByFace(cube, 3);
        rotateCounterclockwise90ByFace(cube, 5);
        rotateClockwise90ByFace(cube, 4);
    }

    private static void rollRight(char[][][] cube) {
        changeFace(cube, 0, 3);
        changeFace(cube, 2, 5);
        changeFace(cube, 5, 0);
        rotateClockwise90ByFace(cube, 0);
        rotateClockwise90ByFace(cube, 2);
        rotateClockwise90ByFace(cube, 1);
        rotateClockwise90ByFace(cube, 3);
        rotateClockwise90ByFace(cube, 5);
        rotateCounterclockwise90ByFace(cube, 4);
    }

    private static void changeFace(char[][][] cube, int faceIndex1, int faceIndex2) {
        char[][] temp = cube[faceIndex1];
        cube[faceIndex1] = cube[faceIndex2];
        cube[faceIndex2] = temp;
    }

    private static void rotateClockwise90ByFace(char[][][] cube, int faceIndex) {
        char[][] face = cube[faceIndex];
        int N = face.length;
        int M = face[0].length;
        char[][] rotatedFace = new char[N][M];

        for(int i = 0; i < N; i++) {
            int k = M - 1;
            for(int j = 0; j < M; j++) {
                rotatedFace[i][j] = face[k][i];
                k--;
            }
        }

        cube[faceIndex] = rotatedFace;
    }

    private static void rotateCounterclockwise90ByFace(char[][][] cube, int faceIndex) {
        char[][] face = cube[faceIndex];
        int N = face.length;
        int M = face[0].length;
        char[][] rotatedFace = new char[N][M];

        int k = N - 1;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                rotatedFace[i][j] = face[j][k];
            }
            k--;
        }

        cube[faceIndex] = rotatedFace;
    }


    private static char[][][] createCube() {
        char[][][] cube = new char[6][3][3];

        for (int i = 0; i < 6; i++) {
            char color = switch (i) {
                case 0 -> 'w';
                case 1 -> 'r';
                case 2 -> 'g';
                case 3 -> 'b';
                case 4 -> 'o';
                case 5 -> 'y';
                default -> ' ';
            };
            ;

            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    cube[i][j][k] = color;
                }
            }
        }
        return cube;
    }

    private static void printAnswer(char[][][] cube) {
        for(char[] rows : cube[0]) {
            for(char row : rows) {
                System.out.print(row);
            }
            System.out.println();
        }
    }
}