package io.src.test;

import io.src.controller.MenuController.ProfileMenuController;
import io.src.model.App;
import io.src.model.Enums.Menu;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.src.view.ProfileMenu;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;


public class ProfileMenuUnitTest {
    private ProfileMenu profileMenu;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @Before
    public void setUp() {
        profileMenu = new ProfileMenu(new ProfileMenuController());
        System.setOut(new PrintStream(outputStream));
        App.setCurrentMenu(Menu.profileMenu);
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }



    @Test
    public void testShowCurrentMenu() {
        simulateInput("menu show-current");
        Scanner scanner = new Scanner(System.in);
        profileMenu.check(scanner, scanner.nextLine() );
        assertTrue(outputStream.toString().contains("you are in profile menu!"));
    }

    @Test
    public void testChangeEmailValid() {
        simulateInput("profile change --email new@example.com");
        Scanner scanner = new Scanner(System.in);
        profileMenu.check(scanner, scanner.nextLine() );
        assertTrue(outputStream.toString().contains("Email changed successfully"));
    }

    @Test
    public void testChangePasswordValid() {
        simulateInput("profile change --password oldPass123 --new newPass456");
        Scanner scanner = new Scanner(System.in);
        profileMenu.check(scanner, scanner.nextLine() );
        assertTrue(outputStream.toString().contains("Password changed successfully"));
    }

    @Test
    public void testChangeUsernameValid() {
        simulateInput("profile change --username newAli");
        Scanner scanner = new Scanner(System.in);
        profileMenu.check(scanner, scanner.nextLine() );
        assertTrue(outputStream.toString().contains("Username changed successfully"));
    }

    @Test
    public void testChangeNicknameValid() {
        simulateInput("profile change --nickname newNick");
        Scanner scanner = new Scanner(System.in);
        profileMenu.check(scanner, scanner.nextLine() );
        assertTrue(outputStream.toString().contains("Nickname changed successfully"));
    }

    @Test
    public void testShowUserInfo() {
        simulateInput("profile show info");
        Scanner scanner = new Scanner(System.in);
        profileMenu.check(scanner, scanner.nextLine() );
        assertTrue(outputStream.toString().contains("Username: ali"));
    }

    @Test
    public void testBackToMainMenu() {
        simulateInput("menu back");
        Scanner scanner = new Scanner(System.in);
        profileMenu.check(scanner, scanner.nextLine() );
        assertEquals(Menu.mainMenu, App.getCurrentMenu());
        assertTrue(outputStream.toString().contains("you are in main menu now!"));
    }

    @Test
    public void testInvalidCommand() {
        simulateInput("invalid_command --wrong");
        Scanner scanner = new Scanner(System.in);
        profileMenu.check(scanner, scanner.nextLine() );
        assertTrue(outputStream.toString().contains("invalid command bro!.."));
    }


    private void simulateInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }
}
