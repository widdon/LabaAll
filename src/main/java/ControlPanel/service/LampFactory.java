package ControlPanel.service;


import ControlPanel.model.Lamp;

//интерфейс фабрики, который определяет метод
public interface LampFactory {
    Lamp createLamp(int x, int y, String color);
}

