package com.utime.msword.utils;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

/**
 * Roman format conversion format class
 * chatGPT help.
 */
class RomanNumeralFormat extends NumberFormat {

    private static final long serialVersionUID = -3991599096869729444L;

    static final String[] ROMAN_NUMERALS = {
        "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"
    };
    
    static final int[] DECIMAL_VALUES = {
        1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1
    };
    
    @Override
    public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
        int integer = (int) Math.round(number);
        return format(integer, toAppendTo, pos);
    }
    
    @Override
    public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
        int integer = (int) number;
        toAppendTo.setLength(0);
        
        int i = 0;
        while (integer > 0) {
            if (integer >= DECIMAL_VALUES[i]) {
                toAppendTo.append(ROMAN_NUMERALS[i]);
                integer -= DECIMAL_VALUES[i];
            } else {
                i++;
            }
        }
        
        return toAppendTo;
    }
    
    @Override
    public Number parse(String source, ParsePosition parsePosition) {
        return null;  // Not implemented
    }
}
