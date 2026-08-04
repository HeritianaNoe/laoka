package com.lakabe.laoka.gs.utils;

public class WordUtils {
    public static String  Capitalize(String text){
        if(text == null || text.isEmpty())
            return new String();
        return new String(text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase());
    }

    public static String readMore(String line) {
        if(line.length() < 29) {
            String[] strings = line.split(" ");
            StringBuilder b = new StringBuilder();
            for(String s: strings){
                b.append(s.trim());
                b.append(" ");
            }

            return b.toString();
        }
        else {
            String[] strings = line.split(" ");
            StringBuilder b = new StringBuilder();
            for(String s: strings){
                if(b.toString().length() < 29){
                    b.append(s.trim());
                    b.append(" ");
                }
                else{
                    break;
                }
            }

            return b.append("...").toString();
        }
    }
}
