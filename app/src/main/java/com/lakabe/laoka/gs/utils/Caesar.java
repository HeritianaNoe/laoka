package com.lakabe.laoka.gs.utils;

public class Caesar {
    private static final int shift = 11;

    public static String encrypt(String str, String keyword)
    {
        String cipher_text = "";
        String key = generateKey(str, keyword);

        for (int i = 0; i < str.length(); i++){
            // converting in range 0-25
            int x = (str.charAt(i) + key.charAt(i)) % 65536;

            // convert into alphabets(ASCII)
            x += shift;

            cipher_text += (char)(x);
        }
        return cipher_text;
    }

    public static String decrypt(String cipher_text, String keyword)
    {
        String orig_text = "";
        String key = generateKey(cipher_text, keyword);

        for (int i = 0 ; i < cipher_text.length(); i++){
            // converting in range 0-25
            int x = (cipher_text.charAt(i) - key.charAt(i) + 65536) % 65536;

            // convert into alphabets(ASCII)
            x -= shift;

            orig_text += (char)(x);
        }
        return orig_text;
    }

    private static String generateKey(String str, String key)
    {
        int x = str.length();

        for (int i = 0; i < str.length(); i++){
            if (x == i)
                i = 0;

            if (key.length() == str.length())
                break;

            key += (key.charAt(i));
        }
        return key;
    }
}
