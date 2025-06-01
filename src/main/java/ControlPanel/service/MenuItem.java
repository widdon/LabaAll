package ControlPanel.service;

import java.util.Scanner;
import java.util.function.BiConsumer;

public class MenuItem extends MenuComponent {
    private final String name; //отображаемое название пункта меню
    private final BiConsumer<ControlPanelProxy, Scanner> action;

    public MenuItem(String name, BiConsumer<ControlPanelProxy, Scanner> action) {
        this.name = name;
        this.action = action;
    }

    @Override
    public String getName() { return name; } // вззвращает текстовое название пункта меню

    @Override
    public void execute(ControlPanelProxy proxy, Scanner scanner) {
        action.accept(proxy, scanner);
    }
}
