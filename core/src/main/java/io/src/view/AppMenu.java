package io.src.view;

import io.src.model.Result;

import java.util.Scanner;

public interface AppMenu {
    Result check(Scanner scanner, String cmd);
}
