package ControlPanel.service;

import ControlPanel.model.Button;
import ControlPanel.model.Lamp;
import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ControlPanel.service.Menu.requestIntegerInput;

public class ControlPanel //бизнес-логика управления панелью
{
    private final int width; // размер панели
    private final int height;
    private final List<Button> buttons; // списки всех кнопок и ламп на панели
    private final List<Lamp> lamps;
    private final LampFactory lampFactory;

    public Button createButton(int x, int y)
    {
        return ButtonBuilder.builder().setX(x).setY(y).build();
    }

    public ControlPanel(LampFactory lampFactory) {
        this.width = 5;
        this.height = 5;
        this.buttons = new ArrayList<>();
        this.lamps = new ArrayList<>();
        this.lampFactory = lampFactory;  // Внедряем фабрику
    }

    public LampIterator getRandomLampIterator() {
        return new RandomLampIterator(this.lamps);
    }

    @PostConstruct
    public void init() {
        synchronized (this) {
            Random random = new Random();
            String[] colors = {"Красный", "Зеленый", "Синий", "Желтый", "Белый"};

            for (int y = 1; y <= height; y++) {
                for (int x = 1; x <= width; x++) {
                    if (random.nextBoolean()) {
                        buttons.add(createButton(x, y));
                    } else {
                        String color = colors[random.nextInt(colors.length)];
                        lamps.add(lampFactory.createLamp(x, y, color));
                    }
                }
            }

            System.out.printf("Сгенерировано кнопок: %d%n", buttons.size());
            System.out.printf("Сгенерировано ламп: %d%n", lamps.size());
        }
    }

    // Визуализация панели
    public void visualize() {
        for (int y = 1; y <= height; y++) {
            for (int x = 1; x <= width; x++) {
                // Проверяем лампу
                boolean lampFound = false;
                for (Lamp lamp : lamps) {
                    if ((lamp.getX() == x) && (lamp.getY() == y)) {
                        System.out.print(lamp.isActive() ? "Л_" + lamp.getColor().charAt(0) + " " : "Л_ ");
                        lampFound = true;
                        break;
                    }
                }
                if (lampFound) continue;

                // Проверяем кнопку
                boolean buttonFound = false;
                for (Button button : buttons) {
                    if ((button.getX() == x) && (button.getY() == y)) {
                        System.out.print(button.isPressed() ? "o " : "O ");
                        buttonFound = true;
                        break;
                    }
                }

                // Если ничего не найдено (хотя такого не должно быть)
                if (!buttonFound) {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    // Нажатие кнопки
    public void pressButton(int x, int y) {
        // Проверяем, есть ли лампа в этих координатах
        for (Lamp lamp : lamps) {
            if ((lamp.getX() == x) && (lamp.getY() == y)) {
                System.out.println("В указанных координатах находится лампа, а не кнопка.");
                return;
            }
        }

        // Проверяем координаты
        if ((x < 1) || (x > width) || (y < 1) || (y > height)) {
            System.out.println("Неверные координаты кнопки. Повторите ввод.");
            return;
        }

        // Ищем кнопку
        for (Button button : buttons) {
            if ((button.getX() == x) && (button.getY() == y)) {
                if (button.isPressed()) {
                    button.release();
                } else {
                    button.press();
                }
                return;
            }
        }

        System.out.println("В указанных координатах нет кнопки.");
    }

    // Автоматическая привязка лампы к кнопке
    public void requestLampBinding() {
        Random random = new Random();

        if (buttons.isEmpty() || lamps.isEmpty()) {
            System.out.println("Нет доступных кнопок или ламп для привязки.");
            return;
        }

        // Собираем список кнопок, которые еще не привязаны ко всем лампам
        List<Button> buttonsWithFreeSlots = new ArrayList<>();
        for (Button button : buttons) {
            // Можно добавить условие, если хотим ограничить максимальное количество ламп на кнопку
            // Например, если кнопка может управлять не более чем 3 лампами (-_-):
            // if (button.getLamps().size() < 3) {
            buttonsWithFreeSlots.add(button);
            // }
        }

        if (buttonsWithFreeSlots.isEmpty()) {
            System.out.println("Все кнопки уже привязаны к максимальному количеству ламп.");
            return;
        }

        // Выбираем случайную кнопку из доступных
        Button button = buttonsWithFreeSlots.get(random.nextInt(buttonsWithFreeSlots.size()));

        // Собираем список ламп, которые еще не привязаны к этой кнопке
        List<Lamp> availableLamps = new ArrayList<>();
        for (Lamp lamp : lamps) {
            if (!button.isRegisteredObserver(lamp)) {
                availableLamps.add(lamp);
            }
        }

        if (availableLamps.isEmpty()) {
            System.out.println("Нет доступных ламп для привязки к выбранной кнопке.");
            return;
        }

        // Выбираем случайную лампу из доступных
        Lamp lamp = availableLamps.get(random.nextInt(availableLamps.size()));

        // Привязываем лампу к кнопке
        button.registerObserver(lamp);
        System.out.printf("Привязана лампа (%d, %d) цвета %s к кнопке (%d, %d)%n",
                lamp.getX(), lamp.getY(), lamp.getColor(), button.getX(), button.getY());
    }




    //Проверяет, есть ли лампа в заданных координатах
    private boolean isLamp(int x, int y) { //Использовался бы, например, чтобы запретить дублирование
        for (Lamp lamp : lamps) {
            if (lamp.getX() == x && lamp.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public void unlinkButtonFromLamp(int buttonX, int buttonY, int lampX, int lampY) {
        Button selectedButton = null;
        for (Button button : buttons) {
            if (button.getX() == buttonX && button.getY() == buttonY) {
                selectedButton = button;
                break;
            }
        }

        if (selectedButton == null) {
            System.out.println("Кнопка с указанными координатами не найдена.");
            return;
        }

        // Ищем лампу по координатам
        Lamp selectedLamp = null;
        for (Lamp lamp : lamps) {
            if (lamp.getX() == lampX && lamp.getY() == lampY) {
                selectedLamp = lamp;
                break;
            }
        }

        if (selectedLamp == null) {
            System.out.println("Лампа с указанными координатами не найдена.");
            return;
        }

        // Проверяем, привязана ли кнопка к лампе
        if (!selectedButton.isRegisteredObserver(selectedLamp)) {
            System.out.println("Эта кнопка не привязана к этой лампе.");
            return;
        }

        // Удаляем лампу из списка ламп кнопки
        selectedButton.getLamps().remove(selectedLamp);
        System.out.println("Лампа успешно отвязана от этой кнопки.");
    }
    public void processRandomLamps(int count) {
        LampIterator iterator = getRandomLampIterator();
        int processed = 0;
        while (iterator.hasNext() && processed < count) {
            Lamp lamp = iterator.next();
            System.out.println("Обработка лампы: (" + lamp.getX() + ", " + lamp.getY() + ") Цвет: " + lamp.getColor());
            processed++;
        }
    }
}