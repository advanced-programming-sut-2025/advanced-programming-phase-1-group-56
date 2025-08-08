package io.src.view;

import io.src.controller.GameMenuController.GameController;
import io.src.model.App;
import io.src.model.Enums.Menu;

import java.util.Scanner;

public class AppView {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        App.setScanner(scanner);
        do {
            System.out.println(App.getCurrentMenu().checkCommand(scanner, scanner.nextLine()).getMessage());
        } while (App.getCurrentMenu() != Menu.exitMenu);
    }
}
