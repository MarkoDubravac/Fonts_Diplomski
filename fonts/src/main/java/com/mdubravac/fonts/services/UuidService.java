package com.mdubravac.fonts.services;

import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UuidService {

    private static final int NUMBER_OF_CHARACTERS = 6;

    public static String generateUuid() {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(uuid);

        StringBuilder result = new StringBuilder();
        while (matcher.find() && result.length() < NUMBER_OF_CHARACTERS) {
            result.append(matcher.group());
        }

        return result.toString();
    }
}
