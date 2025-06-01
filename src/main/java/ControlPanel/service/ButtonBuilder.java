package ControlPanel.service;

//
import ControlPanel.model.Button;
//import org.springframework.stereotype.Service;
//@Service

//билдер для создания кнопок с настройкой координат x и y
public class ButtonBuilder {
    private int x; //Хранят координаты будущей кнопки
    private int y;

    public ButtonBuilder() {
        this.x = 0;
        this.y = 0;
    }

    public ButtonBuilder setX(int x) {
        this.x = x; //чтобы вызывать методы цепочкой
        return this;
    }

    public ButtonBuilder setY(int y) {
        this.y = y;
        return this;
    }

    public Button build() {
        return new Button(x, y);
    } //Создаёт и возвращает объект Button с указанными координатами

    //чтобы начать построение без создания переменной вручную
    public static ButtonBuilder builder() {
        return new ButtonBuilder();
    }
}
