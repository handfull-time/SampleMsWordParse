package com.utime.msword.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.utime.msword.objects.DocElement;

/**
 * Ms word parser service
 */
public interface MsWordParse {

    /**
     * word parsing service
     * @param is
     * @return
     * @throws IOException
     */
    List<DocElement> Parser( InputStream is) throws IOException;
}
