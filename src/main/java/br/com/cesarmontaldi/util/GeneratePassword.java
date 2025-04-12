package br.com.cesarmontaldi.util;

import java.security.SecureRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GeneratePassword {
	
	public static String generateRandomPassword(int len) {
       
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ@!#$%&>?;:<~^abcdefghijklmnopqrstuvwxyz0123456789";
 
        SecureRandom random = new SecureRandom();
 
        return IntStream.range(0, len)
                .map(i -> random.nextInt(chars.length()))
                .mapToObj(randomIndex -> String.valueOf(chars.charAt(randomIndex)))
                .collect(Collectors.joining());
    }

}
