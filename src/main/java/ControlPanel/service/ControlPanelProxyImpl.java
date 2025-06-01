package ControlPanel.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ControlPanelProxyImpl implements ControlPanelProxy {
    private final ControlPanel controlPanel;
    private final Map<String, String> cache;
    private boolean initialized = false;

    public ControlPanelProxyImpl(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
        this.cache = new HashMap<>();
        System.out.println();
        boolean isAuthenticated = false;
        while (!isAuthenticated) {
            if (checking()) {
                System.out.println("Добро пожаловать в панель управления");
                isAuthenticated = true;
            } else {
                System.out.println("Неверный пароль. Попробуйте еще раз.");
            }
        }
        initialize();
    }

    private void initialize() {
        if (!initialized) {
            System.out.println("[ДЕЙСТВИЕ] Инициализация панели управления...");
            initialized = true;
        }
    }

    @Autowired
    private ControlPanelProxy proxy;

    @PostConstruct
    public void checkProxy() {
        System.out.println("Тип прокси: " + proxy.getClass().getName());
        // Должно быть: com.sun.proxy.$ProxyXX или подобное
    }

    @Override
    public void visualize() {
        initialize();
        controlPanel.visualize();
        cache.put("visualize", "[РЕЗУЛЬТАТ] Панель управления отображена");
    }

    @Override
    public void pressButton(int x, int y) {
        if (checkAccess()) {
            System.out.println(String.format("[ДЕЙСТВИЕ] Кнопка нажата в координатах (%s, %s)...", x, y));
            controlPanel.pressButton(x, y);
            System.out.println("[РЕЗУЛЬТАТ] Панель после нажатия кнопки:");
            cache.put("pressButton", "Кнопка нажата");
        } else {
            System.out.println("[РЕЗУЛЬТАТ] Отказано в доступе");
        }
    }

    @Override
    public void requestLampBinding() {
        if (checkAccess()) {
            System.out.println("[ДЕЙСТВИЕ] Запрос на привязку лампы...");
            controlPanel.requestLampBinding();
            System.out.println("[РЕЗУЛЬТАТ] Панель после попытки привязки:");
            cache.put("requestLampBinding", "Лампа привязана к кнопке");
        } else {
            System.out.println("[РЕЗУЛЬТАТ] Отказано в доступе");
        }
    }

    @Override
    public void requestLampUnlink() {
        Scanner scanner = new Scanner(System.in);

        int buttonX = Menu.requestIntegerInput(scanner, "Введите координату кнопки (X):");
        int buttonY = Menu.requestIntegerInput(scanner, "Введите координату кнопки (Y):");

        int lampX = Menu.requestIntegerInput(scanner, "Введите координату лампы (X):");
        int lampY = Menu.requestIntegerInput(scanner, "Введите координату лампы (Y):");

        if (checkAccess()) {
            System.out.println("[ДЕЙСТВИЕ] Запрос на отвязку лампы...");
            controlPanel.unlinkButtonFromLamp(buttonX, buttonY, lampX, lampY);
            System.out.println("[РЕЗУЛЬТАТ] Панель после попытки отвязки:");
            cache.put("unlinkButtonFromLamp", "Лампа отвязана от кнопки");
        } else {
            System.out.println("[РЕЗУЛЬТАТ] Отказано в доступе");
        }
    }

    private boolean checkAccess() {
        return true;
    }

    private boolean checking() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Введите пароль, чтобы войти в панель управления: ");
        String password = scanner.nextLine();
        return validatePassword(password);
    }

    private boolean validatePassword(String password) {
        return password.equals("1234");
    }

    @Override
    public void processRandomLamps(int count) {
        controlPanel.processRandomLamps(count);
    }
}