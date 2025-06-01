package ControlPanel.Threads;

import ControlPanel.service.MenuGroup;
import ControlPanel.service.MenuItem;
import ControlPanel.service.ControlPanelProxy;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Producer implements Runnable {
    private final TaskQueue taskQueue;
    private final Object confirmationLock;
    private final Scanner scanner;
    private final ControlPanelProxy proxy;

    public Producer(TaskQueue taskQueue, Object confirmationLock, Scanner scanner, ControlPanelProxy proxy) {
        this.taskQueue = taskQueue;
        this.confirmationLock = confirmationLock;
        this.scanner = scanner;
        this.proxy = proxy;
    }

    @Override
    public void run() {
        MenuGroup mainMenu = new MenuGroup("Главное меню");

        mainMenu.add(new MenuItem("Нажать кнопку", (proxy, sc) -> {
            int x = getValidCoordinate(sc, "Введите координату кнопки (X):");
            int y = getValidCoordinate(sc, "Введите координату кнопки (Y):");
            try {
                taskQueue.put(new Task(1, x, y));
                waitForConfirmation();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }));

        mainMenu.add(new MenuItem("Авто-привязка лампы к кнопке", (proxy, sc) -> {
            try {
                taskQueue.put(new Task(2));
                waitForConfirmation();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }));

        mainMenu.add(new MenuItem("Отвязать кнопку от лампы", (proxy, sc) -> {
            try {
                taskQueue.put(new Task(3));
                waitForConfirmation();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }));

        mainMenu.add(new MenuItem("5 случайных ламп", (proxy, sc) -> {
            try {
                taskQueue.put(new Task(4));
                waitForConfirmation();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }));

        mainMenu.add(new MenuItem("Выход", (proxy, sc) -> {
            try {
                taskQueue.put(new Task(5));
                waitForConfirmation();
                System.exit(0);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }));

        while (true) {
            mainMenu.execute(proxy, scanner);
        }
    }

    private void waitForConfirmation() throws InterruptedException {
        System.out.flush(); // Очищаем буфер вывода
        synchronized (confirmationLock) {
            confirmationLock.wait();
        }
    }

    private int getValidCoordinate(Scanner scanner, String message) {
        while (true) {
            try {
                System.out.println(message);
                int coord = scanner.nextInt();
                scanner.nextLine();
                return coord;
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите целое число!");
                scanner.nextLine();
            }
        }
    }
}