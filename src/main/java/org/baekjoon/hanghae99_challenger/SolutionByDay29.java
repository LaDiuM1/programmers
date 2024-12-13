package org.baekjoon.hanghae99_challenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SolutionByDay29 {

    /**

     [https://www.acmicpc.net/problem/17825]
     백준 17825 - 주사위 윷놀이

     -- 문제
     이 문제는 윷놀이에서 발판마다 점수가 있으며 주사위로 던져 나온 수 10개가
     주어질 때 말 4개로 얻을 수 있는 가장 큰 점수를 구하는 문제이다.

     -- 문제 분석
     주사위 스코어 10개가 주어질 때 가장 큰 점수를 얻는 방법이라는 것은 결국
     각 차례마다 어떤 말을 선택해야 가장 큰 점수를 얻을 수 있는지와 동일한 의
     미이다. 이는 수학적으로 10개의 순열에서 4개를 선택하는 경우의 수 중 가장
     큰 값을 찾는 문제이고 이는 P(10,4) = 5040으로 완전 탐색으로 푸는데 큰
     무리가 없는 문제로 보인다.

     가장 큰 값을 스코어에 대한 4개의 말 선택 순열 탐색으로 구하는 것으로 정의
     했으니, 이제 윷놀이 보드를 그려보자.

     윷놀이 보드의 가중치와 그래프의 규칙을 분석해 보았으나 한 개의 규칙으로
     표현할 수 없다는 걸 알게 되었고 최대한 작은 단위의 규칙으로 보드를 생성
     하기 위해 보드의 규칙을 정의해 보았다.

     윷놀이 보드 구조 분해
     1. 시작 노드(가중치 0)부터 단방향으로 도착 지점과 연결된 마지막 노드까지
        가중치가 2씩 증가하는 형태로 21개의 노드가 연결되어 있음.
     2. 위의 1번에서 마지막 노드를 제외한 10의 배수 노드에는 조건 간선이 존재하
        며 각 조건 간선별로 다른 조건의 간선이 존재한다.
            -> 가중치 10의 노드에는 13부터 3의 배수로 19까지 3개의 노드가 존재
               한다.
            -> 가중치 20의 노드에는 22와 24의 간선이 존재한다.
            -> 가중치 30의 노드에는 27과 26의 간선이 존재한다.
        -> 모든 조건 간선 마지막의 노드는 중앙 노드(가중치 25)로 연결된다.
     4. 중앙 노드에서 마지막 노드로 연결되는 간선이 30부터 5의 배수로 2개 존재한다.

     보드의 구조를 정의하였다. 보드의 구조를 살펴보면 그래프의 형태는 트리 구조로
     볼 수 있다. 트리 구조라면 2차원 인접 행렬 표현 방법과 노드 간 자식 노드 참조
     방식이 가장 먼저 떠올랐며 이 중 노드 간 자식 노드 참조 방식이 트리 구조의 관계
     표현 방법에 좀 더 적합해 보였기 때문에 자식 노드 참조 방식으로 트리 구조의 관계
     를 표현하는 것으로 결정하였다.

     보드의 구조, 가장 큰 누적 가중치를 찾는 방법을 정의하였으니, 아래와 같이 해결
     코드 흐름의 논리를 작성해 보았다.

     1. 윷놀이 보드판을 규칙에 따라 트리 구조를 그린다. 노드 간 관계는 노드 클래스
        에서 자식 노드를 참조하는 방식으로 표현한다, 특정 조건하에서는 다른 경로로
        향하는 노드도 있으니 이를 구분하여 두 개의 자식 노드 참조로 구성한다.
     2. 말의 상태를 관리하는 클래스를 선언하여 현재 노드의 위치와 도착 여부를 확인
        하는 필드를 선언한다.
     3. DFS를 이용한 완전 탐색으로 P(10, 4)를 구현한다. 각 턴에서 말을 선택하는
        형태로 작성하며 이동 후 다른 말이 존재하는지, 이동 시작 시 파란색 노드의
        경로가 있는지 여부를 조건으로 추가하여 탐색한다.
     4. 깊이가 10(마지막 턴 이후)까지 도달 시 경로의 가중치 합의 최댓값을 갱신하고
        완전 탐색 후 최종적으로 결과를 반환한다.

     -- 회고
     이 문제는 그래프를 활용하는 구현 문제이기 때문에 해결 흐름 자체는 생각하는데
     큰 어려움이 없었으나 트리 구조의 규칙을 분석하는 것이 쉽지 않았다. 트리 구조를
     표현할 때 관계를 직접 하드코딩하여 표현하는 방식은 비효율적이라 생각하여 단순
     화된 규칙으로 생성하기 위한 규칙을 찾기 위해 시간을 투자하였으나, 한 가지 규칙
     으로 트리 구조를 표현하는 방법을 찾을 수 없었고, 결국 타협하며 좀 더 여러 복잡
     한 규칙들로 나눠지기 시작하였다.

     결과적으로 인접 행렬로 직접 그리는 시간보다 훨씬 많은 시간이 소요되었으나 인접 행
     렬에 비해 가독성도 좋지 않고 복잡한 트리 생성 코드가 나왔다. 이런 유형의 그래프
     표현은 수정 가능성이 낮고 고정되어 있으며 관계들을 한눈에 알 수 있는 하드코딩된
     인접 행렬이 오히려 강점으로 작용할 것으로 느껴진다.

     결론은 특정 자료구조의 개념적 유사성만 따져 관계 설정을 정의하는 방법보다는, 규칙
     이 복잡해질 우려가 있는 경우 오히려 하드코딩된 인접 행렬 표현 방법이 더 적합 할 수
     있다는 걸 알 수 있었다. 이는 작성 시점의 비효율성만으로 관계 설정을 정의하는 것보
     다 여러 요소를 종합적으로 고려하여 관계 표현을 정의하는 것이 중요하다는 것을 깨닫게
     해준 문제였다고 생각한다.

     */

    private static class Node {
        int weight;
        Node redLineNode;
        Node blueLineNode;
        boolean isArrive;

        public Node(int weight) {
            this.weight = weight;
        }
        public Node(boolean isArrive) {
            this.isArrive = isArrive;
        }
    }

    private static class Piece {
        Node currentNode;
        boolean isArrived;

        public Piece(Node currentNode, boolean isArrived) {
            this.currentNode = currentNode;
            this.isArrived = isArrived;
        }
    }

    static int maximumWeight = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] scores = new int[10];
        for(int i = 0; i < 10; i++) {
            scores[i] = Integer.parseInt(st.nextToken());
        }

        Node startNode = new Node(0);
        initTree(startNode);

        Piece[] pieces = new Piece[4];
        for(int i = 0; i < 4; i++) {
            pieces[i] = new Piece(startNode, false);
        }

        dfs(0, 0, pieces, scores);
        System.out.print(maximumWeight);
    }

    private static void dfs(int turn, int curWeight, Piece[] pieces, int[] scores) {
        if(turn == 10 || Arrays.stream(pieces).allMatch(p -> p.isArrived)) {
            maximumWeight = Math.max(curWeight, maximumWeight);
            return;
        }

        for (int j = 0; j < 4; j++) {
            if (pieces[j].isArrived) continue;

            Piece[] newPieces = new Piece[4];
            for (int k = 0; k < 4; k++) {
                newPieces[k] = new Piece(pieces[k].currentNode, pieces[k].isArrived);
            }

            for (int k = 0; k < scores[turn]; k++) {
                if (k == 0 && newPieces[j].currentNode.blueLineNode != null) {
                    newPieces[j].currentNode = newPieces[j].currentNode.blueLineNode;
                } else {
                    newPieces[j].currentNode = newPieces[j].currentNode.redLineNode;
                }

                if (newPieces[j].currentNode.isArrive) {
                    newPieces[j].isArrived = true;
                    break;
                }
            }

            if (!newPieces[j].isArrived) {
                boolean overlap = false;
                for (int k = 0; k < 4; k++) {
                    if (k != j && !newPieces[j].currentNode.isArrive && newPieces[k].currentNode == newPieces[j].currentNode) {
                        overlap = true;
                        break;
                    }
                }
                if(overlap) continue;
            }

            dfs(turn + 1, curWeight + newPieces[j].currentNode.weight, newPieces, scores);
        }
    }

    private static void initTree(Node startNode) {
        Node current = startNode;
        Node centerNode = new Node(25);
        Node lastNode = new Node(40);
        lastNode.redLineNode = new Node(true);

        for(int weight = 2; weight <= 38; weight += 2) {
            current.redLineNode = new Node(weight);
            current = current.redLineNode;

            switch (weight) {
                case 10 -> {
                    Node newNode = new Node(13);
                    current.blueLineNode = newNode;

                    for(int newWeight = 16; newWeight <= 19; newWeight += 3) {
                        newNode.redLineNode = new Node(newWeight);
                        newNode = newNode.redLineNode;
                    }

                    newNode.redLineNode = centerNode;
                }
                case 20 -> {
                    Node newNode = new Node(22);
                    current.blueLineNode = newNode;

                    newNode.redLineNode = new Node(24);
                    newNode.redLineNode.redLineNode = centerNode;
                }
                case 30 -> {
                    Node newNode = new Node(28);
                    current.blueLineNode = newNode;

                    for(int newWeight = 27; newWeight >= 26; newWeight--) {
                        newNode.redLineNode = new Node(newWeight);
                        newNode = newNode.redLineNode;
                    }
                    newNode.redLineNode = centerNode;
                }
            }

            if(weight == 38) current.redLineNode = lastNode;
        }

        centerNode.redLineNode = new Node(30);
        centerNode.redLineNode.redLineNode = new Node(35);
        centerNode.redLineNode.redLineNode.redLineNode = lastNode;
    }
}
