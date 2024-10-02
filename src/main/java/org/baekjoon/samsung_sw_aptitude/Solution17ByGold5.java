package org.baekjoon.samsung_sw_aptitude;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution17ByGold5 {

    /**
     [https://www.acmicpc.net/problem/15686]
     삼성 SW 역량 테스트 기출 문제
     치킨 배달

     이 문제도 이전에 했던 사다리 조작과 같이 조합 선택 문제이며
     조합 선택 후 조합과(치킨 집들)과 고정 인덱스(집들)의 최소
     거리의 총합이 가장 최소가 되는 값을 구하는 문제이다.

     -- 구상
     문제에서 아주 친절하게 임의의 두 칸 거리를 구하는 공식까지 적어서
     중간의 집이나 치킨 집과의 충돌도 확인해야 할 최단 거리 bfs등을
     구현 할 필요가 없기때문에 간단하게 구상하면 될 것으로 보인다.
     집과 치킨집들의 위치를 저장하고 치킨집의 M가지 조합을 선택 후
     집들의 위치를 순회하여 각 집마다 선택된 치킨집을 순회하여 가장 짧은
     거리의 치킨집까지의 거리를 누적하여 최종적으로 모든 조합에서 가장
     누적거리가 짧은 거리를 반환하면 될 것이다.

     -- 구현
     구상과 똑같이 구현하였으며 조합에서 선택된 수는 인덱스만 리스트에 추가 후
     조합 완성 시 치킨집이 저장된 위치 리스트에서 해당 인덱스만 가져와 순회하여
     문제를 상대적으로 간단하게 해결하였다. 이 문제는 조합 및 순열에 대한 탐색이
     익숙한 현재로써는 상당히 빠르게 해결할 수 있는 문제였다.

     */

    static int minimumCount = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] firstLine = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        int N = firstLine[0];
        int M = firstLine[1];

        List<int[]> marketPositions = new ArrayList<>();
        List<int[]> housePositions = new ArrayList<>();

        for(int i = 0; i < N; i++) {
            String rows = br.readLine().replaceAll(" ", "");
            for(int j = 0; j < rows.length(); j++) {
                int value = rows.charAt(j) - '0';
                if(value == 2) {
                    marketPositions.add(new int[]{i, j});
                } else if(value == 1) {
                    housePositions.add(new int[]{i, j});
                }
            }
        }

        selectMarkets(new ArrayList<>(), 0, 0, M, marketPositions, housePositions);
        System.out.print(minimumCount);
    }

    private static void selectMarkets(List<Integer> now, int depth, int start, int M, List<int[]> marketPositions, List<int[]> housePositions) {
        if(depth == M) {
            getTotalDist(now, marketPositions, housePositions);
            return;
        }

        for(int i = start; i < marketPositions.size(); i++) {
            now.add(i);
            selectMarkets(now, depth + 1, i + 1, M, marketPositions, housePositions);
            now.remove(now.size() - 1);
        }

    }

    private static void getTotalDist(List<Integer> now, List<int[]> marketPositions, List<int[]> housePositions) {
        int totalDist = 0;

        for (int[] housePosition : housePositions) {
            int dist = Integer.MAX_VALUE;

            int houseX = housePosition[0];
            int houseY = housePosition[1];

            for(int i : now) {
                int[] marketPosition = marketPositions.get(i);
                int marketX = marketPosition[0];
                int marketY = marketPosition[1];

                int calDist = Math.abs(houseX - marketX) + Math.abs(houseY - marketY);
                dist = Math.min(calDist, dist);
            }

            totalDist += dist;
        }

        minimumCount = Math.min(totalDist, minimumCount);
    }
}