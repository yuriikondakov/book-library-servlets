package service.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class UserInputValidatorPasswordTest {
    private static final UserInputValidator userInputValidator = new UserInputValidator();
    private String password;
    private boolean expectedResult;

    public UserInputValidatorPasswordTest(String password, boolean expectedResult) {
        this.password = password;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection testPasswordParametrs() {
        return Arrays.asList(new Object[][]{
                {"", false},
                {"123", false},
                {"pass", false},
                {"pass1", true},
                {"pass13letters", true},
                {"suuuuuperLooooooooongPasswoooord", false}
        });
    }

    @Test
    public void isValidPasswordShouldValidPassword() {
        assertThat(userInputValidator.isValidPassword(password), is(expectedResult));
    }

}