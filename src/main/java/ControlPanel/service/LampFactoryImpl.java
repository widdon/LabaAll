package ControlPanel.service;

import ControlPanel.model.Lamp;

public class LampFactoryImpl implements LampFactory { //Создаёт и возвращает новый экземпляр LAMP, передавая в него координаты и цвет
    @Override
    public Lamp createLamp(int x, int y, String color) {
        return new Lamp(x, y, color);
    }
}
