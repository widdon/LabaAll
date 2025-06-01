package ControlPanel;

import ControlPanel.config.AppConfig;
import ControlPanel.Threads.Consumer;
import ControlPanel.Threads.Producer;
import ControlPanel.service.ControlPanelProxy;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "ControlPanel.service",
        "ControlPanel.aspect"
})
public class ControlPanelApplication {
    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            ControlPanelProxy proxy = context.getBean(ControlPanelProxy.class);

            // Вывод панели ДО запуска потоков
            System.out.println("Сгенерирована панель управления:");
            proxy.visualize();

            // Получаем Producer и Consumer
            Producer producer = context.getBean(Producer.class);
            Consumer consumer = context.getBean(Consumer.class);

            // Запускаем потоки
            Thread producerThread = new Thread(producer);
            Thread consumerThread = new Thread(consumer);

            producerThread.start();
            consumerThread.start();

            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}