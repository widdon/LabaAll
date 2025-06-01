package ControlPanel.service;

import java.util.Scanner;

//построение консольного меню с вложенными пунктами
public abstract class MenuComponent {
    public String getName() { throw new UnsupportedOperationException(); } //возвращает название пункта меню
    public void execute(ControlPanelProxy proxy, Scanner scanner) { throw new UnsupportedOperationException(); } //выполняет действие, связанное с пунктом меню
}