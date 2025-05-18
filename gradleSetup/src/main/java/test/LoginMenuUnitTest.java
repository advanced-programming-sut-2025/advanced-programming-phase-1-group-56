package test;

import model.App;
import model.Enums.Menu;
import model.Enums.commands.LoginMenuCommands;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import view.LoginMenu;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class LoginMenuUnitTest {
    private LoginMenu loginMenu;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @Before
    public void setUp() {
        loginMenu = new LoginMenu();
        System.setOut(new PrintStream(outputStream)); // گرفتن خروجی کنسول
        App.setCurrentMenu(Menu.loginMenu); // ریست حالت برنامه
    }

    @After
    public void tearDown() {
        System.setOut(originalOut); // بازگردانی خروجی اصلی
        System.setIn(originalIn); // بازگردانی ورودی اصلی
    }

    // ------------ تست‌های اصلی ------------

    @Test
    public void testValidRegistrationFlow() {
        // شبیه‌سازی ورودی کاربر
        String input = "register --username ali --password Aa123! --nickname ali\n" +
                "question pick --question 1 --answer test --confirm test";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        loginMenu.check(new Scanner(System.in));

        // بررسی خروجی
        String output = outputStream.toString();
        assertTrue(output.contains("Success"));
        assertTrue(output.contains("Question set"));
    }

    @Test
    public void testLoginWithStayLoggedIn() {
        String input = "login --username ali --password Aa123! --stay-logged-in";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        loginMenu.check(new Scanner(System.in));

        String output = outputStream.toString();
        assertTrue(output.contains("Logged in"));
    }

    @Test
    public void testInvalidCommand() {
        String input = "invalid_command --wrong-option";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        loginMenu.check(new Scanner(System.in));

        String output = outputStream.toString();
        assertTrue(output.contains("invalid command bro!.."));
    }

    @Test
    public void testShowCurrentMenu() {
        String input = "invalid_command --wrong-option";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        loginMenu.check(new Scanner(System.in));

        String output = outputStream.toString();
        assertTrue(output.contains("invalid command bro!.."));
    }
    @Test
    public void testَnswerSecurityQuestion() {
        String input = "invalid_command --wrong-option";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        loginMenu.check(new Scanner(System.in));

        String output = outputStream.toString();
        assertTrue(output.contains("invalid command bro!.."));
    }

    @Test
    public void testForgetPassword() {
        String input = "invalid_command --wrong-option";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        loginMenu.check(new Scanner(System.in));

        String output = outputStream.toString();
        assertTrue(output.contains("invalid command bro!.."));
    }

    @Test
    public void testPickSecurityQuestion() {
        String input = "invalid_command --wrong-option";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        loginMenu.check(new Scanner(System.in));

        String output = outputStream.toString();
        assertTrue(output.contains("invalid command bro!.."));
    }


    @Test
    public void testExitMenu() {
        String input = "menu exit";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        loginMenu.check(new Scanner(System.in));

        assertEquals(Menu.exitMenu, App.getCurrentMenu());
    }
}