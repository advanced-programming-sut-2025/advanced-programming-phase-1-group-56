package io.src.controller.MenuController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.gson.Gson;
import io.src.StardewValley;
import io.src.controller.CommandController;
import io.src.model.App;
import io.src.model.Enums.InfoRegexes;
import io.src.model.Enums.SecurityQuestion;
import io.src.model.MakePasswordSHA_256;
import io.src.model.Result;
import io.src.model.User;
import io.src.view.LoginMenu;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class LoginMenuController extends CommandController {

    // fields :

    private final StardewValley game;
    private LoginMenu menu;

    // init :

    public LoginMenuController(StardewValley game) {
        this.game = game;
    }

    public void init() {
        menu = new LoginMenu();
    }

    public void run() {
        game.setScreen(menu);
        App.init();
        initialize();
    }

    // UI

    private void initialize() {

        // login :

        menu.getLoginButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Result result = manageLoginUser(
                    menu.getUsernameField().getText(),
                    menu.getPasswordField().getText(),
                    menu.getStayLoggedInCheckBox().isChecked()
                );
                menu.showWarningLabel(result.getMessage());
                if (result.isSuccess()) {
                    MainMenuController controller = new MainMenuController(game);
                    controller.init();
                    controller.run();
                }
            }
        });

        menu.getRegisterButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                menu.getLoginWindow().setVisible(false);
                menu.getRegisterWindow().setVisible(true);
            }
        });

        menu.getExitButton().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        menu.getmaleCheckBox().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                menu.getmaleCheckBox().setChecked(true);
                menu.getfemaleCheckBox().setChecked(false);
            }
        });

        menu.getfemaleCheckBox().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                menu.getmaleCheckBox().setChecked(false);
                menu.getfemaleCheckBox().setChecked(true);
            }
        });

        // forget password.


        // Register :

        menu.getRegisterButton2().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Result result = manageRegisterUser(
                    menu.getUsernameField2().getText(),
                    menu.getPasswordField2().getText(),
                    menu.getRePassField().getText(),
                    menu.getNicknameField().getText(),
                    menu.getEmailField().getText()
                );
                if (!result.isSuccess()) {
                    menu.showWarningLabel(result.getMessage());
//                    return;
                }
                menu.getRegisterWindow().setVisible(false);
                menu.getSecurityWindow().setVisible(true);
            }
        });

        menu.getExitButton2().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        menu.getLoginButton2().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                menu.getRegisterWindow().setVisible(false);
                menu.getLoginWindow().setVisible(true);
            }
        });

        // security question :

        for (CheckBox c : menu.getSecurityQuestions()) {
            c.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    for (CheckBox cb : menu.getSecurityQuestions())
                        cb.setChecked(false);
                    c.setChecked(true);
                }
            });
        }
    }

    // logic :

    public static Result manageRegisterUser(String username, String password, String rePassword, String nickname, String email) {
        Random random = new Random();
        username = username.trim();
        password = password.trim();
        rePassword = rePassword.trim();
        nickname = nickname.trim();
        email = email.trim();
        if (!InfoRegexes.usersName.isValid(username)) {
            return new Result(false, "username is incorrect! you can use :\"" + makeRandomUserName() + "\"");
        } else if (returnUser(username) != null) {
            return new Result(false, "we already have an account with that name");
        } else if (!InfoRegexes.password.isValid(password)) {
            return new Result(false, "please enter valid password!");
        } else if (password.length() < 8) {
            return new Result(false, "password is too short but  you can use " + manageMakeRandomPassword(random.nextInt(10, 12), password));
        } else if (!InfoRegexes.strongPassword.isValid(password)) {
            return new Result(false, "please only use special character and letter and number for password! but you can use " + manageMakeRandomPassword(random.nextInt(10, 12), password));
        } else if (!password.equals(rePassword)) {
            return new Result(false, "passwords do not match!");
        } else if (nickname.isEmpty()) {
            return new Result(false, "nickname is empty!");
        } else if (!InfoRegexes.email.isValid(email)) {
            return new Result(false, "please enter valid email!");
        }
        return new Result(true, "");
    }

    private static String showSecurityQuestion() {
        StringBuilder stringBuilder = new StringBuilder();
        SecurityQuestion[] questions = SecurityQuestion.values();
        for (int i = 0; i < questions.length; i++) {
            stringBuilder.append((i + 1))
                .append(". ")
                .append(questions[i].getQuestion())
                .append("\n");
        }
        return stringBuilder.toString();
    }

    public static ArrayList<String> getSecurityQuestions() {
        ArrayList<String> securityQuestions = new ArrayList<>();
        SecurityQuestion[] questions = SecurityQuestion.values();
        for (SecurityQuestion question : questions) {
            securityQuestions.add(question.getQuestion());
        }
        return securityQuestions;
    }

    public static Result answer(Matcher matcher, Matcher matcher1) {
        String username = matcher1.group(1);
        String answer = matcher.group(1).trim();
        if (!App.getCurrentUser().getAnswer().equals(answer)) {
            return new Result(false, "wrong answer!!!");
        }

        String stringBuilder = "please enter new password : " + "\nyou can use this : " +
            manageMakeRandomPassword(10, "somthing");
        return new Result(true, stringBuilder);
    }

    public static Result manageAnswerForgotPassword(String input, Matcher matcher) {
        if (!InfoRegexes.password.isValid(input)) {
            return new Result(false, "sorry! is not correct!(babol)");
        }
        String username = matcher.group(1).trim();
        for (User user : App.getUsers()) {
            if (user.getUsername().equals(username)) {
                user.setPassword(input);
            }
        }
        return new Result(true, username + "your password have been changed!");
    }

    public static String manageMakeRandomPassword(int length, String password) {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String symbols = "?<>',;:\\/|[]{}+=)(*&^%$#@!";
        String allChars = upper + lower + digits + symbols;

        SecureRandom random = new SecureRandom();
        List<Character> passwordChars = password.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        passwordChars.add(upper.charAt(random.nextInt(upper.length())));
        passwordChars.add(lower.charAt(random.nextInt(lower.length())));
        passwordChars.add(digits.charAt(random.nextInt(digits.length())));
        passwordChars.add(symbols.charAt(random.nextInt(symbols.length())));
        for (int i = password.length() + 4; i < length; i++) {
            passwordChars.add(allChars.charAt(random.nextInt(allChars.length())));
        }

        StringBuilder tmpPassword = new StringBuilder();
        for (char c : passwordChars) {
            tmpPassword.append(c);
        }

        return tmpPassword.toString();
    }

    public static Result manageLoginUser(String username, String password, boolean stayLoggedIn) {
        username = username.trim();
        password = password.trim();

        User user = returnUser(username);
        if (user == null) {
            return new Result(false, "there is no user found with such name!");
        }

        String hashedInputPassword = MakePasswordSHA_256.hashPassword(password, user.getSalt());

        if (!user.getPassword().equals(hashedInputPassword)) return new Result(false, "incorrect password");

        if (stayLoggedIn) {
            Gson gson = new Gson();
            try (Writer writer = new FileWriter("assets\\StayLoggedIn.json")) {
                gson.toJson(user, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        App.setCurrentUser(user);
        return new Result(true, "you have successfully logged in!");
    }

    public static Result manageForgotPassword(Matcher matcher) {
        String username = matcher.group(1).trim();
        if (returnUser(username) == null) {
            return new Result(false, "there is no user found with such name!");
        }
        SecurityQuestion[] questions = SecurityQuestion.values();
        return new Result(true, "answer the question!" + "\n" + questions[returnUser(username).getSecurityQuestion()]);
    }

    public static User returnUser(String username) {
        ArrayList<User> users = App.getUsers();
        if (users == null) return null;

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static Result peakSecurityQuestion(Matcher matcher1, Matcher matcher) {
        Random random = new Random();
        String username = matcher.group(1).trim().trim();
        String password = matcher.group(2);
        String rePassword = matcher.group(3).trim();
        String nickName = matcher.group(4).trim();
        String email = matcher.group(5).trim();
        String gender = matcher.group(6).trim();
        boolean gender1 = gender.equalsIgnoreCase("male");
        int QuestionNumber = Integer.parseInt(matcher1.group(1).trim());
        String answer = matcher1.group(2).trim();
        String answerConfirmation = matcher1.group(3).trim();

        if (QuestionNumber > 10 || QuestionNumber < 0) {
            return new Result(false, "choose beetWeen 1 to 10!");
        } else if (!answer.equals(answerConfirmation)) {
            return new Result(false, "wrong answer confirmation!");
        }
        String salt = MakePasswordSHA_256.generateSalt();
        String hashPassword = MakePasswordSHA_256.hashPassword(password, salt);

        User user = new User(username, nickName, hashPassword, salt, email, QuestionNumber, answer, gender1);
        App.addUser(user);
        return new Result(true, "welcome baby!");

    }

    private static String makeRandomUserName() {
        Random random = new Random();
        String newUserName = "username";
        while (returnUser(newUserName) != null) {
            boolean randomBoolean = random.nextBoolean();
            if (randomBoolean) {
                int randomNumber = random.nextInt(1000);
                newUserName = "username" + randomNumber;
            } else
                newUserName = "username" + "-";
        }
        return newUserName;
    } //
}
