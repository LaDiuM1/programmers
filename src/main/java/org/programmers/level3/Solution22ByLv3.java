package org.programmers.level3;

import java.util.*;

public class Solution22ByLv3 {

    /**
     * [https://school.programmers.co.kr/learn/courses/30/lessons/150366]
     * 2023 KAKAO BLIND RECRUITMENT
     * 표 병합
     *
     * 프로그래머스 level3 문제중 난이도 높은 문제를 골라서 풀어보기로 하였고, 상대적
     * 으로 정답률이 낮은 이 문제를 선택하였다.
     *
     * --
     *
     * 이 문제는 엑셀과 같은 테이블을 조작하는 명령어 리스트가 주어지며 명령어대로 잘
     * 작동하였을 때 특정 셀의 값을 출력하는 명령어가 들어올 때마다 정확한 값을 출력
     * 하는지 확인하는 문제이다.
     *
     * 문제를 확인해보면 엑셀과 동일한 동작을 수행하지 않는 셀 병합, 해제가 등장하는데
     * 의도를 생각해보면 집합에 대한 관리, 특히 집합을 효율적으로 합치고 집합을 분리하는
     * 것을 테스트 하는것으로 나는 느꼈다.
     *
     * -- 구상
     * 이 문제의 핵심은 집합에 대한 관리이기 때문에 예전에 한번 다뤄보았던 union find
     * 알고리즘을 사용하기로 하였다. union find는 서로소 집합을 배열로 표현하여 특정
     * 요소가 어떤 집합에 속해있는지 효율적으로 판단할 수 있게 도와주는 알고리즘이다.
     * 여기서는 셀 병합 해제가 나오니 Map으로 key에 집합, value에 집합에 속해있는 집
     * 단을 Set으로 저장하여 셀 병합 해제 명령이 들어올 시 특정 집단을 롤백하기 위한
     * 자료구조를 구상하였다.
     *
     * -- 구현
     * - UPDATE
     * UPDATE 명령어는 종류가 두 가지이며 하나는 특정 위치의 값을 설정하는 명령이고
     * 두번째로는 특정 값을 모두 변경할 값으로 변경하는 명령어이다. 명령어를 분리하여
     * 개수로 UPDATE 명령어의 종류를 판단하였다.
     * UPDATE 1
     * 값을 설정하는 명령어로 명령어에 들어온 위치의 집합을 찾아 집합의 루트 위치에 명령
     * 어의 값을 설정
     * UPDATE 2
     * 특정 값을 변경하는 명령어로 table에서 특정 값을 찾아 다른 값으로 치환하는 코드
     * 작성
     *
     * -- MERGE
     * 명령어에 들어온 두 셀의 위치의 집합을 찾아, 합집합으로 변경하는 union find 메서드
     * 로 두 집합을 합집합으로 변경, union find 메서드에서는 두 요소의 위치를 매개변수로
     * 받아 각 루트를 find 메서드로 찾은 후 두 집합이 동일한지 확인하여 다르다면 집합 1에
     * 집합 2의 루트 값을 설정하여 합집합으로 병합, 합집합 병합 시 mergedSetMap에 집합에
     * 속하는 하위 집합들을 저장하여 병합 해제 시 활용, 이후 조건에 따라 특정 집합 위치에
     * 값 설정
     *
     * -- UNMERGE
     * 명령으로 들어온 셀 위치의 집합을 찾아 루트를 반환, 이후 루트에 해당하는 집합으로
     * 병합을 재귀적으로 해제하는 메서드 호출하여 집합을 롤백할 리스트에 순차적으로 추가,
     * 재귀에서는 mergedSetMap에 root가 있는지 확인 후 있다면 key에 해당하는 집합들
     * 을 다시 메서드로 호출하여 특정 집합에 속한 모든 요소를 탐색, 이후 key가 없을 때
     * (가장 깊은 depth) list에 root를 추가하여 특정 집합의 모든 요소를 롤백 리스트에
     * 추가, 재귀 호출 이후 list에 추가된 요소들을 순회하여 값을 자기 자신(독립 집합)으
     * 로 변경 후 해당 위치의 값을 null로 초기화, 이후 명령으로 들어온 위치에 집합의 값
     * 설정
     *
     * -- PRINT
     * 명령으로 들어온 위치의 집합의 값을 반환, null이라면 EMPTY를 반환하는 조건 추가
     *
     * -- 최종평
     * 이 문제는 집합에 대한 효율적인 처리를 학습할 수 있는 문제로 구현과 알고리즘이 섞여
     * 있는 꽤 난이도 있는 문제였다, 각 명령어에서 집합에 대한 처리가 구상에 맞게 정확하게
     * 처리되어야 하며 특정 명령어에 대한 코드를 작성할 때도 다른 명령어들의 관계까지 생각
     * 하여야 잘 작성하여야 풀 수 있는 문제이기 때문에 상대적으로 구현 시간이 오래 걸리는
     * 문제였다. 예전에 union find를 한번 경험해봐서 집합 처리를 좀 더 빠르게 할 수 있
     * 었지만, 만약 경험이 없었다면 상당히 난이도가 높은 문제였을 것이라 생각한다.
     *
     */

