package org.example;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int quantity = 0;
        System.out.println("Сколько чисел вы хотите перевести:");

        Scanner scanQuantity = new Scanner(System.in);


        quantity = scanQuantity.nextInt();


        for (int i = 1; i <= quantity; ) {
            try {
                System.out.println("Введите число:");

                Scanner scanNum = new Scanner(System.in);

                Integer number = scanNum.nextInt();

                System.out.println("Выберите систему счисления, в которую нужно перевести число (2, 8, 16)");

                int numSys = scanNum.nextInt();

                System.out.println(transform(number, numSys));
                i++;
            } catch (Exception ex) {
                System.out.println("Некорретный ввод данных!");
            }
        }


    }

    static String transform(int number, int numSys) {
        return switch (numSys) {
            case 2 -> Integer.toBinaryString(number);
            case 8 -> Integer.toOctalString(number);
            case 16 -> Integer.toHexString(number);
            default -> throw new IllegalStateException("Ошибка, введите 2, 8 или 16" + numSys);
        };

    }
}