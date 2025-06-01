package ControlPanel.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

//вложенное консольное меню из групп и пунктов
public class MenuGroup extends MenuComponent {
    private final String name; //название группы
    private final List<MenuComponent> children = new ArrayList<>(); // список вложенных элементов меню

    public MenuGroup(String name) {
        this.name = name;
    }

    public void add(MenuComponent component) {
        children.add(component);
    } // позволяет добавить новый пункт в группу

    @Override
    public String getName() {
        return name;
    } // возвращает название группы

    @Override
    public void execute(ControlPanelProxy proxy, Scanner scanner) { //отображает меню и обрабатывает выбор пользователя
        while (true) {
            try {
                System.out.println("\n--- " + name + " ---");
                for (int i = 0; i < children.size(); i++) {
                    System.out.printf("%d. %s%n", i + 1, children.get(i).getName());
                }
                System.out.print("Выберите пункт: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Очистка буфера

                if (choice > 0 && choice <= children.size()) {
                    children.get(choice - 1).execute(proxy, scanner);
                    return;
                }
                System.out.println("Неверный пункт меню! Попробуйте снова.");
            } catch (InputMismatchException e) {
                System.out.println("Ошибка: введите номер пункта меню!");
                scanner.nextLine(); // Очищаем буфер
            }
        }
    }
}