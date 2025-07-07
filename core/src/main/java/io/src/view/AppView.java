package io.src.view;

import io.src.model.App;
import io.src.model.Enums.Menu;
import java.util.Scanner;

public class AppView {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        do {
            App.getCurrentMenu().checkCommand(scanner);
        } while (App.getCurrentMenu() != Menu.exitMenu);
    }
}
