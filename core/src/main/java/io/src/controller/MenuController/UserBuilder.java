package io.src.controller.MenuController;

import io.src.model.MakePasswordSHA_256;
import io.src.model.User;

import java.util.UUID;

public class UserBuilder {
    private String username;
    private String name;
    private String password;
    private String salt;
    private String email;
    private int securityQuestion;
    private String answer;
    private Boolean gender;//True = male  false = female

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder securityQuestion(int securityQuestion) {
        this.securityQuestion = securityQuestion;
        return this;
    }

    public UserBuilder answer(String answer) {
        this.answer = answer;
        return this;
    }

    public UserBuilder gender(Boolean gender) {
        this.gender = gender;
        return this;
    }

    public UserBuilder salt(String salt) {
        this.salt = salt;
        return this;
    }

    public void reset() {
        username = null;
        name = null;
        password = null;
        salt = null;
        email = null;
        securityQuestion = 0;
        answer = null;
    }

    public User build() {
        if (!username.isEmpty() && !name.isEmpty() && !password.isEmpty() && !salt.isEmpty() && !email.isEmpty() && !answer.isEmpty() && gender != null) {
            String hashPassword = MakePasswordSHA_256.hashPassword(password, salt);
            return new User(username, name, hashPassword, salt, email, securityQuestion, answer, gender);
        }
        return null;
    }
}
