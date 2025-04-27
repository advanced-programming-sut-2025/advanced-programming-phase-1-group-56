package controller.MenuController;

import com.google.gson.Gson;
import controller.CommandController;
import model.App;
import model.Enums.InfoRegexes;
import model.Enums.SecurityQuestion;
import model.Result;
import model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;

public class LoginMenuController extends CommandController {

    public static Result manageRegisterUser(Matcher matcher) {
        Random random = new Random();
        String username = matcher.group(1);
        String password = matcher.group(2);
        String rePassword = matcher.group(3);
        String nickName = matcher.group(4);
        String email = matcher.group(5);
        String gender = matcher.group(6);
        if (returnUser(username) == null) {
            return new Result(false, "WTF!, no user found with such name! but if you can use " + makeRandomUserName(username) + " if you want!");
        } else if (!InfoRegexes.usersName.isValid(username)) {
            return new Result(false, "do yo wanna play game with me?(username is incorrect)");
        } else if (!InfoRegexes.email.isValid(email)) {
            return new Result(false, "please enter valid email!");
        } else if (!InfoRegexes.password.isValid(password)) {
            return new Result(false, "please enter correct password! but you can use " + manageMakeRandomPassword(random.nextInt(10, 20)));
        } else if (password.length() < 8) {
            return new Result(false, "password is too short!");
        } else if (!password.equals(rePassword)) {
            return new Result(false, "passwords do not match!");
        }
        String result = showSecurityQuestion();
        return new Result(true, result + "choose between this!");

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

    public static Result answer(Matcher matcher, Matcher matcher1) {
        String username = matcher1.group(1);
        String answer = matcher.group(1);
        if(!returnUser(username).getAnswer().equals(answer)) {
            return new Result(false, "wrong answer!!!");
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("please enter new password : ").append("\nyou can use this : ");
        stringBuilder.append(manageMakeRandomPassword(10));
        return new Result(true, stringBuilder.toString());
    }
    public static Result manageAnswerForgotPassword(String input,Matcher matcher) {
        if(!InfoRegexes.password.isValid(input)) {
            return new Result(false, "sorry! is not correct!(babol)");
        }
        String username = matcher.group(1);
        for(User user : App.getUsers()) {
            if(user.getUsername().equals(username)) {
                user.setPassword(input);
            }
        }
        return new Result(true, username + "your password have been changed!");
    }


    public static String manageMakeRandomPassword(int length) {
        if (length < 8) {
            throw new IllegalArgumentException("طول رمز عبور باید حداقل ۸ کاراکتر باشد.");
        }
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String symbols = "?<>',;:\\/|[]{}+=)(*&^%$#@!";
        String allChars = upper + lower + digits + symbols;

        SecureRandom random = new SecureRandom();
        List<Character> passwordChars = new ArrayList<>();
        passwordChars.add(upper.charAt(random.nextInt(upper.length())));
        passwordChars.add(lower.charAt(random.nextInt(lower.length())));
        passwordChars.add(digits.charAt(random.nextInt(digits.length())));
        passwordChars.add(symbols.charAt(random.nextInt(symbols.length())));
        for (int i = 4; i < length; i++) {
            passwordChars.add(allChars.charAt(random.nextInt(allChars.length())));
        }
        Collections.shuffle(passwordChars);

        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }

    public static Result manageLoginUser(Matcher matcher, boolean stayLoggedIn) {
        String username = matcher.group(1);
        String password = matcher.group(2);
        if(returnUser(username) == null) {
            return new Result(false,"there is no user found with such name!");
        } else if(!returnUser(username).getPassword().equals(password)) {
            return new Result(false,"password is incorrect!");
        }
        if(stayLoggedIn) {
            //TODO
        } else {
            App.setCurrentUser(returnUser(username));
        }
        return new Result(true, "you have successfully logged in!");

    }

    public static Result manageForgotPassword(Matcher matcher) {
        String username = matcher.group(1);
        if(returnUser(username) == null) {
            return new Result(false,"there is no user found with such name!");
        }
        SecurityQuestion[] questions = SecurityQuestion.values();
        return new  Result(true, "answer the question!"+"\n"+questions[returnUser(username).getSecurityQuestion()]);
    }

    private static User returnUser(String username) {
        for (User user : App.getUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public static Result peakSecurityQuestion(Matcher matcher1, Matcher matcher) {
        Random random = new Random();
        String username = matcher.group(1).trim();
        String password = matcher.group(2).trim();
        String rePassword = matcher.group(3).trim();
        String nickName = matcher.group(4).trim();
        String email = matcher.group(5).trim();
        String gender = matcher.group(6).trim();
        boolean gender1;
        if(gender.equals("male")){
            gender1 = true;
        }else {
            gender1 = false;
        }
        int QuestionNumber = Integer.parseInt(matcher1.group(1).trim());
        String answer = matcher1.group(2).trim();
        String answerConfirmation = matcher1.group(3).trim();

        if (QuestionNumber > 10 || QuestionNumber < 0) {
            return new Result(false, "choose beetWeen 1 to 10!");
        } else if (!answer.equals(answerConfirmation)) {
            return new Result(false, "wrong answer confirmation!");
        }
        User user = new User(username,nickName,password,email,QuestionNumber,answer,gender1);
        //TODO
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter("Users.json")) {
            gson.toJson(user, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(true, "you were Registered!");

    }

    private static String makeRandomUserName(String username) {
        Random random = new Random();
        String newUserName = username;

        while (returnUser(newUserName) == null) {

            boolean randomBoolean = random.nextBoolean();
            if (randomBoolean) {
                int randomNumber = random.nextInt(1000);  // عددی بین 0 تا 999
                newUserName = username + randomNumber;
            } else {
                newUserName = username + "-";
            }
        }
        return newUserName;
    }
}
