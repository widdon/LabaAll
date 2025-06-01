package ControlPanel.model;

public class Lamp extends PanelElement implements ButtonObserver {
    private boolean active; //флаг, включена лампа или нет
    private final String color; // неизменяемый цвет лампы (задаётся при создании)

    public Lamp(int x, int y, String color) {
        super(x, y); //Устанавливает координаты
        this.color = color; //Сохраняет цвет лампы
        this.active = false;
    }


    public String getColor() {
        return color;
    } //возвращает цвет лампы

    public boolean isActive() {
        return active;
    } //показывает, включена ли лампа

    @Override
    public void buttonClicked() {
        this.active = !active; //переключает состояние лампы (вкл/выкл) при каждом нажатии кнопки
        System.out.printf("Лампа (%d,%d) -> %s%n", getX(), getY(), active ? "ЗАГОРЕЛАСЬ" : "ПОГАСЛА");
    }
}
