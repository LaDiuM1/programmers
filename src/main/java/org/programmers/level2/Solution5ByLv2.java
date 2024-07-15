package org.programmers.level2;

public class Solution5ByLv2 {

    int[][] dungeons;
    int dungeonCount;
    boolean[] visitedDungeons;
    int maxCount = 0;

    public int solution(int k, int[][] dungeons) {
        this.dungeons = dungeons;
        dungeonCount = dungeons.length;
        visitedDungeons = new boolean[dungeonCount];

        dfs(k, 0);

        return maxCount;
    }

    void dfs(int nowStamina, int count) {

        maxCount = Math.max(maxCount, count);

        for(int i = 0; i < dungeonCount; i++) {
            if(visitedDungeons[i]) continue;
            visitedDungeons[i] = true;

            int requireStamina = dungeons[i][0];
            int consumedStamina = dungeons[i][1];

            if(nowStamina >= requireStamina) {
                dfs(nowStamina - consumedStamina, count + 1);
            }

            visitedDungeons[i] = false;
        }
    }

}