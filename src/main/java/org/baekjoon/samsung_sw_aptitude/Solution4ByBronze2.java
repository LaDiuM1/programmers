package org.baekjoon.samsung_sw_aptitude;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Solution4ByBronze2 {

    /**
     [https://www.acmicpc.net/problem/13458]
     삼성 SW 역량 테스트 기출 문제
     시험 감독관

     삼성 SW역량 테스트 기출 문제를 순서대로 풀던중 만난 문제로
     각 시험장마다 시험보는 인원수가 배열로 주어지며
     필수이며 유일한 감독원 A와 중복 가능한 감독원 B의 각각 담당 가능한
     인원이 주어질 때, 필요한 최소한의 총 감독원 수를 구하는 문제로
     별다른 알고리즘 없이 간단하게 반복문과 계산식으로 문제를 해결하였다.

     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        int[] numOfPeoples = new int[n];

        String[] splitPeoples = br.readLine().split(" ");
        for(int i = 0; i < splitPeoples.length; i++) {
            numOfPeoples[i] = Integer.parseInt(splitPeoples[i]);
        }

        String[] viewer = br.readLine().split(" ");

        int b = Integer.parseInt(viewer[0]);
        int c = Integer.parseInt(viewer[1]);

        long count = 0;
        for(int numOfPeople : numOfPeoples) {
            count++;
            if(numOfPeople <= b) {
                continue;
            }
            numOfPeople -= b;
            count += numOfPeople % c == 0 ? numOfPeople / c : numOfPeople / c + 1;
        }

        System.out.println(count);
    }

}