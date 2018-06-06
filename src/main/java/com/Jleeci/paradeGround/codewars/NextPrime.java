
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
}
