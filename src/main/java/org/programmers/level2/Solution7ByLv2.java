package org.programmers.level2;

import java.util.*;

public class Solution7ByLv2 {

    /**
     * 하노이의 탑.
     * 조건 1 : 큰 원판은 작은 원판 위에 있을 수 없다.
     * 조건 2 : 하나에 한번의 원판만 옮길 수 있다.
     * 조건 3 : 기둥은 세개이다.
     *
     * 초기 조건 : 원판은 매개변수 갯수만큼 크기 순서의 내림차순으로 1번의 기둥에 쌓여 있다.
     * 종료 조건 : 마지막 기둥에 모든 원판이 옮겨가야 한다.
     * 과제 : 최소 횟수로 옮겨간 경로를 반환해야 함.
     *
     * 구상
     * 기둥과 원판의 관계는 스택 자료구조와 동일하다. 얼핏 보면 DFS로 해결해야 할 것으로 보이나
     * 문제의 지문은 가장 적은 횟수로 이동하는 것이 조건이다. 최적 탐색은 BFS가 유리하다.
     * BFS로 구상해보면 종료 조건을 설정하여 가장 적은 경로만 반환하면 해결되는 문제이다.
     * 그렇다면 종료 조건을 설정하여 BFS를 이용하여 순차적 최적 탐색으로 문제를 해결해야 할 것으로 보인다.
     * 종료 조건을 살펴본다면 3번 기둥에 큰 원판부터 쌓아가는 것이 조건이므로 각 원판마다 최적 경로를 탐색하여
     * 최적 경로의 원판 위치로 이어간다면 마지막까지 쌓였을때의 최적 경로를 구할 수 있을 것이다.
     *
     * 구현 순서 구상
     * 원판의 보유 여부를 확인하는 3개의 기둥 배열 생성, 기둥은 스택 형식의 자료구조 사용
     * 1번 기둥에 매개변수 갯수만큼 내림차순으로 스택에 순차적으로 입력
     * 최적 경로를 저장할 int 배열 List 클래스 필드에 선언
     * 각 원판마다 이동 경로를 갱신할 객체 클래스 생성
     * 탐색을 진행할 원판 큐 클래스 필드에 선언
     * BFS 매서드 생성, 매개변수로 3번 기둥에 도착해야 하는 원판 번호와 순서를 받아 최적 경로 및 현재 원판들의 위치를 갱신
     * BFS 메서드에서는 원판들의 이동 가능 경우의 수를 큐에 추가하며 탐색 진행, 3번 기둥의 목표 원반 번호가 스택의 목표 순서에 도착하면 종료
     * 매개변수의 내림차순으로 반복문 실행하여 각 반복자와 순서를 BFS 매개변수로 전달
     * 반복문 종료 시 최적 경로를 반환
     *
     *
     */

    //  n	result
    //  2	{ {1,2}, {1,3}, {2,3} }

    public class Disk {
        int diskNo;
        int currentPosition;
        List<int[]> route;
        Stack<Disk>[] diskPositions;

        Disk(int diskNo, List<int[]> route, int currentPosition) {
            this.diskNo = diskNo;
            this.route = route;
            this.currentPosition = currentPosition;
        }
    }

    Stack<Disk>[] pegs = new Stack[3];
    List<int[]> optimalRoute;
    Queue<Disk> queue = new LinkedList<>();

    public int[][] solution(int n) {

        for(int i = 0; i < 3; i++) {
            Stack<Disk> peg = new Stack<>();
            if (i == 0) {
                for (int j = n; j > 0; j--) {
                    Disk disk = new Disk(j, new ArrayList<>(), 0);
                    peg.push(disk);
                }
            }
            pegs[i] = peg;
        }

        Disk disk = pegs[0].lastElement();
        disk.diskPositions = pegs;
        queue.add(disk);

        for(int i = n; i > 0; i--) {
            bfs(i);
        }

        return optimalRoute.toArray(int[][]::new);
    }

    public void bfs(int goal) {

        while(!queue.isEmpty()) {
            Disk nowDisk = queue.poll();

            Stack<Disk>[] pegsStatus = nowDisk.diskPositions;
            int currentPosition = nowDisk.currentPosition;

            if(currentPosition == 2 && nowDisk.diskNo == goal) {
                optimalRoute = nowDisk.route;
                pegs = pegsStatus;
                queue = new LinkedList<>();
                queue.add(nowDisk);
                return;
            }

            for (int i = 0; i < 3; i++) {
                if (pegsStatus[i].isEmpty()) continue;
                if(!nowDisk.route.isEmpty() && nowDisk.route.get(nowDisk.route.size() - 1)[1] - 1 == i) continue;
                Disk moveDisk = pegsStatus[i].pop();
                for (int j = 0; j < 3; j++) {
                    int moveDiskNo = moveDisk.diskNo;
                    if (i != j && (pegsStatus[j].isEmpty() || pegsStatus[j].lastElement().diskNo > moveDiskNo)) {
                        Stack<Disk>[] newDiskPositions = copyPositions(pegsStatus);
                        LinkedList<int[]> newRoute = new LinkedList<>(nowDisk.route);
                        newRoute.add(new int[]{i + 1, j + 1});
                        Disk disk = new Disk(moveDiskNo, newRoute, j);
                        newDiskPositions[j].push(moveDisk);
                        disk.diskPositions = newDiskPositions;
                        disk.currentPosition = j;
                        queue.add(disk);
                    }
                }
                pegsStatus[i].push(moveDisk);
            }
        }
    }

    public Stack<Disk>[] copyPositions(Stack<Disk>[] diskPositions) {
        Stack<Disk>[] newDiskPositions = new Stack[3];

        for(int i = 0; i < 3; i++) {
            Stack<Disk> newStack = new Stack<>();
            newStack.addAll(diskPositions[i]);
            newDiskPositions[i] = newStack;
        }

        return newDiskPositions;
    }

}