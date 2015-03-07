/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.ydp.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @version 1.0, 07-Mar-2015
 * @author priyanka
 */
public class IDGenerator {

    private static final char                ALPHA_NUMERIC_CHARACTERS[]         = "0123456789BCDFGHJKLMNPQRSTVWXYZbcdfghjklmnpqrstvwxyz".toCharArray();
    private static final char                ALPHA_NUMERIC_CHARACTERS_NO_CAPS[] = "0123456789bcdfghjklmnpqrstvwxyz".toCharArray();
    private static final char                NUMBERIC_CHARACTERS[]              = "0123456789".toCharArray();

    private static final ThreadLocal<Random> randomVal                          = new ThreadLocal<Random>() {
                                                                                    protected Random initialValue() {
                                                                                        return new SecureRandom();
                                                                                    }
                                                                                };

    public static String generate() {
        return randomStrategy(10);
    }

    public static String generateWithNoCaps() {
        return randomStrategy(10, ALPHA_NUMERIC_CHARACTERS_NO_CAPS);
    }

    public static String generateNumericCode(int size) {
        return randomStrategy(size, NUMBERIC_CHARACTERS);
    }

    public static String generate(int keyLength) {
        return randomStrategy(keyLength);
    }

    private static String randomStrategy(int length) {
        char retVal[] = new char[length];
        int rand;
        for (int i = 0; i < length; i++) {
            rand = randomVal.get().nextInt(ALPHA_NUMERIC_CHARACTERS.length);
            retVal[i] = ALPHA_NUMERIC_CHARACTERS[rand];
        }
        return new String(retVal);
    }

    private static String randomStrategy(int length, char[] allowedCharArray) {
        if (allowedCharArray == null) {
            return null;
        }
        char retVal[] = new char[length];
        int rand;
        for (int i = 0; i < length; i++) {
            rand = randomVal.get().nextInt(allowedCharArray.length);
            retVal[i] = allowedCharArray[rand];
        }
        return new String(retVal);
    }

    public static String genratePassword() {
        return randomStrategy(6);
    }
    
    public static void main(String[] args) {
        System.out.println(generateUniqueId(5));
        System.out.println(generateUniqueId(6));
        System.out.println(generateUniqueId(7));
    }

    public static String generateUniqueId(int size) {
        String uniqueId = null;
        StringBuilder builder = new StringBuilder(Long.toHexString(System.currentTimeMillis()));
        builder.append(IDGenerator.generate(size));
        uniqueId = builder.toString();
        
        return uniqueId;
    }

}
