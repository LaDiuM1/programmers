package org.baekjoon.hanghae99_challenger;

import java.io.*;
import java.util.*;

public class SolutionByDay17 {
    /**

     [https://www.acmicpc.net/problem/1083]
     백준 1083 - 소트

     -- 문제
     이 문제는 중복 없는 수열이 주어지며 인접한 숫자들 간에만 교환이 가능하다.
     교환 가능한 횟수가 주어질 때 사전 뒤에 있는 숫자로 정렬되도록 출력하는 문제
     이다.

     -- 문제 분석
     처음 문제를 접하였을 때는 단순하게 배열을 처음부터 순회하여 인접한 숫자들을
     교환해 가며 내림차순으로 정렬하면 될 것 같은 문제로 보였다. 하지만 그런 문제
     가 골드 수준의 문제로 나올 것 같지 않기 때문에 문제를 자세히 읽어 보았다. 문
     제를 읽어보면 "사전순으로 가장 뒷서는 것을 출력한다." 이라는 문구가 있는데,
     자주 사용되는 단어가 아니라 의미가 모호하여 단어의 의미를 유추해 보자면 이는
     제한된 교환 조건 내에서 가장 높은 숫자가 가장 앞으로 오게 한다는 의미로 해석할
     수 있다는 것으로 결론을 내렸다. 백준은 문제를 가장 사전상의 의미대로 출제하려다
     보니 다소 친절하지 않게 느껴지는 과한 용어도 사용하는 것으로 보인다. 일단 해결
     조건을 유추하였으니 문제를 다시 분석해 본다면, 제한 조건은 s이고 s의 제한 안에
     가장 높은 숫자를 앞으로 이동하기 위해서는 각 1회의 정렬마다 s로 이동 가능한 가장
     높은 숫자를 찾는 것이다. 뒤에 있는 숫자를 앞으로 이동하려면 반드시 앞에 있는 숫
     자들을 모두 교환해 나가야 하므로 최대 s만큼의 인덱스까지 번호 중 가장 높은 값을
     찾고 맨 앞으로 가져오면 된다. 그렇다면 인덱스 0부터 시작하여 현재의 인덱스의 뒤
     에 있는 숫자 중 이동 가능한 가장 큰 숫자를 맨 앞으로 가져오면서 더 이상 교환할
     게 없거나, s를 모두 소모할 때까지 정렬을 수행하면 될 것이다.

     -- 구현
     1. 수열을 배열로 저장하고 s가 0보다 클 때에만 정렬하는 코드를 작성.
     2. 배열을 0부터 n - 1까지 순회하며 각 순회에서 순회 번호부터 s까지의 숫자
        중 가장 큰 값을 찾아 맨 앞으로 가져오고 교환 횟수만큼 s를 차감한다.
     3. 각 교환 시 s내에서 더 이상 교환할 수 없다면 다음 순회로 바로 이동한다.
     4. 모든 순회를 완료하거나 각 순회에서 s를 모두 소모하였다면 정답을 반환한다.

     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int s = Integer.parseInt(br.readLine());

        if(s > 0) {
            for (int i = 0; i < n - 1; i++) {
                int maxIndex = i;
                int maxValue = arr[i];
                for (int j = i + 1; j < n && j - i <= s; j++) {
                    if (arr[j] > maxValue) {
                        maxValue = arr[j];
                        maxIndex = j;
                    }
                }

                if (maxIndex == i) continue;

                for (int j = maxIndex; j > i; j--) {
                    int temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                    s--;
                }

                if (s == 0) break;
            }
        }

        for (int i = 0; i < n; i++) {
            sb.append(arr[i]);
            if(i < n - 1) sb.append(' ');
        }

        bw.write(sb.toString());
        bw.close();
    }

}