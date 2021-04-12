package utils;

import commons.KeyValue;
import jdk.jshell.JShell;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtils {
    private StringUtils() {
    }

    public static Charset encodingType = StandardCharsets.UTF_8;

    public static ArrayList<String> getMostProbableWord(String halfWord, String[] possibleWords) {
        try {
            ArrayList<KeyValue<String, Float>> outputList = new ArrayList<>();
            for (int i = 0; i < possibleWords.length; i++) {
                outputList.add(new KeyValue<String, Float>(possibleWords[i], getMatchScore(halfWord, possibleWords[i])));
            }
            outputList.sort(new Comparator<KeyValue<String, Float>>() {
                @Override
                public int compare(KeyValue<String, Float> stringFloatKeyValue, KeyValue<String, Float> t1) {
                    if (stringFloatKeyValue.value.equals(t1.value))
                        return 0;
                    else if (((Float) stringFloatKeyValue.value) > ((Float) t1.value))
                        return -1;
                    else
                        return 1;
                }
            });
            ArrayList<String> result = new ArrayList<>();
            for (KeyValue<String, Float> item:outputList)
                if(item.value != 0.0f)
                    result.add(item.key);
            return result;
        } catch (Exception ex) {
        }
        return null;
    }

    public static Float getMatchScore(String halfWord, String possibleWord) {
        if (halfWord.length() == 0 || possibleWord.length() == 0)
            return 0.0f;
        float score = 0.0f;
        for (int i = halfWord.length(); i > halfWord.length() / 1.5; i--) {
            if (possibleWord.indexOf(halfWord.substring(0, i)) > -1)
                score += ((float) possibleWord.length() / (float) halfWord.length() * i);
        }
        String smallerWord = (halfWord.length() < possibleWord.length()) ? halfWord : possibleWord;
        String largerWord = (halfWord.length() > possibleWord.length()) ? halfWord : possibleWord;
        int k = 0;
        for (k = 0; k < smallerWord.length(); k++) {
            if (smallerWord.charAt(k) != largerWord.charAt(k))
                break;
        }
        score += k;
        return score;
    }

    public static String generateSpaces(int numberOfSpaces){
        return generateCharacters(' ', numberOfSpaces);
    }

    public static String generateCharacters(char c, int numberOfCharacters){
        String result = "";
        for (int i=0;i<numberOfCharacters;i++)
            result += c;
        return result;
    }

    public static String generateRandomString(int length){
        StringBuilder result = new StringBuilder();
        for (int i=0;i<length;i++)
            result.append( "" + (char)((int)(Math.random()*26) + 65));
        return result.toString();
    }

    public static String prepareCodeToSave(String code) {
        return textToHex(code);
    }

    public static String prepareLoadedCode(String rawCode) {
        return hexToText(rawCode);
    }

    public static String textToHex(String text)
    {
        byte[] buf = null;
        buf = text.getBytes(encodingType);
        char[] HEX_CHARS = "0123456789abcdef".toCharArray();
        char[] chars = new char[2 * buf.length];
        for (int i = 0; i < buf.length; ++i)
        {
            chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
        }
        return new String(chars);
    }


    public static String hexToText(String hex)
    {
        int l = hex.length();
        byte[] data = new byte[l / 2];
        for (int i = 0; i < l; i += 2)
        {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        String st = new String(data, encodingType);
        return st;
    }

    public static String processIdeCode(String str) {
        // TODO: UPDATE METHOD
        return str.toUpperCase();
    }

    public static InputStream getAsStream(String string){
        return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
    }
}
