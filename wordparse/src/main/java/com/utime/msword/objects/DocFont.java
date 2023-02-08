package com.utime.msword.objects;

import lombok.Data;

/**
 * Document font
 */
@Data
public class DocFont {
    /** size */
    Double size;
    /** color */
    String color;
    /** bold */
    boolean bold;
    /** italic */
    boolean italic;
}
