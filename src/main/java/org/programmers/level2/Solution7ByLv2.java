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
     * -- 시간 초과 실패
     *
     * 1차 시도로 BFS를 이용하여 해결을 시도하였으나 정답은 출력되나 매개변수 갯수가 증가할 때마다 경우의 수가 기하급수적으로 증가하여
     * 8부터 시간초과가 발생하였다. 그렇다면 이동 조건만 일치 여부 확인하여 모든 경우의 수를 확인하는 방법으로는 푸는 것 자체가
     * 불가능해진다. 완전탐색이 아닌 문제가 정답에 이르는 과정을 분해하여 해결해야 할 것으로 보인다.
     *
     * 새로운 구상 시도.
     * 첫번째 시도에서는 마지막 막대에 큰 원반부터 쌓이게 하는 것이 조건이었다.
     * 하지만 큰 원반이 3번 막대에 도착하는 과정도 무수히 많은 작은 문제들을 해결해야 하므로
     * 작은 문제부터 살펴보도록 하자.
     *
     * 1차적으로 큰 원반이 3번 막대로 이동하기 위해서는 나머지 원반이 원반의 현재 위치와 목표 위치 사이에 모두 있어야 한다.
     * 그렇다면 그 다음으로 큰 원반이 보조 막대 위치로 이동해야 하며 이를 위해서는 또 다음 크기의 원반들이 보조 막대로 이동하여야 한다.
     * 그렇다면 이동이 가능해 질 때까지 보조 막대의 위치를 찾은 뒤 실제 이동이 가능 할 때 해당 위치로 이동해야한다.
     *
     * 상기를 간략하게 정리하자면 아래와 같이 된다.
     * 현재 번호가 목표 위치로 갈수 있는지 검사, 갈 수 있다면 이동 시키고 다음 번호도 목표 위치로 이동
     * 없다면 다음 번호를 보조 위치로 이동(현재 위치와 목표 위치가 아닌 위치)
     *
     * 상기 구상은 재귀적인 작업이 필요해보인다. 문제를 작은 문제로 분해했으니 코드로 구현하면 된다.
     * 구현 구상을 새로 해보자.
     *
     * 재귀적 해결 구상
     * 재귀 함수를 선언하여 현재 번호가 목표 위치로 갈 수 있는지 검사하는 while 반복 선언
     * 갈 수 있다면 원판을 이동, 이후 다음 원판까지 동일한 목표를 재귀에 전달
     * 갈 수 없다면 보조 위치를 목표 위치로 전달하여 재귀 호출
     *
     * 현재 위치가 목표 위치에 도달할 때까지 반복
     * 원반 번호가 1일 시 재귀 종료
     *
     */

    Stack<Integer>[] pegs = new Stack[3];
    Map<Integer, Integer> diskPositionMap = new HashMap<>();
    List<int[]> route = new ArrayList<>();

    public int[][] solution(int n) {
        initialize(n);

        hanoiDfs(n, 2);

        return route.toArray(int[][]::new);
    }

    public void hanoiDfs(int diskNo, int target) {
        if(diskNo == 1) {
            updateStatus(diskNo, target);
            return;
        }

        while(true) {
            int nowPosition = diskPositionMap.get(diskNo);
            if(nowPosition == target) return;

            if((!pegs[nowPosition].isEmpty() && pegs[nowPosition].lastElement() == diskNo)
                    && (pegs[target].isEmpty() || pegs[target].lastElement() > diskNo)) {
                updateStatus(diskNo, target);
                hanoiDfs(diskNo -1, target);
            } else {
                int sub = 3 - (nowPosition + target);
                hanoiDfs(diskNo -1, sub);
            }
        }
    }

    public void updateStatus(int diskNo, int target) {
        Integer nowPosition = diskPositionMap.get(diskNo);
        pegs[target].push(pegs[nowPosition].pop());
        diskPositionMap.put(diskNo, target);
        route.add(new int[]{nowPosition + 1, target + 1});
    }

    public void initialize(int n) {
        for(int i = 0; i < 3; i++) {
            Stack<Integer> peg = new Stack<>();
            if (i == 0) {
                for (int j = n; j > 0; j--) {
                    diskPositionMap.put(j, i);
                    peg.push(j);
                }
            }
            pegs[i] = peg;
        }
    }
}