package service;

import java.util.Base64;

public class PasswordEncoder {

    public String encode(String plainPassword) {
        return Base64.getEncoder().encodeToString(plainPassword.getBytes());
    }

    public boolean checkPassword (String plainPassword, String encPassword) {
        return encode(plainPassword).equals(encPassword);
    }
}
