package net.miraclepvp.kitpvp.bukkit;

import org.bukkit.ChatColor;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Text {

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String IntegerToCompactInteger(int input, int digits, boolean nearest) {
        Double number = (double) input;

        String pattern = "###.";
        for (int i = 0; i < digits; i++)
            pattern += "#";
        DecimalFormat df2 = new DecimalFormat(pattern);

        if(nearest)
            df2.setRoundingMode(RoundingMode.HALF_UP);
        else
            df2.setRoundingMode(RoundingMode.DOWN);

        if (number >= 1000 && number < 1000000)
            return df2.format(number / 1000) + "k";
        else if (number >= 1000000)
                return df2.format(number / 1000000) + "m";
        else
            return "" + input;
    }

    public static String IntegerToRomanNumeral(int input) {
        if (input < 1 || input > 3999)
            return "Invalid Roman Number Value";
        String s = "";
        while (input >= 1000) {
            s += "M";
            input -= 1000;
        }
        while (input >= 900) {
            s += "CM";
            input -= 900;
        }
        while (input >= 500) {
            s += "D";
            input -= 500;
        }
        while (input >= 400) {
            s += "CD";
            input -= 400;
        }
        while (input >= 100) {
            s += "C";
            input -= 100;
        }
        while (input >= 90) {
            s += "XC";
            input -= 90;
        }
        while (input >= 50) {
            s += "L";
            input -= 50;
        }
        while (input >= 40) {
            s += "XL";
            input -= 40;
        }
        while (input >= 10) {
            s += "X";
            input -= 10;
        }
        while (input >= 9) {
            s += "IX";
            input -= 9;
        }
        while (input >= 5) {
            s += "V";
            input -= 5;
        }
        while (input >= 4) {
            s += "IV";
            input -= 4;
        }
        while (input >= 1) {
            s += "I";
            input -= 1;
        }
        return s;
    }

    public static Integer RomanNumeralToInteger(String value) {
        int result = 0;
        int[] decimal = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] roman = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for (int i = 0; i < decimal.length; i++) {
            while (value.indexOf(roman[i]) == 0) {
                result += decimal[i];
                value = value.substring(roman[i].length());
            }
        }
        return result;
    }
}