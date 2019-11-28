package service.validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class UserInputValidatorEmailTest {
    private static final UserInputValidator userInputValidator = new UserInputValidator();
    private String email;
    private boolean expectedResult;

    public UserInputValidatorEmailTest(String email, boolean expectedResult) {
        this.email = email;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection testEmailParametrs() {
        return Arrays.asList(new Object[][]{
                {"", false},
                {".@.", false},
                {"user@gmail", false},
                {"@gmail.com", false},
                {"user@gmail.123", false},
                {"user@dot.com", true},
                {"yurii.kondakov@gmail.com", true},
                {"yurii.kondakov@gmail.com.ua", true}
        });
    }

    @Test
    public void isValidEmailShouldValidEmails() {
        assertThat(userInputValidator.isValidEmail(email), is(expectedResult));
    }

}