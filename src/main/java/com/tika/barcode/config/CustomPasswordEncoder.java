package com.tika.barcode.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Processes an {@link CustomPasswordEncoder} request.
 * @author Raghu
 *
 */
@Component
public class CustomPasswordEncoder implements PasswordEncoder{

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public String encode(CharSequence rawPassword) {
        return bCryptPasswordEncoder.encode(rawPassword);
    }

    
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }

    
}
