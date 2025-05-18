package test;

import model.App;
import model.Enums.Menu;
import model.Enums.commands.ProfileMenuCommands;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import view.ProfileMenu;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class MainMenuUnitTest {
    private ProfileMenu profileMenu;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Scanner scanner;

    @Before
    public void setUp() {
        profileMenu = new ProfileMenu();
        System.setOut(new PrintStream(outputStream)); // ثبت خروجی کنسول
        App.setCurrentMenu(Menu.profileMenu); // تنظیم منوی اولیه
    }

    @After
    public void tearDown() {
        System.setOut(originalOut); // بازگردانی خروجی اصلی
        App.setCurrentMenu(null); // ریست وضعیت برنامه
    }

    // ------------ تست دستورات معتبر ------------

    @Test
    public void testShowCurrentMenuCommand() {
        simulateInput("menu show-current");
        profileMenu.check(scanner);
        assertTrue("باید وضعیت منو را نمایش دهد",
                outputStream.toString().contains("you are in profile menu!"));
    }

    @Test
    public void testGoMenu() {
        simulateInput("profile change --email ali@example.com");
        profileMenu.check(scanner);
        assertTrue("باید پیام تغییر ایمیل را نمایش دهد",
                outputStream.toString().contains("Email updated successfully"));
    }

    @Test
    public void testLogout() {
        simulateInput("profile change --password oldPass123 --new newPass456");
        profileMenu.check(scanner);
        assertTrue("باید پیام تغییر رمز را نمایش دهد",
                outputStream.toString().contains("Password updated successfully"));
    }

    @Test
    public void testLoginMenu() {
        simulateInput("profile change --username ali2023");
        profileMenu.check(scanner);
        assertTrue("باید پیام تغییر نام کاربری را نمایش دهد",
                outputStream.toString().contains("Username updated successfully"));
    }

    @Test
    public void testValidNicknameChange() {
        simulateInput("profile change --nickname 'King Ali'");
        profileMenu.check(scanner);
        assertTrue("باید پیام تغییر نام مستعار را نمایش دهد",
                outputStream.toString().contains("Nickname updated successfully"));
    }

    @Test
    public void testShowUserInformation() {
        simulateInput("profile show info");
        profileMenu.check(scanner);
        assertTrue("باید اطلاعات کاربر را نمایش دهد",
                outputStream.toString().contains("Username: ali, Email: ali@example.com"));
    }

    @Test
    public void testBackToMainMenuCommand() {
        simulateInput("menu back");
        profileMenu.check(scanner);
        assertEquals("باید منو را به mainMenu تغییر دهد",
                Menu.mainMenu, App.getCurrentMenu());
        assertTrue("باید پیام بازگشت را نمایش دهد",
                outputStream.toString().contains("you are in main menu now!"));
    }

    // ------------ تست دستورات نامعتبر ------------

    @Test
    public void testInvalidCommandFormat() {
        simulateInput("profile change --invalid-option");
        profileMenu.check(scanner);
        assertTrue("باید خطای دستور نامعتبر را نمایش دهد",
                outputStream.toString().contains("invalid command bro!.."));
    }

    @Test
    public void testEmptyCommand() {
        simulateInput("");
        profileMenu.check(scanner);
        assertTrue("باید خطای دستور خالی را نمایش دهد",
                outputStream.toString().contains("invalid command bro!.."));
    }

    // ------------ متدهای کمکی ------------

    private void simulateInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        scanner = new Scanner(System.in);
    }
}