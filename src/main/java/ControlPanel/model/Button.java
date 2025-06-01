package ControlPanel.model;

import java.util.ArrayList;
import java.util.List;

public class Button extends PanelElement {
    private boolean pressed;

    private final List<ButtonObserver> observers;

    public Button(int x, int y) {
        super(x, y);
        this.pressed = false;
        this.observers = new ArrayList<>();
    }

    // Добавляем метод для регистрации наблюдателя и проверяет, что лампа ещё не привязана
    public void registerObserver(ButtonObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer); //Если нет — добавляет observer в список.
            System.out.println(String.format("К кнопке добавлен новый наблюдатель! Количество наблюдателей кнопки (%s, %s) = %s", getX(), getY(), observers.size()));
        } else {
            System.out.println("Эта кнопка уже привязана к лампе.");
        }
    }

    // Добавляем метод для оповещения наблюдателей
    private void notifyObservers() {
        for (ButtonObserver observer : observers) {
            observer.buttonClicked();

        }
    }

    public void press() {
        this.pressed = true;
        System.out.printf("Нажата кнопка (%d,%d), наблюдателей: %d\n", getX(), getY(), observers.size());
        notifyObservers(); //Все наблюдатели оповещаются (лампы могут загореться).
    }

    public void release() {
        this.pressed = false;
        notifyObservers();

    }

    //Метод проверяет, есть ли лампа в списке подписчиков.
    public boolean isRegisteredObserver(Lamp lamp) {
        return observers.contains(lamp);
    }

    public boolean isPressed() {
        return pressed;
    } //нужен для визуализации состояния кнопок

    public List<ButtonObserver> getLamps() {
        return observers;
    } // возвращает список всех привязанных ламп
}