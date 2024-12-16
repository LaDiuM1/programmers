package org.baekjoon.hanghae99_challenger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SolutionByDay30 {

    /**

     [https://school.programmers.co.kr/learn/courses/30/lessons/258707]
     프로그래머스 - n + 1 카드게임

     -- 문제 분석
     이 문제는 약 2개월 전에 이미 해결했던 문제이며 기존에도 풀어본 경험이 있는 문제가
     몇 개 정도 나왔지만 학습적인 측면에서 유의미하지 않아 패스했었다, 하지만 항해 99
     플랫폼의 문제는 이 문제가 최종 문제이기 때문에 프로젝트를 마무리한다는 의미에서
     코드를 초기화 후 다시 문제를 풀어 보았다.

     -- 회상
     문제를 다시 읽어보기 시작했을 때는 어떤 문제였는지 확실한 기억이 떠오르지 않았지만
     패를 버린다는 문구를 읽는 순간 '미래 가치' 라는 키워드가 문득 떠올랐다, 이 문구가
     떠오른 순간 과거의 해결 과정이 머릿속에 자연스럽게 다시 상기되었으며 코드는 일사천
     리로 작성되었다. 결과적으로 현재 다시 해결을 위해 작성한 코드와 과거의 코드를 비교
     했을 때 구현 논리는 거의 완벽하게 일치하는 코드가 작성되었다. 다만 실제 게임 흐름
     에 맞게 아얘 처음부터 큐로 덱을 관리해 분배하는 과정이 추가되었고 불필요한 작업이
     몇 가지 제거되어 개선된 점이 눈에 띄었다.

     겨우 2개월 전에 풀었던 문제라 아직 기억이 유지되었을 수도 있으나 이 문제를 풀어보고
     느낀 점은, 유사한 문제가 주어졌을 때 해결을 위한 핵심 키워드나 논리 하나만 떠올릴
     수 있다면 충분히 과거의 경험을 바탕으로 해결 및 응용이 가능하고 해결을 위한 어떤
     코드를 외울 필요가 전혀 없다는 점이다.

     이는 문제 해결이라는 것은 단순 과거의 '기억'에 의존하는 것이 아니라 고차원적인 상호
     작용의 결과이며 이는 단순히 암기와는 다른 차원 수준의 인지 능력을 뜻하는 것이 아닐
     까 하는 생각이 든다.

     예를 들면 문제 해결을 위한 코드를 모두 외우고 매일 같이 다른 유형의 문제의 코드들을
     새로 외웠을 때 두 달 뒤에 외웠던 코드만으로 문제를 해결하는 사람은 극소수일 것이다.

     이는 암기력에 의존한 문제 해결의 한계일 것이고 만약 문제 해결을 위한 핵심 논리를 구
     성하고 문제를 풀었다면 시간이 많이 흐른 뒤에도 동일한 유형의 문제를 접했을 때 논리
     하나만 떠올릴 수 있다면 지금과 같이 거의 동일한 코드, 오히려 개선된 코드가 나올 수
     있다는 점이다.

     이는 기억력이라는 인지 능력과는 분명하게 차별되는 강점이 있는 인지 능력이며 논리력,
     분석력, 창의력등으로 이루어진 문제 해결 능력이라는 것은 엄청난 잠재력을 내포하고
     있음을 의미하는 게 아닐까 한다.

     다른 생각이 길어졌지만, 결과적으로 항해99 플랫폼을 이용한 알고리즘 문제 풀이 프로
     젝트는 이로써 마무리하며 고수준의 알고리즘을 학습하지는 않았지만 여러 가지 얻는 것
     이 많은 프로젝트였다.

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
