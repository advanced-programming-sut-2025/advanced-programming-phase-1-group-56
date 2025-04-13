package model;
import java.util.ArrayList;

public class User {
    private String username;
    private String name;
    private String password;
    private String email;
    private String securityQuestion;
    private String answer;
    private Boolean gender;//True = male  false = female
    private Game currentGame;
    private final ArrayList<Game> games = new ArrayList<>();
    private int numOfGames;
    private int highScore;

    public User(String username, String name, String password, String email, String securityQuestion,
                String answer, Boolean gender) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.securityQuestion = securityQuestion;
        this.answer = answer;
        this.gender = gender;
        this.numOfGames = 0;
        this.highScore = 0;
        this.currentGame = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public int getNumOfGames() {
        return numOfGames;
    }

    public void setNumOfGames(int numOfGames) {
        this.numOfGames = numOfGames;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public ArrayList<Game> getGames() {
        return games;
    }
}
