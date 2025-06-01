package ControlPanel.Threads;

import java.util.LinkedList;
import java.util.Queue;

public class TaskQueue {
    private final Queue<Task> queue = new LinkedList<>();
    private final int maxSize;

    public TaskQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized void put(Task task) throws InterruptedException {
        while (queue.size() == maxSize) {
            wait(); // Ждем, если очередь полна
        }
        queue.add(task);
        notifyAll(); // Оповещаем всех ожидающих потребителей
    }

    public synchronized Task take() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // Ждем, если очередь пуста
        }
        Task task = queue.poll();
        notifyAll(); // Оповещаем производителей, что есть место
        return task;
    }

    public synchronized int size() {
        return queue.size();
    }
}