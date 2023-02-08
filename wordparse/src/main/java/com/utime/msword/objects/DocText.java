package com.utime.msword.objects;

import com.utime.msword.define.EDocType;

import lombok.Data;

@Data
public class DocText extends AbsDocObject{
    private static final long serialVersionUID = 5699437193138873181L;
    
    /** font */
    DocFont font;
    
    /** text data */
    String text = "";
    
    public DocText() {
        super(EDocType.Text);
    }

    @Override
    public String toString() {
        return "DocText [" + (font != null ? "font=" + font + ", " : "") + (text != null ? "\ttext=" + text : "") + "]";
    }
    
}
