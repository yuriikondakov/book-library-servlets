package service;

import org.junit.Test;
import service.PasswordEncoder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class PasswordEncoderTest {
    private static final PasswordEncoder PASSWORD_ENCODER = new PasswordEncoder();

    @Test
    public void encodeShouldReturnEncodedString() {
        String actual = PASSWORD_ENCODER.encode("userpass");
        assertThat(actual, is("dXNlcnBhc3M="));
    }

    @Test
    public void checkPasswordShouldReturnTrue() {
        boolean actual = PASSWORD_ENCODER.checkPassword("userpass","dXNlcnBhc3M=" );
        assertThat(actual, is(true));
    }

    @Test
    public void checkPasswordShouldReturnFalse() {
        boolean actual = PASSWORD_ENCODER.checkPassword("userPass","dXNlcnBhc3M=" );
        assertThat(actual, is(false));
    }
}