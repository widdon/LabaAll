package ControlPanel.model;

public abstract class PanelElement { //инкапсулирует координаты x и y, чтобы их не дублировать в каждом элементе
    private final int x;
    private final int y;

    public PanelElement(int x, int y) {
        this.x = x; //Устанавливает координаты при создании потомка
        this.y = y;
    }

    //получаем значения координат
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
