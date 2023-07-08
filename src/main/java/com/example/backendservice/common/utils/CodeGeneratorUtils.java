package com.example.backendservice.common.utils;

import java.sql.Timestamp;
import java.util.Random;

public class CodeGeneratorUtils {
    public static int CODE_SIZE = 6;
    public static String invoke(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 90; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i >= 48 && i <= 57) || (i >= 65 && i <= 90))
                .limit(CODE_SIZE)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static Timestamp randomTimeStamp() {
        long offset = Timestamp.valueOf("2022-09-09 00:00:00").getTime();
        long end = Timestamp.valueOf("2023-06-06 00:00:00").getTime();
        long diff = end - offset + 1;
        return new Timestamp(offset + (long)(Math.random() * diff));
    }

}
