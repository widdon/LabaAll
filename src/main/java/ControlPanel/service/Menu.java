package ControlPanel.service;

import java.util.Scanner;
import java.util.InputMismatchException;

//безопасный ввод чисел из консоли
public class Menu {
    public static int requestIntegerInput(Scanner scanner, String message) {
        while (true) {
            try {
                System.out.println(message);
                int input = scanner.nextInt();
                scanner.nextLine(); // Очистка буфера
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите целое число!");
                scanner.nextLine(); // Очищаем буфер
            }
        }
    }
}

