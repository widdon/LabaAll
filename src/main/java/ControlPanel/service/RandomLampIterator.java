package ControlPanel.service;

import ControlPanel.model.Lamp;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//итерация по лампам в случайном порядке
public class RandomLampIterator implements LampIterator {
    private final List<Lamp> lamps; //список всех ламп
    private int currentIndex; //индекс текущей лампы, по которой идёт перебор
    private final Random random;

    public RandomLampIterator(List<Lamp> lamps) {
        this.lamps = lamps; //Сохраняется переданный список
        this.random = new Random();
        Collections.shuffle(this.lamps); //Перемешивается
        this.currentIndex = 0; //Устанавливается начальный индекс в 0
    }

    @Override
    public boolean hasNext() {
        return currentIndex < 5 && currentIndex < lamps.size();
    } //максимум 5 ламп для перебора

    @Override
    public Lamp next() {
        return lamps.get(currentIndex++);
    } //возвращает текущую лампу и увеличивает индекс
}