package view;

import com.google.gson.Gson;
import model.App;
import model.Enums.Menu;
import model.User;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

public class AppView {
    public void run() {
        Scanner scanner = new Scanner(System.in);
        do {
            App.getCurrentMenu().checkCommand(scanner);
        } while (App.getCurrentMenu() != Menu.exitMenu);
    }
}