    final static int COLS = 51;
    final static int ROWS = 51;
    int[] group = new int[COLS * ROWS];
    Map<Integer, Set<Integer>> mergedSetMap = new HashMap<>();

    public String[] solution(String[] commands) {
        String[][] table = new String[ROWS][COLS];

        for(int i = 1; i < group.length; i++) {
            group[i] = i;
        }

        List<String> resultList = new ArrayList<>();

        for(String command : commands) {
            String[] cmdArr = command.split(" ");
            String type = cmdArr[0];

            switch (type) {
                case "UPDATE" : {
                    if(cmdArr.length == 4) {
                        // 특정 셀 위치의 값을 업데이트
                        int row = Integer.parseInt(cmdArr[1]);
                        int col = Integer.parseInt(cmdArr[2]);
                        String value = cmdArr[3];

                        int[] axis1 = getAxis(find(row, col)); // 1번 셀 집합
                        int i = axis1[0];
                        int j = axis1[1];

                        table[i][j] = value;
                    } else {
                        // 특정 값을 가지고 있는 모든 셀을 다른 값으로 업데이트
                        String value = cmdArr[1];
                        String newValue = cmdArr[2];

                        for(int i = 0; i < table.length; i++) {
                            for(int j = 0; j < table[0].length; j++) {
                                if(table[i][j] != null && table[i][j].equals(value)) {
                                    table[i][j] = newValue;
                                }
                            }
                        }
                    }

                    break;
                }
                case "MERGE" : { // 셀 병합
                    // 값이 있을 경우 병합된 셀은 그 값을 가짐,
                    // 두 값 모두 값을 가질 경우 병합된 셀은 i1, j1 기준으로 값을 가짐
                    int row1 = Integer.parseInt(cmdArr[1]);
                    int col1 = Integer.parseInt(cmdArr[2]);
                    int row2 = Integer.parseInt(cmdArr[3]);
                    int col2 = Integer.parseInt(cmdArr[4]);

                    int[] axis1 = getAxis(find(row1, col1)); // 1번 셀 집합
                    int i1 = axis1[0];
                    int j1 = axis1[1];

                    int[] axis2 = getAxis(find(row2, col2)); // 2번 셀 집합
                    int i2 = axis2[0];
                    int j2 = axis2[1];

                    String value1 = table[i1][j1]; // 1번 셀 집합의 값
                    String value2 = table[i2][j2]; // 2번 셀 집합의 값
                    union(i1, j1, i2, j2); // 집합 합치기

                    int[] afterUnionAxis = getAxis(find(i1, j1)); // 합쳐진 집합의 좌표

                    if(value1 != null && value2 != null) { // 두 값이 모두 있을 때 처음 1번 집합 값으로 설정
                        table[afterUnionAxis[0]][afterUnionAxis[1]] = value1;
                    } else if (value1 != null || value2 != null) { // 두 값중 하나만 있을 때 집합 값을 해당 값으로 설정
                        table[afterUnionAxis[0]][afterUnionAxis[1]] = value1 != null ? value1 : value2;
                    }

                    break;
                }
                case "UNMERGE" : { // 셀 병합 취소
                    // 특정 셀의 집합을 모두 취소하고 집합 셀 값을 i, j의 집합 값으로 설정
                    int row = Integer.parseInt(cmdArr[1]);
                    int col = Integer.parseInt(cmdArr[2]);

                    int root = find(row, col);  // 현재 집합의 루트
                    int[] axis1 = getAxis(root); // 1번 셀 집합
                    int i = axis1[0];
                    int j = axis1[1];
                    String value = table[i][j]; // 현재 집합의 값

                    List<Integer> rollbackList = new ArrayList<>();

                    // 집합 해제, 재귀적으로 루트 값을 가지는 모든 집합을 원상복구
                    addUnmergedList(root, rollbackList);

                    for (int index : rollbackList) {
                        group[index] = index;
                        int[] axis = getAxis(index);
                        table[axis[0]][axis[1]] = null;
                    }
                    table[row][col] = value; // 특정 셀의 값을 집합의 값으로 설정

                    break;
                }

                case "PRINT" : { // 특정 셀 값 출력, 비어있을 경우 EMPTY 출력
                    int row = Integer.parseInt(cmdArr[1]);
                    int col = Integer.parseInt(cmdArr[2]);

                    int[] axis = getAxis(find(row, col)); // 1번 셀 집합
                    int i = axis[0];
                    int j = axis[1];
                    String printMessage = table[i][j] != null ? table[i][j] : "EMPTY";
                    resultList.add(printMessage);
                }
            }
        }

        return resultList.toArray(new String[0]);
    }

