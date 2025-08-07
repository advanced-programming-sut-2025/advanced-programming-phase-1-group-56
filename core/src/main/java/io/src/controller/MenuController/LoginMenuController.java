
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
    private static final UserBuilder builder = new UserBuilder();
    private static String Inner_username;

    // init :

    public LoginMenuController(StardewValley game) {
        this.game = game;
    }

    public void init() {
        menu = new LoginMenu();
        App.init();
    }

    public void run() {
        game.setScreen(menu);
        initialize();
    }

    // UI

    private void initialize() {

        // login :

        {
            menu.getLoginButton().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Result result = manageLoginUser(
                        menu.getUsernameField().getText(),
                        menu.getPasswordField().getText(),
                        menu.getStayLoggedInCheckBox().isChecked()
                    );
                    menu.showWarningLabel(result.getMessage());
                    if (result.isSuccess())
                        changeMenuToMainMenu();
                }
            });

            menu.getRegisterButton().addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    menu.getLoginTable().setVisible(false);
                    menu.getRegisterTable().setVisible(true);
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
        }

        // forget password.

        {
            menu.getForgetPassButton().addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    Result result = manageForgotPassword(menu.getUsernameField().getText());
                    if (!result.isSuccess()) {
                        menu.showWarningLabel(result.getMessage());
                        return;
                    }
                    Inner_username = menu.getUsernameField().getText();
                    menu.showQuestionLabel(result.getMessage());
                    menu.getForgetTable().setVisible(true);
                    menu.getLoginTable().setVisible(false);
                }
            });

            menu.getBackButton().addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    backFromForgetPass();
                }
            });

            menu.getOkButton().addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    if (!menu.getPassField().isVisible()) {
                        if (Inner_username.isEmpty())
                            return;
                        Result result = answer(Inner_username, menu.getAnswerField().getText());
                        if (!result.isSuccess()) {
                            menu.showWarningLabel(result.getMessage());
                            return;
                        }
                        menu.getPassField().setVisible(true);
                        menu.getPassLabel().setVisible(true);
                    } else {
                        Result result = manageAnswerForgotPassword(menu.getPassField().getText(), Inner_username);
                        menu.showWarningLabel(result.getMessage());
                        if (!result.isSuccess())
                            return;
                        backFromForgetPass();
                    }
                }
            });
        }

        // Register :

        {
            menu.getRegisterButton2().addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    Result result = manageRegisterUser(
                        menu.getUsernameField2().getText(),
                        menu.getPasswordField2().getText(),
                        menu.getRePassField().getText(),
                        menu.getNicknameField().getText(),
                        menu.getEmailField().getText(),
                        menu.getmaleCheckBox().isChecked()
                    );
                    if (!result.isSuccess()) {
                        menu.showWarningLabel(result.getMessage());
                        return;
                    }
                    menu.getRegisterTable().setVisible(false);
                    menu.getSecurityTable().setVisible(true);
                }
            });

            menu.getExitButton2().addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.exit();
                }
            });

            menu.getLoginButton2().addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    menu.getRegisterTable().setVisible(false);
                    menu.getLoginTable().setVisible(true);
                }
            });
        }

        // security question :

        {
            for (CheckBox c : menu.getSecurityQuestions()) {
                c.addListener(new ClickListener() {
                    public void clicked(InputEvent event, float x, float y) {
                        for (CheckBox cb : menu.getSecurityQuestions())
                            cb.setChecked(false);
                        c.setChecked(true);
                    }
                });
            }

            menu.getCancelButton().addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    menu.getSecurityTable().setVisible(false);
                    menu.getRegisterTable().setVisible(true);
                    menu.getSecurityField().setText("");
                    for (CheckBox cb : menu.getSecurityQuestions())
                        cb.setChecked(false);
                    menu.getSecurityQuestions().getFirst().setChecked(true);
                }
            });

            menu.getSignUpButton().addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    int questionID = 0;
                    for (int i = 0; i < menu.getSecurityQuestions().size(); i++) {
                        if (menu.getSecurityQuestions().get(i).isChecked())
                            questionID = i;
                    }
                    Result result = peakSecurityQuestion(
                        questionID,
                        menu.getSecurityField().getText()
                    );
                    if (!result.isSuccess()) {
                        menu.showWarningLabel(result.getMessage());
                        return;
                    }
                    changeMenuToMainMenu();
                }
            });
        }
    }

    private void changeMenuToMainMenu() {
        MainMenuController controller = new MainMenuController(game);
        controller.init();
        controller.run();
    }

    private void backFromForgetPass() {
        menu.getPassLabel().setVisible(false);
        menu.getPassField().setVisible(false);
        menu.getPassField().setText("");
        menu.getAnswerField().setText("");
        menu.getForgetTable().setVisible(false);
        menu.getLoginTable().setVisible(true);
        Inner_username = null;
    }

    // logic :

    public static Result manageRegisterUser(String username, String password, String rePassword, String nickname, String email, boolean gender) {
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
        builder.username(username).name(nickname).email(email).password(password).gender(gender);
        return new Result(true, "");
    }

    public static ArrayList<String> getSecurityQuestions() {
        ArrayList<String> securityQuestions = new ArrayList<>();
        SecurityQuestion[] questions = SecurityQuestion.values();
        for (SecurityQuestion question : questions) {
            securityQuestions.add(question.getQuestion());
        }
        return securityQuestions;
    }

    public static Result peakSecurityQuestion(int questionId, String answer) {
        if (answer.isEmpty())
            return new Result(false, "answer is empty!");
        User user = builder.salt(MakePasswordSHA_256.generateSalt()).securityQuestion(questionId).answer(answer).build();
        App.addUser(user);
        App.setCurrentUser(user);
        return new Result(true, "welcome baby!");
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

    public static Result manageForgotPassword(String username) {
        username = username.trim();
        if (username.isEmpty())
            return new Result(false, "username is empty!");
        User targetUser = returnUser(username);
        if (targetUser == null)
            return new Result(false, "there is no user found with such name!");
        return new Result(true, getSecurityQuestions().get(targetUser.getSecurityQuestion()));
    }

    public static Result answer(String username, String answer) {
        username = username.trim();
        answer = answer.trim();
        User targetUser = returnUser(username);
        if (targetUser == null)
            return new Result(false, "there is no user found with such name!");
        System.out.println(targetUser.getAnswer());
        if (!targetUser.getAnswer().equals(answer))
            return new Result(false, "wrong answer!!!");

        return new Result(true, "");
    }

    public static Result manageAnswerForgotPassword(String password, String username) {
        if (!InfoRegexes.password.isValid(password))
            return new Result(false, "sorry! is not correct!(babol)");
        username = username.trim();
        User user = returnUser(username);
        if (user == null)
            return new Result(false, "there is no user found with such name!");
        user.setPassword(MakePasswordSHA_256.hashPassword(password, user.getSalt()));
        App.saveUsers();
        return new Result(true, "your password have been changed!");
    }

    public static User returnUser(String username) {
        return App.getUserByUsername(username);
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
}
