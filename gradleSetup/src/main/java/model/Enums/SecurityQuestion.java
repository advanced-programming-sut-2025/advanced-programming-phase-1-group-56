package model.Enums;

public enum SecurityQuestion {
    QUESTION_1("What is your favorite color?"),
    QUESTION_2("What is your pet's name?"),
    QUESTION_3("What is your favorite food?"),
    QUESTION_4("What is your birth month?"),
    QUESTION_5("What is your favorite number?"),
    QUESTION_6("What is your favorite sport?"),
    QUESTION_7("What is your father's first name?"),
    QUESTION_8("What is your favorite movie?"),
    QUESTION_9("What is your best friend's name?"),
    QUESTION_10("What is your favorite hobby?");

    private final String question;

    SecurityQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public static SecurityQuestion getByNumber(int number) {
        if (number < 1 || number > values().length) {
            throw new IllegalArgumentException("Invalid question number: " + number);
        }
        return values()[number - 1];
    }
}