    private void addUnmergedList(int root, List<Integer> now) {
        if(mergedSetMap.containsKey(root)) {
            Set<Integer> rootSet = mergedSetMap.get(root);
            for (Integer i : rootSet) {
                addUnmergedList(i, now);
            }
            mergedSetMap.remove(root);
        }
        now.add(root);
    }

    private int find(int i, int j) {
        int x = getIndex(i, j);
        if(group[x] != x) {
            int[] axis = getAxis(group[x]);
            group[x] = find(axis[0], axis[1]);
        }

        return group[x];
    }

    private int getIndex(int i, int j) {
        return i * COLS + j;
    }

    private int[] getAxis(int index) {
        return new int[]{index / COLS, index % COLS};
    }

    private void union(int i1, int j1, int i2, int j2) {
        int group1 = find(i1, j1);
        int group2 = find(i2, j2);

        if(group1 != group2) {
            group[group2] = group1;
            Set<Integer> newSet = mergedSetMap.getOrDefault(group1, new HashSet<>());
            newSet.add(group2);
            mergedSetMap.put(group1, newSet);
        }
    }

    private void printGroup() {
        for(int i = 1; i < group.length; i++) {
            System.out.printf("%-3s", group[i] + " ");
            if(i % 10 == 0) System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Solution22ByLv3 solution = new Solution22ByLv3();

        String[] commands = {
                "UPDATE 1 1 a",
                "UPDATE 1 2 b",
                "UPDATE 2 1 c",
                "UPDATE 2 2 d",
                "MERGE 1 1 1 2",
                "MERGE 2 2 2 1",
                "MERGE 2 1 1 1",
                "PRINT 1 1",
                "UNMERGE 2 2",
                "PRINT 1 1"
        };

        String[] result = solution.solution(commands);
        System.out.println(Arrays.toString(result));
    }



}