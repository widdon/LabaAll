package ControlPanel.Threads;

import ControlPanel.service.ControlPanelProxy;
import java.util.Scanner;

public class Consumer implements Runnable {
    private final TaskQueue taskQueue;
    private final Object confirmationLock;
    private final ControlPanelProxy proxy;

    public Consumer(TaskQueue taskQueue, Object confirmationLock, ControlPanelProxy proxy) {
        this.taskQueue = taskQueue;
        this.confirmationLock = confirmationLock;
        this.proxy = proxy;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Task task = taskQueue.take();
                Thread.sleep(100); // Небольшая задержка для синхронизации вывода
                System.out.println("Потребитель взял запрос: '" + task.getChoice() + "' из очереди задач");

                switch (task.getChoice()) {
                    case 1 -> {
                        Scanner sc = new Scanner(System.in);
                        proxy.pressButton(task.getX(), task.getY());
                        proxy.visualize();
                    }
                    case 2 -> {
                        proxy.requestLampBinding();
                        proxy.visualize();
                    }
                    case 3 -> {
                        proxy.requestLampUnlink();
                        proxy.visualize();
                    }
                    case 4 -> {
                        proxy.processRandomLamps(5);
                    }
                    case 5 -> {
                        System.out.println("Завершение работы потребителя.");
                        System.exit(0);
                    }
                    default -> System.out.println("Неизвестный запрос.");
                }

                synchronized (confirmationLock) {
                    confirmationLock.notify(); // Оповещаем Producer о завершении задачи
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}