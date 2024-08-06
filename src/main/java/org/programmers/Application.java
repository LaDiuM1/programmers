package org.programmers;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String[] strArr = scan.nextLine().split(" ");
        int n = Integer.parseInt(strArr[0]);
        int k = Integer.parseInt(strArr[1]);

        boolean[] checkArr = new boolean[n + 1];

        int count = 0;
        for (int i = 2; i <= n; i++) {
            if (!checkArr[i]) {
                for (int j = i; j <= n; j += i) {
                    if (!checkArr[j]) {
                        checkArr[j] = true;
                        count++;
                        if (count == k) {
                            System.out.println(j);
                            return;
                        }
                    }
                }
            }
        }
    }
}