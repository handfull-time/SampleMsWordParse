package com.utime.msword.objects;

import lombok.Data;

/**
 * Document number
 */
@Data
public class DocNumLevel {
    /** Document number level */
    int level;
    /** The sequence number of the document number level. */
	int count;

    /**
     * increment by 1.
     * @return
     */
    public int increment(){
        return ++count;
    }
}
