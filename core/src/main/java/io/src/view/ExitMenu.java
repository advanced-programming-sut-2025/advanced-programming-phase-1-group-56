package io.src.view;

import io.src.model.Result;

import java.util.Scanner;

public class ExitMenu implements AppMenu {

    @Override
    public Result check(Scanner scanner, String cmd) {
        return new Result(true, "");
    }
}
