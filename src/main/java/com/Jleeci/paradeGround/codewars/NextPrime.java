
package com.Jleeci.paradeGround.codewars;

public class NextPrime {
    public static void main(String[] args) {
        nextPrime(1);
    }

    public static void nextPrime(int num) {
        Boolean isPrime = true;
        for (int nextNum = num + 1; nextNum > 0; nextNum++) {
            isPrime = true;
            for (int i = 2; i < nextNum; i++) {
                if (nextNum % i == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                System.out.println("next prime is " + nextNum);
            }
        }
    }
     /**
     * 计算一个数的最大质因数
     * @param num
     */
    public static void test(long num) {
        long x = 1;
        long factor = 1;
        while (x < num) {
            if (num % x == 0) {
                factor = num / x;
                for (int i = 2; i < factor; i++) {
                    if (factor % i == 0) {
                        break;
                    } else {
                        System.out.println(factor);
                        System.out.println(x);
                        return;
                    }
                }
            }
            x++;
        }
    }
}
