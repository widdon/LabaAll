package ControlPanel.config;

import ControlPanel.aspect.LoggingAspect;
import ControlPanel.service.*;
import ControlPanel.Threads.Consumer;
import ControlPanel.Threads.Producer;
import ControlPanel.Threads.TaskQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Scanner;

@Configuration
@EnableAspectJAutoProxy  // ← Критически важно!
@ComponentScan("ControlPanel.aspect")
public class AppConfig {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @Bean
    public String checkAspects(LoggingAspect aspect) {
        System.out.println("Аспект LoggingAspect работает: " + aspect);
        return "AOP check";
    }

    @Bean
    public LampFactory lampFactory() {
        return new LampFactoryImpl();
    }

    @Bean
    public ControlPanel controlPanel(LampFactory lampFactory) {
        return new ControlPanel(lampFactory);
    }

    @Bean
    public ControlPanelProxy controlPanelProxy(ControlPanel controlPanel) {
        return new ControlPanelProxyImpl(controlPanel);
    }

    // Заменяем BlockingQueue на нашу TaskQueue
    @Bean
    public TaskQueue taskQueue() {
        return new TaskQueue(10); // Максимальный размер очереди 10
    }

    @Bean
    public Object confirmationLock() {
        return new Object();
    }

    @Bean
    public Producer producer(
            TaskQueue taskQueue,
            Object confirmationLock,
            Scanner scanner,
            ControlPanelProxy proxy
    ) {
        return new Producer(taskQueue, confirmationLock, scanner, proxy);
    }

    @Bean
    public Consumer consumer(
            TaskQueue taskQueue,
            Object confirmationLock,
            ControlPanelProxy proxy
    ) {
        return new Consumer(taskQueue, confirmationLock, proxy);
    }
}