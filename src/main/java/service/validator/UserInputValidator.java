package service.validator;

public class UserInputValidator {
    //Java email validation permitted by RFC 5322
    private static final String REGEXP_EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*" +
            "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

    private static final int PASSWORD_MIN_LENGTH = 5;
    private static final int PASSWORD_MAX_LENGTH = 30;

    public boolean isValidEmail(Object obj) {
        if (obj == null) {
            return false;
        }
        if (String.class != obj.getClass()) {
            return false;
        }
        String login = (String) obj;
        return login.matches(REGEXP_EMAIL);
    }

    public boolean isValidPassword(Object obj) {
        if (obj == null) {
            return false;
        }
        if (String.class != obj.getClass()) {
            return false;
        }
        String password = (String) obj;
        return password.length() >= PASSWORD_MIN_LENGTH && password.length() <= PASSWORD_MAX_LENGTH;
    }
}
